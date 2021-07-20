/*
 *  Copyright (c) 2021, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.event.output.adapter.asb;

import com.azure.core.amqp.AmqpRetryMode;
import com.azure.core.amqp.AmqpRetryOptions;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.event.output.adapter.core.EventAdapterUtil;
import org.wso2.carbon.event.output.adapter.core.OutputEventAdapter;
import org.wso2.carbon.event.output.adapter.core.OutputEventAdapterConfiguration;
import org.wso2.carbon.event.output.adapter.core.exception.ConnectionUnavailableException;
import org.wso2.carbon.event.output.adapter.core.exception.OutputEventAdapterException;
import org.wso2.carbon.event.output.adapter.core.exception.TestConnectionNotSupportedException;
import org.wso2.carbon.event.output.adapter.asb.internal.util.ASBEventAdapterConstants;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ASBEventAdapter implements OutputEventAdapter {
    private static final Log log = LogFactory.getLog(ASBEventAdapter.class);
    private OutputEventAdapterConfiguration eventAdapterConfiguration;
    private Map<String, String> globalProperties;
    private static ExecutorService executorService;
    private int tenantId;
    ServiceBusSenderClient senderClient;
    String connectionString = "";
    String topicName = "";


    public ASBEventAdapter(OutputEventAdapterConfiguration eventAdapterConfiguration,
                           Map<String, String> globalProperties) {
        this.eventAdapterConfiguration = eventAdapterConfiguration;
        this.globalProperties = globalProperties;
    }

    @Override
    public void init() throws OutputEventAdapterException {
        tenantId = PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantId();

        // ExecutorService will be assigned  if it is null
        if (executorService == null) {
            int minThread;
            int maxThread;
            long defaultKeepAliveTime;
            int jobQueSize;

            // If global properties are available those will be assigned else constant values will be assigned
            if (ASBEventAdapterConstants.ADAPTER_ASB_ALLOW_CONCURRENT_CONNECTIONS_NOT_ALLOWED.equals(
                    eventAdapterConfiguration.getStaticProperties().get(ASBEventAdapterConstants
                            .ADAPTER_ASB_ALLOW_CONCURRENT_CONNECTIONS)) ) {
                minThread = 1;
            } else if (globalProperties.get(ASBEventAdapterConstants.ADAPTER_MIN_THREAD_POOL_SIZE_NAME) != null) {
                minThread = Integer.parseInt(globalProperties.get(
                        ASBEventAdapterConstants.ADAPTER_MIN_THREAD_POOL_SIZE_NAME));
            } else {
                minThread = ASBEventAdapterConstants.ADAPTER_MIN_THREAD_POOL_SIZE;
            }

            if (ASBEventAdapterConstants.ADAPTER_ASB_ALLOW_CONCURRENT_CONNECTIONS_NOT_ALLOWED.equals(
                    eventAdapterConfiguration.getStaticProperties().get(ASBEventAdapterConstants
                            .ADAPTER_ASB_ALLOW_CONCURRENT_CONNECTIONS))) {
                maxThread = 1;
            } else if (globalProperties.get(ASBEventAdapterConstants.ADAPTER_MAX_THREAD_POOL_SIZE_NAME) != null) {
                maxThread = Integer.parseInt(globalProperties.get(
                        ASBEventAdapterConstants.ADAPTER_MAX_THREAD_POOL_SIZE_NAME));
            } else {
                maxThread = ASBEventAdapterConstants.ADAPTER_MAX_THREAD_POOL_SIZE;
            }

            if (globalProperties.get(ASBEventAdapterConstants.ADAPTER_KEEP_ALIVE_TIME_NAME) != null) {
                defaultKeepAliveTime = Integer.parseInt(globalProperties.get(
                        ASBEventAdapterConstants.ADAPTER_KEEP_ALIVE_TIME_NAME));
            } else {
                defaultKeepAliveTime = ASBEventAdapterConstants.DEFAULT_KEEP_ALIVE_TIME_IN_MILLIS;
            }

            if (globalProperties.get(ASBEventAdapterConstants.ADAPTER_EXECUTOR_JOB_QUEUE_SIZE_NAME) != null) {
                jobQueSize = Integer.parseInt(globalProperties.get(
                        ASBEventAdapterConstants.ADAPTER_EXECUTOR_JOB_QUEUE_SIZE_NAME));
            } else {
                jobQueSize = ASBEventAdapterConstants.ADAPTER_EXECUTOR_JOB_QUEUE_SIZE;
            }

            executorService = new ThreadPoolExecutor(minThread, maxThread, defaultKeepAliveTime, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(jobQueSize));
        }

        // Create Retry Options for the Service Bus client
        AmqpRetryOptions amqpRetryOptions = new AmqpRetryOptions();
        amqpRetryOptions.setDelay(Duration.ofSeconds(1));
        amqpRetryOptions.setMaxRetries(120);
        amqpRetryOptions.setMode(AmqpRetryMode.FIXED);

        senderClient = new ServiceBusClientBuilder()
                .connectionString(connectionString)
                .retryOptions(amqpRetryOptions)
                .sender()
                .topicName(topicName)
                .buildClient();
    }

    @Override
    public void publish(Object message, Map<String, String> dynamicProperties) throws ConnectionUnavailableException {
        ServiceBusMessage serviceBusMessage = (ServiceBusMessage) message;
        try {
            executorService.submit(new ASBSender(serviceBusMessage, topicName));
        } catch (RejectedExecutionException e) {
            EventAdapterUtil.logAndDrop(eventAdapterConfiguration.getName(), message, "Job queue is full", e,
                    log, tenantId);
        }
    }

    @Override
    public void testConnect() throws TestConnectionNotSupportedException, ConnectionUnavailableException {

    }

    @Override
    public void connect() throws ConnectionUnavailableException {


    }

    @Override
    public void disconnect() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean isPolled() {
        return false;
    }

    public class ASBSender implements Runnable {
        private ServiceBusMessage asbMessage;
        private String topicName;

        ASBSender(ServiceBusMessage asbMessage, String topicName) {
            this.asbMessage = asbMessage;
            this.topicName = topicName;
        }

        @Override
        public void run() {
            senderClient.sendMessage(asbMessage);
        }
    }
}

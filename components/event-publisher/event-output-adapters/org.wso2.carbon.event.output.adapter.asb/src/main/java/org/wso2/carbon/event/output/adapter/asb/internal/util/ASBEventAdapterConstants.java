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
package org.wso2.carbon.event.output.adapter.asb.internal.util;

public class ASBEventAdapterConstants {
    public static final String ADAPTER_TYPE_ASB = "asb";
    public static final String JNDI_INITIAL_CONTEXT_FACTORY_CLASS = "java.naming.factory.initial";
    public static final String JNDI_INITIAL_CONTEXT_FACTORY_CLASS_HINT = "java.naming.factory.initial.hint";
    public static final String JAVA_NAMING_PROVIDER_URL = "java.naming.provider.url";
    public static final String JAVA_NAMING_PROVIDER_URL_HINT = "java.naming.provider.url.hint";
    public static final String ADAPTER_ASB_USERNAME = "transport.asb.UserName";
    public static final String ADAPTER_ASB_PASSWORD = "transport.asb.Password";
    public static final String ADAPTER_ASB_CONNECTION_FACTORY_JNDINAME = "transport.asb.ConnectionFactoryJNDIName";
    public static final String ADAPTER_ASB_CONNECTION_FACTORY_JNDINAME_HINT =
            "transport.asb.ConnectionFactoryJNDIName.hint";
    public static final String ADAPTER_ASB_DESTINATION_TYPE = "transport.asb.DestinationType";
    public static final String ADAPTER_ASB_DESTINATION_TYPE_HINT = "transport.asb.DestinationType.hint";
    public static final String ADAPTER_ASB_DESTINATION = "transport.asb.Destination";
    public static final String ADAPTER_ASB_ALLOW_CONCURRENT_CONNECTIONS = "transport.asb.ConcurrentPublishers";
    public static final String ADAPTER_ASB_ALLOW_CONCURRENT_CONNECTIONS_HINT = "transport.asb.ConcurrentPublishers.hint";
    public static final String ADAPTER_ASB_ALLOW_CONCURRENT_CONNECTIONS_ALLOWED = "allow";
    public static final String ADAPTER_ASB_ALLOW_CONCURRENT_CONNECTIONS_NOT_ALLOWED = "disallow";
    public static final String ADAPTER_ASB_HEADER = "transport.asb.Header";
    public static final String ADAPTER_ASB_HEADER_HINT = "transport.asb.Header.Hint";
    public static final long DEFAULT_KEEP_ALIVE_TIME_IN_MILLIS = 20000;
    public static final String ADAPTER_MIN_THREAD_POOL_SIZE_NAME = "minThread";
    public static final String ADAPTER_MAX_THREAD_POOL_SIZE_NAME = "maxThread";
    public static final String ADAPTER_KEEP_ALIVE_TIME_NAME = "keepAliveTimeInMillis";
    public static final String ADAPTER_EXECUTOR_JOB_QUEUE_SIZE_NAME = "jobQueueSize";
    public static final int ADAPTER_MIN_THREAD_POOL_SIZE = 8;
    public static final int ADAPTER_MAX_THREAD_POOL_SIZE = 100;
    public static final int ADAPTER_EXECUTOR_JOB_QUEUE_SIZE = 2000;
    public static final String HEADER_SEPARATOR = ",";
    public static final String ENTRY_SEPARATOR = ":";
    public static final String PROPERTY_SEPARATOR = ",";
    public static final String ADAPTER_PROPERTIES = "asb.properties";
    public static final String ADAPTER_SECURED_PROPERTIES = "asb.secured.properties";
    public static final String ADAPTER_PROPERTIES_HINT = "asb.properties.hint";
    public static final String ADAPTER_SECURED_PROPERTIES_HINT = "asb.secured.properties.hint";

}

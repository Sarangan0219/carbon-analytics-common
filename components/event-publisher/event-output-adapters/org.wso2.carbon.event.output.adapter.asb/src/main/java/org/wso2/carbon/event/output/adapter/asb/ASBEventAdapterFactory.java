package org.wso2.carbon.event.output.adapter.asb;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.event.output.adapter.asb.internal.util.ASBEventAdapterConstants;
import org.wso2.carbon.event.output.adapter.core.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * The asb event adapter factory class to create a asb output adapter
 */
public class ASBEventAdapterFactory extends OutputEventAdapterFactory {

    private static final Log log = LogFactory.getLog(ASBEventAdapterFactory.class);

    @Override
    public String getType() {
        return ASBEventAdapterConstants.ADAPTER_TYPE_ASB;
    }

    @Override
    public List<String> getSupportedMessageFormats() {
        List<String> supportedMessageFormats = new ArrayList<String>();
        supportedMessageFormats.add(MessageType.TEXT);
        supportedMessageFormats.add(MessageType.MAP);
        supportedMessageFormats.add(MessageType.XML);
        supportedMessageFormats.add(MessageType.JSON);
        return supportedMessageFormats;
    }

    @Override
    public List<Property> getStaticPropertyList() {
        return null;
    }

    @Override
    public List<Property> getDynamicPropertyList() {
        return null;
    }

    @Override
    public String getUsageTips() {
        return null;
    }

    @Override
    public OutputEventAdapter createEventAdapter(OutputEventAdapterConfiguration eventAdapterConfiguration,
                                                 Map<String, String> globalProperties) {
        return new ASBEventAdapter(eventAdapterConfiguration, globalProperties);
    }
}

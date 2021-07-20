package org.wso2.carbon.event.output.adapter.asb;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.event.output.adapter.asb.internal.util.ASBEventAdapterConstants;
import org.wso2.carbon.event.output.adapter.core.*;


import java.util.*;


/**
 * The asb event adapter factory class to create a asb output adapter
 */
public class ASBEventAdapterFactory extends OutputEventAdapterFactory {

    private ResourceBundle resourceBundle = ResourceBundle.getBundle("org.wso2.carbon.event.output.adapter.asb.i18n.Resources", Locale.getDefault());

    @Override
    public String getType() {
        return ASBEventAdapterConstants.ADAPTER_TYPE_AMQP;
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
        List<Property> propertyList = new ArrayList<Property>();

        Property connectionStringProperty = new Property(ASBEventAdapterConstants.ADAPTER_ASB_CONNECTION_STRING);
        connectionStringProperty.setDisplayName(
                resourceBundle.getString(ASBEventAdapterConstants.ADAPTER_ASB_CONNECTION_STRING));
        propertyList.add(connectionStringProperty);

        Property destinationTypeProperty = new Property(ASBEventAdapterConstants.ADAPTER_ASB_DESTINATION_TYPE);
        destinationTypeProperty.setRequired(true);
        destinationTypeProperty.setDisplayName(
                resourceBundle.getString(ASBEventAdapterConstants.ADAPTER_ASB_DESTINATION_TYPE));
        destinationTypeProperty.setOptions(new String[]{"queue", "topic"});
        destinationTypeProperty.setDefaultValue("topic");
        propertyList.add(destinationTypeProperty);

        Property topicProperty = new Property(ASBEventAdapterConstants.ADAPTER_ASB_DESTINATION);
        topicProperty.setDisplayName(
                resourceBundle.getString(ASBEventAdapterConstants.ADAPTER_ASB_DESTINATION));
        topicProperty.setRequired(true);
        propertyList.add(topicProperty);

        return propertyList;
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

package org.wso2.carbon.event.output.adapter.asb.internal.ds;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.wso2.carbon.event.output.adapter.asb.ASBEventAdapterFactory;
import org.wso2.carbon.event.output.adapter.core.OutputEventAdapterFactory;

@Component(
        name = "output.asb.AdapterService.component",
        immediate = true)
public class ESBEventAdapterServiceDS {

    private static final Log log = LogFactory.getLog(ESBEventAdapterServiceDS.class);

    @Activate
    protected void activate(ComponentContext context) {

        try {
            ASBEventAdapterFactory asbEventAdaptorFactory = new ASBEventAdapterFactory();
            context.getBundleContext().registerService(OutputEventAdapterFactory.class.getName(),
                    asbEventAdaptorFactory, null);
            if (log.isDebugEnabled()) {
                log.debug("Successfully deployed the output ASB event adaptor service");
            }
        } catch (Throwable e) {
            log.error("Can not create the output ASB event adaptor service: " + e.getMessage(), e);
        }
    }
}

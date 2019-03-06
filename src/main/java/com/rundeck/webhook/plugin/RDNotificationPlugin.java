package com.rundeck.webhook.plugin;

import okhttp3.*;
import java.io.IOException;
import java.util.Map;
import com.dtolabs.rundeck.core.plugins.Plugin;
import com.dtolabs.rundeck.plugins.descriptions.PluginDescription;
import com.dtolabs.rundeck.plugins.descriptions.PluginProperty;
import com.dtolabs.rundeck.plugins.descriptions.SelectValues;
import com.dtolabs.rundeck.plugins.notification.NotificationPlugin;

@Plugin(service="Notification",name="Advanced Webhook")
@PluginDescription(title="Advanced Webhook", description="A webhook plugin that allows the body, content type, http method, and url to be specified")
public class RDNotificationPlugin implements NotificationPlugin {

    @PluginProperty(name = "httpMethod",title = "Request http method",description = "Request http method", validatorClass = HttpValidator.class, required = true)
    @SelectValues(values={"GET","POST","PUT","PATCH","HEAD","OPTIONS","DELETE"})
    String httpMethod;
    @PluginProperty(name = "content",title = "Http body",description = "Content to put or post")
    String content;
    @PluginProperty(name = "contentType",title = "Content type",description = "Content type to use for request body")
    String contentType;
    @PluginProperty(name = "targetUrl",title = "URL target of http request",description = "URL target of http request",required = true)
    String targetUrl;

    /**
     *
     * @param trigger Notification type
     * @param executionData Context data 
     * @param config User configured values
     * @return
     */

    public boolean postNotification(String trigger, Map executionData, Map config) {
        Response response = null;
        boolean succeeded = false;
        try {
            OkHttpClient client = new OkHttpClient();
            HttpMethod method = HttpMethod.valueOf(httpMethod);

            RequestBody body = null;
            if(method.isAllowBody()) { 
                MediaType ctype = MediaType.parse(contentType);
                body = RequestBody.create(ctype, content.getBytes());
            }

            Request request = new Request.Builder()
                    .method(method.name(),body)
                    .url(targetUrl)
                    .build();

            response = client.newCall(request).execute();
            succeeded = response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if(response != null) response.body().close(); 
        }
        return succeeded;
    }
}

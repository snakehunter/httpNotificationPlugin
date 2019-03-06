package com.rundeck.webhook.plugin;

import junit.framework.TestCase;
import okhttp3.HttpUrl;
import okhttp3.internal.http.*;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import java.nio.charset.Charset;
import java.util.HashMap;

public class RDNotificationPluginTest extends TestCase {

    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    MockWebServer server;
    HttpUrl testServerUrl;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        server = new MockWebServer();
        server.start();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        server.shutdown();
    }

    public void testGet() throws Exception {
        RecordedRequest request = testHttpMethod(HttpMethod.GET);
        assertEquals(HttpMethod.GET.name(), request.getMethod());
    }

    public void testGetIgnoresProvidedBody() throws Exception {
        RecordedRequest request = testHttpMethod(HttpMethod.GET,"text/plain","ignore me",true,200);
        assertEquals(HttpMethod.GET.name(), request.getMethod());
        assertEquals("",request.getBody().readString(Charset.defaultCharset()));
        assertEquals(null,request.getHeader(CONTENT_TYPE_HEADER));
    }

    public void testPost() throws Exception {
        String contentBody = "My test body";
        String contentType = "text/plain";

        RecordedRequest request = testHttpMethod(HttpMethod.POST,contentType,contentBody,true,200);
        assertEquals(contentBody,request.getBody().readString(Charset.defaultCharset()));
        assertEquals(contentType,request.getHeader(CONTENT_TYPE_HEADER));
        assertEquals(HttpMethod.POST.name(),request.getMethod());
    }

    public void testPut() throws Exception {
        String contentBody = "<xml>xml element</xml>";
        String contentType = "application/xml";

        RecordedRequest request = testHttpMethod(HttpMethod.PUT,contentType,contentBody,true,200);
        assertEquals(contentBody,request.getBody().readString(Charset.defaultCharset()));
        assertEquals(contentType,request.getHeader(CONTENT_TYPE_HEADER));
        assertEquals(HttpMethod.PUT.name(),request.getMethod());
    }

    public void testPatch() throws Exception {
        String contentBody = "{\"msg\":\"My message\"}";
        String contentType = "application/json";

        RecordedRequest request = testHttpMethod(HttpMethod.PATCH,contentType,contentBody,true,200);
        assertEquals(contentBody,request.getBody().readString(Charset.defaultCharset()));
        assertEquals(contentType,request.getHeader(CONTENT_TYPE_HEADER));
        assertEquals(HttpMethod.PATCH.name(),request.getMethod());
    }

    public void testServer500ErrorCausesPluginPostNotificationToReturnFalse() throws Exception {
        RecordedRequest request = testHttpMethod(HttpMethod.GET,null,null,false,500);
        assertEquals(HttpMethod.GET.name(), request.getMethod());
    }

    private RecordedRequest testHttpMethod(HttpMethod method) throws Exception {
        return testHttpMethod(method, null, null, true,200);
    }

    private RecordedRequest testHttpMethod(HttpMethod method, String contentType, String contentBody, boolean expectedPluginReturnValue, int testHttpStatusCode) throws Exception {
        MockResponse response = new MockResponse().setResponseCode(testHttpStatusCode);
        if(!(contentBody == null ||contentType == null)) server.enqueue(response.setBody(contentBody).setHeader(CONTENT_TYPE_HEADER,contentType));
        else server.enqueue(response);
        testServerUrl = server.url("");

        RDNotificationPlugin plugin = new RDNotificationPlugin();
        plugin.targetUrl = testServerUrl.toString();
        plugin.content = contentBody;
        plugin.contentType = contentType;
        plugin.httpMethod = method.name();
        assertEquals(expectedPluginReturnValue, plugin.postNotification("",new HashMap(), new HashMap()));

        return server.takeRequest();

    }

}
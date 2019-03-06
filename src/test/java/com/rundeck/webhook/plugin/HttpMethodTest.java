package com.rundeck.webhook.plugin;

import junit.framework.TestCase;

public class HttpMethodTest extends TestCase {
    public void testIsAllowBody() throws Exception {

        assertEquals(true,HttpMethod.POST.isAllowBody());
        assertEquals(true,HttpMethod.PUT.isAllowBody());
        assertEquals(true,HttpMethod.PATCH.isAllowBody());

        assertEquals(false,HttpMethod.GET.isAllowBody());
        assertEquals(false,HttpMethod.OPTIONS.isAllowBody());
        assertEquals(false,HttpMethod.HEAD.isAllowBody());
        assertEquals(false,HttpMethod.DELETE.isAllowBody());
    }

}
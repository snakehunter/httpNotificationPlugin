package com.rundeck.webhook.plugin;

public enum HttpMethod {
    GET(false),POST(true),PUT(true),HEAD(false),PATCH(true),OPTIONS(false),DELETE(false);

    private boolean allowBody;

    HttpMethod(boolean allowBody) {
        this.allowBody = allowBody;
    }

    public boolean isAllowBody() { return allowBody; };
}

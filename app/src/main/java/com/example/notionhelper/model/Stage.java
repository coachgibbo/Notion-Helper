package com.example.notionhelper.model;

import com.example.notionhelper.infrastructure.NotionClient;
import com.example.notionhelper.infrastructure.config.NotionInterface;
import com.google.gson.JsonObject;

import retrofit2.Call;

public class Stage {

    private final String name;
    private final String pageId;
    private final String method;
    private final JsonObject jsonBody;

    private final NotionInterface notionInterface;

    public Stage(Builder builder) {
        this.name = builder.name;
        this.pageId = builder.pageId;
        this.method = builder.method;
        this.jsonBody = builder.jsonBody;

        notionInterface = NotionClient.getNotionInterface();
    }

    // Update as new stage types are added
    public Call<JsonObject> run() {
        if (method.equals("updatePage")) {
            return notionInterface.updatePage(pageId, jsonBody);
        }

        return null;
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        private String name;
        private String pageId;
        private String method;
        private JsonObject jsonBody;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder pageId(String pageId) {
            this.pageId = pageId;
            return this;
        }

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder jsonBody(JsonObject jsonBody) {
            this.jsonBody = jsonBody;
            return this;
        }

        public Stage build() {
            return new Stage(this);
        }
    }

}

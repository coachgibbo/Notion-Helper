package com.example.notionhelper.model;

import com.example.notionhelper.infrastructure.NotionClient;
import com.example.notionhelper.infrastructure.config.NotionInterface;
import com.google.gson.JsonObject;

import retrofit2.Call;

public class Stage {

    private final String name;
    private final String pageId;
    private final JsonObject jsonBody;

    private final NotionInterface notionInterface;

    public Stage(Builder builder) {
        this.name = builder.name;
        this.pageId = builder.pageId;
        this.jsonBody = builder.jsonBody;

        notionInterface = NotionClient.getNotionInterface();
    }

    public Call<JsonObject> updatePage() {
        return notionInterface.updatePage(pageId, jsonBody);
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        private String name;
        private String pageId;
        private JsonObject jsonBody;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder pageId(String pageId) {
            this.pageId = pageId;
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

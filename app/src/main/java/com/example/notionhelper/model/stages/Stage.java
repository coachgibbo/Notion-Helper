package com.example.notionhelper.model.stages;

import com.example.notionhelper.common.NotionPropKeys;
import com.example.notionhelper.infrastructure.notion.NotionClient;
import com.example.notionhelper.infrastructure.notion.NotionInterface;
import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;

public class Stage {

    private final String method;
    private final JsonObject jsonBody;
    private final HashMap<String, String> properties;

    private final NotionInterface notionInterface;

    public Stage(Builder builder) {
        this.method = builder.method;
        this.jsonBody = builder.jsonBody;
        this.properties = builder.properties;

        notionInterface = NotionClient.getNotionInterface();
    }

    public Call<JsonObject> run() {
        if (method.equals("updatePage")) {
            return notionInterface.updatePage(getProperty(NotionPropKeys.PAGEID.name()), jsonBody);
        }

        return null;
    }

    public String getProperty(String propKey) {
        return properties.get(propKey);
    }

    public static class Builder {

        private String method;
        private JsonObject jsonBody;
        private final HashMap<String, String> properties = new HashMap<>();

        public Builder addProperty(String propKey, String propValue) {
            properties.put(propKey, propValue);
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

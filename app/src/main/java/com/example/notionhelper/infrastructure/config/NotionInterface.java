package com.example.notionhelper.infrastructure.config;

import static com.example.notionhelper.common.Constants.PAGES;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NotionInterface {
    @POST(PAGES)
    Call<JsonObject> createPage(
            @Body JsonObject body
    );
}

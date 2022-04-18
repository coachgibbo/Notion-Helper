package com.example.notionhelper.infrastructure.config;

import static com.example.notionhelper.common.Constants.BEARER;
import static com.example.notionhelper.common.Constants.PAGES;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NotionInterface {

    String BASE_URL = "https://api.notion.com/v1/";

    @Headers({
            "Authorization: " + BEARER,
            "Content-Type: application/json",
            "Notion-Version: 2022-02-22"
    })
    @POST(PAGES)
    Call<JsonObject> createPage(
            @Body JsonObject body
    );
}

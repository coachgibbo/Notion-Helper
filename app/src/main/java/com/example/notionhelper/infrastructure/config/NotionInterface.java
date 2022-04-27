package com.example.notionhelper.infrastructure.config;

import static com.example.notionhelper.common.Constants.PAGES;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NotionInterface {
    @POST(PAGES)
    Call<JsonObject> createPage(
            @Body JsonObject body
    );

    @PATCH(PAGES + "/{pageId}")
    Call<JsonObject> updatePage(
            @Path("pageId") String pageId,
            @Body JsonObject body
    );
}

package com.example.notionhelper.infrastructure.notion;

import static com.example.notionhelper.common.Constants.DATABASES;
import static com.example.notionhelper.common.Constants.PAGES;
import static com.example.notionhelper.common.NotionObjectIds.DAILY_TASK_DATABASE;

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

    @POST(DATABASES + "/" + DAILY_TASK_DATABASE + "/query")
    Call<JsonObject> getPageFromDatabase(
            @Body JsonObject body
    );

    @PATCH(PAGES + "/{pageId}")
    Call<JsonObject> updatePage(
            @Path("pageId") String pageId,
            @Body JsonObject body
    );
}

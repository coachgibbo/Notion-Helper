package com.example.notionhelper.infrastructure.notion;

import static com.example.notionhelper.common.Constants.BEARER;
import static com.example.notionhelper.common.Constants.NOTION_BASE_URL;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class NotionClient {
    private static Retrofit retrofit=null;
    private static OkHttpClient httpClient=null;

    private NotionClient() {

    }

    public static NotionInterface getNotionInterface() {
        if (httpClient == null) {
            httpClient = new OkHttpClient()
                    .newBuilder()
                    .addInterceptor(buildHttpInterceptor())
                    .build();
        }

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(httpClient)
                    .baseUrl(NOTION_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(NotionInterface.class);
    }

    private static Interceptor buildHttpInterceptor() {
        return chain -> {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", BEARER)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Notion-Version", "2022-02-22")
                    .build();
            return chain.proceed(request);
        };
    }
}

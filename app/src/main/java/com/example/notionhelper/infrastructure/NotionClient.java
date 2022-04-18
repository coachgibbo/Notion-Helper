package com.example.notionhelper.infrastructure;

import com.example.notionhelper.infrastructure.config.NotionInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotionClient {
    private static Retrofit retrofit=null;

    public static NotionInterface getNotionInterface() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(NotionInterface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(NotionInterface.class);
    }


}

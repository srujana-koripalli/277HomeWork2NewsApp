package com.srujana.newsappcode;

import com.srujana.newsappcode.Models.Headlines;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines")
    Call<Headlines> getHeadlines(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

    @GET("everything")
    Call<Headlines> getSearchData(
            @Query("q") String query,
            @Query("apiKey") String apiKey
    );

}

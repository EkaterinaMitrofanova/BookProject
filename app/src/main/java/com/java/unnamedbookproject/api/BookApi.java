package com.java.unnamedbookproject.api;

import com.java.unnamedbookproject.model.Shop;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface BookApi {

    @Multipart
    @POST("/api/upload")
    Call<List<Shop>> getShops(@Part("file\"; filename=\"pi.jpg\" ")RequestBody file);

    @GET("/api/search")
    Call<List<Shop>> getByName(@Query("name") String text);
}

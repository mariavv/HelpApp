package com.example.user.myapplication.model.remote;

import com.example.user.myapplication.model.entity.Category;
import com.example.user.myapplication.model.entity.Event;
import com.example.user.myapplication.model.entity.Token;
import com.example.user.myapplication.model.entity.UserCredentials;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestService {

    String DOMAIN = "http://api-factory.simbirsoft";
    String API_URL = DOMAIN + "/api/";
    String DB_URL = API_URL + "db/";

    @POST(API_URL + "auth/login")
    Observable<Token> getToken(@Body UserCredentials credentials);

    @GET(DB_URL + "category")
    Observable<BaseResponse<List<Category>>> getCategories();

    @GET(DB_URL + "event")
    Observable<BaseResponse<List<Event>>> getEvents();
}

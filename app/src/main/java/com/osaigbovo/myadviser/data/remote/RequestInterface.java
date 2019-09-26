package com.osaigbovo.myadviser.data.remote;

import com.osaigbovo.myadviser.data.model.Advicer;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface RequestInterface {

    @GET("advice")
    Single<Advicer> getAdvice();

}

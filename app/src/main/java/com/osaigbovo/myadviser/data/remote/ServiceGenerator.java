package com.osaigbovo.myadviser.data.remote;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

@Singleton
public class ServiceGenerator {

    private static final String ADVICE_BASE_URL = "https://api.adviceslip.com/";

    private static final Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private static final Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(ADVICE_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson));

    private static Retrofit retrofit = builder.build();

    private static final HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .addInterceptor(new ErrorHandlerInterceptor());

    public static <S> S createService(Class<S> serviceClass) {

        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }

        return retrofit.create(serviceClass);
    }

    /**
     * Interceptor to display response message
     */
    private static class ErrorHandlerInterceptor implements Interceptor {
        @NonNull
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            switch (response.code()) {
                case 200:
                    Timber.i("200 - Found");
                    break;
                case 404:
                    Timber.i("404 - Not Found");
                    break;
                case 500:
                    Timber.i("500 - Server Broken");
                    break;
                case 504:
                    Timber.i("500 - Server Broken");
                    break;
                default:
                    Timber.i("Network Unknown Error");
                    break;
            }

            return response;
        }
    }

}
package com.kiwabolab.lisa.network.retrofit;

import com.kiwabolab.lisa.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestApiAdapter {
    //----------------------------------------------------------------------------------------------
    /**Recibe la url base del servidor*/
    public RestClient EstablecerConexion(String servidor){
        //building HTTP Client for Interceptor
        OkHttpClient.Builder okHttpbuilder = new OkHttpClient().newBuilder();
        //headers for the web services are handled on GearMeAppInterceptor
        okHttpbuilder.addInterceptor(new AppInterceptor());
        if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpbuilder.addInterceptor(loggingInterceptor);
        }
        //Here should be included authentication parameters for request
        okHttpbuilder.retryOnConnectionFailure(false);
        okHttpbuilder.readTimeout(48, TimeUnit.SECONDS);
        okHttpbuilder.writeTimeout(60, TimeUnit.SECONDS);
        OkHttpClient client = okHttpbuilder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(servidor)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(RestClient.class);
    }
}
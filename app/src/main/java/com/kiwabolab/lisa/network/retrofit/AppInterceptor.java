package com.kiwabolab.lisa.network.retrofit;

import android.util.Log;

import com.kiwabolab.lisa.BuildConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AppInterceptor implements Interceptor {
    //----------------------------------------------------------------------------------------------
    //Constructor
    public AppInterceptor() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(5)         // (Optional) How many method line to show. Default 2
                .methodOffset(10)        // (Optional) Skips some method invokes in stack trace. Default 5
                .tag("Interrapidismimo-ControlLogis-AppInterceptor")   // (Optional) Custom tag for each log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                if (BuildConfig.DEBUG) {
                    return true;
                } else if (priority == Logger.ERROR || priority == Logger.INFO) {
                    return true;
                }

                return false;
            }
        });
    }
    //----------------------------------------------------------------------------------------------
    //
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();

        //builder.addHeader("Usuario", AppData.getNombreUsuario());
       // builder.addHeader("Content-Type", "application/json");
        builder.addHeader("Accept", "text/json");

        if(request.body() != null){
            Log.v("BODY",request.body().toString());
        }

        request = builder.build();
        //Logger.i("Add JSON Header Request : " + request.headers().get("Content-Type"));
        Response response = chain.proceed(request);
        //Logger.i(response.toString() + "\n body: " + response.body());
        return response;
    }
}
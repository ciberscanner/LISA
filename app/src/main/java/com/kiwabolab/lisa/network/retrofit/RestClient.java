package com.kiwabolab.lisa.network.retrofit;


import com.kiwabolab.lisa.modelo.Factura;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Diego Fernando.
 */
public interface RestClient {
    //----------------------------------------------------------------------------------------------
    //
    @GET("factura/obtenerfactura/{id}")
    Call<Factura> obtenerFactura(@Path("id") String idNovedad);
    //----------------------------------------------------------------------------------------------
    //
    @GET("factura/todas")
    Call<List<Factura>>obtenerFacturas();
    //----------------------------------------------------------------------------------------------
    //
    @POST("factura/subirfactura")
    Call<String>enviarFactura(@Body Factura registroSolicitudApp);
}
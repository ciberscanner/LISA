package com.kiwabolab.lisa.presentacion;

import com.kiwabolab.lisa.modelo.Factura;
import com.kiwabolab.lisa.network.retrofit.RestApiAdapter;
import com.kiwabolab.lisa.network.retrofit.RestClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kiwabolab.lisa.network.Servidor.URLSERVIDOR;

public class FacturaInteractor implements contratoFactura.InteractorFactura {
    //----------------------------------------------------------------------------------------------
    //Variables
    private final  contratoFactura.PresenterFactura presenter;
    //----------------------------------------------------------------------------------------------
    //Constructor
    public FacturaInteractor(contratoFactura.PresenterFactura presenterFactura) {
        this.presenter = presenterFactura;
    }
    //----------------------------------------------------------------------------------------------
    //
    @Override
    public void obtenerFacturas() {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        RestClient endpointsApi = restApiAdapter.EstablecerConexion(URLSERVIDOR);
        Call<List<Factura>> contactoResponseCall = endpointsApi.obtenerFacturas();
        contactoResponseCall.enqueue(new Callback<List<Factura>>() {
            @Override
            public void onResponse(Call<List<Factura>> call, Response<List<Factura>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        presenter.obtenerFacturasOk(response.body());
                    }
                } else {
                    presenter.obtenerFacturasError();
                }
            }
            @Override
            public void onFailure(Call<List<Factura>> call, Throwable t) {
                presenter.obtenerFacturasError();
            }
        });
    }
    //----------------------------------------------------------------------------------------------
    //
    @Override
    public void obtenerFactura(String id) {

    }
    //----------------------------------------------------------------------------------------------
    //
    @Override
    public void enviarFactura(Factura factura) {

    }
}

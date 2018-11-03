package com.kiwabolab.lisa.presentacion;

import com.kiwabolab.lisa.modelo.Factura;

import java.util.List;

public interface contratoFactura {
    //----------------------------------------------------------------------------------------------
    //
    interface VistaFactura{
        void ShowLoadin();
        void CloseLoading();

        void obtenerFacturas();
        void obtenerFacturasOk(List<Factura> facturas);
        void obtenerFacturasError();

        void obtenerFactura(String id);
        void obtenerFacturaOk(Factura factura);
        void obtenerFacturaError();

        void enviarFactura(Factura factura);
        void enviarFacturaOk();
        void enviarFacturaError();
    }
    //----------------------------------------------------------------------------------------------
    //
    interface PresenterFactura{
        void ShowLoadin();
        void CloseLoading();

        void obtenerFacturas();
        void obtenerFacturasOk(List<Factura> facturas);
        void obtenerFacturasError();

        void obtenerFactura(String id);
        void obtenerFacturaOk(Factura factura);
        void obtenerFacturaError();

        void enviarFactura(Factura factura);
        void enviarFacturaOk();
        void enviarFacturaError();
    }
    //----------------------------------------------------------------------------------------------
    //
    interface InteractorFactura{
        void obtenerFacturas();
        void obtenerFactura(String id);
        void enviarFactura(Factura factura);
    }
}
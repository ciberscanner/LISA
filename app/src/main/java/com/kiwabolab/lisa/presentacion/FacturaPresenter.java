package com.kiwabolab.lisa.presentacion;

import com.kiwabolab.lisa.modelo.Factura;

import java.util.List;

public class FacturaPresenter implements contratoFactura.PresenterFactura {
    //----------------------------------------------------------------------------------------------
    //Variables
    private contratoFactura.VistaFactura vista;
    private contratoFactura.InteractorFactura interactorFactura;
    //----------------------------------------------------------------------------------------------
    //Constructor
    public FacturaPresenter(contratoFactura.VistaFactura vista) {
        this.vista = vista;
        interactorFactura = new FacturaInteractor(this);
    }
    //----------------------------------------------------------------------------------------------
    //
    @Override
    public void ShowLoadin() {
        vista.ShowLoadin();
    }
    //----------------------------------------------------------------------------------------------
    //
    @Override
    public void CloseLoading() {
        vista.CloseLoading();
    }
    //----------------------------------------------------------------------------------------------
    //
    @Override
    public void obtenerFacturas() {
        ShowLoadin();
        interactorFactura.obtenerFacturas();
    }
    //----------------------------------------------------------------------------------------------
    //
    @Override
    public void obtenerFacturasOk(List<Factura> facturas) {
        vista.obtenerFacturasOk(facturas);
        CloseLoading();
    }
    //----------------------------------------------------------------------------------------------
    //
    @Override
    public void obtenerFacturasError() {
        vista.obtenerFacturasError();
        CloseLoading();
    }
    //----------------------------------------------------------------------------------------------
    //
    @Override
    public void obtenerFactura(String id) {
        ShowLoadin();
        interactorFactura.obtenerFactura(id);
    }
    //----------------------------------------------------------------------------------------------
    //
    @Override
    public void obtenerFacturaOk(Factura factura) {
        vista.obtenerFacturaOk(factura);
        CloseLoading();
    }
    //----------------------------------------------------------------------------------------------
    //
    @Override
    public void obtenerFacturaError() {
        vista.obtenerFacturaError();
        CloseLoading();
    }
    //----------------------------------------------------------------------------------------------
    //
    @Override
    public void enviarFactura(Factura factura) {
        ShowLoadin();
        interactorFactura.enviarFactura(factura);
    }
    //----------------------------------------------------------------------------------------------
    //
    @Override
    public void enviarFacturaOk() {
        vista.enviarFacturaOk();
        CloseLoading();
    }
    //----------------------------------------------------------------------------------------------
    //
    @Override
    public void enviarFacturaError() {
        vista.enviarFacturaError();
        CloseLoading();
    }
}

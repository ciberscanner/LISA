package com.kiwabolab.lisa.vista;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.kiwabolab.lisa.R;
import com.kiwabolab.lisa.modelo.Factura;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VerFacturas extends Activity {
    //----------------------------------------------------------------------------------------------
    //Variables
    @BindView(R.id.listaFacturas)ListView listafacturas;
    private ArrayList<Factura>facturas;
    //----------------------------------------------------------------------------------------------
    //Constructor
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verfacturas);
        ButterKnife.bind(this);


    }
    //----------------------------------------------------------------------------------------------
    //
}
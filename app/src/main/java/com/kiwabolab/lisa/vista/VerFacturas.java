package com.kiwabolab.lisa.vista;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.kiwabolab.lisa.R;
import com.kiwabolab.lisa.modelo.Factura;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VerFacturas extends Activity {
    //----------------------------------------------------------------------------------------------
    //Variables
    @BindView(R.id.listaFacturas)ListView listafacturas;
    @BindView(R.id.texto)TextView texto;

    private List<Factura> facturas;
    //----------------------------------------------------------------------------------------------
    //Constructor
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verfacturas);
        ButterKnife.bind(this);

        //facturas=getIntent().getParcelableArrayListExtra("FALLAS");
        facturas=Home.facturas;

        String textos="";
        textos+="Facturas: "+facturas.size()+"\n";
        for(Factura factura: facturas){
            textos+=factura.getDescripcion()+" "+ factura.getNumero() +"\n";
        }
        texto.setText(textos);
    }
    //----------------------------------------------------------------------------------------------
    //
}
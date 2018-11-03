package com.kiwabolab.lisa.vista;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.kiwabolab.lisa.BuildConfig;
import com.kiwabolab.lisa.R;
import com.kiwabolab.lisa.util.CheckField;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Home extends AppCompatActivity {
    //----------------------------------------------------------------------------------------------
    //Variables
    private static final String LOG_TAG = "Text API";
    private static final int PHOTO_REQUEST = 10;
    private TextView scanResults;
    private Uri imageUri;
    private TextRecognizer detector;
    private static final int REQUEST_WRITE_PERMISSION = 20;
    private static final String SAVED_INSTANCE_URI = "uri";
    private static final String SAVED_INSTANCE_RESULT = "result";

    private CheckField checkField;
    private ArrayList<String> palabras;
    private ArrayList<String>lineas;
    private ArrayList<TextBlock> bloques;
    private ArrayList<TextBlock> bloqueN;


    @BindView(R.id.nombreempresa)TextView nombre;
    @BindView(R.id.fechaempresa)TextView fecha;
    @BindView(R.id.numerofactura)TextView numero;
    @BindView(R.id.total)TextView total;
    //----------------------------------------------------------------------------------------------
    //Constructor
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        scanResults = (TextView) findViewById(R.id.results);

        lineas= new ArrayList<>();
        palabras = new ArrayList<>();
        bloques = new ArrayList<>();
        checkField = new CheckField();
        bloqueN= new ArrayList<>();

        if (savedInstanceState != null) {
            imageUri = Uri.parse(savedInstanceState.getString(SAVED_INSTANCE_URI));
            scanResults.setText(savedInstanceState.getString(SAVED_INSTANCE_RESULT));
        }
        detector = new TextRecognizer.Builder(getApplicationContext()).build();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lineas.clear();
                fecha.setText("");
                bloques.clear();
                palabras.clear();
                scanResults.setText("");
                bloqueN.clear();
                ActivityCompat.requestPermissions(Home.this, new
                        String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }
    //----------------------------------------------------------------------------------------------
    //
    //----------------------------------------------------------------------------------------------
    //
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePicture();
                } else {
                    Toast.makeText(Home.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }
    //----------------------------------------------------------------------------------------------
    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST && resultCode == RESULT_OK) {
            launchMediaScanIntent();
            SparseArray<TextBlock> textBlocks = new SparseArray();
            try {
                Bitmap bitmap = decodeBitmapUri(this, imageUri);
                if (detector.isOperational() && bitmap != null) {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    textBlocks = detector.detect(frame);
                    String blocks = "";
                    String lines = "";
                    String words = "";
                    for (int index = 0; index < textBlocks.size(); index++) {
                        //extract scanned text blocks here
                        TextBlock tBlock = textBlocks.valueAt(index);
                        bloques.add(tBlock);
                        blocks = blocks + tBlock.getValue() + "\n" + "\n";
                        for (Text line : tBlock.getComponents()) {
                            //extract scanned text lines here
                            lines = lines + line.getValue() + "\n";
                            lineas.add(line.getValue());
                            for (Text element : line.getComponents()) {
                                //extract scanned text words here
                                palabras.add(element.getValue());
                                words = words + element.getValue() + ", ";
                            }
                        }
                    }
                    if (textBlocks.size() == 0) {
                        scanResults.setText("Scan Failed: Found nothing to scan");
                    } else {
                        scanResults.setText(scanResults.getText() + "Blocks: " + "\n");
                        scanResults.setText(scanResults.getText() + blocks + "\n");
                        scanResults.setText(scanResults.getText() + "---------" + "\n");
                        scanResults.setText(scanResults.getText() + "Lines: " + "\n");
                        scanResults.setText(scanResults.getText() + lines + "\n");
                        scanResults.setText(scanResults.getText() + "---------" + "\n");
                        scanResults.setText(scanResults.getText() + "Words: " + "\n");
                        scanResults.setText(scanResults.getText() + words + "\n");
                        scanResults.setText(scanResults.getText() + "---------" + "\n");
                    }
                } else {
                    scanResults.setText("Could not set up the detector!");
                }
            } catch (Exception e) {
                Toast.makeText(this, "Failed to load Image", Toast.LENGTH_SHORT)
                        .show();
                Log.e(LOG_TAG, e.toString());
            }
            procesarDatos(textBlocks);
        }
    }
    //----------------------------------------------------------------------------------------------
    //Procesar bloques
    private void procesarDatos(SparseArray<TextBlock> textBlocks){
        Log.v("TAMAÑO",textBlocks.size()+"");

        int mostTop=1000;
        int pos=0;
        for (int index = 0; index < textBlocks.size(); index++) {
            if(textBlocks.get(index)!= null){
                if(mostTop>textBlocks.get(index).getBoundingBox().top){
                    mostTop = textBlocks.get(index).getBoundingBox().top;
                    pos=index;
                }
            }

        }
        Log.v("NOMBRE EMPRESA",textBlocks.get(pos).getComponents().get(0).getValue()+"");
        nombre.setText(""+textBlocks.get(pos).getComponents().get(0).getValue());


        for(int i =0; i<palabras.size();i++){
            if(checkField.isDateValid(palabras.get(i)) == 1 || checkField.isDateValid(palabras.get(i)) == 2){
                Log.v("Fecha",palabras.get(i));
                fecha.setText(palabras.get(i));
                i=10000;
            }
            if(i==palabras.size()){
                Log.v("Error","No hay fecha");
            }
        }

        for( int i=0; i< lineas.size(); i++){
            if(lineas.get(i).contains("No, ")||lineas.get(i).contains("No. ") && lineas.get(i).length()<12){
                numero.setText(lineas.get(i));
                i=9999;
                //return;
            }
        }
        //Quicksort(textBlocks,  0,  textBlocks.size()-1);

        sera();
        Log.v("Salida","SERA");
        Log.v("Bloque",bloqueN.size()+"");
        String texto="";
        for(int index = 0; index < bloqueN.size(); index++){
            TextBlock tBlock = bloqueN.get(index);
            if(tBlock!=null){
                texto = texto + tBlock.getValue()+ "\n" + "\n";
            }

        }
        scanResults.setText(texto);


    }

    //----------------------------------------------------------------------------------------------
    //
    private void sera(){
        int val=9999;
        int pos=0;
        Log.v("nuevo tamaño",bloques.size()+"");
        for(int i = 0; i < bloques.size(); i++){
            if(bloques.get(i)!=null){
                Log.v("bangalore",bloques.get(i).getCornerPoints()[0].y+" i:"+i);
                if(bloques.get(i).getCornerPoints()[0].y < val){
                    val = bloques.get(i).getCornerPoints()[0].y;
                    pos=i;
                }
            }
        }
        bloqueN.add(bloques.get(pos));
        bloques.remove(pos);
        //Log.v("ESTADO","SERA");
        if(bloques.size()>0){
            sera();
        }

    }
    //----------------------------------------------------------------------------------------------
    //
    void Quicksort(SparseArray<TextBlock> textBlocks, int first, int last) {


        int i=first, j=last;
        TextBlock pivote=textBlocks.get((first + last) / 2);
        TextBlock auxiliar;
        do {
            while(textBlocks.get(i).getBoundingBox().top < pivote.getBoundingBox().top) i++;
            while(textBlocks.get(j).getBoundingBox().top > pivote.getBoundingBox().top) j--;
            if (i<=j){
                auxiliar=textBlocks.get(j);
                textBlocks.setValueAt(j,textBlocks.get(i));
                textBlocks.setValueAt(i, auxiliar); i= auxiliar.getBoundingBox().top ;
                i++;
                j--;
            }
        }
        while (i<=j);

        if(first<j) {
            Quicksort(textBlocks,first, j);
        }
        if(last>i) {
            Quicksort(textBlocks,i, last);
        }

        String texto="";
        for(int index = 0; index < textBlocks.size(); index++){
            TextBlock tBlock = textBlocks.valueAt(index);
            texto = texto + tBlock.getValue() + "\n" + "\n";
        }
        scanResults.setText(texto);

    }
    //----------------------------------------------------------------------------------------------
    //
    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), "picture.jpg");
        imageUri = FileProvider.getUriForFile(Home.this,
                BuildConfig.APPLICATION_ID + ".provider", photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, PHOTO_REQUEST);
    }
    //----------------------------------------------------------------------------------------------
    //
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (imageUri != null) {
            outState.putString(SAVED_INSTANCE_URI, imageUri.toString());
            outState.putString(SAVED_INSTANCE_RESULT, scanResults.getText().toString());
        }
        super.onSaveInstanceState(outState);
    }
    //----------------------------------------------------------------------------------------------
    //
    private void launchMediaScanIntent() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(imageUri);
        this.sendBroadcast(mediaScanIntent);
    }
    //----------------------------------------------------------------------------------------------
    //
    private Bitmap decodeBitmapUri(Context ctx, Uri uri) throws FileNotFoundException {
        int targetW = 600;
        int targetH = 600;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(uri), null, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeStream(ctx.getContentResolver()
                .openInputStream(uri), null, bmOptions);
    }
}

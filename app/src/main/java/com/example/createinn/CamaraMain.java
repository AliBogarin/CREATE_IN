package com.example.createinn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class CamaraMain extends AppCompatActivity {
   ImageButton main_button;
   ImageButton capture;
   EditText result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara_main);
        main_button=(ImageButton)findViewById(R.id.button_to_go_hand);
        capture = findViewById(R.id.button_to_go_hand);
        result= findViewById(R.id.result);

        main_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CamaraMain.this, MainActivity.class);
                startActivity(intent);
            }
        });

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //abrir camara
                IntentIntegrator intentIntegrator  = new IntentIntegrator(CamaraMain.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES); //tipo de codigo a leer
                intentIntegrator.setPrompt("Lector + CDP"); //lo que me aparece
                intentIntegrator.setCameraId(0);//camara trasera
                intentIntegrator.setBeepEnabled(true);//que suene cuando lo capture
                intentIntegrator.setBarcodeImageEnabled(true);//para que pueda leer  los codigos correctamente
                intentIntegrator.initiateScan();


            }
        });

    }
    protected  void  onActivityResult(int requestCode, int resultCode, Intent data) {
        /*1 codigo de respuesta(enter), resultado, datos que transfiere la aplicacion*/

        IntentResult intentResult= IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null){
            if(intentResult.getContents()== null){
                Toast.makeText(this, "Lectura cancelada", Toast.LENGTH_SHORT).show();
            }else{
                //muestro el resultado que me envia tanto por toat como por texto
                Toast.makeText(this, intentResult.getContents(), Toast.LENGTH_SHORT).show();
                result.setText(intentResult.getContents());
            }
        }else {
            //esta es la respuesta de cuando sea nulo
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    }

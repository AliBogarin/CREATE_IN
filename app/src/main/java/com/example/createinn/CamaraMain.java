package com.example.createinn;

import static java.lang.System.load;

import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.BreakIterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CamaraMain extends AppCompatActivity {
    ImageButton main_button;
    ImageButton capture;
    EditText result;
    TextView label_name;
    TextView name;
    TextView factured;
    TextView country;
    ImageView image;
    Context context=this;
    ImageView unknown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara_main);
        main_button=(ImageButton)findViewById(R.id.button_to_go_hand);
        capture = findViewById(R.id.capture_Image);
        result= findViewById(R.id.result);
        name= findViewById(R.id.name_product);
        factured = findViewById(R.id.factured_place);
        country= findViewById(R.id.country);
        image= findViewById(R.id.image);
        label_name= findViewById(R.id.label_name);
        unknown= findViewById(R.id.unknown);
        //abrir camara al comenzar
        IntentIntegrator intentIntegrator  = new IntentIntegrator(CamaraMain.this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES); //tipo de codigo a leer
        intentIntegrator.setPrompt("Lector + CDP"); //lo que me aparece
        intentIntegrator.setCameraId(0);//camara trasera
        intentIntegrator.setOrientationLocked(false); /* bloqueo posicion de camara*/intentIntegrator.setBeepEnabled(true);//que suene cuando lo capture
       intentIntegrator.setCaptureActivity(CaptureActivityPosition.class);
       intentIntegrator.initiateScan();

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
                intentIntegrator.setOrientationLocked(false); /* bloqueo posicion de camara*/
                intentIntegrator.setBeepEnabled(true);//que suene cuando lo capture
                intentIntegrator.setCaptureActivity(CaptureActivityPosition.class);
                intentIntegrator.initiateScan();


            }
        });

    }
    protected  void  onActivityResult(int requestCode, int resultCode, Intent data) {
        /*1 codigo de respuesta(enter), resultado, datos que transfiere la aplicacion*/

        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(this, "Lectura cancelada", Toast.LENGTH_SHORT).show();
            } else {
                //muestro el resultado que me envia tanto por toast como por texto
                Toast.makeText(this, intentResult.getContents(), Toast.LENGTH_SHORT).show();
                result.setText(intentResult.getContents()); //esto es para que me devuelva la lectura del codigo
                String barcode =intentResult.getContents();
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url( "https://world.openfoodfacts.org/api/v0/product/"+barcode+".json").build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            final String myResponse = response.body().string();
                            Log.d("JSON Response", myResponse); //esto para visualizar mi json

                            CamaraMain.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {

                                        Gson gson = new Gson();
                                        JsonObject json = gson.fromJson(myResponse, JsonObject.class);
                                        JsonObject product = json.getAsJsonObject("product");
                                        /*Compruebo que el producto exista*/
                                        if(json.get("status").getAsInt() == 0) {
                                            Toast.makeText(context, "Producto Desconocido", Toast.LENGTH_SHORT).show();
                                            Glide.with(context)
                                                    .load(R.drawable.desconocido)
                                                    .into(unknown);

                                        } else{
                                        /*Aquí leo los datos que voy a mostrar del json obtenido por la lectura del codigo de barras*/
                                        String names = product.get("brands").getAsString();
                                        label_name.setText(names);

                                        String productName = product.get("product_name").getAsString();
                                        name.setText(productName);

                                        String manufacturingPlaces = product.get("manufacturing_places").getAsString();
                                        factured.setText(manufacturingPlaces);

                                        String countries = product.get("countries").getAsString();
                                        country.setText(countries);


                                            String imageFrontUrl = product.get("image_thumb_url").getAsString();
                                            Glide.with(context)
                                                    .load(imageFrontUrl)
                                                    .placeholder(R.drawable.ic_launcher_background)
                                                    .into(image);


                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        }
                    }
                });
            }

        }else {
            //esta es la respuesta de cuando sea nulo
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

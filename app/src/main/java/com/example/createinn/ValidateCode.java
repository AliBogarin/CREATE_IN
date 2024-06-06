package com.example.createinn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ValidateCode extends AppCompatActivity {

    TextView label_name;
    TextView name;
    TextView factured;
    TextView country;
    ImageView image;
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_code);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        /*Creo los huecos para alojar el producto obtenido*/
        name= findViewById(R.id.name_product);
        factured = findViewById(R.id.factured_place);
        country= findViewById(R.id.country);
        image= findViewById(R.id.image);
        label_name= findViewById(R.id.label_name);

        /*Obtengo el número del producto que ha introducido el usuario de forma manual*/
        String codigo = getIntent().getStringExtra("codigo");
        if (codigo != null) {
            procesarCodigo(codigo);
        }

    }
    /*Método para procesar la obtención de respuesta por medio del codigo*/
    public void procesarCodigo(String codigo) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url( "https://world.openfoodfacts.org/api/v0/product/"+codigo+".json").build();

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

                    ValidateCode.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                Gson gson = new Gson();
                                JsonObject json = gson.fromJson(myResponse, JsonObject.class);
                                JsonObject product = json.getAsJsonObject("product");
                                /*Compruebo que el producto exista*/
                                if(json.get("status").getAsInt() == 0) {
                                    Intent intent = new  Intent(ValidateCode.this,UnknownProduct.class);
                                    startActivity(intent);

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

}    }
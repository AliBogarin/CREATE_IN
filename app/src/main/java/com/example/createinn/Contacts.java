package com.example.createinn;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Contacts extends AppCompatActivity {

    ImageButton sendInformation;
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        sendInformation=(ImageButton)findViewById(R.id.send_information);
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        final String infoProduct = sharedPref.getString("infoProduct", "");
        sendInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_SUBJECT,"Información importante!.");
                intent.putExtra(Intent.EXTRA_TEXT ,"Mi web: <a href= 'https://es.openfoodfacts.org/' >Leer</a>"+infoProduct);
                intent.setPackage("com.whatsapp");
                startActivity(intent);

            }
        });



    }
}
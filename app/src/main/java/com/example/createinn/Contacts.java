package com.example.createinn;


import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        sendInformation=(ImageButton)findViewById(R.id.send_information);

        sendInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_SUBJECT,"Información importante!.");
                intent.putExtra(Intent.EXTRA_TEXT ,"Mi web: <a href= 'https://es.openfoodfacts.org/' >Leer</a>");

                intent.setPackage("com.whatsapp");
                startActivity(intent);

            }
        });



    }
}
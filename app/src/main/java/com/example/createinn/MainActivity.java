package com.example.createinn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;



import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    Context context=this;
    Button handValidate;
    EditText code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        code= findViewById(R.id.rellenable);
        handValidate=findViewById(R.id.button_validate);

        /*Flecha de retroceso*/

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        /*boton para saltar  a validar codigo*/
        handValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String takeCode = code.getText().toString();
                Intent intent = new Intent(MainActivity.this,ValidateCode.class);
                intent.putExtra("codigo",takeCode);
                startActivity(intent);

            }
        });


        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
/*Los menus se visualizan a la parte izquierda para compartir y visualizar la camara*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.camera:
                Intent camera = new Intent(MainActivity.this,CamaraMain.class);
                startActivity(camera);
                break;

            case R.id.share:
                Intent share = new Intent(MainActivity.this, Contacts.class);
                startActivity(share);
                break;

        }
        return super.onOptionsItemSelected(item);

    }



    }





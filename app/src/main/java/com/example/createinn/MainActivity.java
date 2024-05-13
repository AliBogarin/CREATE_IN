package com.example.createinn;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        }

     @Override
    public  boolean onCreateOptionsMenu(Menu menu){
         getMenuInflater().inflate(R.menu.main_menu,menu);
            return super.onCreateOptionsMenu(menu);
        }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            /* esto va a se en el caso de clicar encima de alguno de ellos*/
        }
        return super.onOptionsItemSelected(item);
    }
}

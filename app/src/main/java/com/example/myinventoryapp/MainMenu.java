package com.example.myinventoryapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainMenu extends AppCompatActivity {

    ImageView btnInv, btnUser;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btnLogout=findViewById(R.id.bLogout);
        btnInv=findViewById(R.id.btnInventory);
        btnUser=findViewById(R.id.btnUserMnj);
        //klik regis
        btnInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //membuat object intent berpindah ke activity registrasi
                Intent nt = new Intent(getApplicationContext(),MainInventory.class);
                //berpindah ke Activity regitrasi
                startActivity(nt);
            }
        });
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //membuat object intent berpindah ke activity registrasi
                Intent nt = new Intent(getApplicationContext(),MainUser.class);
                //berpindah ke Activity regitrasi
                startActivity(nt);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //membuat object intent berpindah ke Main Activity
                Intent nt = new Intent(getApplicationContext(),MainActivity.class);
                //berpindah ke Main Activity
                startActivity(nt);
            }
        });
    }
}
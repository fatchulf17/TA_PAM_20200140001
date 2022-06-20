package com.example.myinventoryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    TextView txtRegister;
    EditText inp_mail, inp_pass;
    String email, password;
    Button btnLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        txtRegister=findViewById(R.id.Regis);
        inp_mail=findViewById(R.id.edmail);
        inp_pass=findViewById(R.id.edpass);
        btnLogin=findViewById(R.id.Login);

        //pengecekan inputan email dan pw
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menyimpan input user di edittext email kedalam variabel nama
                email = inp_mail.getText().toString();
                //menyimpan input user di edittext password kedalam variabel password
                password = inp_pass.getText().toString();

                //mengeset email yang benar
                String mail = "admin";
                //mengeset password yang benar
                String pass = "123";

                //mengecek apakah edit text email dan password terdapat isi atau tidak
                if(email.trim().equalsIgnoreCase("")){
                    inp_mail.setError("");
                }
                else {
                    //mengecek apakah isi dari email dan password sudah sama dengan email dan
                    //password yang sudah diset
                    if (email.equals(mail) && password.equals(pass)) {

                        //membuat variabel toast dan membuat toast dengan menambahkan variabel nama dan password
                        Toast t = Toast.makeText(getApplicationContext(),
                                "Login Berhasil", Toast.LENGTH_LONG);
                        //menampilkan toast
                        t.show();

                        //membuat object bundle
                        Bundle b = new Bundle();
                        //memasukkan value dari variabel nama dengan kunci "a"
                        //dan dimasukkan kedalam bundle

                        b.putString("a", email.trim());
                        //membuat object intent berpindah activity dari main activity ke activity hasil
                        Intent i = new Intent(getApplicationContext(), MainMenu.class);

                        //memasukkan bundle kedalam intent untuk dikirimkan ke Activity hasil
                        i.putExtras(b);

                        //berpindah ke ActivityHasil
                        startActivity(i);
                    } else {
                        cekLogin();
                    }
                }
            }
        });

        //klik regis
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //membuat object intent berpindah ke activity registrasi
                Intent nt = new Intent(getApplicationContext(),Register.class);
                //berpindah ke Activity regitrasi
                startActivity(nt);
            }
        });
    }

    private void cekLogin() {
        email = inp_mail.getText().toString();
        password = inp_pass.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //membuat object intent berpindah ke Activity No Admin
                            Intent nt = new Intent(getApplicationContext(),MainMenu.class);
                            //berpindah ke Activity
                            startActivity(nt);
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Login Gagal, masukkan email dan password dengan benar", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.forlogin)
                .setTitle(R.string.app_name)
                .setMessage("Apakah kamu yakin?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
    }
}
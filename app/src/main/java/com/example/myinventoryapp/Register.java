package com.example.myinventoryapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Register extends AppCompatActivity {
    EditText inp_fullname, inp_email, inp_password, inp_password2;
    Button btnRegis;
    String fullname, email, password, password2;
    TextView btnHaveAcc;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        inp_fullname = findViewById(R.id.ifullname);
        inp_email = findViewById(R.id.iemail);
        inp_password = findViewById(R.id.ipassword);
        inp_password2 = findViewById(R.id.ipassword2);
        btnRegis = findViewById(R.id.saveRegis);
        btnHaveAcc = findViewById(R.id.haveacc);

        btnHaveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //membuat object intent berpindah ke Mainactivity
                Intent nt = new Intent(getApplicationContext(),MainActivity.class);
                //berpindah ke Activity regitrasi
                startActivity(nt);
            }
        });
        //klikregis
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inp_fullname.getText().length()>0 && inp_email.getText().length()>0 && inp_password.getText().length()>0){
                    if(inp_password.getText().toString().equals(inp_password2.getText().toString()) ){
                        registrasi();
                    }else{
                        Toast.makeText(getApplicationContext(), "Password Harus sama", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Isi semua data", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void registrasi() {
        fullname = inp_fullname.getText().toString();
        email = inp_email.getText().toString();
        password = inp_password.getText().toString();
        password2 = inp_password2.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password2)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //membuat object intent berpindah ke Mainactivity
                            Intent nt = new Intent(getApplicationContext(),MainActivity.class);
                            //berpindah start
                            startActivity(nt);
                            // Sign in sukses
                            Toast.makeText(Register.this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            // If sign gagal
                            Toast.makeText(Register.this, "Registrasi Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
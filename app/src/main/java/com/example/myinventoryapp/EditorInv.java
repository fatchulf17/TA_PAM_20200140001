package com.example.myinventoryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import io.grpc.util.TransmitStatusRuntimeExceptionInterceptor;

public class EditorInv extends AppCompatActivity {

    ///Mendefinisikan variable yang di pakai
    private EditText editName, editQty, editLoc, editStatus;
    private Button btnSave;

    ///inisialisasi firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ProgressDialog progressDialog;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_inv);

        ///fungsi untuk mendapatkan element dari layout
        editName = findViewById(R.id.name);
        editQty = findViewById(R.id.qty);
        editLoc = findViewById(R.id.loc);
        editStatus = findViewById(R.id.status);
        btnSave = findViewById(R.id.btn_save);


        progressDialog = new ProgressDialog(EditorInv.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Menyimpan...");

        btnSave.setOnClickListener(v -> {
            if (editName.getText().length() > 0 && editQty.getText().length()>0 && editLoc.getText().length()>0 && editStatus.getText().length()>0) {
                /*
                 * Memanggil method save data
                 *
                 * */
                saveData(editName.getText().toString(), editQty.getText().toString(), editLoc.getText().toString(), editStatus.getText().toString());
            } else {
                Toast.makeText(getApplicationContext(), "Silahkan isi semua data", Toast.LENGTH_SHORT).show();
            }
        });
        /*
         * Mendapatkan data dari main activity
         */
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            editName.setText(intent.getStringExtra("name"));
            editQty.setText(intent.getStringExtra("qty"));
            editLoc.setText(intent.getStringExtra("loc"));
            editStatus.setText(intent.getStringExtra("status"));
        }
    }

    private void saveData(String name, String qty, String loc, String status) {
        Map<String, Object> inventory = new HashMap<>();
        inventory.put("name",name);
        inventory.put("qty",qty);
        inventory.put("loc", loc);
        inventory.put("status",status);

        progressDialog.show();
        /*
         * jika id kosong maka akan edit data
         * */
        if (id != null) {
            /*
             * kode untuk edit data firestore dengan mengambil id
             * */
            db.collection("inventorys").document()
                    .set(inventory)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            /*
             * kode untuk menambahkan data dengan .add
             * */
            db.collection("inventorys")
                    .add(inventory)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }

    }
}
package com.example.myinventoryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myinventoryapp.adapter.InventoryAdapter;
import com.example.myinventoryapp.model.Inventory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainInventory extends AppCompatActivity {
    /*
     * mendefinisikan variable yang akan dipakai
     *
     * */
    private RecyclerView recyclerView;
    private FloatingActionButton btnAdd;

    /*
     * inisialisasi object firebase firestore
     * untuk menghubungkan dengan firebase
     * */
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private List<Inventory> list = new ArrayList<>();
    private InventoryAdapter inventoryAdapter;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_inventory);

        recyclerView = findViewById(R.id.recycler_view);
        btnAdd = findViewById(R.id.btn_add);

        progressDialog = new ProgressDialog(MainInventory.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Mengambil data...");
        inventoryAdapter = new InventoryAdapter(getApplicationContext(), list);

        inventoryAdapter.setDialog(new InventoryAdapter.Dialog() {
            @Override
            public void onClick(int pos) {
                final CharSequence[] dialogItem = {"Edit", "Hapus"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainInventory.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            /*
                             * Melemparkan data ke class berikutnya
                             *
                             * */
                            case 0:
                                Intent intent = new Intent(getApplicationContext(), EditorInv.class);
                                intent.putExtra("id", list.get(pos).getId());
                                intent.putExtra("name", list.get(pos).getName());
                                intent.putExtra("qty", list.get(pos).getQty());
                                intent.putExtra("loc", list.get(pos).getLoc());
                                intent.putExtra("status", list.get(pos).getStatus());
                                startActivity(intent);
                                deleteData(list.get(pos).getId());
                                break;
                            case 1:
                                /*
                                 * memanggil class delete data
                                 * */
                                deleteData(list.get(pos).getId());
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(inventoryAdapter);

        btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), EditorInv.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    private void getData() {
        progressDialog.show();
        /*
         * Mengambil data dari firebase
         * */
        db.collection("inventorys")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        list.clear();
                        if (task.isSuccessful()) {
                            /*
                             * code ini mengambil data dari collection
                             * */
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                /*
                                 * Data apa saja yang ingin diambil dari collection
                                 * */
                                Inventory inventory = new Inventory(document.getString("name"), document.getString("qty"), document.getString("loc"), document.getString("status"));
                                inventory.setId(document.getId());
                                list.add(inventory);
                            }
                            inventoryAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), "Data gagal di ambil", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    /*
     * method untuk mengahapus data
     * */
    private void deleteData(String id) {
        progressDialog.show();
        db.collection("inventorys").document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Data gagal di hapus", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                        getData();
                    }
                });
    }
}
package com.example.c302firebasefirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirestoreDetailsActivity extends AppCompatActivity {

    private static final String TAG = "IntDetailsActivity";

    private EditText etName, etCost;
    private Button btnUpdate, btnDelete;

    private FireStore inventory;

    private FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firestore_details);

        etName = (EditText) findViewById(R.id.editTextName);
        etCost = (EditText) findViewById(R.id.editTextCost);
        btnUpdate = (Button) findViewById(R.id.buttonUpdate);
        btnDelete = (Button) findViewById(R.id.buttonDelete);

        Intent intent = getIntent();
        String id = (String) intent.getStringExtra("InventoryID");

        firestore = FirebaseFirestore.getInstance();

        final CollectionReference collection = firestore.collection("inventory");
        final DocumentReference docRef = collection.document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        etName.setText(String.valueOf(document.get("name")));
                        etCost.setText(String.valueOf(document.get("cost")));
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                        Toast.makeText(FirestoreDetailsActivity.this, "No such document", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Task 4: Update Student record based on input given
                String name = etName.getText().toString();
                Double cost = Double.parseDouble(etCost.getText().toString());
                FireStore newInventory = new FireStore(name, cost);

                docRef
                        .set(newInventory)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                Toast.makeText(getApplicationContext(), "Inventory record updated successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Inventory record updated failed", Toast.LENGTH_SHORT).show();

                                Log.w(TAG, "Error writing document", e);
                            }
                        });


                finish();
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Task 5: Delete Student record based on student id

                docRef
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Inventory record deleted successfully", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Inventory record deleted unsuccessfull", Toast.LENGTH_SHORT).show();

                                Log.w(TAG, "Error deleting document", e);
                            }
                        });


                finish();
            }
        });

    }
}

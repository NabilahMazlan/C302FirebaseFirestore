package com.example.c302firebasefirestore;

import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddFirestoreActivity extends AppCompatActivity {

    private static final String TAG = "AddFirestoreActivity";

    private EditText etName, etCost;
    private Button btnAdd;
    private FirebaseFirestore firestore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_firestore);

        etName = (EditText) findViewById(R.id.editTextName);
        etCost = (EditText) findViewById(R.id.editTextCost);
        btnAdd = (Button) findViewById(R.id.buttonAdd);

        firestore = FirebaseFirestore.getInstance();
        final CollectionReference collection = firestore.collection("inventory");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: Task 3: Retrieve name and age from EditText and instantiate a new Student object
                //TODO: Task 4: Add student to database and go back to main screen

                String name = etName.getText().toString();
                Double cost = Double.parseDouble(etCost.getText().toString());
                FireStore newInventory = new FireStore(name, cost);

                collection.add(newInventory)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                Toast.makeText(AddFirestoreActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                                Toast.makeText(AddFirestoreActivity.this, "Not Successful", Toast.LENGTH_SHORT).show();

                            }
                        });

                finish();

            }
        });

    }

}

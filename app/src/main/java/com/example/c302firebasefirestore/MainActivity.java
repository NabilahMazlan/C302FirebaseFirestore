package com.example.c302firebasefirestore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView lvFirestore;
    private ArrayList<FireStore> alFirestore;
    private ArrayAdapter<FireStore> aaFirestore;

    private FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvFirestore = (ListView)findViewById(R.id.listViewFirestore);

        firestore = FirebaseFirestore.getInstance();
        final CollectionReference collection = firestore.collection("inventory");

        collection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                alFirestore = new ArrayList<FireStore>();

                for (QueryDocumentSnapshot doc : value) {
                    if (doc.get("name") != null) {
                        FireStore newInventory = doc.toObject(FireStore.class);
                        newInventory.setId(doc.getId());
                        alFirestore.add(newInventory);

                    }
                }

                aaFirestore = new ArrayAdapter<FireStore>(getApplicationContext(), android.R.layout.simple_list_item_1, alFirestore);
                lvFirestore.setAdapter(aaFirestore);
            }
        });

        lvFirestore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FireStore inventory = alFirestore.get(i);  // Get the selected Student
                Intent intent = new Intent(MainActivity.this, FirestoreDetailsActivity.class);
                intent.putExtra("InventoryID", inventory.getId());
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.addItem) {

            Intent intent = new Intent(getApplicationContext(), AddFirestoreActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

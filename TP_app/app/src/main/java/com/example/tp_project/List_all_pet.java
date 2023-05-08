package com.example.tp_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class List_all_pet extends AppCompatActivity {
    private ArrayList<Animal> animalList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AnimalAdapter animalAdapter;
    Button btn_add_pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_pet);

        btn_add_pet = findViewById(R.id.btn_add_pet);
        recyclerView = findViewById(R.id.re_all_pet);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        animalAdapter = new AnimalAdapter(animalList);
        recyclerView.setAdapter(animalAdapter);

        btn_add_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_Register_pet();
            }
        });

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userAnimalsRef = FirebaseDatabase.getInstance().getReference("animals").child(uid);

        userAnimalsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Animal animal = snapshot.getValue(Animal.class);
                        animalList.add(animal);
                    }
                    animalAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(List_all_pet.this, "No animals found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(List_all_pet.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void show_Register_pet() {
        Intent register_pet = new Intent(this, Register_pet.class);
        startActivity(register_pet);
    }
}

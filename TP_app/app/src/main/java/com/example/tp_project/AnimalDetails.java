package com.example.tp_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AnimalDetails extends AppCompatActivity {
    ImageView image;
    TextView name, age, weight;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_details);
        b1 = findViewById(R.id.b1);
        image = findViewById(R.id.details_image);
        age = findViewById(R.id.details_age);
        name = findViewById(R.id.details_name);
        weight = findViewById(R.id.details_weight);
        Animal animal = getIntent().getParcelableExtra("animal");
        String key = animal.getId();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("animals").child(uid).child(key);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Integer b1_state = dataSnapshot.child("b1").getValue(Integer.class); // получаем данные о состоянии кнопки b1
                    if (b1_state != null) {
                        if (b1_state == 0) {
                            b1.setBackgroundColor(getResources().getColor(R.color.red));
                        }
                        else {
                            b1.setBackgroundColor(getResources().getColor(R.color.green));
                        }

                    } else {
                        myRef.setValue(0); //если данных нет, кнопка красная по умолчанию
                    }
                } else {
                    myRef.setValue(0); //если данных нет, кнопка красная по умолчанию
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("getb1", "onCancelled", databaseError.toException());
            }
        });

        Glide.with(this).load(animal.getImageUrl()).into(image);
        name.setText(animal.getName());
        age.setText(String.valueOf(animal.getAge()));
        weight.setText(String.valueOf(animal.getWeight()));


    }
}
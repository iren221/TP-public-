package com.example.tp_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class AnimalDetails extends AppCompatActivity {
    ImageView image;
    TextView name, age, weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_details);
        image = findViewById(R.id.details_image);
        age = findViewById(R.id.details_age);
        name = findViewById(R.id.details_name);
        weight = findViewById(R.id.details_weight);
        Animal animal = getIntent().getParcelableExtra("animal");

        Glide.with(this).load(animal.getImageUrl()).into(image);
        name.setText(animal.getName());
        age.setText(String.valueOf(animal.getAge()));
        weight.setText(String.valueOf(animal.getWeight()));
        

    }
}
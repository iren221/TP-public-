package com.example.tp_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Register_pet extends AppCompatActivity {

    Button btn_download_photo_pet, btn_ok;
    ImageView download_photo_pet;
    EditText pet_name, pet_age, pet_weight;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pet);

        btn_download_photo_pet =  findViewById(R.id.btn_download_photo_pet);
        download_photo_pet = findViewById(R.id.download_photo_pet);
        pet_name = findViewById(R.id.pet_name);
        pet_age = findViewById(R.id.age_pet);
        pet_weight = findViewById(R.id.weight_pet);

        btn_download_photo_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null && data.getData() != null) {
            if (resultCode == RESULT_OK) {
                download_photo_pet.setImageURI(data.getData());
            }
        }
    }

    private void getImage() {
        Intent intentChooser = new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser, 1);
    }
}
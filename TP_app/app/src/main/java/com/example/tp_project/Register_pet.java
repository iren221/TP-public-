package com.example.tp_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Register_pet extends AppCompatActivity {

    Button btn_download_photo_pet, btn_ok;

    ImageView download_photo_pet;
    EditText pet_name, pet_age, pet_weight;
    FirebaseStorage storage;
    StorageReference storageRef;
    private Uri upload_Uri;
    AppCompatImageButton btn_recommendations;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pet);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        //storageRef = FirebaseStorage.getInstance().getReference("ImagePet");
        btn_download_photo_pet =  findViewById(R.id.btn_download_photo_pet);
        download_photo_pet = findViewById(R.id.download_photo_pet);
        pet_name = findViewById(R.id.pet_name);
        pet_age = findViewById(R.id.age_pet);
        pet_weight = findViewById(R.id.weight_pet);
        btn_recommendations = findViewById(R.id.btn_recommendations);


        btn_download_photo_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage();
            }
        });

        btn_recommendations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recommend = new Intent(Register_pet.this, RecommendationsActivity.class);
                startActivity(recommend);
            }
        });

//        btn_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (!pet_name.getText().toString().isEmpty() && !pet_age.getText().toString().isEmpty() && !pet_weight.getText().toString().isEmpty()) {
//                    Toast.makeText(getApplicationContext(), "Good", Toast.LENGTH_SHORT).show();
//
//                }else {
//                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }




    private void getImage() {
        Intent intentChooser = new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentChooser, "Select your image free here..."), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null && data.getData() != null) {
            if (resultCode == RESULT_OK) {
                download_photo_pet.setImageURI(data.getData());
                uploadImage();
                //upload_Uri = data.getData();

//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), upload_Uri);
//                    download_photo_pet.setImageBitmap(bitmap);
//                }catch (IOException e) {
//                    e.printStackTrace();
//                }
                //uploadImage();
            }
        }
    }
    private void uploadImage() {
        Bitmap bitmap = ((BitmapDrawable) download_photo_pet.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        final StorageReference myRef = storageRef.child(System.currentTimeMillis() + "pet_image");
        UploadTask up = myRef.putBytes(byteArray);
        Task<Uri> task = up.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return myRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                upload_Uri = task.getResult();
                Toast.makeText(Register_pet.this, "Картинка загружена в хранилище",
                        Toast.LENGTH_SHORT).show();
            }
        });

//        if (upload_Uri != null) {
//            ProgressDialog progressDialog = new ProgressDialog(Register_pet.this);
//            progressDialog.setTitle("Загрузка картинки");
//            progressDialog.show();
//
//            String imageName = Double.toString(Math.random());
//            storageRef.child("image").child(imageName).putFile(upload_Uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    progressDialog.dismiss();
//                    Toast.makeText(getApplicationContext(), "Image upload", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }

    }
}
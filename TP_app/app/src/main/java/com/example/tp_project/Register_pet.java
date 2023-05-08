package com.example.tp_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    //private Uri upload_Uri;
    AppCompatImageButton btn_recommendations;
    DatabaseReference mDatabase;
    private Uri selectedImageUri;
    private Intent data;
    private Uri imageUri;
    private Intent resultIntent;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pet);

//        storage = FirebaseStorage.getInstance();
//        storageRef = storage.getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        btn_download_photo_pet =  findViewById(R.id.btn_download_photo_pet);
        download_photo_pet = findViewById(R.id.download_photo_pet);
        pet_name = findViewById(R.id.pet_name);
        pet_age = findViewById(R.id.age_pet);
        pet_weight = findViewById(R.id.weight_pet);
        btn_recommendations = findViewById(R.id.btn_recommendations);
        btn_ok = findViewById(R.id.btn_OK);


        btn_download_photo_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveClick(view);
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
        Intent intentChooser = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //intentChooser.setType("image/*");
        //intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser, 1);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Получаем URI выбранного изображения
            selectedImageUri = data.getData();

            // Отображаем выбранное изображение в ImageView
            download_photo_pet.setImageURI(selectedImageUri);

            // Сохраняем данные Intent для передачи в onSaveClick
            this.data = data;


//        if (requestCode == 1 && data != null && data.getData() != null) {
//            if (resultCode == RESULT_OK) {
//                upload_Uri = data.getData();
//                download_photo_pet.setImageURI(upload_Uri);
//                uploadImage();


//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), upload_Uri);
//                    download_photo_pet.setImageBitmap(bitmap);
//                }catch (IOException e) {
//                    e.printStackTrace();
//                }
                //uploadImage();
            }
        }


    public void onSaveClick(View view) {
        String name = pet_name.getText().toString();
        int age = Integer.parseInt(pet_age.getText().toString());
        float weight = Float.parseFloat(pet_weight.getText().toString());
        String Age = pet_age.getText().toString();
        String Weight = pet_weight.getText().toString();
        if (name.trim().isEmpty() || Age.trim().isEmpty() || Weight.trim().isEmpty()) {
            Toast.makeText(this, "Заполните пустые поля", Toast.LENGTH_SHORT).show();
            return;
        }

        // Получаем ссылку на Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("images/" + selectedImageUri.getLastPathSegment());
        UploadTask uploadTask = imageRef.putFile(selectedImageUri);
        // Загружаем изображение животного в Firebase Storage

        uploadTask.addOnSuccessListener(taskSnapshot -> {
            // Получаем URL-адрес загруженного файла
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String imageURL = uri.toString();



                // Создаем новый объект Animal
                Animal animal = new Animal(null, name, age, weight, imageURL);
                saveAnimalToFirebase(animal);

                // Передаем данные о животном в MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("animal", animal);
                setResult(Activity.RESULT_OK, resultIntent);
                Intent intent = new Intent(this, List_all_pet.class);
                startActivity(intent);
            });
        }).addOnFailureListener(exception -> {
            // Обработка ошибки загрузки файла
            Toast.makeText(this, "Error uploading image", Toast.LENGTH_SHORT).show();
        });

    }

    private void uploadImage() {
        // Проверяем, было ли выбрано изображение
        if (imageUri != null) {
            // Определяем путь в Firebase Storage, где будет храниться изображение
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images" + System.currentTimeMillis() + ".jpg");

            // Загружаем изображение в Firebase Storage
            storageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Если загрузка успешна, получаем URL изображения
                        Task<Uri> downloadUrl = storageRef.getDownloadUrl();
                        downloadUrl.addOnSuccessListener(uri -> {
                            // Сохраняем URL изображения в базу данных
                            String imageUrl= uri.toString();
                            saveImageToFirebaseDatabase(imageUrl);
                        });
                    })
                    .addOnFailureListener(exception -> {
                        // Если загрузка не удалась, выводим сообщение об ошибке
                        Toast.makeText(getApplicationContext(), "Загрузка изображения не удалась", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void saveImageToFirebaseDatabase(String imageUrl) {
        String uid = user.getUid(); //получаем id пользователя
        String name = data.getStringExtra("name");
        int age = data.getIntExtra("age", 0);
        float weight = data.getFloatExtra("weight", 0.0f);
        byte[] imageData = data.getByteArrayExtra("image");
        String animalId = data.getStringExtra("animalId");
        // Создаем новый объект CatDog с данными о животном
        Animal animal = new Animal(animalId, name, age, weight,imageUrl);



        // Сохраняем новый объект в базе данных
        mDatabase.child(uid).setValue(animal);

        // Выводим сообщение об успешном сохранении
        Toast.makeText(getApplicationContext(), "Данные о животном успешно сохранены", Toast.LENGTH_SHORT).show();
    }
    private void saveAnimalToFirebase(Animal animal) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("animals");
        String uid = user.getUid(); //получаем id пользователя
        // Сохраняем данные животного в Firebase Database
        String key = databaseRef.push().getKey(); // получаем ключ изображения
        Animal newAnimal = new Animal(uid, animal.getName(), animal.getAge(), animal.getWeight(), animal.getImageUrl());
        databaseRef.child(uid).child(key).setValue(newAnimal);
    }



//        Bitmap bitmap = ((BitmapDrawable) download_photo_pet.getDrawable()).getBitmap();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] byteArray = baos.toByteArray();
//        final StorageReference myRef = storageRef.child(System.currentTimeMillis() + "pet_image");
//        UploadTask up = myRef.putBytes(byteArray);
//        Task<Uri> task = up.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//            @Override
//            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                return myRef.getDownloadUrl();
//            }
//        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//            @Override
//            public void onComplete(@NonNull Task<Uri> task) {
//                upload_Uri = task.getResult();
//                Toast.makeText(Register_pet.this, "Картинка загружена в хранилище",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });

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

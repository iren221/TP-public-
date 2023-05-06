package com.example.tp_project;

import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class FirebaseStorageHelper {

    private final FirebaseStorage mFirebaseStorage;
    private final StorageReference mStorageReference;

    public FirebaseStorageHelper() {
        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageReference = mFirebaseStorage.getReference().child("animal_images");
    }

    public void uploadImage(Uri imageUri, final OnSuccessListener<Uri> onSuccessListener,
                            final OnFailureListener onFailureListener) {
        StorageReference imageReference = mStorageReference.child(imageUri.getLastPathSegment());
        imageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageReference.getDownloadUrl().addOnSuccessListener(onSuccessListener);
            }
        }).addOnFailureListener(onFailureListener);
    }

}

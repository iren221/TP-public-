package com.example.tp_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button btn_regist;
    Button btn_login;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputName;
    private TextInputLayout textInputPassword;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference myRef = database.getReference("test");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        mAuth = FirebaseAuth.getInstance();
        btn_regist = findViewById(R.id.button2);
        btn_login = findViewById(R.id.button1);

        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterWindow();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {showLoginWindow("");

            }
        });

    }

    private void showLoginWindow(String mail) {
        AlertDialog.Builder dialog_login = new AlertDialog.Builder(this);
        dialog_login.setTitle("Вход")
                .setMessage("Введите все данные");

        LayoutInflater inflater = LayoutInflater.from(this);
        View aut_window = inflater.inflate(R.layout.aut_window, null);
        dialog_login.setView(aut_window);

        textInputEmail = aut_window.findViewById(R.id.textInputEmail);
        textInputPassword = aut_window.findViewById(R.id.textInputPassword);

        if (!mail.isEmpty())  {
            textInputEmail.getEditText().setText(mail);
        }

        dialog_login.setPositiveButton("Войти", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String email = textInputEmail.getEditText().getText().toString().trim();
                String password = textInputPassword.getEditText().getText().toString().trim();
                loginUser(email, password);

            }

            })
                .setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        AlertDialog alert = dialog_login.create();
        alert.show();
    }

    private void register_petActivity() {
        Intent register_pet = new Intent(this, Register_pet.class);
        startActivity(register_pet);
    }
    private void list_petActivity() {
        Intent list_pet = new Intent(this, List_all_pet.class);
        startActivity(list_pet);
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("login", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            myRef.child("users").child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() { // функция считывающая данные из бд
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "error",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    else {

                                        String s = task.getResult().child("name").getValue().toString(); //получаем имя пользователя из бд
                                        Toast.makeText(MainActivity.this, "hello "+s,
                                                Toast.LENGTH_SHORT).show();
                                        list_petActivity();

                                    }
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("login", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            showLoginWindow(email); //если авторизация не удалась, заново открывается окно

                        }
                    }
                });
    }


    private void showRegisterWindow() {
        AlertDialog.Builder dialog_register = new AlertDialog.Builder(this);
        dialog_register.setTitle("Регистрация")
                .setMessage("Введите все данные");

        LayoutInflater inflater = LayoutInflater.from(this);
        View register_window = inflater.inflate(R.layout.register_window, null);
        dialog_register.setView(register_window);

        textInputEmail = register_window.findViewById(R.id.textInputEmail);
        textInputName = register_window.findViewById(R.id.textInputName);
        textInputPassword = register_window.findViewById(R.id.textInputPassword);

        dialog_register.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String email = textInputEmail.getEditText().getText().toString().trim();
                String password = textInputPassword.getEditText().getText().toString().trim();
                String name = textInputName.getEditText().getText().toString().trim();
                addUser(email, password, name);
            }
        })

        .setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alert = dialog_register.create();
        alert.show();
    }

    private void addUser(String email, String password, String name) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("signup", "createUserWithEmail:success");
                            Toast.makeText(MainActivity.this, "Registration succesed.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            myRef.child("users").child(user.getUid()).child("name").setValue(name); // добавляем в бд имя пользователя


                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("signup", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();
                            showRegisterWindow(); //если регистрация не удалась, заново открывается окно
                            //updateUI(null);
                        }
                    }
                });
    }
}
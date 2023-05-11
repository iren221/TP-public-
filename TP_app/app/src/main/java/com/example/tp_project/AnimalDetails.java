package com.example.tp_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
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
    Button eat, kupanie, apteka, walk, grumer, puzirek, btn_notification;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_details);

        eat = findViewById(R.id.eat);
        kupanie = findViewById(R.id.kupanie);
        apteka = findViewById(R.id.apteka);
        walk = findViewById(R.id.walk);
        grumer = findViewById(R.id.grumer);
        puzirek = findViewById(R.id.puzirek);

        btn_notification = findViewById(R.id.btn_notification);

        image = findViewById(R.id.details_image);
        age = findViewById(R.id.details_age);
        name = findViewById(R.id.details_name);
        weight = findViewById(R.id.details_weight);
        Animal animal = getIntent().getParcelableExtra("animal");
        String key = animal.getId();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("animals").child(uid).child(key);


        btn_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recommend = new Intent(AnimalDetails.this, NotificationActivity.class);
                startActivity(recommend);
                //NotificationManagerCompat.from(getApplicationContext()).areNotificationsEnabled();
            }
        });


        //1 кнопка
        eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Integer b1_state = dataSnapshot.child("eat").getValue(Integer.class); // получаем данные о состоянии кнопки b1
                            if (b1_state != null) {
                                if (b1_state == 0) {
                                    eat.setBackgroundColor(getResources().getColor(R.color.green));
                                    myRef.child("eat").setValue(1);
                                }
                                else {
                                    eat.setBackgroundColor(getResources().getColor(R.color.red));
                                    myRef.child("eat").setValue(0);
                                }

                            } else {
                                myRef.child("eat").setValue(1);//если данных нет, кнопка красная по умолчанию
                                eat.setBackgroundColor(getResources().getColor(R.color.green));
                            }
                        } else {
                            myRef.child("eat").setValue(1); //если данных нет, кнопка красная по умолчанию
                            eat.setBackgroundColor(getResources().getColor(R.color.green));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("getb1", "onCancelled", databaseError.toException());
                    }
                });
            }
        });
        //2 кнопка
        kupanie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Integer b1_state = dataSnapshot.child("kupanie").getValue(Integer.class); // получаем данные о состоянии кнопки b1
                            if (b1_state != null) {
                                if (b1_state == 0) {
                                    kupanie.setBackgroundColor(getResources().getColor(R.color.green));
                                    myRef.child("kupanie").setValue(1);
                                }
                                else {
                                    kupanie.setBackgroundColor(getResources().getColor(R.color.red));
                                    myRef.child("kupanie").setValue(0);
                                }

                            } else {
                                myRef.child("kupanie").setValue(1);//если данных нет, кнопка красная по умолчанию
                                kupanie.setBackgroundColor(getResources().getColor(R.color.green));
                            }
                        } else {
                            myRef.child("kupanie").setValue(1); //если данных нет, кнопка красная по умолчанию
                            kupanie.setBackgroundColor(getResources().getColor(R.color.green));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("getb2", "onCancelled", databaseError.toException());
                    }
                });
            }
        });
        //3 кнопка
        apteka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Integer b1_state = dataSnapshot.child("apteka").getValue(Integer.class); // получаем данные о состоянии кнопки b1
                            if (b1_state != null) {
                                if (b1_state == 0) {
                                    apteka.setBackgroundColor(getResources().getColor(R.color.green));
                                    myRef.child("apteka").setValue(1);
                                }
                                else {
                                    apteka.setBackgroundColor(getResources().getColor(R.color.red));
                                    myRef.child("apteka").setValue(0);
                                }

                            } else {
                                myRef.child("apteka").setValue(1);//если данных нет, кнопка красная по умолчанию
                                apteka.setBackgroundColor(getResources().getColor(R.color.green));
                            }
                        } else {
                            myRef.child("apteka").setValue(1); //если данных нет, кнопка красная по умолчанию
                            apteka.setBackgroundColor(getResources().getColor(R.color.green));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("getb3", "onCancelled", databaseError.toException());
                    }
                });
            }
        });
        //4 кнопка
        walk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Integer b1_state = dataSnapshot.child("walk").getValue(Integer.class); // получаем данные о состоянии кнопки b1
                            if (b1_state != null) {
                                if (b1_state == 0) {
                                    walk.setBackgroundColor(getResources().getColor(R.color.green));
                                    myRef.child("walk").setValue(1);
                                }
                                else {
                                    walk.setBackgroundColor(getResources().getColor(R.color.red));
                                    myRef.child("walk").setValue(0);
                                }

                            } else {
                                myRef.child("walk").setValue(1);//если данных нет, кнопка красная по умолчанию
                                walk.setBackgroundColor(getResources().getColor(R.color.green));
                            }
                        } else {
                            myRef.child("walk").setValue(1); //если данных нет, кнопка красная по умолчанию
                            walk.setBackgroundColor(getResources().getColor(R.color.green));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("getb4", "onCancelled", databaseError.toException());
                    }
                });
            }
        });
        //5 кнопка
        grumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Integer b1_state = dataSnapshot.child("grumer").getValue(Integer.class); // получаем данные о состоянии кнопки b1
                            if (b1_state != null) {
                                if (b1_state == 0) {
                                    grumer.setBackgroundColor(getResources().getColor(R.color.green));
                                    myRef.child("grumer").setValue(1);
                                }
                                else {
                                    grumer.setBackgroundColor(getResources().getColor(R.color.red));
                                    myRef.child("grumer").setValue(0);
                                }

                            } else {
                                myRef.child("grumer").setValue(1);//если данных нет, кнопка красная по умолчанию
                                grumer.setBackgroundColor(getResources().getColor(R.color.green));
                            }
                        } else {
                            myRef.child("grumer").setValue(1); //если данных нет, кнопка красная по умолчанию
                            grumer.setBackgroundColor(getResources().getColor(R.color.green));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("getb5", "onCancelled", databaseError.toException());
                    }
                });
            }
        });
        //6 кнопка
        puzirek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Integer b1_state = dataSnapshot.child("puzirek").getValue(Integer.class); // получаем данные о состоянии кнопки b1
                            if (b1_state != null) {
                                if (b1_state == 0) {
                                    puzirek.setBackgroundColor(getResources().getColor(R.color.green));
                                    myRef.child("puzirek").setValue(1);
                                }
                                else {
                                    puzirek.setBackgroundColor(getResources().getColor(R.color.red));
                                    myRef.child("puzirek").setValue(0);
                                }

                            } else {
                                myRef.child("puzirek").setValue(1);//если данных нет, кнопка красная по умолчанию
                                puzirek.setBackgroundColor(getResources().getColor(R.color.green));
                            }
                        } else {
                            myRef.child("puzirek").setValue(1); //если данных нет, кнопка красная по умолчанию
                            puzirek.setBackgroundColor(getResources().getColor(R.color.green));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("getb6", "onCancelled", databaseError.toException());
                    }
                });
            }
        });

        Glide.with(this).load(animal.getImageUrl()).into(image);
        name.setText(animal.getName());
        age.setText(String.valueOf(animal.getAge()));
        weight.setText(String.valueOf(animal.getWeight()));


    }
}
package com.example.tp_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class RecommendationsActivity extends AppCompatActivity {

    String[] name_recommend = new String[] {"Первый совет", "Второй совет", "Третий совет", "Четвертый совет", "Пятый совет"};
    int[] photo_recommend = new int[] {R.drawable.logo, R.drawable.mask_group, R.drawable.mask_group_1, R.drawable.mask_group_2, R.drawable.mask_group_3};
    ListView list_recommend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);

        list_recommend = findViewById(R.id.list_recommend);

        ProgramAdapter programAdapter = new ProgramAdapter(RecommendationsActivity.this, name_recommend, photo_recommend);
        list_recommend.setAdapter(programAdapter);

    }
}
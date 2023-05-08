package com.example.tp_project;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import com.bumptech.glide.Glide;


import java.util.ArrayList;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {

    private ArrayList<Animal> animalList;
    private Context context;

    public AnimalAdapter(ArrayList<Animal> animalList, Context context) {

        this.animalList = animalList;
        this.context = context;
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.elem_pet, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        Animal animal = animalList.get(position);
        holder.bind(animal);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AnimalDetails.class);
                intent.putExtra("animal", animal);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }

    public static class AnimalViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView ageTextView;
        private TextView weightTextView;
        private ImageView animalImageView;

        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_pet_name);
            ageTextView = itemView.findViewById(R.id.text_pet_age);
            weightTextView = itemView.findViewById(R.id.text_pet_weight);
            animalImageView = itemView.findViewById(R.id.image_photo_pet);
        }

        public void bind(Animal animal) {
            nameTextView.setText(animal.getName());
            ageTextView.setText(String.valueOf(animal.getAge()));
            weightTextView.setText(String.valueOf(animal.getWeight()));
            Glide.with(itemView.getContext()).load(animal.getImageUrl()).into(animalImageView);
        }
    }
}


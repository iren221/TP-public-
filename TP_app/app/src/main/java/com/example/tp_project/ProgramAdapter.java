package com.example.tp_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

public class ProgramAdapter extends ArrayAdapter<String>{

    Context context;
    int[] images;
    String[] names;
    ImageButton itemImage;


    public ProgramAdapter(Context context, String[] names, int[] images) {
        super(context, R.layout.recommendations, R.id.name_recommend, names);

        this.context = context;
        this.images = images;
        this.names = names;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        View singleItem = convertView;
        ProgramViewHolder holder = null;
        if (singleItem == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            singleItem = layoutInflater.inflate(R.layout.recommendations, parent, false);

            holder = new ProgramViewHolder(singleItem);
            singleItem.setTag(holder);

        }
        else {
            holder = (ProgramViewHolder) singleItem.getTag();

        }
        holder.itemImage.setImageResource(images[position]);
        holder.petName.setText(names[position]);
        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "You clicked", Toast.LENGTH_SHORT).show();
            }
        });
        return singleItem;
    }
}


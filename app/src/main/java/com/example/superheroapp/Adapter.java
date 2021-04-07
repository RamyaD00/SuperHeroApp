package com.example.superheroapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    LayoutInflater inflater;
    List<Hero> heros;

    public Adapter(Context ctx, List<Hero> heros){
        this.inflater = LayoutInflater.from(ctx);
        this.heros = heros;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // bind the data
        holder.HeroName.setText(heros.get(position).getSuperheroname());
        holder.Strength.setText(heros.get(position).getSvalue());
        Picasso.get().load(heros.get(position).getCoverImage()).into(holder.heroCoverImage);

    }

    @Override
    public int getItemCount() {
        return heros.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{
        TextView HeroName, Strength;
        ImageView heroCoverImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            HeroName = itemView.findViewById(R.id.HeroName);
            Strength = itemView.findViewById(R.id.Strength);
            heroCoverImage = itemView.findViewById(R.id.coverImage);

            // handle onClick

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Do Something With this Click", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

package com.csm43.storyteller;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {
   /* Classe que permite adicionar itens em um RecyclerView */

   private ArrayList<Item> storyList;
   private OnItemClickListener listener;

   public interface OnItemClickListener{
       void onItemClick(int position);
   }

   public void setOnItemClickListener(OnItemClickListener listener){
       this.listener = listener;
   }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new StoryViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        Item currentItem = storyList.get(position);
        holder.storyTitle.setText(currentItem.getTitle());
        holder.image.setImageResource(currentItem.getImageId());
        holder.arrow.setImageResource(currentItem.getArrowId());
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    public StoryAdapter(ArrayList<Item> storyList){
        this.storyList = storyList;
    }

    public static class StoryViewHolder extends RecyclerView.ViewHolder{
        public TextView storyTitle;
        public ImageView image;
        public ImageView arrow;

        public StoryViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            storyTitle = itemView.findViewById(R.id.itemTitle);
            image = itemView.findViewById(R.id.itemImg);
            arrow = itemView.findViewById(R.id.itemArrow);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}

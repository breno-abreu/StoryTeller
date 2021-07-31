package com.csm43.storyteller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {
   private ArrayList<StoryItem> storyList;

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_item, parent, false);
        return new StoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        StoryItem currentItem = storyList.get(position);
        holder.storyTitle.setText(currentItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    public StoryAdapter(ArrayList<StoryItem> storyList){
        this.storyList = storyList;
    }

    public static class StoryViewHolder extends RecyclerView.ViewHolder{
        public TextView storyTitle;

        public StoryViewHolder(@NonNull View itemView) {
            super(itemView);

            storyTitle = itemView.findViewById(R.id.storyTitleList);
        }
    }
}

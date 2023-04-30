package com.sobhy.quran.ui.audio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sobhy.quran.R;

import java.util.ArrayList;

public class LetterAdapter extends RecyclerView.Adapter<LetterAdapter.LetterViewHolder> {
    ArrayList<String> StringArrayList = new ArrayList<>();

    OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String letter);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public LetterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_letter, parent, false);
        return new LetterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LetterViewHolder holder, int position) {
        String String = StringArrayList.get(position);
        holder.letterTextView.setText(String);
    }

    @Override
    public int getItemCount() {
        return StringArrayList.size();
    }

    public void setList(ArrayList<String> StringArrayList) {
        this.StringArrayList = StringArrayList;
        notifyDataSetChanged();
    }

    class LetterViewHolder extends RecyclerView.ViewHolder {
        TextView letterTextView;
        public LetterViewHolder(@NonNull View itemView) {
            super(itemView);
            letterTextView= itemView.findViewById(R.id.item_reciter_letter);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(StringArrayList.get(position));
                }
            });
        }
    }
}
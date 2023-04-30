package com.sobhy.quran.ui.audio;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sobhy.quran.R;

import java.util.ArrayList;

public class ReciterDownloadAdapter extends RecyclerView.Adapter<ReciterDownloadAdapter.ReciterDownloadViewHolder> {
    ArrayList<String> StringArrayList = new ArrayList<>();

    @NonNull
    @Override
    public ReciterDownloadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_reciter, parent, false);
        return new ReciterDownloadViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReciterDownloadViewHolder holder, int position) {
        String String = StringArrayList.get(position);
        holder.reciterDownloadName.setText(String);
        holder.itemView.setOnClickListener(v -> {
            Intent intent= new Intent(holder.itemView.getContext(), ListOfSuras.class);
            intent.putExtra("reciterNameFromDownload", String);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return StringArrayList.size();
    }

    public void setList(ArrayList<String> StringArrayList) {
        this.StringArrayList = StringArrayList;
        notifyDataSetChanged();
    }

    static class ReciterDownloadViewHolder extends RecyclerView.ViewHolder {
        TextView reciterDownloadName;
        public ReciterDownloadViewHolder(@NonNull View itemView) {
            super(itemView);
            reciterDownloadName= itemView.findViewById(R.id.item_reciter_name);
        }
    }
}
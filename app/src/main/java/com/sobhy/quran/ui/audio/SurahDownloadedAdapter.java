package com.sobhy.quran.ui.audio;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sobhy.quran.R;
import com.sobhy.quran.pojo.quran.AudioSura;

import java.util.ArrayList;

public class SurahDownloadedAdapter extends RecyclerView.Adapter<SurahDownloadedAdapter.SurahDownloadedViewHolder> {
    ArrayList<AudioSura> SoraArrayList = new ArrayList<>();

    @NonNull
    @Override
    public SurahDownloadedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_audio_suras, parent, false);
        return new SurahDownloadedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SurahDownloadedViewHolder holder, int position) {
        AudioSura Sora = SoraArrayList.get(position);
        holder.surahDownloadName.setText(Sora.getSuraName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent= new Intent(holder.itemView.getContext(), PlayerActivity.class);
            intent.putExtra("suraList", SoraArrayList);
            intent.putExtra("position", holder.getAdapterPosition());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return SoraArrayList.size();
    }

    public void setList(ArrayList<AudioSura> SoraArrayList) {
        this.SoraArrayList = SoraArrayList;
        notifyDataSetChanged();
    }

    static class SurahDownloadedViewHolder extends RecyclerView.ViewHolder {
        TextView surahDownloadName;
        ImageView downloadImage;
        public SurahDownloadedViewHolder(@NonNull View itemView) {
            super(itemView);
            surahDownloadName= itemView.findViewById(R.id.item_audio_suras_name);
            downloadImage= itemView.findViewById(R.id.item_audio_suras_download);
            downloadImage.setVisibility(View.GONE);
        }
    }
}
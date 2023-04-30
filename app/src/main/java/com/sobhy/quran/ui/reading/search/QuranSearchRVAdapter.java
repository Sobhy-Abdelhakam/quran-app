package com.sobhy.quran.ui.reading.search;

import static com.sobhy.quran.ui.reading.search.QuranSearch.source;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sobhy.quran.R;
import com.sobhy.quran.pojo.quran.ShamarlyAyah;
import com.sobhy.quran.ui.reading.OnRecyclerViewItemClickListener;
import com.sobhy.quran.pojo.quran.Ayah;

import java.util.ArrayList;

public class QuranSearchRVAdapter extends RecyclerView.Adapter<QuranSearchRVAdapter.quranSearchViewHolder> {
    ArrayList<Ayah> AyahArrayList = new ArrayList<>();
    ArrayList<ShamarlyAyah> shamarlyAyahArrayList= new ArrayList<>();
    OnRecyclerViewItemClickListener listener;

    ButtonClick click;

    public QuranSearchRVAdapter(OnRecyclerViewItemClickListener listener, ButtonClick click) {
        this.listener = listener;
        this.click= click;
    }

    @NonNull
    @Override
    public quranSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.quran_search_item_view, parent, false);
        quranSearchViewHolder viewHolder = new quranSearchViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull quranSearchViewHolder holder, int position) {
        if(source.equals("QuranContainer")){
            Ayah Ayah = AyahArrayList.get(position);
            holder.soraName.setText(Ayah.getSora_name_ar());
            holder.details.setText("الآية:"+Ayah.getAya_no()+"، صفحة:"+ Ayah.getPage());
            holder.text.setText(Ayah.getAya_text());
            holder.page= Ayah.getPage();
            holder.ayahId= Ayah.getId();
        }
        if(source.equals("ShamarlyQuranContainer")) {
            ShamarlyAyah shamarlyAyah= shamarlyAyahArrayList.get(position);
            holder.soraName.setText(shamarlyAyah.getSura_name());
            holder.details.setText("الآية:"+shamarlyAyah.getAyah_num()+"، صفحة:"+ shamarlyAyah.getPage());
            holder.text.setText(shamarlyAyah.getAyah_text());
            holder.page= shamarlyAyah.getPage();
            holder.ayahId= shamarlyAyah.getAyah_id();
        }

    }

    @Override
    public int getItemCount() {
        if(source.equals("QuranContainer"))
            return AyahArrayList.size();
        if(source.equals("ShamarlyQuranContainer"))
            return shamarlyAyahArrayList.size();
        return 0;
    }

    public void setList(ArrayList<Ayah> AyahArrayList) {
        this.AyahArrayList = AyahArrayList;
        notifyDataSetChanged();
    }
    public void setShamarlyList(ArrayList<ShamarlyAyah> AyahArrayList) {
        this.shamarlyAyahArrayList = AyahArrayList;
        notifyDataSetChanged();
    }


    class quranSearchViewHolder extends RecyclerView.ViewHolder {
        TextView soraName, details, text;
        Button moveButton, tafseerButton;
        int page, ayahId;
        public quranSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            soraName= itemView.findViewById(R.id.search_sora_name);
            details= itemView.findViewById(R.id.search_sora_detals);
            text= itemView.findViewById(R.id.search_ayah_text);
            moveButton= itemView.findViewById(R.id.search_btn_move);
            tafseerButton= itemView.findViewById(R.id.search_btn_tafseer);

           moveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(page);
                }
            });

           tafseerButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   click.tafseerBtnClick(ayahId);
               }
           });
        }
    }

    interface ButtonClick{
        void tafseerBtnClick(int ayahId);
    }
}
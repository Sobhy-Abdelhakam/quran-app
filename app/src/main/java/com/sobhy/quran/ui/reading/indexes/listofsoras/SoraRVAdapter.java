package com.sobhy.quran.ui.reading.indexes.listofsoras;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sobhy.quran.R;
import com.sobhy.quran.ui.reading.OnRecyclerViewItemClickListener;
import com.sobhy.quran.pojo.quran.Sora;

import java.util.ArrayList;

public class SoraRVAdapter extends RecyclerView.Adapter<SoraRVAdapter.SoraViewHolder> {
    ArrayList<Sora> SoraArrayList = new ArrayList<>();
    private OnRecyclerViewItemClickListener listener;

    public SoraRVAdapter(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SoraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_sora, parent, false);
        return new SoraViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SoraViewHolder holder, int position) {
        Sora Sora = SoraArrayList.get(position);
        holder.numberOfSora.setText(Integer.toString(Sora.getSoraNumber()));
        holder.nameOfSora.setText("سورة " + Sora.getArabicName());
        holder.numberOfItsAyat.setText("آياتها: " + Sora.getNumbersOfAyat());
        holder.startPage.setText("صفحة: " + Sora.getStartPage());

        holder.page = Sora.getStartPage();
    }

    @Override
    public int getItemCount() {
        return SoraArrayList.size();
    }

    public void setList(ArrayList<Sora> SoraArrayList) {
        this.SoraArrayList = SoraArrayList;
        notifyDataSetChanged();
    }

    class SoraViewHolder extends RecyclerView.ViewHolder {
        TextView numberOfSora, nameOfSora, numberOfItsAyat, startPage;
        int page;

        public SoraViewHolder(@NonNull View itemView) {
            super(itemView);
            numberOfSora = itemView.findViewById(R.id.item_sora_number);
            nameOfSora = itemView.findViewById(R.id.item_sora_name);
            numberOfItsAyat = itemView.findViewById(R.id.item_number_of_ayat);
            startPage = itemView.findViewById(R.id.item_start_page);

            itemView.setOnClickListener(v -> listener.onItemClick(page));
        }
    }
}
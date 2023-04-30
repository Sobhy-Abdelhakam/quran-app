package com.sobhy.quran.ui.reading.indexes.listofjozz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sobhy.quran.R;
import com.sobhy.quran.ui.reading.OnRecyclerViewItemClickListener;
import com.sobhy.quran.pojo.quran.Jozz;

import java.util.ArrayList;

public class JozzRVAdapter extends RecyclerView.Adapter<JozzRVAdapter.JozzViewHolder> {
    ArrayList<Jozz> JozzArrayList = new ArrayList<>();
    OnRecyclerViewItemClickListener listener;

    public JozzRVAdapter(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public JozzViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_jozz, parent, false);
        return new JozzViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull JozzViewHolder holder, int position) {
        Jozz Jozz = JozzArrayList.get(position);
        holder.numberOfJozz.setText("جزء\n" + Jozz.getJozzNumber());
        holder.FirstAyahInJozz.setText(Jozz.getFirstAyahInJozz());
        holder.startPage.setText("صفحة: " + Jozz.getStartPage());

        holder.jozzStartPage = Jozz.getStartPage();
    }

    @Override
    public int getItemCount() {
        return JozzArrayList.size();
    }

    public void setList(ArrayList<Jozz> JozzArrayList) {
        this.JozzArrayList = JozzArrayList;
        notifyDataSetChanged();
    }

    class JozzViewHolder extends RecyclerView.ViewHolder {
        TextView numberOfJozz, FirstAyahInJozz, startPage;
        int jozzStartPage;

        public JozzViewHolder(@NonNull View itemView) {
            super(itemView);
            numberOfJozz = itemView.findViewById(R.id.item_jozz_number);
            FirstAyahInJozz = itemView.findViewById(R.id.item_jozz_name);
            startPage = itemView.findViewById(R.id.item_start_page);

            itemView.setOnClickListener(v -> listener.onItemClick(jozzStartPage));
        }
    }
}
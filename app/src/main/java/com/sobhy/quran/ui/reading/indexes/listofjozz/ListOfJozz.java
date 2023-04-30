package com.sobhy.quran.ui.reading.indexes.listofjozz;

import static com.sobhy.quran.ui.reading.indexes.IndexesOfQuran.sourceInIndex;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sobhy.quran.R;

public class ListOfJozz extends Fragment {
    RecyclerView jozzRecyclerView;
    JozzRVAdapter jozzRVAdapter;

    public ListOfJozz() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_of_jozz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        jozzRecyclerView = view.findViewById(R.id.jozz_recycler_view);
        jozzRVAdapter = new JozzRVAdapter(pageNumber -> {
            Intent intent = new Intent().putExtra("pageNumber", pageNumber);
            getActivity().setResult(2, intent);
            getActivity().finish();
        });
        if (sourceInIndex.equals("QuranContainer"))
            jozzRVAdapter.setList(JozzListViewModel.getAllJozz(view.getContext()));
        if (sourceInIndex.equals("ShamarlyQuranContainer"))
            jozzRVAdapter.setList(JozzListViewModel.getAllShamarlyJozz(view.getContext()));
        jozzRecyclerView.setAdapter(jozzRVAdapter);
        jozzRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }
}
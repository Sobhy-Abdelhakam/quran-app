package com.sobhy.quran.ui.reading.indexes.listofsoras;

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

public class ListOfSoras extends Fragment {
    RecyclerView soraRecyclerView;
    SoraRVAdapter soraRVAdapter;

    public ListOfSoras() {
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
        return inflater.inflate(R.layout.fragment_list_of_soras, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        soraRecyclerView = view.findViewById(R.id.soras_recycler_view);
        soraRVAdapter = new SoraRVAdapter(pageNumber -> {
            Intent intent = new Intent().putExtra("pageNumber", pageNumber);
            getActivity().setResult(2, intent);
            getActivity().finish();
        });
        if (sourceInIndex.equals("QuranContainer"))
            soraRVAdapter.setList(SoraListViewModel.getAllSoras(view.getContext()));
        if (sourceInIndex.equals("ShamarlyQuranContainer"))
            soraRVAdapter.setList(SoraListViewModel.getAllShamarlySuras(view.getContext()));
        soraRecyclerView.setAdapter(soraRVAdapter);
        soraRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

    }
}
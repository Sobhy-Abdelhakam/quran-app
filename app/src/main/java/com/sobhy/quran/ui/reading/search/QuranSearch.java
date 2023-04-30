package com.sobhy.quran.ui.reading.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.sobhy.quran.R;
import com.sobhy.quran.ui.tafseer.TafseerOfAyah;
import java.util.ArrayList;

public class QuranSearch extends AppCompatActivity implements TextWatcher, AdapterView.OnItemSelectedListener {
    Spinner searchSpinner;
    EditText searchEditText;
    TextView searchResultTextView;
    RecyclerView resultRecyclerView;
    QuranSearchRVAdapter quranSearchRVAdapter;

    static Bundle extras;
    static String source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran_search);
        extras = getIntent().getExtras();
        source = extras.getString("source");

        searchSpinner = findViewById(R.id.quran_search_spinner);
        searchEditText = findViewById(R.id.quran_search_edit_text);
        searchResultTextView = findViewById(R.id.quran_research_results);
        resultRecyclerView = findViewById(R.id.quran_search_recycler_view);
        quranSearchRVAdapter = new QuranSearchRVAdapter(pageNumber -> {
            Intent intent = new Intent().putExtra("pageNumber", pageNumber);
            setResult(1, intent);
            finish();
        }, ayahId -> {
            Intent intent = new Intent(this, TafseerOfAyah.class);
            intent.putExtra("Ayah_Id", ayahId);
            startActivity(intent);
        });
        resultRecyclerView.setAdapter(quranSearchRVAdapter);
        searchSpinner.setOnItemSelectedListener(this);
        searchEditText.addTextChangedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = searchEditText.getText().toString();
        if (text.isEmpty()) {
            quranSearchRVAdapter.setList(new ArrayList<>());
            quranSearchRVAdapter.setShamarlyList(new ArrayList<>());
        } else {
            Runnable searchMethod = null;
            switch (position) {
                case 0:
                    searchMethod = () -> listOfAyahContain(text);
                    break;
                case 1:
                    searchMethod = () -> listOfAyahStartBy(text);
                    break;
                case 2:
                    searchMethod = () -> listOfAyahEndBy(text);
                    break;
            }
            searchMethod.run();
            searchResultTextView.setText(String.format("نتائج البحث: %d", quranSearchRVAdapter.getItemCount()));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int position = searchSpinner.getSelectedItemPosition();
        if (s.toString().isEmpty()) {
            quranSearchRVAdapter.setList(new ArrayList<>());
            quranSearchRVAdapter.setShamarlyList(new ArrayList<>());
        } else {
            switch (position) {
                case 0:
                    listOfAyahContain(s.toString());
                    break;
                case 1:
                    listOfAyahStartBy(s.toString());
                    break;
                case 2:
                    listOfAyahEndBy(s.toString());
                    break;
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        searchResultTextView.setText(String.format("نتائج البحث: %d", quranSearchRVAdapter.getItemCount()));
    }

    private void listOfAyahContain(String text) {
        if (source.equals("QuranContainer")) {
            quranSearchRVAdapter.setList(SearchResultViewModel.getAyatContain(this, text));
        } else {
            quranSearchRVAdapter.setShamarlyList(ShamarlySearchViewModel.getAyatContain(this, text));
        }
    }

    private void listOfAyahStartBy(String text) {
        if (source.equals("QuranContainer")) {
            quranSearchRVAdapter.setList(SearchResultViewModel.getAyatStartWith(this, text));
        } else {
            quranSearchRVAdapter.setShamarlyList(ShamarlySearchViewModel.getAyatStartWith(this, text));
        }
    }

    private void listOfAyahEndBy(String text) {
        if (source.equals("QuranContainer")) {
            quranSearchRVAdapter.setList(SearchResultViewModel.getAyatEndsWith(this, text));
        } else {
            quranSearchRVAdapter.setShamarlyList(ShamarlySearchViewModel.getAyatEndsWith(this, text));
        }
    }
}
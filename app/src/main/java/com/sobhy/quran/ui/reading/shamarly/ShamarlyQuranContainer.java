package com.sobhy.quran.ui.reading.shamarly;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.sobhy.quran.R;
import com.sobhy.quran.ui.reading.indexes.IndexesOfQuran;
import com.sobhy.quran.ui.reading.search.QuranSearch;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;

public class ShamarlyQuranContainer extends AppCompatActivity implements ColorPickerDialogListener {

    ViewPager2 shamarlyViewPager;
    static Toolbar shamarlyToolbar;
    private boolean isViewPagerUpdated = false;
    int clickedPage;
    int openPageInViewPager;

    ActivityResultLauncher<Intent> activityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shamarly_quran_container);
        shamarlyViewPager= findViewById(R.id.shamarly_view_pager);
        shamarlyToolbar= findViewById(R.id.shamarly_toolbar);
        setSupportActionBar(shamarlyToolbar);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        FragmentStateAdapter pagesAdapter = new ShamarlyQuranPagesAdapter(this);
        shamarlyViewPager.setAdapter(pagesAdapter);
        openPageInViewPager= shamarlyViewPager.getCurrentItem();
        shamarlyViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                openPageInViewPager= shamarlyViewPager.getCurrentItem();
            }
        });

        activityResultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getData() != null)
                clickedPage= result.getData().getIntExtra("pageNumber", 1)-1;
            else
                clickedPage= openPageInViewPager;
            shamarlyViewPager.setCurrentItem(clickedPage, false);
            isViewPagerUpdated= true;
        });

    }
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences= getPreferences(MODE_PRIVATE);
        int savedItem = sharedPreferences.getInt("shamarly mushaf current_item", 0);
        if(!isViewPagerUpdated){
            shamarlyViewPager.setCurrentItem(savedItem, false);
        }
        isViewPagerUpdated= false;
    }
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences= getPreferences(MODE_PRIVATE);
        openPageInViewPager= shamarlyViewPager.getCurrentItem();
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putInt("shamarly mushaf current_item", openPageInViewPager);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.container_toolbar_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.container_menu_shange_color:
                ColorPickerDialog.newBuilder().show(this);
                return true;
            case R.id.container_menu_moveto:
                intent= new Intent(getApplicationContext(), IndexesOfQuran.class);
                intent.putExtra("source", "ShamarlyQuranContainer");
                activityResultLauncher.launch(intent);
                return true;
            case R.id.container_menu_search:
                intent= new Intent(getApplicationContext(), QuranSearch.class);
                intent.putExtra("source", "ShamarlyQuranContainer");
                activityResultLauncher.launch(intent);
                return true;
        }
        return false;
    }

    static void shamrlyToggleToolbarAndTaskbar() {
        if (shamarlyToolbar.getVisibility() == View.VISIBLE) {
            shamarlyToolbar.setVisibility(View.GONE);
        } else {
            shamarlyToolbar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onColorSelected(int dialogId, int color) {
        shamarlyViewPager.setBackgroundColor(color);
    }
    @Override
    public void onDialogDismissed(int dialogId) {

    }


    private static final int SHAMARLY_NUM_PAGES= 522;
    private static class ShamarlyQuranPagesAdapter extends FragmentStateAdapter {

        public ShamarlyQuranPagesAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return new ShamarlyPageFragment(position+1);
        }

        @Override
        public int getItemCount() {
            return SHAMARLY_NUM_PAGES;
        }
    }
}
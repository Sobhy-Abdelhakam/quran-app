package com.sobhy.quran.ui.reading;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
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


public class QuranContainer extends AppCompatActivity implements ColorPickerDialogListener{
    private static final int NUM_PAGES= 605;
    private boolean isViewPagerUpdated = false;
    ActivityResultLauncher<Intent> arl;
    ViewPager2 viewPager;
    int openPageInViewPager;
    static Toolbar toolbar;
    FragmentStateAdapter pagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran_container);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        toolbar= findViewById(R.id.material_toolbar);
        viewPager = findViewById(R.id.view_pager);
        setSupportActionBar(toolbar);

        pagesAdapter = new QuranPagesAdapter(this);
        viewPager.setAdapter(pagesAdapter);
        openPageInViewPager= viewPager.getCurrentItem();


        arl= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getData() != null)
                openPageInViewPager= result.getData().getIntExtra("pageNumber", 1);
            viewPager.setCurrentItem(openPageInViewPager, false);
            isViewPagerUpdated= true;
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                openPageInViewPager= viewPager.getCurrentItem();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences= getPreferences(MODE_PRIVATE);
        openPageInViewPager = sharedPreferences.getInt("current_item", 0);
        if(!isViewPagerUpdated){
            viewPager.setCurrentItem(openPageInViewPager, false);
        }
        isViewPagerUpdated= false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences= getPreferences(MODE_PRIVATE);
        openPageInViewPager= viewPager.getCurrentItem();
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putInt("current_item", openPageInViewPager);
        editor.apply();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.container_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.container_menu_shange_color:
                ColorPickerDialog.newBuilder().show(this);
                return true;
            case R.id.container_menu_moveto:
                intent= new Intent(getApplicationContext(), IndexesOfQuran.class);
                intent.putExtra("source", "QuranContainer");
                arl.launch(intent);
                return true;
            case R.id.container_menu_search:
                intent= new Intent(getApplicationContext(), QuranSearch.class);
                intent.putExtra("source", "QuranContainer");
                arl.launch(intent);
                return true;
        }
        return false;
    }

    static void toggleToolbarAndTaskbar() {
        if (toolbar.getVisibility() == View.VISIBLE) {
            toolbar.setVisibility(View.GONE);
        } else {
            toolbar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onColorSelected(int dialogId, int color) {
        viewPager.setBackgroundColor(color);
    }
    @Override
    public void onDialogDismissed(int dialogId) {

    }

    private static class QuranPagesAdapter extends FragmentStateAdapter{

        public QuranPagesAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return new OnePageFragment(position);
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

}
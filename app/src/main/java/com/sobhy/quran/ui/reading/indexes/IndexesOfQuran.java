package com.sobhy.quran.ui.reading.indexes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.sobhy.quran.R;
import com.sobhy.quran.ui.reading.indexes.listofjozz.ListOfJozz;
import com.sobhy.quran.ui.reading.indexes.listofsoras.ListOfSoras;
import com.google.android.material.tabs.TabLayout;

public class IndexesOfQuran extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    IndexesViewPagerAdapter indexesViewPagerAdapter;
    public static String sourceInIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indexes_of_quran);
        sourceInIndex = getIntent().getExtras().getString("source");

        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.index_view_pager);

        indexesViewPagerAdapter = new IndexesViewPagerAdapter(this);
        viewPager2.setAdapter(indexesViewPagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
    }

    static class IndexesViewPagerAdapter extends FragmentStateAdapter {

        public IndexesViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 1:
                    return new ListOfJozz();
                case 0:
                default:
                    return new ListOfSoras();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }

}
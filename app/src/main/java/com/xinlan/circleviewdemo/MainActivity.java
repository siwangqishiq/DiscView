package com.xinlan.circleviewdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    int[] layouts = {R.layout.fragment_1, R.layout.fragment_2, R.layout.fragment_3, R.layout.fragment_4};
    private ViewPager mGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGallery = (ViewPager) findViewById(R.id.gallery);
        mGallery.setOffscreenPageLimit(4);
        mGallery.setAdapter(new GalleryAdapter(this.getSupportFragmentManager()));
    }

    private final class GalleryAdapter extends FragmentStatePagerAdapter {

        public GalleryAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return SubFragment.newInstance(layouts[position]);
        }

        @Override
        public int getCount() {
            return layouts.length;
        }
    }//end inner class
}//end class

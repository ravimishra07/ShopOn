package com.ravimishra.tradzhub.Adapter;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ravimishra.tradzhub.R;

import java.util.ArrayList;

public class BannerAddapter extends PagerAdapter {

    private ArrayList<Integer> images;
    private LayoutInflater inflater;
    private Context context;

    public BannerAddapter(Context context, ArrayList<Integer> images) {

        this.context = context;

        this.images = images;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {

        int img = images.get(position);
        View myImageLayout = inflater.inflate(R.layout.bannerlayout, view, false);

        final ImageView myImage = (ImageView) myImageLayout.findViewById(R.id.img_banner);

        myImage.setImageResource(img);
        // myImage.setScaleType(ImageView.ScaleType.FIT_XY);

        view.addView(myImageLayout);


        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {

        return view.equals(o);
    }

}

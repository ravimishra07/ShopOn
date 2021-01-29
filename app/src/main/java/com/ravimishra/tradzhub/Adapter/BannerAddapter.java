package com.ravimishra.tradzhub.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ravimishra.tradzhub.R;

import java.util.List;

public class BannerAddapter extends PagerAdapter {

    private final List<String> imagesurls;
    private final LayoutInflater inflater;
    private final Context context;

    public BannerAddapter(Context context, List<String> imagesurls) {
        this.context = context;
        this.imagesurls = imagesurls;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imagesurls.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {

        String imgUrl = imagesurls.get(position);
        View myImageLayout = inflater.inflate(R.layout.bannerlayout, view, false);
        final ImageView myImage = myImageLayout.findViewById(R.id.img_banner);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.place_holder_image)
                .error(R.drawable.place_holder_image);
        Glide.with(context).load(imgUrl).apply(options).into(myImage);
        view.addView(myImageLayout);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

}

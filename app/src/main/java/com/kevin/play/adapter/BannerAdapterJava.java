package com.kevin.play.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.request.RequestOptions;
import com.kevin.play.R;
import com.kevin.play.bean.HomeBannerData;
import com.kevin.play.view.GlideApp;

import java.util.List;

/**
 * Created by Kevin on 2019/1/3<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 * <b>add description on 2019/1/3</b><br>
 * GlideApp不支持Kotlin，通过java来实现
 */
public class BannerAdapterJava extends PagerAdapter {
    private Context context;
    private List<HomeBannerData> data;

    public BannerAdapterJava(Context context, List<HomeBannerData> data) {
        this.context = context;
        this.data = data;
    }

    public void addData(List<HomeBannerData> d) {
        data.clear();
        HomeBannerData bannerData = d.get(0);
        if (d.size() > 1) {
            HomeBannerData homeBannerData = d.get(d.size() - 1);
            data.add(0, homeBannerData);
        }
        data.addAll(d);
        data.add(data.size(), bannerData);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_banner, null);
        ImageView ivBanner = view.findViewById(R.id.ivBanner);
        HomeBannerData homeBannerData = data.get(position);
        GlideApp.with(context)
                .load(homeBannerData.getImagePath())
                .apply(new RequestOptions().centerCrop())
                .into(ivBanner);
        ivBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onViewPagerClick(position);
                }
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public interface OnViewPagerClickListener {
        void onViewPagerClick(int position);
    }

    private OnViewPagerClickListener listener;

    public void setOnViewPagerClickListener(OnViewPagerClickListener l) {
        listener = l;
    }
}

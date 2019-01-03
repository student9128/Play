package com.kevin.play.base;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.Transition;
import com.kevin.play.R;

/**
 * Created by Kevin on 2018/12/18<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> views;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        this.views = new SparseArray<>();
    }

    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getRootView() {
        return itemView;
    }

    public void setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
    }

    public void setImageResource(int viewId, int drawableId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(drawableId);
    }

    public void setImageResource(int viewId, String url, Context context) {
        ImageView iv = getView(viewId);
        RequestManager with = Glide.with(context);
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(new RequestOptions().centerCrop())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
//                .transition(DrawableTransitionOptions.withCrossFade())
                .thumbnail(0.3f)
                .into(iv);

    }

    public void setBackground(int viewId, int r) {
        ViewGroup v = getView(viewId);
        v.setBackgroundColor(r);
    }

    /**
     * 子控件点击事件
     * @param viewId
     * @param position
     */
    public void onChildClick(final int viewId, final int position) {
        getView(viewId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (childClickListener != null) {
                    childClickListener.onChildClick(viewId, position);
                }
            }
        });
    }

    /**
     * 条目点击事件
     * @param position
     */
    public void onSubItemClick(final int position) {
        getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onSubItemClick(position);
                }
            }
        });
    }

    private OnChildClickListener childClickListener;
    private OnSubItemClickListener listener;

    public interface OnChildClickListener {
        void onChildClick(int viewId, int position);

    }

    public interface OnSubItemClickListener {
        void onSubItemClick(int position);

    }

    public void setOnChildClickListener(OnChildClickListener childClickListener) {
        this.childClickListener = childClickListener;
    }

    public void setOnSubItem(OnSubItemClickListener listener) {
        this.listener = listener;
    }
}

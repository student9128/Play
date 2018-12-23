package com.kevin.play.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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
    public void setImageResource(int viewId, String url, Context context){
        ImageView iv = getView(viewId);
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().centerCrop())
                .into(iv);

    }

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
    public interface OnSubItemClickListener{
        void onSubItemClick(int position);

    }

    public void setOnChildClickListener(OnChildClickListener childClickListener) {
        this.childClickListener = childClickListener;
    }

    public void setOnSubItem(OnSubItemClickListener listener) {
        this.listener = listener;
    }
}

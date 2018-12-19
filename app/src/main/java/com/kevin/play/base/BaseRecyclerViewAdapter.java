package com.kevin.play.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.kevin.play.R;

import java.util.List;

/**
 * Created by Kevin on 2018/12/18<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    public Context context;
    public List<T> data;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_FOOTER = 2;


    public BaseRecyclerViewAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_FOOTER:
                view = LayoutInflater.from(context).inflate(R.layout.layout_footer, viewGroup, false);
                break;
            case TYPE_NORMAL:
                view = LayoutInflater.from(context).inflate(layoutId(), viewGroup, false);
                break;
            default:
                view = LayoutInflater.from(context).inflate(layoutId(), viewGroup, false);
                break;
        }
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int position) {
        if (data.size() > 0) {
            if (getItemViewType(position) == TYPE_NORMAL) {
                bindViewHolder(baseViewHolder, data.get(position), position);
            }
        }
        if (getItemViewType(position) == TYPE_FOOTER) {
            if (loadMoreListener != null) {
                loadMoreListener.onLoadMore();
            }
        }

    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();//开启自动加载更多
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    protected abstract void bindViewHolder(BaseViewHolder viewHolder, T t, int position);

    public abstract int layoutId();

    public interface OnRecyclerItemClickListener {
        void onRecyclerItemClick(View v, int position);
    }

    private OnRecyclerItemClickListener listener;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener l) {
        listener = l;
    }

    private OnLoadMoreListener loadMoreListener;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener l) {
        loadMoreListener = l;
    }


}

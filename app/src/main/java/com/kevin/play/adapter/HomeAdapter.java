package com.kevin.play.adapter;

import android.content.Context;
import android.view.View;
import com.kevin.play.R;
import com.kevin.play.base.BaseRecyclerViewAdapter;
import com.kevin.play.base.BaseViewHolder;
import com.kevin.play.bean.Content;
import com.kevin.play.bean.Tag;

import java.util.List;

/**
 * Created by Kevin on 2018/12/19<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class HomeAdapter extends BaseRecyclerViewAdapter<Content> implements BaseViewHolder.OnChildClickListener {
    public HomeAdapter(Context context, List<Content> data) {
        super(context, data);
    }

    public void updateData(List<Content> d) {
        data.clear();
        data.addAll(d);
        notifyDataSetChanged();
    }

    public void addData(List<Content> d) {
        data.addAll(d);
        notifyDataSetChanged();
    }

    @Override
    protected void bindViewHolder(BaseViewHolder viewHolder, Content content, int position) {
        String author = content.getAuthor();
        String title = content.getTitle();
        boolean collect = content.getCollect();
        String niceDate = content.getNiceDate();
        String superChapterName = content.getSuperChapterName();
        List<Tag> tags = content.getTags();
        if (tags.size() > 0) {
            viewHolder.getView(R.id.tv_tag).setVisibility(View.VISIBLE);
            String name = tags.get(0).getName();
            viewHolder.setText(R.id.tv_tag, name);
        } else {
            viewHolder.getView(R.id.tv_tag).setVisibility(View.GONE);
        }
        viewHolder.setText(R.id.tv_author, "作者：" + author);
        viewHolder.setText(R.id.tv_title, title);
        viewHolder.setText(R.id.tv_category, "分类：" + superChapterName);
        viewHolder.setText(R.id.tv_update_time, niceDate);
        if (collect) {
            viewHolder.setImageResource(R.id.iv_favorite, R.drawable.ic_favorite_filled);
        } else {
            viewHolder.setImageResource(R.id.iv_favorite, R.drawable.ic_favorite);
        }
        viewHolder.getView(R.id.iv_favorite).setTag(position);
        viewHolder.onChildClick(R.id.iv_favorite, position);
        viewHolder.setOnChildClickListener(this);

    }

    @Override
    public int layoutId() {
        return R.layout.adapter_home_article;
    }

    @Override
    public void onChildClick(int viewId, int position) {
        if (childClickListener!=null){
            childClickListener.onChildItemClick(viewId,position);
        }

    }

    private OnChildItemClickListener childClickListener;

    public interface OnChildItemClickListener {
        void onChildItemClick(int viewId, int position);
    }

    public void setOnChildItemClickListener(OnChildItemClickListener childClickListener) {
        this.childClickListener = childClickListener;
    }
}

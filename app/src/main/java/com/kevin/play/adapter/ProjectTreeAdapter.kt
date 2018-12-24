package com.kevin.play.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import com.kevin.play.R
import com.kevin.play.base.BaseRecyclerViewAdapter
import com.kevin.play.base.BaseViewHolder
import com.kevin.play.bean.Content
import com.kevin.play.bean.ProjectTitle

/**
 * Created by Kevin on 2018/12/20<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
open class ProjectTreeAdapter(context: Context, data: MutableList<ProjectTitle>?) : BaseRecyclerViewAdapter<ProjectTitle>(context, data), BaseViewHolder.OnSubItemClickListener {

    private var index = 0;
    private var listener: OnRecyclerItemClickListener? = null
    override fun bindViewHolder(viewHolder: BaseViewHolder, t: ProjectTitle, position: Int) {
        val name = t.name
        viewHolder.setText(R.id.tv_title, name)
        viewHolder.onSubItemClick(position)
        viewHolder.setOnSubItem(this)

    }

    override fun layoutId(): Int {
        return R.layout.adapter_project_tree
    }

    override fun setOnRecyclerItemClickListener(l: OnRecyclerItemClickListener) {
        listener = l
    }

    override fun onSubItemClick(position: Int) {
        if (listener != null) {
            listener!!.onRecyclerItemClick(position)
            index = position
        }

    }

}
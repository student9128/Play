package com.kevin.play.adapter

import android.content.Context
import android.widget.ImageView
import com.kevin.play.R
import com.kevin.play.base.BaseRecyclerViewAdapter
import com.kevin.play.base.BaseViewHolder
import com.kevin.play.bean.Image
import com.kevin.play.bean.ProjectList

/**
 * Created by Kevin on 2018/12/21<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class ProjectListAdapter(context: Context, data: MutableList<ProjectList>) : BaseRecyclerViewAdapter<ProjectList>(context, data), BaseViewHolder.OnChildClickListener {

    override fun bindViewHolder(viewHolder: BaseViewHolder, t: ProjectList, position: Int) {
        val author = t.author
        val title = t.title
        val niceDate = t.niceDate
        val desc = t.desc
        val collect = t.collect
        val envelopePic = t.envelopePic
        with(viewHolder) {
            setText(R.id.tv_title, title)
            setText(R.id.tv_author, author)
            setText(R.id.tv_time, niceDate)
            setText(R.id.tv_des, desc)
//            setImageResource(R.id.iv_pre, envelopePic, context)
            if (collect) {
                setImageResource(R.id.iv_favorite, R.drawable.ic_favorite_filled)
            } else {
                setImageResource(R.id.iv_favorite, R.drawable.ic_favorite)
            }
        }
//        viewHolder.getView<ImageView>(R.id.iv_favorite)
        viewHolder.onChildClick(R.id.iv_favorite, position)
        viewHolder.onChildClick(R.id.tv_title, position)
        viewHolder.onChildClick(R.id.tv_des, position)
        viewHolder.setOnChildClickListener(this)
    }


    override fun layoutId(): Int {
        return R.layout.adapter_project_list
    }

    override fun onChildClick(viewId: Int, position: Int) {
        if (childClickListener != null) {
            childClickListener!!.onChildItemClick(viewId, position)
        }
    }

    private var childClickListener: OnChildItemClickListener? = null

    open interface OnChildItemClickListener {
        fun onChildItemClick(viewId: Int, position: Int)
    }

    open fun setOnChildItemClickListener(childItemClickListener: OnChildItemClickListener) {
        this.childClickListener = childItemClickListener
    }
}
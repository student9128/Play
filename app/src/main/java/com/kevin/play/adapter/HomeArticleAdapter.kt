package com.kevin.play.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.kevin.play.R
import com.kevin.play.bean.Content

/**
 * Created by Kevin on 2018/12/18<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class HomeArticleAdapter(var context: Context, var data: MutableList<Content>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_NORMAL = 1
    private val TYPE_FOOTER = 2

    fun updateData(d: List<Content>) {
        data.clear()
        data.addAll(d)
        notifyDataSetChanged()
    }

    fun addData(d: List<Content>) {
        data.addAll(d)
        notifyDataSetChanged()
    }

    fun setData(position: Int, d: Content) {
        data[position] = d
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var vh: RecyclerView.ViewHolder
        return when (viewType) {
            TYPE_NORMAL -> {
                var v = LayoutInflater.from(context).inflate(R.layout.adapter_home_article, parent, false)
                MyViewHolder(v)
            }
            TYPE_FOOTER -> {
                var v = LayoutInflater.from(context).inflate(R.layout.layout_footer, parent, false)
                FooterViewHolder(v)
            }
            else -> {
                var v = LayoutInflater.from(context).inflate(R.layout.adapter_home_article, parent, false)
                MyViewHolder(v)
            }
        }

    }

    override fun getItemCount(): Int {
        return if (data == null) 0 else data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            var h = holder as MyViewHolder
            val content = data[position]
            with(content) {
                with(h) {
                    tvAuthor.text = "作者:$author"
                    tvCategory.text = "分类:$superChapterName"
                    tvTitle.text = title
                    tvUpdateTime.text = niceDate
                    if (tags.isNotEmpty()) {
                        tvTag.text = tags[0].name
                        tvTag.visibility = View.VISIBLE
                    } else {
                        tvTag.visibility = View.GONE
                    }
                    if (collect) {
                        ivFavorite.setImageResource(R.drawable.ic_favorite_filled)
                    } else {
                        ivFavorite.setImageResource(R.drawable.ic_favorite)
                    }

                }
            }
            h.ivFavorite.setOnClickListener {
                if (childClickListener != null) {
                    childClickListener!!.onChildItemClick(R.id.iv_favorite, position)
                }
            }
        }
        if (getItemViewType(position) == TYPE_FOOTER) {
            if (loadMoreListener != null) {
                loadMoreListener!!.onLoadMore()
            }
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvAuthor: TextView = itemView.findViewById<TextView>(R.id.tv_author)
        var tvTag: TextView = itemView.findViewById<TextView>(R.id.tv_tag)
        var tvTitle: TextView = itemView.findViewById<TextView>(R.id.tv_title)
        var tvUpdateTime: TextView = itemView.findViewById<TextView>(R.id.tv_update_time)
        var tvCategory: TextView = itemView.findViewById<TextView>(R.id.tv_category)
        var ivFavorite: ImageView = itemView.findViewById<ImageView>(R.id.iv_favorite)

    }

    class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            itemCount - 1 -> TYPE_FOOTER
            else -> {
                TYPE_NORMAL
            }
        }
    }

    private var childClickListener: OnChildItemClickListener? = null

    interface OnChildItemClickListener {
        fun onChildItemClick(viewId: Int, position: Int)
    }

    fun setOnChildItemClickListener(childClickListener: OnChildItemClickListener) {
        this.childClickListener = childClickListener
    }

    private var loadMoreListener: OnLoadMoreListener? = null

    interface OnLoadMoreListener {
        fun onLoadMore()
    }

    fun setOnLoadMoreListener(l: OnLoadMoreListener) {
        loadMoreListener = l
    }
}
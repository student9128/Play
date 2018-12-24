package com.kevin.play.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kevin.play.R
import com.kevin.play.util.LogUtils

/**
 * Created by Kevin on 2018/12/18<br></br>
 * Blog:http://student9128.top/<br></br>
 * Describe:<br></br>
 */
abstract class BaseRecyclerViewAdapter<T>(var context: Context, var data: MutableList<T>?) : RecyclerView.Adapter<BaseViewHolder>() {

    private var listener: OnRecyclerItemClickListener? = null

    private var loadMoreListener: OnLoadMoreListener? = null

    var dataType = -2//0显示空,

    private var isShowFooter = false

    open fun setShowFoot(isShowFooter: Boolean) {
        this.isShowFooter = isShowFooter
        notifyDataSetChanged()
    }

    private fun getShowFoot(): Boolean {
        return isShowFooter
    }

    open fun updateData(d: List<T>) {
        data!!.clear()
        data!!.addAll(d)
        dataType = 3
        notifyDataSetChanged()
    }

    open fun updateData(d: List<T>, showFoot: Boolean) {
        data!!.clear()
        data!!.addAll(d)
        dataType = 4
        isShowFooter = showFoot
        notifyDataSetChanged()
    }

    open fun addData(d: List<T>) {
        getAddDataCount(d)
        data!!.addAll(d)
        if (d.isEmpty()) {
            dataType = 4
        }
        notifyDataSetChanged()
    }

    open fun dataError() {
        dataType = -1
    }


    private fun getAddDataCount(d: List<T>) {
        dataType = d.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseViewHolder {
        var view: View? = null
        view = when (viewType) {
            TYPE_FOOTER -> LayoutInflater.from(context).inflate(R.layout.layout_footer, viewGroup, false)
            TYPE_NORMAL -> LayoutInflater.from(context).inflate(layoutId(), viewGroup, false)
            TYPE_NODATA -> LayoutInflater.from(context).inflate(R.layout.layout_footer_nodata, viewGroup, false)
            TYPE_NODATA_SHOW -> LayoutInflater.from(context).inflate(R.layout.layout_footer_nodata_show, viewGroup, false)
            else -> LayoutInflater.from(context).inflate(layoutId(), viewGroup, false)
        }
        return BaseViewHolder(view!!)
    }

    override fun onBindViewHolder(baseViewHolder: BaseViewHolder, position: Int) {
        if (data!!.size > 0) {
            if (getItemViewType(position) == TYPE_NORMAL) {
                bindViewHolder(baseViewHolder, data!![position], position)
            }
        }
        if (getItemViewType(position) == TYPE_FOOTER) {
            if (loadMoreListener != null) {
                loadMoreListener!!.onLoadMore()
            }
        }
//        when (getItemViewType(position)) {
//            TYPE_NORMAL -> {
//                bindViewHolder(baseViewHolder, data!![position], position)
//            }
//            TYPE_FOOTER -> {
//                if (loadMoreListener != null) {
//                    loadMoreListener!!.onLoadMore()
//                }
//            }
//            TYPE_NODATA -> {
//            }
//            TYPE_NODATA_SHOW -> {
//            }
//        }

    }


    override fun getItemCount(): Int {
        return if (data == null) 0 else data!!.size//开启自动加载更多
    }

    override fun getItemViewType(position: Int): Int {
        LogUtils.d("BaseRecyclerView", "position=$position")
        LogUtils.d("BaseRecyclerView", "getShowFoot=" + getShowFoot())
        return if (position >= itemCount - 1) {
            if (dataType == 4) {
                TYPE_NODATA_SHOW
            } else {
                TYPE_FOOTER
            }
        } else {
            TYPE_NORMAL
        }
    }

    protected abstract fun bindViewHolder(viewHolder: BaseViewHolder, t: T, position: Int)

    abstract fun layoutId(): Int

    interface OnRecyclerItemClickListener {
        fun onRecyclerItemClick(position: Int)
    }

    open fun setOnRecyclerItemClickListener(l: OnRecyclerItemClickListener) {
        listener = l
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }

    fun setOnLoadMoreListener(l: OnLoadMoreListener) {
        loadMoreListener = l
    }

    companion object {
        private const val TYPE_NORMAL = 1
        private const val TYPE_FOOTER = 2
        private const val TYPE_NODATA = 3
        private const val TYPE_NODATA_SHOW = 4
        private const val TYPE_ERROR = 5
    }


}

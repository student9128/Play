package com.kevin.play.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.flexbox.FlexboxLayout
import com.kevin.play.R
import com.kevin.play.bean.NavData
import com.kevin.play.util.DisplayUtils
import com.kevin.play.util.LogUtils

/**
 * Created by Kevin on 2018/12/20<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class NavAdapter(var context: Context, var data: MutableList<NavData>) : BaseExpandableListAdapter() {

    open fun updateData(d: List<NavData>) {
        data.clear()
        data.addAll(d)
        notifyDataSetChanged()
    }

    override fun getGroup(groupPosition: Int): Any {
        return data[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        var groupViewHolder: GroupViewHolder? = null
        var view = convertView
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_nav_expandable_title, null)
            groupViewHolder = GroupViewHolder()
            groupViewHolder.tvTitle = view.findViewById(R.id.tv_title)
            view!!.tag = groupViewHolder
        } else {
            groupViewHolder = view!!.tag as GroupViewHolder
        }
        groupViewHolder.tvTitle!!.text = data[groupPosition].name
        return view!!
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return if (data[groupPosition].articles.isNotEmpty()) 1 else 0
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return data[groupPosition].articles[childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        var childViewHolder: ChildViewHolder? = null
        var view = convertView
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_nav_expandable_content, null)
            childViewHolder = ChildViewHolder()
            childViewHolder.flexBox = view.findViewById(R.id.flex_box)
            view!!.tag = childViewHolder
        } else {
            childViewHolder = view!!.tag as ChildViewHolder
        }
        var count = data[groupPosition].articles.size
        LogUtils.d("NavAdapter", "count=$count")
        val childCount = childViewHolder!!.flexBox!!.childCount
        if (childCount != count) {
            childViewHolder!!.flexBox!!.removeAllViews()
            for (i in 0..(count - 1)) {
                var cText = TextView(context)
                var lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                cText.layoutParams = lp
                var paddingVertical = DisplayUtils.dip2px(context, 5F)
                var paddingHorizontal = DisplayUtils.dip2px(context, 10F)
                cText.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical)
                cText.gravity = Gravity.CENTER
                cText.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                cText.text = data[groupPosition].articles[i].title
                childViewHolder!!.flexBox!!.addView(cText)
                cText.setOnClickListener {
                    if (listener != null) {
                        listener!!.onChildItemClick(groupPosition, i)
                    }
                }
            }
        }
        return view!!

    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return data.size
    }

    class GroupViewHolder {
        var tvTitle: TextView? = null

    }

    class ChildViewHolder {
        var flexBox: FlexboxLayout? = null
    }

    private var listener: OnChildItemClickListener? = null

    interface OnChildItemClickListener {
        fun onChildItemClick(groupPosition: Int, childPosition: Int)
    }

    open fun setOnChildItemClickListener(l: OnChildItemClickListener) {
        listener = l;
    }

}
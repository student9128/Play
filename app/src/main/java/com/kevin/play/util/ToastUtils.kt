package com.kevin.play.util

import android.content.Context
import android.widget.Toast
import org.jetbrains.annotations.NotNull

/**
 * Created by Kevin on 2018/12/5<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
object ToastUtils {
    var toast: Toast? = null

    fun showToast(@NotNull context: Context, resId: Int, duration: Int) {
        showToast(context, context.getString(resId), duration)
    }

    fun showToast(context: Context, msg: String, duration: Int) {
        if (null == toast) {
            toast = Toast.makeText(context, msg, duration)
        }
        toast!!.setText(msg)
        toast!!.show()
    }

}
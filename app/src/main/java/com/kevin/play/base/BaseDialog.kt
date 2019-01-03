package com.kevin.play.base

import android.content.DialogInterface
import android.support.v4.app.DialogFragment

/**
 * Created by Kevin on 2018/12/28<br></br>
 * Blog:http://student9128.top/<br></br>
 * Describe:<br></br>
 */
open class BaseDialog : DialogFragment() {
    open var mListener: DialogButtonClickListener? = null

    open interface DialogButtonClickListener {
        fun onLeftBtnClick(dialog: DialogInterface)
        fun onRightBtnClick(dialog: DialogInterface)
    }
}

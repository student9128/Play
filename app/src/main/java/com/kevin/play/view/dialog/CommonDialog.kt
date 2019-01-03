package com.kevin.play.view.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import com.kevin.play.R
import com.kevin.play.base.BaseDialog
import com.kevin.play.constant.Constants

/**
 * Created by Kevin on 2019/1/2<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class CommonDialog : BaseDialog() {
    companion object {
        fun newInstance(title: String?, msg: String?, btnLeft: String?, btnRight: String?, listener: DialogButtonClickListener?): CommonDialog {
            var dialog = CommonDialog()
            dialog.mListener = listener
            var bundle = Bundle()
            bundle.putString(Constants.DIALOG_TITLE, title)
            bundle.putString(Constants.DIALOG_MESSAGE, msg)
            bundle.putString(Constants.DIALOG_LEFT_BUTTON, btnLeft)
            bundle.putString(Constants.DIALOG_RIGHT_BUTTON, btnRight)
            dialog.arguments = bundle
            return dialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var bundle = arguments
        val title = bundle!!.getString(Constants.DIALOG_TITLE)
        val message = bundle!!.getString(Constants.DIALOG_MESSAGE)
        val leftBtn = bundle!!.getString(Constants.DIALOG_LEFT_BUTTON)
        var rightBtn = bundle!!.getString(Constants.DIALOG_RIGHT_BUTTON)
        var builder = AlertDialog.Builder(activity!!)
        if (!title.isNullOrEmpty()) {
            builder.setTitle(title)
        }
        builder.setMessage(message)
            .setCancelable(false)
            .setNegativeButton(leftBtn) { dialog, _ ->
                dialog.dismiss()
                mListener!!.onLeftBtnClick(dialog)
            }
            .setPositiveButton(rightBtn) { dialog, _ ->
                dialog.dismiss()
                mListener!!.onRightBtnClick(dialog)
            }

        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }


}
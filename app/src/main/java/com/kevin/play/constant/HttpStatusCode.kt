package com.kevin.play.constant

import android.view.View
import com.kevin.play.R

/**
 * Created by Kevin on 2018/12/20<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
object HttpStatusCode {
    private const val UNAUTHORIZED = 401
    private const val FORBIDDEN = 403
    private const val NOT_FOUND = 404
    private const val REQUEST_TIMEOUT = 408
    private const val INTERNAL_SERVER_ERROR = 500
    private const val BAD_GATEWAY = 502
    private const val SERVICE_UNAVAILABLE = 503
    private const val GATEWAY_TIMEOUT = 504


    fun getErrorDesResId(statusCode: Int): Int {
        var resId = View.NO_ID
        resId = when (statusCode) {
            UNAUTHORIZED -> R.string.http_error_unauthorized
            FORBIDDEN -> R.string.http_error_forbidden
            NOT_FOUND -> R.string.http_error_not_found
            REQUEST_TIMEOUT -> R.string.http_error_request_timeout
            INTERNAL_SERVER_ERROR -> R.string.http_error_internal_server_error
            BAD_GATEWAY -> R.string.http_error_bad_gateway
            SERVICE_UNAVAILABLE -> R.string.http_error_service_unavailable
            GATEWAY_TIMEOUT -> R.string.http_error_gateway_timeout
            else -> R.string.http_error_unknown
        }
        return resId
    }
}
package com.kevin.play.manager

import android.os.Handler


/**
 * Created by Kevin on 2018/12/17<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class ThreadManager {
    companion object {
        open fun runOnSubThread(r: Runnable) {
            ThreadPoolManager.threadInstance
                .createLongThreadPool()
                .execute(r)
        }

        open fun runOnChildThread(r: Runnable) {
            ThreadPoolManager.threadInstance
                .createShortThreadPool()
                .execute(r)
        }

        open fun runOnUIThread(r: Runnable) {
            Handler().post(r)
        }
    }
}
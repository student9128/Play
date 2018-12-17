package com.kevin.play.manager

import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by Kevin on 2018/12/17<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class ThreadPoolManager {
    companion object {
        var threadInstance = ThreadPoolManager()
    }

    private var shortPoolProxy: ThreadPoolProxy? = null
    private var longPoolProxy: ThreadPoolProxy? = null

    /**
     * 联网的线程池
     */
    @Synchronized
    open fun createLongThreadPool(): ThreadPoolProxy {
        if (longPoolProxy == null) {
            longPoolProxy = ThreadPoolProxy(5, 5, 5000)

        }
        return longPoolProxy as ThreadPoolProxy
    }

    /**
     * 读写文件的线程池
     */
    @Synchronized
    open fun createShortThreadPool(): ThreadPoolProxy {
        if (shortPoolProxy == null) {
            shortPoolProxy = ThreadPoolProxy(3, 3, 5000)
        }
        return shortPoolProxy as ThreadPoolProxy
    }

    /**
     * 1. 初始化的线程数量 2. 不包括初始化的线程数量 如果任务比较多 再创建的线程数量 3. 没有任务执行的时候 最多的存活时间
     * 4. 时间单位 5. 排队任务列表
     */
    class ThreadPoolProxy(var corePoolSize: Int, var maximumPoolSize: Int, var time: Long) {
        private var threadPool: ThreadPoolExecutor? = null

        fun execute(r: Runnable) {
            if (threadPool == null) {
                threadPool = ThreadPoolExecutor(corePoolSize, maximumPoolSize, time, TimeUnit.MICROSECONDS, LinkedBlockingDeque<Runnable>(10))
            }
            threadPool!!.execute(r)
        }

        fun cancel(r: Runnable) {
            if (threadPool != null && !threadPool!!.isShutdown && !threadPool!!.isTerminated) {
                threadPool!!.remove(r)

            }
        }


    }
}


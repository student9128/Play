package com.kevin.play.http

import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 * Created by Kevin on 2018/12/5<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 * 信任所有证书
 */
open class SSLSocketFactoryUtils {
    companion object {
        fun createSSLSocketFactory(): SSLSocketFactory {
            val sslContext = SSLContext.getInstance("TLS")
            sslContext!!.init(null, arrayOf(createTrustAllManager()), SecureRandom())
            return sslContext.socketFactory

        }

        open fun createTrustAllManager(): X509TrustManager {
            return object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate?> {
                    return arrayOfNulls(0)
                }

                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                }

                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                }
            }
        }
    }

    open class TrustAllHostnameVerifier : HostnameVerifier {
        override fun verify(hostname: String?, session: SSLSession?): Boolean {
            return true
        }
    }
}
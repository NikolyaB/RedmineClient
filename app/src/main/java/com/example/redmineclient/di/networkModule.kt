package com.example.redmineclient.di

import android.content.Context
import com.example.redmineclient.R
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.logger.Level
import org.koin.dsl.module
import java.io.FileInputStream
import java.security.KeyStore
import java.security.SecureRandom
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager
import javax.security.cert.X509Certificate

val networkModule = module {
    single {
        HttpClient(Android) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            engine {
                logger.level = Level.DEBUG
                sslManager = { httpsURLConnection ->
                    httpsURLConnection.sslSocketFactory = SslSettings.getSslContext()?.socketFactory
                }
            }

            install(ContentNegotiation) {
                json(Json { isLenient = true; ignoreUnknownKeys = true })
            }
        }
    }
}

class TrustAllX509TrustManager : X509TrustManager {
    override fun checkClientTrusted(chain: Array<out java.security.cert.X509Certificate>?, authType: String?) {}

    override fun checkServerTrusted(chain: Array<out java.security.cert.X509Certificate>?, authType: String?) {}

    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate?> = arrayOfNulls(0)
}

object SslSettings {
    fun getSslContext(): SSLContext? {
        val sslContext = SSLContext.getInstance("TLS")
            .apply {
                init(null, arrayOf(TrustAllX509TrustManager()), SecureRandom())
            }
        return sslContext
    }
}
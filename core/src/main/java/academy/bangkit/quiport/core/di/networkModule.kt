package academy.bangkit.quiport.core.di

import academy.bangkit.quiport.core.BuildConfig.BASE_URL
import academy.bangkit.quiport.core.data.source.remote.network.ApiService
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URI
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        val hostname = URI(BASE_URL).host

        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/0vhur4rv1XMbO/Bzg7SUicSLyTsOoBfhpdTyK3yifDQ==")
            .add(hostname, "sha256/rUCrPVDhSTsRzNk3Kd31x/AJ1FxCyuKlGooIEk9epy8=")
            .add(hostname, "sha256/PJepcSEhCduoNGbeFk/D8M63d1+xi002VfGxM1uK6s8=")
            .build()

        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(240, TimeUnit.SECONDS)
            .readTimeout(240, TimeUnit.SECONDS)
//            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()

        retrofit.create(ApiService::class.java)
    }
}
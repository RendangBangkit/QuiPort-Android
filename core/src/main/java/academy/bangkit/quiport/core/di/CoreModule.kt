package academy.bangkit.quiport.core.di

import androidx.room.Room
import academy.bangkit.quiport.core.BuildConfig
import academy.bangkit.quiport.core.data.MessageRepository
import academy.bangkit.quiport.core.data.source.local.LocalDataSource
import academy.bangkit.quiport.core.data.source.local.room.MessageDatabase
import academy.bangkit.quiport.core.data.source.remote.RemoteDataSource
import academy.bangkit.quiport.core.data.source.remote.network.ApiService
import academy.bangkit.quiport.core.domain.repository.IMessageRepository
import academy.bangkit.quiport.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MessageDatabase>().messageDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            MessageDatabase::class.java, "Message.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IMessageRepository> {
        MessageRepository(
            get(),
            get(),
            get()
        )
    }
}
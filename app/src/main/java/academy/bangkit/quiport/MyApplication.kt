package academy.bangkit.quiport

import android.app.Application
import academy.bangkit.quiport.core.di.databaseModule
import academy.bangkit.quiport.core.di.networkModule
import academy.bangkit.quiport.core.di.repositoryModule
import academy.bangkit.quiport.di.useCaseModule
import academy.bangkit.quiport.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}
package academy.bangkit.quiport

import android.app.Application
import academy.bangkit.quiport.core.di.databaseModule
import academy.bangkit.quiport.core.di.networkModule
import academy.bangkit.quiport.core.di.repositoryModule
import academy.bangkit.quiport.di.featureModule
import academy.bangkit.quiport.di.useCaseModule
import academy.bangkit.quiport.di.viewModelModule
import academy.bangkit.quiport.utils.ReleaseTree
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    featureModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}
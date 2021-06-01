package academy.bangkit.quiport.core.di

import academy.bangkit.quiport.core.data.MessageRepository
import academy.bangkit.quiport.core.data.source.local.LocalDataSource
import academy.bangkit.quiport.core.data.source.remote.RemoteDataSource
import academy.bangkit.quiport.core.domain.repository.IMessageRepository
import academy.bangkit.quiport.core.utils.AppExecutors
import org.koin.dsl.module

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
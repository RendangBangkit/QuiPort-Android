package academy.bangkit.quiport.core.di

import academy.bangkit.quiport.core.data.source.local.room.MessageDatabase
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    factory { get<MessageDatabase>().messageDao() }
    single {
        Room.databaseBuilder(
            get(),
            MessageDatabase::class.java, "Message.db"
        ).fallbackToDestructiveMigration().build()
    }
}
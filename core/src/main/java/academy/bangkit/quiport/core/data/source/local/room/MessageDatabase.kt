package academy.bangkit.quiport.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import academy.bangkit.quiport.core.data.source.local.entity.MessageEntity

@Database(entities = [MessageEntity::class], version = 1, exportSchema = false)
abstract class MessageDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDao

}
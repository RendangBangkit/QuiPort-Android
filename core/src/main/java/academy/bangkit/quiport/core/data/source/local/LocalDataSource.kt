package academy.bangkit.quiport.core.data.source.local

import academy.bangkit.quiport.core.data.source.local.entity.MessageEntity
import academy.bangkit.quiport.core.data.source.local.room.MessageDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val messageDao: MessageDao) {
    fun getMessage(): Flow<List<MessageEntity>> = messageDao.getMessage()

    suspend fun insertMessage(messageList: List<MessageEntity>) = messageDao.insertMessage(messageList)
}
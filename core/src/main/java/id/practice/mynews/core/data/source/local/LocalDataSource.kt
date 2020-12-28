package id.practice.mynews.core.data.source.local

import id.practice.mynews.core.data.source.local.entity.MessageEntity
import id.practice.mynews.core.data.source.local.room.MessageDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val messageDao: MessageDao) {
    fun getMessage(): Flow<List<MessageEntity>> = messageDao.getMessage()

    suspend fun insertMessage(messageList: List<MessageEntity>) = messageDao.insertMessage(messageList)
}
package id.practice.mynews.core.data

import id.practice.mynews.core.data.source.local.LocalDataSource
import id.practice.mynews.core.data.source.remote.RemoteDataSource
import id.practice.mynews.core.data.source.remote.network.ApiResponse
import id.practice.mynews.core.data.source.remote.response.MessageResponse
import id.practice.mynews.core.domain.model.Message
import id.practice.mynews.core.domain.repository.IMessageRepository
import id.practice.mynews.core.utils.AppExecutors
import id.practice.mynews.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MessageRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMessageRepository {

    override fun getMessage(): Flow<Resource<List<Message>>> =
        object : NetworkBoundResource<List<Message>, List<MessageResponse>>() {
            override fun loadFromDB(): Flow<List<Message>> {
                return localDataSource.getMessage().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Message>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MessageResponse>>> =
                remoteDataSource.getMessages()

            override suspend fun saveCallResult(data: List<MessageResponse>) {
                val messageList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertMessage(messageList)
            }
        }.asFlow()
}
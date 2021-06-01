package academy.bangkit.quiport.core.data

import academy.bangkit.quiport.core.data.source.local.LocalDataSource
import academy.bangkit.quiport.core.data.source.remote.RemoteDataSource
import academy.bangkit.quiport.core.data.source.remote.network.ApiResponse
import academy.bangkit.quiport.core.data.source.remote.response.MessageResponse
import academy.bangkit.quiport.core.domain.model.Message
import academy.bangkit.quiport.core.domain.repository.IMessageRepository
import academy.bangkit.quiport.core.utils.AppExecutors
import academy.bangkit.quiport.core.utils.DataMapper
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
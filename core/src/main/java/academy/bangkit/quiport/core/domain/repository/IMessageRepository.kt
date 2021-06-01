package academy.bangkit.quiport.core.domain.repository

import academy.bangkit.quiport.core.data.Resource
import academy.bangkit.quiport.core.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface IMessageRepository {
    fun getMessage(): Flow<Resource<List<Message>>>
}
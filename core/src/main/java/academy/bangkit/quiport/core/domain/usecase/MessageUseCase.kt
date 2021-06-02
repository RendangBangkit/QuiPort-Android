package academy.bangkit.quiport.core.domain.usecase

import academy.bangkit.quiport.core.data.Resource
import academy.bangkit.quiport.core.domain.model.message.Message
import kotlinx.coroutines.flow.Flow

interface MessageUseCase {
    fun getMessage(): Flow<Resource<List<Message>>>
}
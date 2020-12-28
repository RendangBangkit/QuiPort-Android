package id.practice.mynews.core.domain.usecase

import id.practice.mynews.core.data.Resource
import id.practice.mynews.core.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageUseCase {
    fun getMessage(): Flow<Resource<List<Message>>>
}
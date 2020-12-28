package id.practice.mynews.core.domain.repository

import id.practice.mynews.core.data.Resource
import id.practice.mynews.core.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface IMessageRepository {
    fun getMessage(): Flow<Resource<List<Message>>>
}
package id.practice.mynews.core.utils

import id.practice.mynews.core.data.source.local.entity.MessageEntity
import id.practice.mynews.core.data.source.remote.response.MessageResponse
import id.practice.mynews.core.domain.model.Message

object DataMapper {
    fun mapResponsesToEntities(input: List<MessageResponse>): List<MessageEntity> {
        val tourismList = ArrayList<MessageEntity>()
        input.map {
            val tourism = MessageEntity(
                messageId = it.messageId,
                welcomeMessage = it.welcomeMessage,
            )
            tourismList.add(tourism)
        }
        return tourismList
    }

    fun mapEntitiesToDomain(input: List<MessageEntity>): List<Message> =
        input.map {
            Message(
                messageId = it.messageId,
                welcomeMessage = it.welcomeMessage,
            )
        }

    fun mapDomainToEntity(input: Message) = MessageEntity(
        messageId = input.messageId,
        welcomeMessage = input.welcomeMessage,
    )
}
package academy.bangkit.quiport.core.utils

import academy.bangkit.quiport.core.data.source.local.entity.MessageEntity
import academy.bangkit.quiport.core.data.source.remote.response.message.MessageResponse
import academy.bangkit.quiport.core.domain.model.message.Message

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
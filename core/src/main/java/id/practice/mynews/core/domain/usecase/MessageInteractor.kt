package id.practice.mynews.core.domain.usecase

import id.practice.mynews.core.domain.repository.IMessageRepository

class MessageInteractor(private val messageRepository: IMessageRepository) : MessageUseCase {
    override fun getMessage() = messageRepository.getMessage()
}
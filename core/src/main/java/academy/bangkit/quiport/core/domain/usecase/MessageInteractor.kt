package academy.bangkit.quiport.core.domain.usecase

import academy.bangkit.quiport.core.domain.repository.IMessageRepository

class MessageInteractor(private val messageRepository: IMessageRepository) : MessageUseCase {
    override fun getMessage() = messageRepository.getMessage()
}
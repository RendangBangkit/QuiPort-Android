package academy.bangkit.quiport.presentation.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import academy.bangkit.quiport.core.domain.usecase.MessageUseCase

class MessageViewModel(messageUseCase: MessageUseCase) : ViewModel() {
    val message = messageUseCase.getMessage().asLiveData()
}
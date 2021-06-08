package academy.bangkit.quiport.presentation.message

import academy.bangkit.quiport.core.domain.usecase.MessageUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData

class MessageViewModel(messageUseCase: MessageUseCase) : ViewModel() {
    val message = messageUseCase.getMessage().asLiveData()
}
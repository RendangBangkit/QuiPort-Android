package academy.bangkit.quiport.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import academy.bangkit.quiport.core.domain.usecase.MessageUseCase

class MainViewModel(messageUseCase: MessageUseCase) : ViewModel() {
    val message = messageUseCase.getMessage().asLiveData()
}
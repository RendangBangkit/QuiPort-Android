package academy.bangkit.quiport.di

import academy.bangkit.quiport.core.domain.usecase.MessageInteractor
import academy.bangkit.quiport.core.domain.usecase.MessageUseCase
import academy.bangkit.quiport.presentation.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MessageUseCase> { MessageInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}
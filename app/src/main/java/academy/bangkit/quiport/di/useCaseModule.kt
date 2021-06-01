package academy.bangkit.quiport.di

import academy.bangkit.quiport.core.domain.usecase.MessageInteractor
import academy.bangkit.quiport.core.domain.usecase.MessageUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory<MessageUseCase> { MessageInteractor(get()) }
}
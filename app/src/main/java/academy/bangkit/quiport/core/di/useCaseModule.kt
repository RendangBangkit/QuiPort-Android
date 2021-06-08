package academy.bangkit.quiport.core.di

import academy.bangkit.quiport.core.domain.usecase.MessageInteractor
import academy.bangkit.quiport.core.domain.usecase.MessageUseCase
import academy.bangkit.quiport.core.domain.usecase.ReportInteractor
import academy.bangkit.quiport.core.domain.usecase.ReportUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory<MessageUseCase> { MessageInteractor(get()) }
    factory<ReportUseCase> { ReportInteractor(get()) }
}
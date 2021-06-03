package academy.bangkit.quiport.di

import academy.bangkit.quiport.presentation.message.MessageViewModel
import academy.bangkit.quiport.presentation.report.ReportViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MessageViewModel(get()) }
    viewModel { ReportViewModel(get()) }
}
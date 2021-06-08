package academy.bangkit.quiport.core.di

import academy.bangkit.quiport.presentation.example.ExampleViewModel
import academy.bangkit.quiport.presentation.main.components.reportList.ReportListViewModel
import academy.bangkit.quiport.presentation.main.components.reportMain.ReportMainViewModel
import academy.bangkit.quiport.presentation.message.MessageViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MessageViewModel(get()) }
    viewModel { ExampleViewModel(get()) }
    viewModel { ReportMainViewModel(get()) }
    viewModel { ReportListViewModel(get()) }
}
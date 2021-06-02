package academy.bangkit.quiport.di

import academy.bangkit.quiport.presentation.main.MainViewModel
import academy.bangkit.quiport.presentation.report.ReportViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { ReportViewModel(get()) }
}
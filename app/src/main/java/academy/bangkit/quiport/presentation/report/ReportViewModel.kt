package academy.bangkit.quiport.presentation.report

import academy.bangkit.quiport.core.domain.usecase.ReportUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData

class ReportViewModel(reportUseCase: ReportUseCase) : ViewModel() {
    val report = reportUseCase.getReport().asLiveData()
}
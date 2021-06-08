package academy.bangkit.quiport.presentation.main.components.reportList

import academy.bangkit.quiport.core.data.Resource
import academy.bangkit.quiport.core.domain.model.report.Report
import academy.bangkit.quiport.core.domain.usecase.ReportUseCase
import androidx.lifecycle.*

class ReportListViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val loadTrigger = MutableLiveData(Unit)
    private fun loadData(): LiveData<Resource<List<Report>>> =
        reportUseCase.getReport().asLiveData()

    val report : LiveData<Resource<List<Report>>> = loadTrigger.switchMap {
        loadData()
    }

    fun refresh() {
        loadTrigger.value = Unit
    }
}
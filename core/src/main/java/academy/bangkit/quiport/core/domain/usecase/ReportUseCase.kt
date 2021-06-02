package academy.bangkit.quiport.core.domain.usecase

import academy.bangkit.quiport.core.data.Resource
import academy.bangkit.quiport.core.domain.model.report.Report
import kotlinx.coroutines.flow.Flow

interface ReportUseCase {
    fun getReport(): Flow<Resource<List<Report>>>
}
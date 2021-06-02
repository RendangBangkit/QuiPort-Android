package academy.bangkit.quiport.core.domain.repository

import academy.bangkit.quiport.core.data.Resource
import academy.bangkit.quiport.core.domain.model.report.Report
import kotlinx.coroutines.flow.Flow

interface IReportRepository {
    fun getReports(): Flow<Resource<List<Report>>>
}
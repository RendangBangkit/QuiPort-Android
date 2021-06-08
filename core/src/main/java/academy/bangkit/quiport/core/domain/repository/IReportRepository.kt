package academy.bangkit.quiport.core.domain.repository

import academy.bangkit.quiport.core.data.Resource
import academy.bangkit.quiport.core.domain.model.report.Report
import kotlinx.coroutines.flow.Flow
import java.io.File

interface IReportRepository {
    fun getReports(): Flow<Resource<List<Report>>>
    fun postReports(
        userId: String, email: String,
        latitude: String, longitude: String, image: File
    ): Flow<Resource<String>>
}
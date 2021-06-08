package academy.bangkit.quiport.core.domain.usecase

import academy.bangkit.quiport.core.data.Resource
import academy.bangkit.quiport.core.domain.model.report.Report
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ReportUseCase {
    fun getReport(): Flow<Resource<List<Report>>>
    fun postReport(
        userId: String,
        email: String,
        latitude: String,
        longitude: String,
        image: File
    ): Flow<Resource<String>>
}
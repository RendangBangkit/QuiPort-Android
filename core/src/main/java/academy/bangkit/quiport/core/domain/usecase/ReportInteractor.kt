package academy.bangkit.quiport.core.domain.usecase

import academy.bangkit.quiport.core.data.Resource
import academy.bangkit.quiport.core.domain.repository.IReportRepository
import kotlinx.coroutines.flow.Flow
import java.io.File

class ReportInteractor(private val reportRepository: IReportRepository) : ReportUseCase {
    override fun getReport() = reportRepository.getReports()
    override fun postReport(
        userId: String, email: String,
        latitude: String, longitude: String, image: File
    ): Flow<Resource<String>> = reportRepository.postReports(
        userId, email, latitude, longitude, image
    )
}
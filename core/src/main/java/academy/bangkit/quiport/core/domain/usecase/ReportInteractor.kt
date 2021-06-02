package academy.bangkit.quiport.core.domain.usecase

import academy.bangkit.quiport.core.domain.repository.IReportRepository

class ReportInteractor(private val reportRepository: IReportRepository) : ReportUseCase {
    override fun getReport() = reportRepository.getReports()
}
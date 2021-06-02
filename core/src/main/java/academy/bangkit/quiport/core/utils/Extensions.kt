package academy.bangkit.quiport.core.utils

import academy.bangkit.quiport.core.data.source.remote.response.report.ReportAddressComponentsResponse
import academy.bangkit.quiport.core.data.source.remote.response.report.ReportAiResultsDetectedClassResponse
import academy.bangkit.quiport.core.data.source.remote.response.report.ReportAiResultsResponse
import academy.bangkit.quiport.core.data.source.remote.response.report.ReportResponse
import academy.bangkit.quiport.core.domain.model.report.Report
import academy.bangkit.quiport.core.domain.model.report.ReportAddressComponents
import academy.bangkit.quiport.core.domain.model.report.ReportAiResults
import academy.bangkit.quiport.core.domain.model.report.ReportAiResultsDetectedClass

fun List<ReportResponse>.toListReport(): List<Report> {
    val listReport = mutableListOf<Report>()

    this.map {
        val report = Report(
            reportId = it.reportId,
            userId = it.userId,
            uid = it.uid,
            email = it.email,
            imageUrl = it.imageUrl,
            otherInfo = it.otherInfo,
            addressComponents = it.addressComponents.toReportAddressComponents(),
            aiResults = it.aiResults.toReportAiResults(),
            createdAt = it.createdAt,
            updatedAt = it.updatedAt
        )

        listReport.add(report)
    }

    return listReport
}

fun ReportAddressComponentsResponse.toReportAddressComponents(): ReportAddressComponents {
    return ReportAddressComponents(
        address = this.address,
        city = this.city,
        province = this.province,
        country = this.country,
        latitude = this.latitude,
        longitude = this.longitude
    )
}

fun ReportAiResultsResponse.toReportAiResults(): ReportAiResults {
    return ReportAiResults(
        detectedClass = this.detectedClass.toReportAiResultsDetectedClass(),
        categories = this.categories
    )
}

fun ReportAiResultsDetectedClassResponse.toReportAiResultsDetectedClass(): ReportAiResultsDetectedClass {
    return ReportAiResultsDetectedClass(
        fire = this.fire,
        accident = this.accident
    )
}
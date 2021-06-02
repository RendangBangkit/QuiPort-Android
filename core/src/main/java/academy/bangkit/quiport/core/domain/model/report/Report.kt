package academy.bangkit.quiport.core.domain.model.report

data class Report(
    val otherInfo: String,
    val createdAt: Long,
    val uid: String,
    val reportId: String,
    val imageUrl: String,
    val addressComponents: ReportAddressComponents,
    val userId: String,
    val email: String,
    val updatedAt: Long,
    val aiResults: ReportAiResults
)
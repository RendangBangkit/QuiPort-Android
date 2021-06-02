package academy.bangkit.quiport.core.domain.model.report

data class ReportAiResults(
	val detectedClass: ReportAiResultsDetectedClass,
	val categories: List<String>
)
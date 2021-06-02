package academy.bangkit.quiport.core.domain.model.report

data class ReportAddressComponents(
	val country: String,
	val address: String,
	val province: String,
	val city: String,
	val latitude: Double,
	val longitude: Double
)
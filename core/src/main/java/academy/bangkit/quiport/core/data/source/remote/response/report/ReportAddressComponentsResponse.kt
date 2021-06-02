package academy.bangkit.quiport.core.data.source.remote.response.report

import com.google.gson.annotations.SerializedName

data class ReportAddressComponentsResponse(

	@field:SerializedName("country")
	val country: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("province")
	val province: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("latitude")
	val latitude: Double,

	@field:SerializedName("longitude")
	val longitude: Double
)
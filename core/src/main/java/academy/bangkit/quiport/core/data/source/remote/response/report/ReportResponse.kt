package academy.bangkit.quiport.core.data.source.remote.response.report

import com.google.gson.annotations.SerializedName

data class ReportResponse(

	@field:SerializedName("otherInfo")
	val otherInfo: String,

	@field:SerializedName("createdAt")
	val createdAt: Long,

	@field:SerializedName("uid")
	val uid: String,

	@field:SerializedName("reportId")
	val reportId: String,

	@field:SerializedName("imageUrl")
	val imageUrl: String,

	@field:SerializedName("address_components")
	val addressComponents: ReportAddressComponentsResponse,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("updatedAt")
	val updatedAt: Long,

	@field:SerializedName("ai_results")
	val aiResults: ReportAiResultsResponse
)
package academy.bangkit.quiport.core.data.source.remote.response.report

import com.google.gson.annotations.SerializedName

data class PostReportResponse(

	@field:SerializedName("idReport")
	val idReport: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

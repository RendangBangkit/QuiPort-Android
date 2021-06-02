package academy.bangkit.quiport.core.data.source.remote.response.report

import com.google.gson.annotations.SerializedName

data class ListReportResponse(

	@field:SerializedName("data")
	val data: List<ReportResponse>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)
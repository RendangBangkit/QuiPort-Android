package academy.bangkit.quiport.core.data.source.remote.response.report

import com.google.gson.annotations.SerializedName

data class ReportAiResultsDetectedClassResponse(

	@field:SerializedName("fire")
	val fire: Int,

	@field:SerializedName("accident")
	val accident: Int
)
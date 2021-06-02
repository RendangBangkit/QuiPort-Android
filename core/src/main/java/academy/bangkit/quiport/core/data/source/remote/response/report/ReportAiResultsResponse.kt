package academy.bangkit.quiport.core.data.source.remote.response.report

import com.google.gson.annotations.SerializedName

data class ReportAiResultsResponse(

	@field:SerializedName("detected_class")
	val detectedClass: ReportAiResultsDetectedClassResponse,

	@field:SerializedName("categories")
	val categories: List<String>
)
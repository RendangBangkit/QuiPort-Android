package academy.bangkit.quiport.core.data.source.remote.network

import academy.bangkit.quiport.core.data.source.remote.response.message.ListMessageResponse
import academy.bangkit.quiport.core.data.source.remote.response.report.ListReportResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("hello")
    suspend fun getMessages(@Query("name") name: String = ""): ListMessageResponse

    @GET("report")
    suspend fun getReports(): ListReportResponse
}

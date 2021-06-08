package academy.bangkit.quiport.core.data.source.remote.network

import academy.bangkit.quiport.core.data.source.remote.response.message.ListMessageResponse
import academy.bangkit.quiport.core.data.source.remote.response.report.ListReportResponse
import academy.bangkit.quiport.core.data.source.remote.response.report.PostReportResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @GET("hello")
    suspend fun getMessages(@Query("name") name: String = ""): ListMessageResponse

    @GET("report")
    suspend fun getReports(): ListReportResponse

    @Multipart
    @POST("report")
    suspend fun postReports(
        @Part("userId") userId : RequestBody,
        @Part("email") email : RequestBody,
        @Part("latitude") latitude : RequestBody,
        @Part("longitude") longitude : RequestBody,
        @Part image: MultipartBody.Part
    ): PostReportResponse
}

package academy.bangkit.quiport.core.data.source.remote

import android.util.Log
import academy.bangkit.quiport.core.data.source.remote.network.ApiResponse
import academy.bangkit.quiport.core.data.source.remote.network.ApiService
import academy.bangkit.quiport.core.data.source.remote.response.message.MessageResponse
import academy.bangkit.quiport.core.data.source.remote.response.report.PostReportResponse
import academy.bangkit.quiport.core.data.source.remote.response.report.ReportResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getMessages(): Flow<ApiResponse<List<MessageResponse>>> {
        return flow {
            try {
                val response = apiService.getMessages()
                val dataArray = response.result

                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.result))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getReports(): Flow<ApiResponse<List<ReportResponse>>> {
        return flow {
            try {
                val response = apiService.getReports()
                val dataArray = response.data

                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.data))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun postReports(
        userId: String,
        email: String,
        latitude: String,
        longitude: String,
        image: File
    ): Flow<ApiResponse<PostReportResponse>> {
        return flow {
            try {
                val rbUserId = userId.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val rbEmail = email.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val rbLatitude = latitude.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val rbLongitude = longitude.toRequestBody("multipart/form-data".toMediaTypeOrNull())

                val requestFile = image.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                val rbImage = MultipartBody.Part.createFormData("image", image.name, requestFile)

                val response = apiService.postReports(
                    rbUserId,
                    rbEmail,
                    rbLatitude,
                    rbLongitude,
                    rbImage
                )

                emit(ApiResponse.Success(response))
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}


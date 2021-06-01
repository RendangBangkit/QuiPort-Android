package academy.bangkit.quiport.core.data.source.remote

import android.util.Log
import academy.bangkit.quiport.core.data.source.remote.network.ApiResponse
import academy.bangkit.quiport.core.data.source.remote.network.ApiService
import academy.bangkit.quiport.core.data.source.remote.response.MessageResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

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
}


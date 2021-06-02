package academy.bangkit.quiport.core.data.repository

import academy.bangkit.quiport.core.data.Resource
import academy.bangkit.quiport.core.data.source.local.LocalDataSource
import academy.bangkit.quiport.core.data.source.remote.RemoteDataSource
import academy.bangkit.quiport.core.data.source.remote.network.ApiResponse
import academy.bangkit.quiport.core.domain.model.report.Report
import academy.bangkit.quiport.core.domain.repository.IReportRepository
import academy.bangkit.quiport.core.utils.AppExecutors
import academy.bangkit.quiport.core.utils.toListReport
import kotlinx.coroutines.flow.*

class ReportRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IReportRepository {
    override fun getReports(): Flow<Resource<List<Report>>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.getReports().first()) {
            is ApiResponse.Success -> {
                emit(
                    Resource.Success<List<Report>>(apiResponse.data.toListReport())
                )
            }
            is ApiResponse.Empty -> {
                emit(
                    Resource.Success<List<Report>>(listOf())
                )
            }
            is ApiResponse.Error -> {
                emit(
                    Resource.Error<List<Report>>(apiResponse.errorMessage)
                )
            }
        }
    }
}
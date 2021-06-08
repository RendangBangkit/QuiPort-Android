package academy.bangkit.quiport.presentation.example

import academy.bangkit.quiport.core.data.Resource
import academy.bangkit.quiport.core.domain.model.location.LocationDetail
import academy.bangkit.quiport.core.domain.usecase.ReportUseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import java.io.File

class ExampleViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _location = MutableLiveData<LocationDetail>()
    fun setLocation(location: LocationDetail) = _location.postValue(location)

    private val _absolutePath = MutableLiveData<String>()
    fun setAbsolutePath(absolutePath: String) = _absolutePath.postValue(absolutePath)

    fun postReport(
        userId: String, email: String,
    ): LiveData<Resource<String>> = reportUseCase.postReport(
        userId, email,
        _location.value?.latitude.toString(),
        _location.value?.longitude.toString(),
        File(_absolutePath.value.toString())
    ).asLiveData()
}
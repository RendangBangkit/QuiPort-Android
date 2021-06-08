package academy.bangkit.quiport.presentation.main.components.reportMain

import academy.bangkit.quiport.core.data.Resource
import academy.bangkit.quiport.core.domain.model.location.LocationDetail
import academy.bangkit.quiport.core.domain.model.user.UserDetail
import academy.bangkit.quiport.core.domain.usecase.ReportUseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import java.io.File

class ReportMainViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _location = MutableLiveData<LocationDetail>()
    fun setLocation(location: LocationDetail) = _location.postValue(location)

    private val _user = MutableLiveData<UserDetail?>()
    val user : LiveData<UserDetail?> = _user
    fun setUser(userDetail: UserDetail?) = _user.postValue(userDetail)

    private val _absolutePath = MutableLiveData<String>()
    fun setAbsolutePath(absolutePath: String) = _absolutePath.postValue(absolutePath)

    fun postReport(): LiveData<Resource<String>> = reportUseCase.postReport(
        _user.value?.id.toString(),
        _user.value?.email.toString(),
        _location.value?.latitude.toString(),
        _location.value?.longitude.toString(),
        File(_absolutePath.value.toString())
    ).asLiveData()
}
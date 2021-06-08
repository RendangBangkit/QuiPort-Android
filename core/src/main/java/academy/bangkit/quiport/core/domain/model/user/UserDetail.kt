package academy.bangkit.quiport.core.domain.model.user

import android.net.Uri

data class UserDetail(
    val displayName: String?,
    val email: String?,
    val id: String?,
    val photoUrl: Uri?,
)
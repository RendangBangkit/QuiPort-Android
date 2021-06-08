package academy.bangkit.quiport.utils

import academy.bangkit.quiport.core.domain.model.user.UserDetail
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

fun GoogleSignInAccount.toUserDetail() : UserDetail {
    return UserDetail(
        this.displayName,
        this.email,
        this.id,
        this.photoUrl
    )
}
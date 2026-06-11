package ci.nsu.mobile.main.model

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class UserLoginRequest(
    val login: String,
    val password: String
)
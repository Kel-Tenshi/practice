package ci.nsu.mobile.main.model

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class RegisterRequest(
    val login: String,
    val password: String,
    val email: String,
    val phoneNumber: String? = null,
    val roleId: Long = 1,
    val authAllowed: Boolean = true,
    val person: PersonDto
)
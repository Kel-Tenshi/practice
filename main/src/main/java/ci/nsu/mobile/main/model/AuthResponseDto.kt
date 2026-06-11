package ci.nsu.mobile.main.model

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable
@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class AuthResponseDto(
    val token: String
)
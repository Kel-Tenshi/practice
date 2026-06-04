package ci.nsu.mobile.main.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val userId: Long,
    val login: String,
    val email: String,
    val phoneNumber: String? = null,
    val roleId: Long,
    val authAllowed: Boolean,
    val personId: Long,
    val createdDate: String,
    val lastLoginDate: String? = null
)
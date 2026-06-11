package ci.nsu.mobile.main.model

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class PersonDto(
    val firstName: String,
    val lastName: String,
    val middleName: String? = null,
    val birthDate: String? = null, // В формате "YYYY-MM-DD"
    val gender: String? = null,    // "MALE" или "FEMALE"
    val groupId: Long
)
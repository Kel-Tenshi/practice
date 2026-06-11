package ci.nsu.mobile.main.model

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class GroupDto(
    @SerialName("groupId")
    val id: Long,
    @SerialName("groupName")
    val name: String
)
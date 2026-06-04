package ci.nsu.mobile.main.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class GroupDto(
    @SerialName("groupId")
    val id: Long,
    @SerialName("groupName")
    val name: String
)
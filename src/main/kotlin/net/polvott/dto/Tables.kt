package dto

import kotlinx.serialization.Serializable

@Serializable
data class Sample(
    val id: Int,
    val name: String
)

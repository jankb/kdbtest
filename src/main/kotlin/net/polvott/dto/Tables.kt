package net.polvott.dto

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable

object Samples: IntIdTable()
{
    val name = varchar("name", 50)
}

@Serializable
data class Sample(
    val id: Int,
    val name: String
)

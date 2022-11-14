package net.polvott.dto

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.*
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Table.Dual.varchar
import org.jetbrains.exposed.sql.Table.*


object Samples: Table()
{
    val sample_id = integer("sample_id")
    val description = varchar("description", 255)
}

@Serializable
data class Sample(
    val sample_id: Int,
    val description: String
)

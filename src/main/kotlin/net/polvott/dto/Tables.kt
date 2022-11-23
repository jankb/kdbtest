package net.polvott.dto

import kotlinx.serialization.Serializable
import net.postgis.jdbc.PGgeometry
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import net.postgis.jdbc.geometry.Point
import org.jetbrains.exposed.sql.ColumnType


object Samples: Table()
{
    val sample_id = integer("sample_id")
    val description = varchar("description", 255)
    val pos = point("point").nullable()
}

val SRID: Int = 4326

fun Table.point(name: String, srid: Int =SRID): Column<Point>
  =registerColumn(name, PointColumnType())


private class PointColumnType(val srid: Int = SRID) : ColumnType() {
    override fun sqlType() = "GEOMETRY(Point, $srid)"
    override fun valueFromDB(value: Any): Any {
        return if (value is PGgeometry) value.geometry else value
    }

    override fun notNullValueToDB(value: Any): Any {
        if (value is Point) {
            if (value.srid == Point.UNKNOWN_SRID) value.srid = srid
            return PGgeometry(value)
        }
        return value
    }
}




@Serializable
data class MTGeometry(
    val type: String,
    val coordinates: Array<Double>
)

@Serializable
data class Feature (
    val type:  String,
    val geometry : MTGeometry
        )

@Serializable
data class Sample(
    val sample_id: Int,
    val description: String,
    val geometry: MTGeometry?
)

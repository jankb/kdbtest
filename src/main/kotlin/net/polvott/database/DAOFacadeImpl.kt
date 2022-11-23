package net.polvott.database


import net.polvott.database.DatabaseFactory.dbQuery
import net.polvott.dto.MTGeometry
import net.polvott.dto.Sample
import net.polvott.dto.Samples
import net.postgis.jdbc.geometry.Point
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class DAOFacadeImpl : DAOFacade {

    private fun resultRowFromSample(row: ResultRow): Sample {
        val geometry = row[Samples.pos]

        if (geometry != null && geometry is Point) {
                return Sample(
                    sample_id = row[Samples.sample_id],
                    description = row[Samples.description],
                    geometry = MTGeometry("Point", arrayOf(geometry.x, geometry.y))
                )
        }
        else
            return Sample(
                sample_id = row[Samples.sample_id],
                description = row[Samples.description],
                null
            )
    }

    override suspend fun allSamples(): List<Sample> = dbQuery {
        Samples.selectAll().map(::resultRowFromSample)
    }

    override suspend fun addNewSample(id: Int, description: String, pos : MTGeometry?): Sample? = dbQuery{

        if (pos != null) {
            val result = Samples.insert {
                it[Samples.sample_id] = id
                it[Samples.description] = description
                it[Samples.pos] = Point(pos.coordinates.get(0), pos.coordinates.get(1))

            }
            result.resultedValues?.singleOrNull()?.let(::resultRowFromSample)
        }
        else
        {
            val result = Samples.insert {
                it[Samples.sample_id] = id
                it[Samples.description] = description
                it[Samples.pos] = null
            }
            result.resultedValues?.singleOrNull()?.let(::resultRowFromSample)
        }
    }

    override suspend fun sample(id: Int): Sample? = dbQuery {
        Samples.select{Samples.sample_id eq id}.map(::resultRowFromSample).singleOrNull()
    }
}

val dao = DAOFacadeImpl()
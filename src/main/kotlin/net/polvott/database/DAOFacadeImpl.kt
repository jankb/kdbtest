package net.polvott.database


import net.polvott.database.DatabaseFactory.dbQuery
import net.polvott.dto.Feature
import net.polvott.dto.MTGeometry
import net.polvott.dto.Sample
import net.polvott.dto.Samples

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll



import net.postgis.jdbc.PGgeometry
import net.postgis.jdbc.geometry.Point

import net.postgis.jdbc.geometry.Geometry

class DAOFacadeImpl : DAOFacade {

    private fun resultRowFromSample(row: ResultRow): Sample {
        val blob = row[Samples.pos]
        val pos = PGgeometry(String(blob.bytes))
        if (pos.geoType == Geometry.POINT) {
            val pt = pos.geometry as Point

            println("landing point for debugger $pt")
            return Sample(
                sample_id = row[Samples.sample_id],
                description = row[Samples.description],
                geometry = MTGeometry((pos.geometry.typeString.lowercase()).replaceFirstChar { it.uppercaseChar() }, arrayOf(pt.x,pt.y))
            )
        }
        else
            return Sample(sample_id = row[Samples.sample_id],
                description = row[Samples.description],null)

    }

    override suspend fun allSamples(): List<Sample> = dbQuery {
        Samples.selectAll().map(::resultRowFromSample)
    }

    override suspend fun addNewSample(id: Int, description: String, pos : MTGeometry?): Sample? = dbQuery{
        val result = Samples.insert {
            it[Samples.sample_id] = id
            it[Samples.description] = description
        }
        result.resultedValues?.singleOrNull()?.let(::resultRowFromSample)
    }

    override suspend fun sample(id: Int): Sample? = dbQuery {
        Samples.select{Samples.sample_id eq id}.map(::resultRowFromSample).singleOrNull()
    }
}

val dao = DAOFacadeImpl()
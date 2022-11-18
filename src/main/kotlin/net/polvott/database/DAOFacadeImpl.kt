package net.polvott.database


import net.polvott.database.DatabaseFactory.dbQuery
import net.polvott.dto.Sample
import net.polvott.dto.Samples
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class DAOFacadeImpl : DAOFacade {

    private fun resultRowFromSample(row: ResultRow) = Sample (
        sample_id = row[Samples.sample_id],
        description = row[Samples.description]
    )

    override suspend fun allSamples(): List<Sample> = dbQuery {
        Samples.selectAll().map(::resultRowFromSample)
    }

    override suspend fun addNewSample(id: Int, description: String): Sample? = dbQuery{
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
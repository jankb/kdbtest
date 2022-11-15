package net.polvott.database


import net.polvott.database.DatabaseFactory.dbQuery
import net.polvott.dto.Sample
import net.polvott.dto.Samples
import org.jetbrains.exposed.sql.ResultRow
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

    override suspend fun sample(id: Int): Sample? = dbQuery {
        Samples.select{Samples.sample_id eq id}.map(::resultRowFromSample).singleOrNull()
    }
}

val dao = DAOFacadeImpl()
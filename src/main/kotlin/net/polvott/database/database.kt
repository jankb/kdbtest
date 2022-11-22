package net.polvott.database


import kotlinx.coroutines.Dispatchers
import net.polvott.dto.Sample
import org.jetbrains.exposed.sql.Database
import io.ktor.server.config.ApplicationConfig
import java.sql.DriverManager
import net.polvott.dto.Feature
import net.polvott.dto.MTGeometry
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object DatabaseFactory
{
    fun init()
    {
        val dbUrl = ApplicationConfig(null).propertyOrNull("ktor.db.url")
        println("### JKB ### database::init, dbUrl is ${dbUrl?.getString()}")
       // val database = Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")
        val database = Database.connect("jdbc:postgresql://localhost:5432/prove?user=prove&password=taking")



       // val flyway = Flyway.configure().dataSource("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "", "").load()
        val flyway = Flyway.configure().dataSource("jdbc:postgresql://localhost:5432/prove", "prove", "taking").load()
        flyway.migrate()
    }
    suspend fun <T> dbQuery( block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}

interface DAOFacade {
    suspend fun allSamples(): List<Sample>
    suspend fun sample(id: Int): Sample?
    suspend fun addNewSample(id: Int, description: String, pos : MTGeometry?): Sample?
}


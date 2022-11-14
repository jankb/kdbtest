package net.polvott.database


import net.polvott.dto.Samples

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

import org.flywaydb.core.*

class Database
{
    init {
        println("### JKB ### database::init")

        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")


        val flyway = Flyway.configure().dataSource("jdbc:h2:mem:test", "", "").load()
        flyway.migrate()

        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Samples)

            val result = Samples.insert { it[name] = "MRSA" } get Samples.id
            println("### JKB ### Created sample with id $result")

            val result2 = Samples.insert { it[name] = "ILA" } get Samples.id
            println("### JKB ### Created sample with id $result2")

            println("### JKB ### Samples: ${Samples.selectAll()}")
        }
    }

    public fun get(id : Int?)
    {
        println("### JKB ### database::get for id $id")
        transaction {
            addLogger(StdOutSqlLogger)
            val result = Samples.select{ Samples.id eq id}.first()[Samples.name]

            println("### JKB ### Samples: ${Samples.selectAll()}")
        }
    }

    public fun insert(name: String)
    {
        transaction {
            addLogger(StdOutSqlLogger)
            Samples.insert { it[Samples.name] = name }
        }
    }
}
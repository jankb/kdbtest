package net.polvott

import io.ktor.serialization.kotlinx.* // ktlint-disable no-wildcard-imports
import io.ktor.serialization.kotlinx.json.* // ktlint-disable no-wildcard-imports
import io.ktor.server.application.* // ktlint-disable no-wildcard-imports
import io.ktor.server.engine.* // ktlint-disable no-wildcard-imports
import io.ktor.server.netty.* // ktlint-disable no-wildcard-imports
import io.ktor.server.plugins.callloging.* // ktlint-disable no-wildcard-imports
import io.ktor.server.plugins.contentnegotiation.* // ktlint-disable no-wildcard-imports
import io.ktor.server.plugins.defaultheaders.* // ktlint-disable no-wildcard-imports
import io.ktor.server.routing.* // ktlint-disable no-wildcard-imports
import kotlinx.serialization.json.Json
import net.polvott.database.Database
// import net.polvott.plugins.* // ktlint-disable no-wildcard-imports
import net.polvott.web.sampleRouter
import web.* // ktlint-disable no-wildcard-imports

fun Application.module() {
    install(CallLogging)
    install(DefaultHeaders)

    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
            }
        )
    }

    val datab = Database()

    install(Routing) {
        setupStatics()
        sampleRouter(datab)
    }
}

fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}

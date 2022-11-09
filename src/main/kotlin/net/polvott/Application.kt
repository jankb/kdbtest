package net.polvott
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import net.polvott.plugins.*
import net.polvott.web.SampleRouter
import web.*

fun Application.module() {
    install(CallLogging)
    install(DefaultHeaders)

    install(ContentNegotiation){
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    install(Routing) {
        setupStatics()
        SampleRouter()
    }
    // configureRouting()
}

fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}

package net.polvott

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.defaultheaders.*
import net.polvott.plugins.*
import web.*


fun Application.module() {
  install(CallLogging)
  install(DefaultHeaders)
  install(Routing)
  {
    setupStatics()
  }
  //configureRouting()
}


fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}

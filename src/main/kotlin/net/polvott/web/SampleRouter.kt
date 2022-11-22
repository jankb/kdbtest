package net.polvott.web

import net.polvott.dto.Sample
import net.polvott.database.dao
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.request.receive
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.util.getOrFail
import kotlinx.serialization.*

fun Route.sampleRouter() {
    route("/samples")
    {
        get("/{id}")
        {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val sample = dao.sample(id)
            if (sample == null) call.respond(HttpStatusCode.NotFound)
            else call.respond(sample)

        }

        post {

            try {
                val sample = call.receive<Sample>()
                val createdSample = dao.addNewSample(sample.sample_id.toInt(), sample.description, sample.geometry)
                call.respond(HttpStatusCode.Created)

            } catch (ex: BadRequestException) {
                println("Exception $ex")
                val c = ex.cause
                call.respond(HttpStatusCode.BadRequest, "Error: ${c?.cause}")
            } catch (ex: SerializationException) {
                println("Exception $ex")
                call.respond(HttpStatusCode.BadRequest, "Error: ${ex.cause}")
            } catch (e: Exception) {
                println("Exception $e")
                call.respond(HttpStatusCode.BadRequest, "Error: ${e.cause}")
            }


            //    val parametes = call.receiveParameters()
            //println("stupid line to have something to jump to in debug.$id")
            /*    val id = parametes.getOrFail("id").toInt()
                val desc = parametes.getOrFail("description")
                val sample = dao.addNewSample(id, desc)
                call.respondRedirect("/samples/${sample?.sample_id}")*/
        }

        get {
            val samples = dao.allSamples()
            if (samples == null) call.respond(HttpStatusCode.NotFound)
            else call.respond(samples)

            call.respondText("All Data", ContentType.Text.Plain)
        }
    }
}

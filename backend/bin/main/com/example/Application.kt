package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import io.ktor.server.response.*
import org.slf4j.event.Level

fun main() {
    embeddedServer(
        Netty,
        host = "0.0.0.0",
        port = 8080,
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    // Install CORS
    install(CORS) {
        anyHost()
        allowHeader("Content-Type")
    }

    // Install Call Logging
    install(CallLogging) {
        level = Level.INFO
    }

    // Install Content Negotiation
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            encodeDefaults = true
        })
    }
    
    // Initialize Database
    DatabaseFactory.init()
    
    // Configure Routes
    configureRoutes()
}

fun Application.configureRoutes() {
    routing {
        get("/") {
            call.respondText("Hello, Ktor!")
        }
    }
} 
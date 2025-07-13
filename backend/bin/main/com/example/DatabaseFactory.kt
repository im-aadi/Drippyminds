package com.example

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

object DatabaseFactory {
    fun init() {
        val dbFile = File("drippy_minds.db").absolutePath
        val databaseUrl = "jdbc:sqlite:$dbFile"
        val driver = "org.sqlite.JDBC"
        
        Database.connect(databaseUrl, driver)

        transaction {
            // Create tables if they don't exist
            SchemaUtils.createMissingTablesAndColumns(
                Assignments,
                Projects,
                ResearchPapers,
                Videos,
                Feedbacks,
                Bookings
            )
        }
    }
} 
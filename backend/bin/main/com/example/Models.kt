package com.example

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import kotlinx.datetime.LocalDateTime

// Exposed Table Definitions
object Assignments : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 100)
    val description = text("description")
    val dueDate = datetime("due_date")
    val status = varchar("status", 20).default("pending")
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    override val primaryKey = PrimaryKey(id)
}

object Projects : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 100)
    val description = text("description")
    val status = varchar("status", 20).default("ongoing")
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    override val primaryKey = PrimaryKey(id)
}

object ResearchPapers : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 200)
    val abstract = text("abstract")
    val authors = varchar("authors", 200)
    val status = varchar("status", 20).default("draft")
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    override val primaryKey = PrimaryKey(id)
}

object Videos : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 100)
    val description = text("description").nullable()
    val filePath = varchar("file_path", 200)
    val status = varchar("status", 20).default("processing")
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    override val primaryKey = PrimaryKey(id)
}

object Feedbacks : Table("Feedback") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val email = varchar("email", 120)
    val message = text("message")
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    override val primaryKey = PrimaryKey(id)
}

object Bookings : Table() {
    val id = integer("id").autoIncrement()
    val userEmail = varchar("user_email", 120)
    val name = varchar("name", 100)
    val itemName = varchar("item_name", 200)
    val deadline = datetime("deadline")
    val type = varchar("type", 50)
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    override val primaryKey = PrimaryKey(id)
}

// Kotlinx Serialization Data Classes
@Serializable
data class Assignment(
    val id: Int? = null,
    val title: String,
    val description: String,
    val dueDate: String, // Represent as String for simplified serialization, parse to LocalDateTime internally
    val status: String = "pending",
    val createdAt: String? = null
)

@Serializable
data class Project(
    val id: Int? = null,
    val title: String,
    val description: String,
    val status: String = "ongoing",
    val createdAt: String? = null
)

@Serializable
data class ResearchPaper(
    val id: Int? = null,
    val title: String,
    val abstract: String,
    val authors: String,
    val status: String = "draft",
    val createdAt: String? = null
)

@Serializable
data class Video(
    val id: Int? = null,
    val title: String,
    val description: String? = null,
    val filePath: String,
    val status: String = "processing",
    val createdAt: String? = null
)

@Serializable
data class Feedback(
    val id: Int? = null,
    val name: String,
    val email: String,
    val message: String,
    val createdAt: String? = null
)

@Serializable
data class Booking(
    val id: Int? = null,
    val userEmail: String,
    val name: String,
    val itemName: String,
    val deadline: String, // Represent as String, parse to LocalDateTime internally
    val type: String,
    val createdAt: String? = null
) 
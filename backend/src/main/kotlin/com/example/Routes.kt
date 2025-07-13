package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.format.DateTimeFormatter

fun Application.configureRoutes() {
    routing {
        // Assignments API
        route("/api/assignments") {
            get {
                val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
                val perPage = call.request.queryParameters["per_page"]?.toIntOrNull() ?: 10

                val assignments = transaction {
                    Assignments.selectAll()
                        .orderBy(Assignments.dueDate to SortOrder.DESC)
                        .limit(perPage, offset = ((page - 1) * perPage).toLong())
                        .map { toAssignment(it) }
                }

                val totalAssignments = transaction {
                    Assignments.selectAll().count().toInt()
                }

                val totalPages = (totalAssignments + perPage - 1) / perPage

                call.respond(mapOf(
                    "items" to assignments,
                    "total" to totalAssignments,
                    "pages" to totalPages,
                    "current_page" to page
                ))
            }

            post {
                try {
                    val assignmentRequest = call.receive<Assignment>()
                    val newAssignmentId = transaction {
                        val dueDate = LocalDateTime.parse(assignmentRequest.dueDate).toJavaLocalDateTime()
                        Assignments.insert {
                            it[title] = assignmentRequest.title
                            it[description] = assignmentRequest.description
                            it[this.dueDate] = dueDate.toKotlinLocalDateTime()
                            it[status] = assignmentRequest.status
                        } get Assignments.id
                    }
                    call.respond(HttpStatusCode.Created, mapOf("message" to "Assignment created successfully", "id" to newAssignmentId))
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, mapOf("error" to e.message))
                }
            }
        }

        // Projects API
        route("/api/projects") {
            get {
                val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
                val perPage = call.request.queryParameters["per_page"]?.toIntOrNull() ?: 10

                val projects = transaction {
                    Projects.selectAll()
                        .orderBy(Projects.createdAt to SortOrder.DESC)
                        .limit(perPage, offset = ((page - 1) * perPage).toLong())
                        .map { toProject(it) }
                }

                val totalProjects = transaction {
                    Projects.selectAll().count().toInt()
                }

                val totalPages = (totalProjects + perPage - 1) / perPage

                call.respond(mapOf(
                    "items" to projects,
                    "total" to totalProjects,
                    "pages" to totalPages,
                    "current_page" to page
                ))
            }

            post {
                try {
                    val projectRequest = call.receive<Project>()
                    val newProjectId = transaction {
                        Projects.insert {
                            it[title] = projectRequest.title
                            it[description] = projectRequest.description
                            it[status] = projectRequest.status
                        } get Projects.id
                    }
                    call.respond(HttpStatusCode.Created, mapOf("message" to "Project created successfully", "id" to newProjectId))
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, mapOf("error" to e.message))
                }
            }
        }

        // Research Papers API
        route("/api/research-papers") {
            get {
                val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
                val perPage = call.request.queryParameters["per_page"]?.toIntOrNull() ?: 10

                val researchPapers = transaction {
                    ResearchPapers.selectAll()
                        .orderBy(ResearchPapers.createdAt to SortOrder.DESC)
                        .limit(perPage, offset = ((page - 1) * perPage).toLong())
                        .map { toResearchPaper(it) }
                }

                val totalResearchPapers = transaction {
                    ResearchPapers.selectAll().count().toInt()
                }

                val totalPages = (totalResearchPapers + perPage - 1) / perPage

                call.respond(mapOf(
                    "items" to researchPapers,
                    "total" to totalResearchPapers,
                    "pages" to totalPages,
                    "current_page" to page
                ))
            }

            post {
                try {
                    val paperRequest = call.receive<ResearchPaper>()
                    val newPaperId = transaction {
                        ResearchPapers.insert {
                            it[title] = paperRequest.title
                            it[abstract] = paperRequest.abstract
                            it[authors] = paperRequest.authors
                            it[status] = paperRequest.status
                        } get ResearchPapers.id
                    }
                    call.respond(HttpStatusCode.Created, mapOf("message" to "Research paper created successfully", "id" to newPaperId))
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, mapOf("error" to e.message))
                }
            }
        }

        // Videos API
        route("/api/videos") {
            get {
                val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
                val perPage = call.request.queryParameters["per_page"]?.toIntOrNull() ?: 10

                val videos = transaction {
                    Videos.selectAll()
                        .orderBy(Videos.createdAt to SortOrder.DESC)
                        .limit(perPage, offset = ((page - 1) * perPage).toLong())
                        .map { toVideo(it) }
                }

                val totalVideos = transaction {
                    Videos.selectAll().count().toInt()
                }

                val totalPages = (totalVideos + perPage - 1) / perPage

                call.respond(mapOf(
                    "items" to videos,
                    "total" to totalVideos,
                    "pages" to totalPages,
                    "current_page" to page
                ))
            }

            post {
                // This is a simplified version. File uploads in Ktor require multipart handling.
                // For a full implementation, you'd need to parse multipart form data.
                // For now, we'll assume filePath is directly sent in the JSON for testing.
                try {
                    val videoRequest = call.receive<Video>()
                    val newVideoId = transaction {
                        Videos.insert {
                            it[title] = videoRequest.title
                            it[description] = videoRequest.description
                            it[filePath] = videoRequest.filePath
                            it[status] = videoRequest.status
                        } get Videos.id
                    }
                    call.respond(HttpStatusCode.Created, mapOf("message" to "Video created successfully", "id" to newVideoId))
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, mapOf("error" to e.message))
                }
            }
        }

        // Feedback API
        post("/api/feedback") {
            try {
                val feedbackRequest = call.receive<Feedback>()
                val newFeedbackId = transaction {
                    Feedbacks.insert {
                        it[name] = feedbackRequest.name
                        it[email] = feedbackRequest.email
                        it[message] = feedbackRequest.message
                        it[createdAt] = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
                    } get Feedbacks.id
                }
                call.respond(HttpStatusCode.Created, mapOf("message" to "Feedback submitted successfully", "id" to newFeedbackId))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to e.message))
            }
        }

        // Bookings API
        route("/api/bookings") {
            get {
                val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
                val perPage = call.request.queryParameters["per_page"]?.toIntOrNull() ?: 10

                val bookings = transaction {
                    Bookings.selectAll()
                        .orderBy(Bookings.createdAt to SortOrder.DESC)
                        .limit(perPage, offset = ((page - 1) * perPage).toLong())
                        .map { toBooking(it) }
                }

                val totalBookings = transaction {
                    Bookings.selectAll().count().toInt()
                }

                val totalPages = (totalBookings + perPage - 1) / perPage

                call.respond(mapOf(
                    "items" to bookings,
                    "total" to totalBookings,
                    "pages" to totalPages,
                    "current_page" to page
                ))
            }

            post {
                try {
                    val bookingRequest = call.receive<Booking>()
                    val newBookingId = transaction {
                        val deadline = LocalDateTime.parse(bookingRequest.deadline).toJavaLocalDateTime()
                        Bookings.insert {
                            it[userEmail] = bookingRequest.userEmail
                            it[name] = bookingRequest.name
                            it[itemName] = bookingRequest.itemName
                            it[this.deadline] = deadline.toKotlinLocalDateTime()
                            it[type] = bookingRequest.type
                        } get Bookings.id
                    }
                    call.respond(HttpStatusCode.Created, mapOf("message" to "Booking created successfully", "id" to newBookingId))
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, mapOf("error" to e.message))
                }
            }
        }

        // Serve static files (similar to your Flask setup for index.html and others)
        get("/") {
            call.respondText("Welcome to Drippy Minds Ktor Backend!", ContentType.Text.Plain)
        }
    }
}

private fun toAssignment(row: ResultRow) = Assignment(
    id = row[Assignments.id],
    title = row[Assignments.title],
    description = row[Assignments.description],
    dueDate = row[Assignments.dueDate].toJavaLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
    status = row[Assignments.status],
    createdAt = row[Assignments.createdAt].toJavaLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
)

private fun toProject(row: ResultRow) = Project(
    id = row[Projects.id],
    title = row[Projects.title],
    description = row[Projects.description],
    status = row[Projects.status],
    createdAt = row[Projects.createdAt].toJavaLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
)

private fun toResearchPaper(row: ResultRow) = ResearchPaper(
    id = row[ResearchPapers.id],
    title = row[ResearchPapers.title],
    abstract = row[ResearchPapers.abstract],
    authors = row[ResearchPapers.authors],
    status = row[ResearchPapers.status],
    createdAt = row[ResearchPapers.createdAt].toJavaLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
)

private fun toVideo(row: ResultRow) = Video(
    id = row[Videos.id],
    title = row[Videos.title],
    description = row[Videos.description],
    filePath = row[Videos.filePath],
    status = row[Videos.status],
    createdAt = row[Videos.createdAt].toJavaLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
)

private fun toFeedback(row: ResultRow) = Feedback(
    id = row[Feedbacks.id],
    name = row[Feedbacks.name],
    email = row[Feedbacks.email],
    message = row[Feedbacks.message],
    createdAt = row[Feedbacks.createdAt].toJavaLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
)

private fun toBooking(row: ResultRow) = Booking(
    id = row[Bookings.id],
    userEmail = row[Bookings.userEmail],
    name = row[Bookings.name],
    itemName = row[Bookings.itemName],
    deadline = row[Bookings.deadline].toJavaLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
    type = row[Bookings.type],
    createdAt = row[Bookings.createdAt].toJavaLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
) 
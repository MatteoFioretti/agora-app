package com.agora.app.data

object FakeData {

    val currentUser = Student(
        id = 0,
        name = "Matteo",
        year = "3rd year",
        faculty = "ACSAI",
        offers = listOf("Programming"),
        needs = listOf("Economics"),
        tagline = "",
        rating = 0.0
    )

    val allStudents = listOf(
        Student(
            id = 1,
            name = "Davide M.",
            year = "3rd year",
            faculty = "Economics",
            offers = listOf("Microeconomics", "Game theory"),
            needs = listOf("Programming"),
            tagline = "Step by step, no rushing",
            rating = 4.8,
            conversationCount = 12
        ),
        Student(
            id = 2,
            name = "Chiara F.",
            year = "2nd year",
            faculty = "Economics",
            offers = listOf("Macroeconomics", "Statistics"),
            needs = listOf("Programming"),
            tagline = "I explain with real world examples",
            rating = 4.7,
            conversationCount = 8
        ),
        Student(
            id = 3,
            name = "Marco R.",
            year = "3rd year",
            faculty = "Computer Science",
            offers = listOf("Python", "Algorithms"),
            needs = listOf("Linear Algebra"),
            tagline = "Real examples, no jargon",
            rating = 4.9,
            conversationCount = 23
        ),
        Student(
            id = 4,
            name = "Sofia L.",
            year = "2nd year",
            faculty = "Mathematics",
            offers = listOf("Linear Algebra", "Calculus"),
            needs = listOf("Python basics"),
            tagline = "I rebuild from foundations up",
            rating = 4.8,
            conversationCount = 15
        ),
        Student(
            id = 5,
            name = "Luca B.",
            year = "4th year",
            faculty = "Engineering",
            offers = listOf("Physics", "Thermodynamics"),
            needs = listOf("Academic writing"),
            tagline = "Diagrams first, formulas after",
            rating = 4.7,
            conversationCount = 19
        ),
        Student(
            id = 6,
            name = "Asel K.",
            year = "1st year",
            faculty = "Psychology",
            offers = listOf("Research methods", "Statistics"),
            needs = listOf("Maths"),
            tagline = "I explain like you're a friend",
            rating = 4.6,
            conversationCount = 6
        ),
        Student(
            id = 7,
            name = "Rania T.",
            year = "5th year",
            faculty = "Architecture",
            offers = listOf("Technical drawing", "CAD"),
            needs = listOf("Physics"),
            tagline = "Visual learner teaching visual thinkers",
            rating = 4.9,
            conversationCount = 31
        ),
        Student(
            id = 8,
            name = "Pietro G.",
            year = "1st year",
            faculty = "Law",
            offers = listOf("Italian grammar", "Essay writing"),
            needs = listOf("Economics"),
            tagline = "Patient, always happy to go slower",
            rating = 4.3,
            conversationCount = 4
        )
    )

    fun getPerfectMatches(): List<Student> {
        return allStudents.filter { student ->
            student.offers.any { offer ->
                currentUser.needs.any { need ->
                    offer.contains(need, ignoreCase = true)
                }
            } && student.needs.any { need ->
                currentUser.offers.any { offer ->
                    need.contains(offer, ignoreCase = true)
                }
            }
        }
    }

    fun getRegularStudents(): List<Student> {
        val perfectMatchIds = getPerfectMatches().map { it.id }
        return allStudents.filter { it.id !in perfectMatchIds }
    }
    fun getAllMeetings(): List<Meeting> {
        val allMeetings = if (AppState.newMeetingBooked && AppState.newMeeting != null) {
            meetings + listOf(AppState.newMeeting!!)
        } else {
            meetings
        }
        return allMeetings.sortedBy { meeting ->
            when (meeting.status) {
                MeetingStatus.CONFIRMED -> 0
                MeetingStatus.PENDING -> 1
                MeetingStatus.COMPLETED -> 2
            }
        }
    }
    val meetings = listOf(
        Meeting(
            id = 1,
            otherStudent = allStudents[0],
            subject = "Microeconomics",
            date = "Tomorrow",
            time = "5:00 PM",
            place = "Library, Room 2",
            status = MeetingStatus.CONFIRMED
        ),
        Meeting(
            id = 2,
            otherStudent = allStudents[1],
            subject = "Macroeconomics",
            date = "Friday",
            time = "3:00 PM",
            place = "Online",
            status = MeetingStatus.PENDING
        ),
        Meeting(
            id = 3,
            otherStudent = allStudents[2],
            subject = "Python debugging",
            date = "Last Monday",
            time = "4:00 PM",
            place = "Café Roma",
            status = MeetingStatus.COMPLETED,
            rating = 4.9,
            feedbackNote = "Real examples, no jargon — exactly as promised"
        )
    )
}
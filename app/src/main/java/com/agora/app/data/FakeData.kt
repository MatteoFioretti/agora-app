package com.agora.app.data

object FakeData {

    val currentUser = Student(
        id = 0,
        name = "Matteo",
        year = "3rd year",
        faculty = "ACSAI",
        offers = listOf("Programming"),
        needs = listOf("Economics"),
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
            rating = 4.3,
            conversationCount = 4
        )
    )

    fun getPerfectMatches(): List<Student> {
        val allMyOffers = AppState.userOffersList.flatMap { it.offers }
        val allMyNeeds = AppState.userOffersList.flatMap { it.needs }
        return allStudents.filter { student ->
            student.id !in AppState.requestedStudentIds &&
                    student.offers.any { offer ->
                        allMyNeeds.any { need ->
                            offer.contains(need, ignoreCase = true)
                        }
                    } && student.needs.any { need ->
                allMyOffers.any { offer ->
                    need.contains(offer, ignoreCase = true)
                }
            }
        }
    }

    fun getRegularStudents(): List<Student> {
        val perfectMatchIds = getPerfectMatches().map { it.id }
        return allStudents.filter {
            it.id !in perfectMatchIds &&
                    it.id !in AppState.requestedStudentIds
        }
    }
    fun getAllMeetings(): List<Meeting> {
        val allMeetings = meetings + AppState.newMeetings
        return allMeetings
            .filter {
                it.id !in AppState.cancelledMeetingIds &&
                        it.id !in AppState.completedMeetingIds
            }
            .sortedBy { meeting ->
                when (meeting.status) {
                    MeetingStatus.CONFIRMED -> 0
                    MeetingStatus.PENDING -> 1
                    MeetingStatus.COMPLETED -> 2
                }
            }
    }

    private val elenaV = Student(
        id = 101,
        name = "Elena V.",
        year = "2nd year",
        faculty = "Economics",
        offers = listOf("Macroeconomics"),
        needs = listOf("Programming"),
        rating = 4.6,
        conversationCount = 7
    )

    private val jamesK = Student(
        id = 102,
        name = "James K.",
        year = "3rd year",
        faculty = "Physics",
        offers = listOf("Thermodynamics"),
        needs = listOf("Mathematics"),
        rating = 4.8,
        conversationCount = 11
    )
    val meetings = listOf(
        Meeting(
            id = 1,
            otherStudent = elenaV,
            subject = "Macroeconomics",
            date = "Tomorrow",
            time = "5:00 PM",
            place = "Library, Room 2",
            status = MeetingStatus.CONFIRMED
        ),
        Meeting(
            id = 2,
            otherStudent = jamesK,
            subject = "Thermodynamics",
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
            )
    )
}
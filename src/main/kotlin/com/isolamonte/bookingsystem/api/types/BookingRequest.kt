package com.isolamonte.bookingsystem.api.types

import kotlinx.serialization.Serializable

@Serializable
data class BookingRequest(val user: User, val date: String, val timeslot: TimeSlot, val laundry_room: LaundryRoom)

@Serializable
data class BookingResponse(val user: User, val date: String, val timeslot: TimeSlot, val laundry_room: LaundryRoom)

@Serializable
@JvmInline
value class User(val value: String)

@Serializable
enum class TimeSlot(val time: String) {
    MORNING("7-10"),
    FORENOON("10-13"),
    MIDDAY("13-16"),
    AFTERNOON("16-19"),
    EVENING("19-22")
}

@Serializable
enum class LaundryRoom {
    MAPLE_STREET_3,
    MAPLE_STREET_5
}
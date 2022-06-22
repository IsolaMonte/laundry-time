package com.isolamonte.bookingsystem.db.types

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("BOOKINGS")
data class Booking(@Id val id: String?, val user: String, val date: String, val timeslot: String, val laundryroom: String) {

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Booking -> {
                this.user == other.user &&
                        this.date == other.date &&
                        this.timeslot == other.timeslot &&
                        this.laundryroom == other.laundryroom
            }
            else -> false
        }
    }
}
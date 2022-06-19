package com.isolamonte.bookingsystem.domain

import com.isolamonte.bookingsystem.db.Booking
import com.isolamonte.bookingsystem.db.BookingRepository
import org.springframework.stereotype.Service

@Service
class BookingService(val db: BookingRepository) {
    fun listBookings(): List<Booking> = db.findBookings()

    fun getBookingById(id: String) = db.findById(id)

    fun createBooking(b: Booking) = db.save(b)

    fun deleteBooking(id: String) = db.deleteById(id)

    fun findMaybeClash(b: Booking): Booking? = db.findMaybeClash(b.date, b.timeslot, b.laundryroom)
}
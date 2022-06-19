package com.isolamonte.bookingsystem.db

import com.isolamonte.bookingsystem.db.types.Booking
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface BookingRepository : CrudRepository<Booking, String> {
    @Query("select * from BOOKINGS")
    fun findBookings(): List<Booking>

    @Query("SELECT * FROM BOOKINGS b WHERE ( b.date = :date and b.timeslot = :timeslot and b.laundryroom = :laundryroom )")
    fun findMaybeClash(@Param("date") date: String,
                       @Param("timeslot") timeslot: String,
                       @Param("laundryroom") laundryroom: String): Booking?
}
package com.isolamonte.bookingsystem.db

import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Table("BOOKINGS")
data class Booking(@Id val id: String?, val user: String, val date: String, val timeslot: String, val laundryroom: String)

@Repository
interface BookingRepository : CrudRepository<Booking, String> {
    @Query("select * from BOOKINGS")
    fun findBookings(): List<Booking>

    @Query("SELECT * FROM BOOKINGS b WHERE ( b.date = :date and b.timeslot = :timeslot and b.laundryroom = :laundryroom )")
    fun findMaybeClash(@Param("date") date: String,
                       @Param("timeslot") timeslot: String,
                       @Param("laundryroom") laundryroom: String): Booking?
}
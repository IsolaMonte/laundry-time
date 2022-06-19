package com.isolamonte.bookingsystem.api

import com.isolamonte.bookingsystem.api.errors.BookingNotFound
import com.isolamonte.bookingsystem.api.errors.TimeSlotAlreadyTaken
import com.isolamonte.bookingsystem.api.types.BookingRequest
import com.isolamonte.bookingsystem.db.Booking
import com.isolamonte.bookingsystem.domain.BookingService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bookings")
class BookingResource(val service: BookingService) {
    @GetMapping
    fun getAllBookings(): List<Booking> = service.listBookings()

    @GetMapping("/{id}")
    fun getBookingById(@PathVariable id: String): Booking = run {
        return service.getBookingById(id)
            .orElseThrow{ BookingNotFound() }
    }

    @PostMapping
    fun post(@RequestBody req: BookingRequest) {
        //TODO: Validate req as a part of creating an instance of Booking
        val booking = Booking(null, req.user.value, req.date, req.timeslot.toString(), req.laundry_room.toString())

        //Perform clash checks in DB layer
        service.findMaybeClash(booking)?.also {
            throw TimeSlotAlreadyTaken()
        }

        service.createBooking(booking)

        /*
        // Perform clash checks in code
        val existingBookings = service.listBookings()
        val bookingClash = existingBookings.filter {
                b -> req.timeslot.toString() == b.timeslot &&
                req.date == b.date &&
                req.laundry_room.toString() == b.laundryroom
        }
        */
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String) {
        service.deleteBooking(id)
    }
}
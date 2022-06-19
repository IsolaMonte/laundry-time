package com.isolamonte.bookingsystem.api.errors

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

class ErrorMessageResponse(
    var status: Int? = null,
    var cause: String? = null,
    var message: String? = null
)

class TimeSlotAlreadyTaken(): Exception()
class BookingNotFound(): Exception()

@ControllerAdvice
internal class ExceptionHandler {

    @ExceptionHandler
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<ErrorMessageResponse> {
        val errorMessage = ErrorMessageResponse(
            HttpStatus.BAD_REQUEST.value(),
            null,
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleBookingNotFoundException(ex: BookingNotFound): ResponseEntity<ErrorMessageResponse> {
        val errorMessage = ErrorMessageResponse(
            HttpStatus.BAD_REQUEST.value(),
            "BOOKING_NOT_FOUND",
            "Could not find a booking with the provided id"
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleTimeSlotAlreadyTakenException(ex: TimeSlotAlreadyTaken): ResponseEntity<ErrorMessageResponse> {
        val errorMessage = ErrorMessageResponse(
            HttpStatus.BAD_REQUEST.value(),
            "TIMESLOT_UNAVAILABLE",
            "This timeslot is already taken"
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }
}
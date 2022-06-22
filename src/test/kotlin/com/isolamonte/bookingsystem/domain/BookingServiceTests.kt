package com.isolamonte.bookingsystem.domain

import com.isolamonte.bookingsystem.db.BookingRepository
import com.isolamonte.bookingsystem.db.types.Booking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookingServiceTests {

	@Autowired
	lateinit var bookingRepository: BookingRepository

	@Test
	fun lookupBooking() {
		// given
		val service = BookingService(bookingRepository)
		val existingBooking = Booking(null, "natalia", "2022-07-12", "MIDDAY", "MAPLE_STREET_5")

		// when
		val listOfBookings = service.listBookings()

		// then
		assertThat(listOfBookings).contains(existingBooking)
	}

	@Test
	fun fetchBookingById() {
		// given
		val service = BookingService(bookingRepository)
		val listOfBookings = service.listBookings()

		//when
		val aBooking = listOfBookings[0].id?.let { service.getBookingById(it) }

		// then
		assertThat(aBooking).isNotNull
	}

	@Test
	fun checkForBookingClash() {
		// given
		val service = BookingService(bookingRepository)
		val existingBooking = Booking(null, "natalia", "2022-07-12", "MIDDAY", "MAPLE_STREET_5")

		// when
		val maybeClash = service.findMaybeClash(existingBooking)

		// then
		println(maybeClash)
	}

	@Test
	fun fetchNonExistingBooking() {
		// given
		val service = BookingService(bookingRepository)
		val newBooking = Booking(null, "kim", "2022-11-22", "MORNING", "MAPLE_STREET_3")

		// when
		val listOfBookings = service.listBookings()

		// then
		assertThat(listOfBookings).doesNotContain(newBooking)
	}

}

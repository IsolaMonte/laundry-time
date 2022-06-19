package com.isolamonte.bookingsystem

import com.isolamonte.bookingsystem.db.Booking
import com.isolamonte.bookingsystem.db.BookingRepository
import com.isolamonte.bookingsystem.domain.BookingService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

//@Disabled("build failure")
@SpringBootTest
class LaundryBookingApplicationTests {

	@Autowired
	lateinit var bookingRepository: BookingRepository

	@Test
	fun bookASlot() {
		// given
		val service = BookingService(bookingRepository)
		val kim = Booking(null, "kim", "2022-11-22", "MORNING", "MAPLE_STREET_2")
		//entityManager.persist(kim)
		//entityManager.flush()

		// when
		val listOfBookings = service.listBookings()

		// then
		//assertThat(listOfBookings).contains(kim)
	}

}

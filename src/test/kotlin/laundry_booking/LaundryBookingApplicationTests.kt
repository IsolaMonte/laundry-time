package laundry_booking

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

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

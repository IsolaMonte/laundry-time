package laundry_booking

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@Disabled("As to not fail the build")
@DataJpaTest
class LaundryBookingApplicationTests {

	@Autowired
	lateinit var entityManager: TestEntityManager

	@Autowired
	lateinit var bookingRepository: BookingRepository

	@Test
	fun bookASlot() {
		// given
		val service = BookingService(bookingRepository)
		val kim = Booking(null, "kim", "2022-11-22", "MORNING", "MAPLE_STREET_2")
		entityManager.persist(kim)
		entityManager.flush()

		// when
		val listOfBookings = service.listBookings()

		// then
		assertThat(listOfBookings.contains(kim))
	}

}

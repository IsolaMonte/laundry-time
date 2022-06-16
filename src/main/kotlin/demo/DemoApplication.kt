package demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import kotlinx.serialization.Serializable

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}

@Table("BOOKINGS")
data class Booking(@Id val id: String?, val user: String, val date: String, val timeslot: String, val laundryroom: String)
data class BookingRequest(val user: String, val date: String, val timeslot: String, val laundry_room: String)

@JvmInline
value class User(private val s: String)

@Serializable
enum class TimeSlot(val time: String) {
	A("7-10"),
	B("10-13"),
	C("13-16"),
	D("16-19"),
	E("19-22")
}

interface BookingRepository : CrudRepository<Booking, String> {
	@Query("select * from BOOKINGS")
	fun findBookings(): List<Booking>
}

@Service
class BookingService(val db: BookingRepository) {
	fun listBookings(): List<Booking> = db.findBookings()

	fun createBooking(booking: Booking){
		db.save(booking)
	}
}

@RestController
class BookingResource(val service: BookingService) {
	@GetMapping("/bookings")
	fun index(): List<Booking> = service.listBookings()

	@PostMapping("/bookings")
	fun post(@RequestBody booking: Booking) {
		service.createBooking(booking)
	}
}

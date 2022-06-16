package demo

import kotlinx.serialization.Serializable
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*


@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}

@Serializable
data class BookingRequest(val user: User, val date: String, val timeslot: TimeSlot, val laundry_room: LaundryRoom)

@Serializable
@JvmInline
value class User(val string: String)

@Serializable
enum class TimeSlot(val time: String) {
	MORNING("7-10"),
	FORENOON("10-13"),
	MIDDAY("13-16"),
	AFTERNOON("16-19"),
	EVENING("19-22")
}

@Serializable
enum class LaundryRoom {
	MAPLE_STREET_3,
	MAPLE_STREET_5
}

class ErrorMessageModel(
	var status: Int? = null,
	var message: String? = null
)

class TimeSlotAlreadyTaken(message:String): Exception(message)
class BookingNotFound(message:String): Exception(message)

@ControllerAdvice
internal class GlobalControllerExceptionHandler {

	@ExceptionHandler
	fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<ErrorMessageModel> {
		val errorMessage = ErrorMessageModel(
			HttpStatus.BAD_REQUEST.value(),
			ex.message
		)
		return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
	}

	@ExceptionHandler
	fun handleBookingNotFoundException(ex: BookingNotFound): ResponseEntity<ErrorMessageModel> {
		val errorMessage = ErrorMessageModel(
			HttpStatus.BAD_REQUEST.value(),
			ex.message
		)
		return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
	}

	@ExceptionHandler
	fun handleTimeSlotAlreadyTakenException(ex: TimeSlotAlreadyTaken): ResponseEntity<ErrorMessageModel> {
		val errorMessage = ErrorMessageModel(
			HttpStatus.BAD_REQUEST.value(),
			ex.message
		)
		return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
	}
}

@RestController
@RequestMapping("/bookings")
class BookingResource(val service: BookingService) {
	@GetMapping
	fun getAllBookings(): List<Booking> = service.listBookings()

	@GetMapping("/{id}")
	fun getOneBooking(@PathVariable id: String): Booking = run {
		val booking = service.getBookingById(id)
		if (booking.isEmpty) {
			throw BookingNotFound("Could not find a booking with the provided id")
		} else {
			return booking.get()
		}
	}

	@PostMapping
	fun post(@RequestBody req: BookingRequest) {
		val booking = Booking(null, req.user.string, req.date, req.timeslot.toString(), req.laundry_room.toString())
		val existingBookings = service.listBookings()
		val bookingClash = existingBookings.filter {
				booking -> req.timeslot.toString() == booking.timeslot && req.date == booking.date && req.laundry_room.toString() == booking.laundryroom
		}
		if (bookingClash.isEmpty()) {
			service.createBooking(booking)
		} else {
			throw TimeSlotAlreadyTaken("This timeslot is already taken")
		}
	}

	@DeleteMapping("/{id}")
	fun delete(@PathVariable id: String) {
		service.deleteBooking(id)
	}
}

/*
fun validateBookingRequest(req: BookingRequest): Optional<Booking> {
	return
}*/

@Table("BOOKINGS")
data class Booking(@Id val id: String?, val user: String, val date: String, val timeslot: String, val laundryroom: String)

interface BookingRepository : CrudRepository<Booking, String> {
	@Query("select * from BOOKINGS")
	fun findBookings(): List<Booking>
}

@Service
class BookingService(val db: BookingRepository) {
	fun listBookings(): List<Booking> = db.findBookings()

	fun getBookingById(id: String) = db.findById(id)

	fun createBooking(booking: Booking){
		db.save(booking)
	}

	fun deleteBooking(id: String){
		db.deleteById(id)
	}
}

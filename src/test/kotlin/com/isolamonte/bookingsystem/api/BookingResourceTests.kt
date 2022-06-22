package com.isolamonte.bookingsystem.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.isolamonte.bookingsystem.api.types.BookingRequest
import com.isolamonte.bookingsystem.api.types.LaundryRoom
import com.isolamonte.bookingsystem.api.types.TimeSlot
import com.isolamonte.bookingsystem.api.types.User
import com.isolamonte.bookingsystem.db.types.Booking
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.*
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Disabled
@SpringBootTest
@AutoConfigureMockMvc
class BookingResourceTests {

	@Autowired
	lateinit var mockMvc: MockMvc

	@Autowired
	lateinit var objectMapper: ObjectMapper

	@Test
	fun bookASlot() {
		val newBooking = BookingRequest(User("kim"), "2022-11-18", TimeSlot.MORNING, LaundryRoom.MAPLE_STREET_3)

		mockMvc.perform(post("/bookings", 42L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(newBooking)))
			.andDo { print() }
			.andExpect(status().isOk)
	}

	@Test
	fun bookADuplicateSlot() {
		val newBooking = BookingRequest(User("ellie"), "2022-11-08", TimeSlot.MORNING, LaundryRoom.MAPLE_STREET_3)

		mockMvc.perform(post("/bookings", 42L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(newBooking)))
			.andDo { print() }
			.andExpect(status().is2xxSuccessful)

		mockMvc.perform(post("/bookings", 42L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(newBooking)))
			.andDo { print() }
			.andExpect(status().is4xxClientError)
	}

	@Test
	fun fetchBookings() {
		mockMvc.perform(get("/bookings", 42L)
			.accept(MediaType.APPLICATION_JSON))
			.andDo { print() }
			.andExpect(status().is2xxSuccessful)
	}

	@Test
	fun postIncorrectPayload() {
		val newBooking = Booking(null,"ellie", "2022-11-05", "MORNING", "MAPLE_STREET_3")

		mockMvc.perform(post("/bookings", 42L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(newBooking)))
			.andDo { print() }
			.andExpect(status().is4xxClientError)
	}

}

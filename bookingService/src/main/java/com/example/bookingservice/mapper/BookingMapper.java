package com.example.bookingservice.mapper;

import com.example.bookingservice.dto.Pagination;
import com.example.bookingservice.dto.requests.CreateBooking;
import com.example.bookingservice.dto.response.GetBooking;
import com.example.bookingservice.dto.response.ListBookings;
import com.example.bookingservice.model.Booking;

import java.util.List;

public class BookingMapper {

    public static Booking createBooking(CreateBooking createBooking) {
        Booking booking = new Booking();
        return booking;
    }

    public static GetBooking getBooking(Booking booking) {
        return new GetBooking(
                booking.getId(),
                booking.getCreateDate(),
                booking.getEditDate(),
                booking.getUserId(),
                booking.getCarId()
        );
    }

    public static ListBookings listBookings(List<GetBooking> listPayments, Pagination pagination) {
        return new ListBookings(
                listPayments,
                pagination
        );
    }

}

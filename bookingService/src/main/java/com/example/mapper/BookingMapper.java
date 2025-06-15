package com.example.mapper;

import com.example.dto.Pagination;
import com.example.dto.requests.CreateBooking;
import com.example.dto.response.GetBooking;
import com.example.dto.response.ListBookings;
import com.example.model.Booking;

import java.util.List;

public class BookingMapper {

    public static Booking createBooking(CreateBooking createBooking) {
        Booking booking = new Booking();
        booking.setName(createBooking.name());
        return booking;
    }

    public static GetBooking getBooking(Booking payment) {
        return new GetBooking(
                payment.getId(),
                payment.getName()
        );
    }

    public static ListBookings listBookings(List<GetBooking> listPayments, Pagination pagination) {
        return new ListBookings(
                listPayments,
                pagination
        );
    }

}

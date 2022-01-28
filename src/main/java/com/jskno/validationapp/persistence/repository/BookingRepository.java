package com.jskno.validationapp.persistence.repository;

import com.jskno.validationapp.persistence.model.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, Long> {
}

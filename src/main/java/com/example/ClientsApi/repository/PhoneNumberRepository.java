package com.example.ClientsApi.repository;

import com.example.ClientsApi.model.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber,Long> {
}

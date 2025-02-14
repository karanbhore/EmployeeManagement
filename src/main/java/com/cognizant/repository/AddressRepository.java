package com.cognizant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
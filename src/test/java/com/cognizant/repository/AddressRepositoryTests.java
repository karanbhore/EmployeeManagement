package com.cognizant.repository;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cognizant.model.Address;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AddressRepositoryTests {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void testSaveAndFindById() {
        // Create an Address entity
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Springfield");
        address.setState("IL");
        address.setPincode("62701");

        // Save the address to the database
        Address savedAddress = addressRepository.save(address);

        // Retrieve the address by its ID
        Optional<Address> retrievedAddress = addressRepository.findById(savedAddress.getId());

        // Assert that the retrieved address matches the saved address
        assertThat(retrievedAddress).isPresent();
        assertThat(retrievedAddress.get()).isEqualTo(savedAddress);
    }
}

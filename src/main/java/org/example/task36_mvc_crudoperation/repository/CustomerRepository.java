package org.example.task36_mvc_crudoperation.repository;

import org.example.task36_mvc_crudoperation.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}

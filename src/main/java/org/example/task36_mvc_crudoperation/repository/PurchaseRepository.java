package org.example.task36_mvc_crudoperation.repository;

import org.example.task36_mvc_crudoperation.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

}

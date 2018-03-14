package rog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rog.domain.Orders;

import java.util.ArrayList;


/**
 * Spring Data JPA repository for the Orders entity.
 */

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    public ArrayList<Orders> findAll();
}

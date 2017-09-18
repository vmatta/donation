package com.donation.repository;

import com.donation.entity.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by Sumit on 9/19/2017.
 */
@Repository
public interface DesignationRepository extends JpaRepository<Designation, String> {

    //usage of modifying  - https://stackoverflow.com/questions/44022076/jparepository-not-supported-for-dml-operations-delete-query
    @Modifying
    @Query("delete from Designation d where d.orderId in :orderIds")
    void deleteOrders(@Param("orderIds") Set<String> orderIds);
}
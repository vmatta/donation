package com.donation.repository;

import com.donation.entity.TransactionDetail;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, String> {

  @Query("select orderId from TransactionDetail transactionDetail order by transactionDetail.orderId DESC")
  Optional<List<String>> getOrderIdsInDescendingOrder();
}

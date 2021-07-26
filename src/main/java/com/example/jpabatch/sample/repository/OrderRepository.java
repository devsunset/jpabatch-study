package com.example.jpabatch.sample.repository;

import com.example.jpabatch.sample.entity.Order;
import com.example.jpabatch.sample.repository.custom.CustomOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * User: HolyEyE
 * Date: 2013. 12. 3. Time: 오후 10:28
 */
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order>, CustomOrderRepository {

}

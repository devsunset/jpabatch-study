package com.example.jpabatch.sample.repository.custom;

import com.example.jpabatch.sample.entity.Order;
import com.example.jpabatch.sample.entity.OrderSearch;

import java.util.List;

/**
 * @author holyeye
 */
public interface CustomOrderRepository {

    public List<Order> search(OrderSearch orderSearch);

}

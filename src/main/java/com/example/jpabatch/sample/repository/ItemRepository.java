package com.example.jpabatch.sample.repository;

import com.example.jpabatch.sample.entity.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: HolyEyE
 * Date: 2013. 12. 3. Time: 오후 9:48
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

}

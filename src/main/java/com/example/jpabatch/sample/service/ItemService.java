package com.example.jpabatch.sample.service;

import com.example.jpabatch.sample.entity.item.Item;
import com.example.jpabatch.sample.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * User: HolyEyE
 * Date: 2013. 12. 3. Time: 오후 9:43
 */
@Service
@Transactional
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}

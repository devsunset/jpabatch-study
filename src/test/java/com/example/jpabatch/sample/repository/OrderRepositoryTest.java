package com.example.jpabatch.sample.repository;

import com.example.jpabatch.sample.entity.Member;
import com.example.jpabatch.sample.entity.Order;
import com.example.jpabatch.sample.entity.OrderSearch;
import com.example.jpabatch.sample.entity.OrderStatus;
import com.example.jpabatch.sample.entity.item.Book;
import com.example.jpabatch.sample.service.ItemService;
import com.example.jpabatch.sample.service.MemberService;
import com.example.jpabatch.sample.service.OrderService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class OrderRepositoryTest {

    @Autowired
    MemberService memberService;
    @Autowired
    ItemService itemService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void test() throws Exception {
        //Given
        Member member = createMember("hello");
        Book book = createItem("시골 Book", 10);
        orderService.order(member.getId(), book.getId(), 1);

        //When
        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setMemberName("hello");
        orderSearch.setOrderStatus(OrderStatus.ORDER);

        List<Order> search = orderRepository.search(orderSearch);

        //Then
        Assert.assertEquals(1, search.size());
    }

    private Member createMember(String name) {
        Member member = new Member();
        member.setName(name);
        memberService.join(member);
        return member;
    }

    private Book createItem(String name, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        itemService.saveItem(book);
        return book;
    }
}
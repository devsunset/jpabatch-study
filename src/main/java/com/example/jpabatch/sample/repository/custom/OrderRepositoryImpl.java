package com.example.jpabatch.sample.repository.custom;

import java.util.List;

import com.example.jpabatch.sample.entity.Order;
import com.example.jpabatch.sample.entity.OrderSearch;
import com.example.jpabatch.sample.entity.QMember;
import com.example.jpabatch.sample.entity.QOrder;
import com.querydsl.jpa.JPQLQuery;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

/**
 * @author holyeye
 */
public class OrderRepositoryImpl extends QuerydslRepositorySupport implements CustomOrderRepository {

    public OrderRepositoryImpl() {
        super(Order.class);
    }

    @Override
    public List<Order> search(OrderSearch orderSearch) {

        QOrder order = QOrder.order;
        QMember member = QMember.member;

        JPQLQuery query = from(order);

        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query.leftJoin(order.member, member)
                    .where(member.name.contains(orderSearch.getMemberName()));
        }

        if (orderSearch.getOrderStatus() != null) {
            query.where(order.status.eq(orderSearch.getOrderStatus()));
        }

        return query.fetch();
//      return query.list(order);

    }
}

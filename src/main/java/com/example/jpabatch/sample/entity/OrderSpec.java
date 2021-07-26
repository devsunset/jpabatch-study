package com.example.jpabatch.sample.entity;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import javax.persistence.criteria.Order;

public class OrderSpec {

    public static Specification<javax.persistence.criteria.Order> memberNameLike(final String memberName) {
        return new Specification<javax.persistence.criteria.Order>() {
            public Predicate toPredicate(Root<javax.persistence.criteria.Order> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

                if (StringUtils.isEmpty(memberName)) return null;

                Join<javax.persistence.criteria.Order, Member> m = root.join("member", JoinType.INNER); //회원과 조인
                return builder.like(m.<String>get("name"), "%" + memberName + "%");
            }
        };
    }

    public static Specification<javax.persistence.criteria.Order> orderStatusEq(final OrderStatus orderStatus) {
        return new Specification<javax.persistence.criteria.Order>() {
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

                if (orderStatus == null) return null;

                return builder.equal(root.get("status"), orderStatus);
            }
        };
    }
}
package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 * <주문 2개>
 * userA
 * JPA1 BOOK
 * JPA2 BOOK
 * userB
 * SPRING1 BOOK
 * SPRING2 BOOK
 */
@Component //스프링빈 등록
@RequiredArgsConstructor
public class InitDb {


    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }


    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        private final EntityManager em;

        public void dbInit1(){
            //회원등록

            Member member = createMember("userA", "서울", "1", "2000");
            em.persist(member);

            //상품등록
            Book book1 = createBook("JPA1 BOOK", 10000, 545);
            em.persist(book1);


            Book book2 = createBook("JPA2 BOOK", 5000, 888);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1,10000,1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2,5000,4);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery,orderItem1,orderItem2); //주문내역
            em.persist(order);

        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private Book createBook(String name, int price, int stockQuantity) {
            Book book1 =new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity); //ctrl+alt+p
            return book1;
        }

        //ctrl+alt+m
        private Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        public void dbInit2(){
            //회원등록
            Member member = createMember("userB", "진주", "2", "22222");
            em.persist(member);

            //상품등록
            Book book1 = createBook("SPRING1 BOOK", 20000, 200);
            em.persist(book1);


            Book book2 = createBook("SPRING2 BOOK", 40000, 400);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1,20000,3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2,40000,5);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery,orderItem1,orderItem2); //주문내역
            em.persist(order);

        }

    }
}

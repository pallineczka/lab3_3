package edu.iis.mto.time;

import org.junit.Test;


public class OrderTest {
    private Order order;

    @Test(expected = OrderExpiredException.class)
    public void orderConfirmShouldThrowOrderExpiredException(){
        order = new Order();
        order.submit();
        order.confirm();
    }
}

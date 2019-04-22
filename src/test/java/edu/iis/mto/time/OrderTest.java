package edu.iis.mto.time;

import org.junit.Test;

import static org.junit.Assert.*;

public class OrderTest {
    private Order order;

    @Test(expected = OrderExpiredException.class)
    public void testOrderConfirmMethodShouldThrowException(){
        order = new Order();
        order.submit();
        order.confirm();
    }
}
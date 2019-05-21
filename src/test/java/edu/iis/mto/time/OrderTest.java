package edu.iis.mto.time;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.omg.PortableServer.POAManagerPackage.State;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class OrderTest {

    Order order;
    Instant currentTime;

    @Mock Clock clock;

    @Before public void setup() {
        order = new Order(clock);
        order.submit();

    }

    @Test(expected = OrderExpiredException.class) public void methodConfirmShouldInvokeOrderExpiredException() {
        order.confirm();
    }
    @Test (expected = OrderStateException.class)
    public void orderSubmitShouldInvokeOrderStateExceptions() {
        order.submit();
        currentTime = currentTime.plus(1, ChronoUnit.DAYS);
        order.confirm();
    }

}

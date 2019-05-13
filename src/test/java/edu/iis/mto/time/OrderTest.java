package edu.iis.mto.time;

import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderTest {
    private Order order;
    private Clock clock;
    private Instant currentTime;

    @Before
    public void initialize(){
        clock = mock(Clock.class);
        currentTime = Instant.now();
        when(clock.instant()).thenAnswer(invocation -> currentTime);
    }

    @Test(expected = OrderExpiredException.class)
    public void orderConfirmShouldThrowOrderExpiredException(){
        order = new Order(clock);
        order.submit();
        order.confirm();
    }
}

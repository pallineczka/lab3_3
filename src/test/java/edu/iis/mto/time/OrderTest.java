package edu.iis.mto.time;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderTest {

    @Mock
    private Clock clock;
    private Instant currentTime;
    private Order order;

    @Before
    public void setUp() {
        currentTime = Instant.now();
        clock = mock(Clock.class);
        order = new Order(clock);
        when(clock.instant()).thenAnswer((invocation) -> currentTime);
    }

    @Test(expected = OrderExpiredException.class)
    public void orderConfirmationAfterBeingExpiredShouldFail() {
        order.submit();
        currentTime = currentTime.plus(2, ChronoUnit.DAYS);
        order.confirm();
    }

    @Test
    public void orderConfirmationBeforeBeingExpiredShouldPass() {
        order.submit();
        currentTime = currentTime.plus(1, ChronoUnit.HOURS);
        order.confirm();
    }
}

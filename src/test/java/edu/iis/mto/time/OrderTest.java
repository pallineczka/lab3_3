package edu.iis.mto.time;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderTest {
    @Mock
    private Clock clock;
    private Instant instant;
    private Order order;

    @Before
    public void setUp() {
        instant = Instant.now();
        clock = mock(Clock.class);
        order = new Order(clock);
        when(clock.instant()).thenAnswer((invocation) -> instant);
    }

    @Test
    public void testOrderConfirmationMethodShouldPass() {
        order.submit();
        instant = instant.plus(1, ChronoUnit.HOURS);
        order.confirm();
    }

    @Test(expected = OrderExpiredException.class)
    public void testOrderConfirmMethodShouldThrowException() {
        order.submit();
        instant = instant.plus(2, ChronoUnit.DAYS);
        order.confirm();
    }

}
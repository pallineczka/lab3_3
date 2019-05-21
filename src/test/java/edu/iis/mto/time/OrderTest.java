package edu.iis.mto.time;


import org.junit.jupiter.api.*;

import org.mockito.Mockito;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
public class OrderTest {

    private Clock clock;
    private Instant time;
    private Order order;


    @BeforeEach
    public void init() {
        time = Instant.now();
        clock = mock(Clock.class);
        order = new Order(clock);
        when(clock.instant()).thenAnswer((invocation) -> time);
    }

    @Test
    public void call_orderconfirm_should_throw_OrderExpiredException() {
        order.submit();
        time = time.plus(25, ChronoUnit.HOURS);
        Mockito.when(clock.instant()).thenReturn(time);
        assertThrows(OrderExpiredException.class, () -> order.confirm());
    }
    @Test
    public void call_orderconfirm_with_correct_time_should_be_call_without_exception () {
        order.submit();
        time = time.plus(24, ChronoUnit.HOURS);
        order.confirm();
    }


}

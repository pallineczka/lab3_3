package edu.iis.mto.time;

import org.joda.time.Hours;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class OrderTest {

    public Clock clock = Mockito.mock(Clock.class);
    public Order order;
    public Instant timeNow;

    @BeforeEach
    public void init() {
        timeNow = Instant.now();
        order = new Order(clock);
        Mockito.when(clock.instant()).thenReturn(timeNow);
    }

    @Test()
    public void expectedOrderExpiredException() {
        order.submit();
        timeNow = timeNow.plus(25, ChronoUnit.HOURS);
        Mockito.when(clock.instant()).thenReturn(timeNow);
        Assertions.assertThrows(OrderExpiredException.class, () -> order.confirm());
    }

    @Test()
    public void confirmWithoutException() {
        order.submit();
        timeNow = timeNow.plus(24, ChronoUnit.HOURS);
        Mockito.when(clock.instant()).thenReturn(timeNow);
        order.confirm();
    }
}
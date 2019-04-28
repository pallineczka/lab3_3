package edu.iis.mto.time;

import org.junit.Before;
import org.mockito.Mock;

import java.time.Clock;
import java.time.Instant;


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

}
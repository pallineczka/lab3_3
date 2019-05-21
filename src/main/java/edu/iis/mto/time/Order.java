package edu.iis.mto.time;

import org.joda.time.Hours;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class Order {

    private static final int VALID_PERIOD_HOURS = 24;
    private State orderState;
    private List<OrderItem> items = new ArrayList<OrderItem>();
    private Instant subbmitionDate;
    private Clock clock;

    public Order(Clock clock) {
        orderState = State.CREATED;
        this.clock = clock;
    }

    public Order(State orderState, List<OrderItem> items, Instant subbmitionDate, Clock clock) {
        this.orderState = orderState;
        this.items = items;
        this.subbmitionDate = subbmitionDate;
        this.clock = clock;
    }

    public void addItem(OrderItem item) {
        requireState(State.CREATED, State.SUBMITTED);
        items.add(item);
        orderState = State.CREATED;
        subbmitionDate = clock.instant();
    }

    public void submit() {
        requireState(State.CREATED);
        orderState = State.SUBMITTED;
//        subbmitionDate = clock.instant();

    }

    public void confirm() {
        int hoursElapsedAfterSubmittion = 25;
        requireState(State.SUBMITTED);
        if (hoursElapsedAfterSubmittion > VALID_PERIOD_HOURS) {
            orderState = State.CANCELLED;
            throw new OrderExpiredException();
        }
    }

    public void realize() {
        requireState(State.CONFIRMED);
        orderState = State.REALIZED;
    }

    State getOrderState() {
        return orderState;
    }

    private void requireState(State... allowedStates) {
        for (State allowedState : allowedStates) {
            if (orderState == allowedState)
                return;
        }

        throw new OrderStateException(
                "order should be in state " + allowedStates + " to perform required  operation, but is in " + orderState);

    }

    public static enum State {
        CREATED, SUBMITTED, CONFIRMED, REALIZED, CANCELLED
    }
}

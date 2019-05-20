package edu.iis.mto.time;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Hours;

public class Order {

    private static final int VALID_PERIOD_HOURS = 24;
    private State orderState;
    private List<OrderItem> items = new ArrayList<OrderItem>();
    private Instant subbmitionDate;
    private Clock clock;

    public Order() {
        orderState = State.CREATED;
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

    }

    public void confirm() {
        org.joda.time.Instant currentInstant = new org.joda.time.Instant(subbmitionDate.toEpochMilli());

        requireState(State.SUBMITTED);
        int hoursElapsedAfterSubmittion = Hours.hoursBetween(currentInstant, new org.joda.time.Instant(clock.instant().toEpochMilli()))
                                               .getHours();
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

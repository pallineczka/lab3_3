package edu.iis.mto.time;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Hours;

public class Order {
	private static final int VALID_PERIOD_HOURS = 24;
	private State orderState;
	private List<OrderItem> items = new ArrayList<OrderItem>();
	private Instant subbmitionDate;
	private final ZoneId ZONE = ZoneId.systemDefault();
	private final Instant START = Instant.now();
	private long count = 0;
	private Clock clock;

	public Order(Clock clock) {
		orderState = State.CREATED;
		this.clock = clock;
	}

	public ZoneId getZone() {
		return ZONE;
	}

	public Clock withZone(ZoneId zone) {
		return Clock.fixed(START, zone);
	}

	public Instant instant() {
		return nextInstant();
	}

	private Instant nextInstant(){
		count++;
		return START.plusSeconds(count);
	}

	public void addItem(OrderItem item) {
		requireState(State.CREATED, State.SUBMITTED);

		items.add(item);
		orderState = State.CREATED;

	}

	public void submit() {
		requireState(State.CREATED);

		orderState = State.SUBMITTED;
		subbmitionDate = clock.instant();

	}

	public void confirm() {
		requireState(State.SUBMITTED);
		int hoursElapsedAfterSubmittion = Hours.hoursBetween(new org.joda.time.Instant(subbmitionDate.toEpochMilli()),
				new org.joda.time.Instant(clock.instant().toEpochMilli())).getHours();
		if(hoursElapsedAfterSubmittion > VALID_PERIOD_HOURS){
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

		throw new OrderStateException("order should be in state "
				+ allowedStates + " to perform required  operation, but is in "
				+ orderState);

	}

	public static enum State {
		CREATED, SUBMITTED, CONFIRMED, REALIZED, CANCELLED
	}
}

package com.henning.oden.java.StockAlert.Backend.events;

import com.henning.oden.java.StockAlert.Backend.entities.StockWatch;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.ZonedDateTime;

public class StockWatchAlertEvent extends ApplicationEvent {
    @Getter
    private final StockWatch stockWatch;

    public StockWatchAlertEvent(StockWatch stockWatch) {
        super(stockWatch);
        this.stockWatch = stockWatch;
    }

    /**
     * This method gives a brief summary of the event, including the toString() method of the associated StockWatch..
     */
    @Override
    public String toString() {
        return "Alert event for " + stockWatch.toString();
    }
}

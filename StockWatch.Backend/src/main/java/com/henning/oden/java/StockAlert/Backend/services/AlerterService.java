package com.henning.oden.java.StockAlert.Backend.services;

import com.henning.oden.java.StockAlert.Backend.dto.StockAlertDto;
import com.henning.oden.java.StockAlert.Backend.entities.Stock;
import com.henning.oden.java.StockAlert.Backend.entities.SystemUser;
import com.henning.oden.java.StockAlert.Backend.events.StockWatchAlertEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AlerterService {
    private EmailService emailService;
    private StockService stockService;
    private StockPriceDataService stockPriceDataService;
    private CustomUserDetailsService userDetailsService;

    @EventListener
    public void alertUser(StockWatchAlertEvent alertEvent) {
        Stock stock = getStock(alertEvent).orElseThrow(RuntimeException::new);
        long userId = alertEvent.getStockWatch().getUserId();
        SystemUser user = userDetailsService.findById(userId).orElseThrow(RuntimeException::new);
        String userEmail = user.getEmailAddress();
        String username = user.getUsername();
        String stockCode = stock.getCode();
        BigDecimal stockPrice = stockPriceDataService.getLastPriceForStock(stock);
        StockAlertDto alertDto = new StockAlertDto(userEmail, username, stockCode, stockPrice);
        emailService.sendStockAlertEmail(alertDto);
    }

    private Optional<Stock> getStock(StockWatchAlertEvent alertEvent) {
        long stockId = alertEvent.getStockWatch().getStockId();
        return stockService.findById(stockId);
    }

    /**
     * Auto-generated toString() method. Format is unspecified and may change.
     */
    @Override
    public String toString() {
        return "AlerterService{" +
                "emailService=" + emailService +
                ", stockService=" + stockService +
                ", stockPriceDataService=" + stockPriceDataService +
                ", userDetailsService=" + userDetailsService +
                '}';
    }
}

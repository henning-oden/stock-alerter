package com.henning.oden.java.StockAlert.Backend.services;
// Todo: Write unit tests for this class.

import com.henning.oden.java.StockAlert.Backend.entities.Stock;
import com.henning.oden.java.StockAlert.Backend.entities.StockWatch;
import net.jacobpeterson.alpaca.AlpacaAPI;
import net.jacobpeterson.alpaca.enums.BarsTimeFrame;
import net.jacobpeterson.alpaca.rest.exception.AlpacaAPIRequestException;
import net.jacobpeterson.domain.alpaca.marketdata.Bar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockPriceDataService {
    private StockService stockService;
    private StockWatchService stockWatchService;

    @Value( "${alpaca.key_id}" )
    private String keyId;

    @Value( "${alpaca.secret}" )
    private String secret;

    @Value( "${alpaca.base_api_url}" )
    private String alpacaBaseUrl;

    public StockPriceDataService (StockService stockService, StockWatchService stockWatchService) {
        this.stockService = stockService;
        this.stockWatchService = stockWatchService;
    }

    @Scheduled(cron = "5 * 9-16 * * *", zone = "America/New_York")
    public void getStockPriceData() {
        AlpacaAPI api = new AlpacaAPI(keyId, secret, alpacaBaseUrl);
        List<String> stockCodes = stockService.findAll().stream().map(s -> s.getCode()).collect(Collectors.toList());
        ZonedDateTime now = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("America/New_York"));
        ZonedDateTime start = now.minusMinutes(2); // Ensures the last minute's full bars are returned from the API.
        String[] stockCodeArray = stockCodes.toArray(new String[stockCodes.size()]);
        try {
            Map<String, ArrayList<Bar>> bars = api.getBars(BarsTimeFrame.ONE_MIN, stockCodeArray, null, start, now,
                    null, null);
            checkStockPrices(bars);
        } catch (AlpacaAPIRequestException e) {
            e.printStackTrace();
        }
    }

    private void checkStockPrices(Map<String, ArrayList<Bar>> bars) {
        bars.keySet().forEach(k -> checkStockWatchesForStockCode(k, bars.get(k)));
    }

    private void checkStockWatchesForStockCode(String code, ArrayList<Bar> bars) {
        Optional<Stock> stockOptional = stockService.findStockByCode(code);
        if (stockOptional.isPresent()) {
            Stock stock = stockOptional.get();
            List<StockWatch> stockWatches = stockWatchService.findStockWatchesByStock(stock);
            if (bars.size() > 0) {
                updateStockWatches(code, bars, stockWatches);
            };

        } else
        throw new IllegalStateException("Could not find own stock in database! Is the database online?");
    }

    private void updateStockWatches(String code, ArrayList<Bar> bars, List<StockWatch> stockWatches) {
        double stockPrice = bars.get(0).getC();
//        System.out.println(stockPrice);
        stockWatches.forEach(sw -> {
            sw.setTimesExceeded(stockPrice < sw.getMinPrice() || stockPrice > sw.getMaxPrice() ?
                    sw.getTimesExceeded() + 1 : sw.getTimesExceeded());
            stockWatchService.saveStockWatch(sw);
        });
        // Todo: Use a proper logger here.
        System.out.println("Stock watches for stock with code " + code + " have been checked.");
    }
}

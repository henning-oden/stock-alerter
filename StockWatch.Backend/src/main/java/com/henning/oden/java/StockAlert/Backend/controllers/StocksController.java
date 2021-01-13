package com.henning.oden.java.StockAlert.Backend.controllers;
// Todo: Write unit tests for this class.

import com.henning.oden.java.StockAlert.Backend.dto.StockResponse;
import com.henning.oden.java.StockAlert.Backend.dto.StockWatchCreationRequest;
import com.henning.oden.java.StockAlert.Backend.dto.StockWatchDto;
import com.henning.oden.java.StockAlert.Backend.entities.Stock;
import com.henning.oden.java.StockAlert.Backend.entities.StockWatch;
import com.henning.oden.java.StockAlert.Backend.entities.SystemUser;
import com.henning.oden.java.StockAlert.Backend.security.JwtTokenProvider;
import com.henning.oden.java.StockAlert.Backend.services.CustomUserDetailsService;
import com.henning.oden.java.StockAlert.Backend.services.StockService;
import com.henning.oden.java.StockAlert.Backend.services.StockWatchService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/stocks")
@AllArgsConstructor
public class StocksController {
    private JwtTokenProvider jwtTokenProvider;
    private ModelMapper modelMapper;
    private StockService stockService;
    private StockWatchService stockWatchService;
    private CustomUserDetailsService userDetailsService;

    @GetMapping("/all")
    public StockResponse getAllStocks() {
        return new StockResponse(stockService.findAll());
    }

    @PostMapping("/create-watch")
    public StockWatchDto createStockWatch(HttpServletRequest httpRequest, @RequestBody StockWatchCreationRequest creationRequest) {
        String username = jwtTokenProvider.getUsernameFromRequest(httpRequest);
        SystemUser user = (SystemUser) userDetailsService.loadUserByUsername(username);
        long userId = user.getId();
        Optional<Stock> stockOptional = stockService.findStockByCode(creationRequest.getStockCode());
        if (stockOptional.isPresent()) {
            return saveNewStockWatch(creationRequest, userId, stockOptional);
        }
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Specified stock not found.");
    }

    private StockWatchDto saveNewStockWatch(StockWatchCreationRequest creationRequest, long userId, Optional<Stock> stockOptional) {
        Stock stock = stockOptional.get();
        long stockId = stock.getId();
        StockWatch stockWatch = new StockWatch(userId, stockId, creationRequest.getMinPrice(), creationRequest.getMaxPrice(), creationRequest.getAlertThreshold());
        StockWatch savedStockWatch = stockWatchService.saveStockWatch(stockWatch);
        return getStockWatchDto(savedStockWatch);
    }

    private StockWatchDto getStockWatchDto(StockWatch savedStockWatch) {
        StockWatchDto stockWatchDto = modelMapper.map(savedStockWatch, StockWatchDto.class);
        return stockWatchDto;
    }

    @PostMapping("/update-watch") // Todo: Override default error handling to forward exception messages to the client.
    // Todo: Investigate validation of the request body to prevent undesired values in the StockWatch.
    // Current implementation allows for 0 value in prices if a price is misspelled or excluded in the request,
    // as well as a maxPrice that is below minPrice.
    public StockWatchDto updateStockWatch(HttpServletRequest httpRequest, @RequestParam Optional<Long> id,
                                          @RequestBody Optional<StockWatchCreationRequest> creationRequestOptional) {
        if (id.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND,
                    "Required request parameter id missing or malformed. Expected: Positive 64-bit signed integer.");
        }
        if (creationRequestOptional.isPresent()) {
            Optional<StockWatch> stockWatchOptional = stockWatchService.findById(id.get());
            if (stockWatchOptional.isPresent()) {
                return updateStockWatch(creationRequestOptional.get(), stockWatchOptional.get());
            }
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Stock watch with id " + id.get() + " not found.");
        }
        throw new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY,
                "Request body malformed or missing. Please refer to documentation.");
    }

    private StockWatchDto  updateStockWatch(StockWatchCreationRequest creationRequestOptional, StockWatch stockWatchOptional) {
        StockWatch stockWatch = stockWatchOptional;
        modelMapper.map(creationRequestOptional, stockWatch);
        StockWatch savedStockWatch = stockWatchService.saveStockWatch(stockWatch);
        return getStockWatchDto(savedStockWatch);
    }

}

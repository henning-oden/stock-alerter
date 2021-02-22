package com.henning.oden.java.StockAlert.Backend.controllers;
// Todo: Write unit tests for this class.

import com.henning.oden.java.StockAlert.Backend.dto.DeletionResponse;
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
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(maxAge = 3600)
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
    public List<Stock> getAllStocks() {
        // TODO: Also include the latest price for each stock here, OR:
        // TODO: Remove latest price from the front end.
        return stockService.findAll();
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/create-watch")
    public StockWatchDto createStockWatch(HttpServletRequest httpRequest, @RequestBody StockWatchCreationRequest creationRequest) {
        long userId = getUserId(httpRequest);
        return getStockWatchDto(creationRequest, userId);
    }

    private long getUserId(HttpServletRequest httpRequest) {
        SystemUser user = getUser(httpRequest);
        return user.getId();
    }

    private SystemUser getUser(HttpServletRequest httpRequest) {
        String username = jwtTokenProvider.getUsernameFromRequest(httpRequest);
        return (SystemUser) userDetailsService.loadUserByUsername(username);
    }

    private StockWatchDto getStockWatchDto(StockWatchCreationRequest creationRequest, long userId) {
        String stockCode = creationRequest.getStockCode();
        Stock stockOptional = stockService.findStockByCode(stockCode)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Stock with code " + stockCode + " not found."));
        return stockWatchService.saveNewStockWatch(creationRequest, userId, stockOptional);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/update-watch") // Todo: Override default error handling to forward exception messages to the client.
    // Todo: Investigate validation of the request body to prevent undesired values in the StockWatch.
    // Current implementation allows for 0 value in prices if a price is misspelled or excluded in the request,
    // as well as a maxPrice that is below minPrice.
    public StockWatchDto updateStockWatch(HttpServletRequest httpRequest, @RequestParam long id,
                                          @RequestBody StockWatchCreationRequest creationRequest) {
        return stockWatchService.updateStockWatchService(id, creationRequest);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/get-watches")
    public List<StockWatchDto> getStockWatches(HttpServletRequest httpRequest) {
        SystemUser user = getUser(httpRequest);
        // TODO: Add stock code to StockWatchDto.
        return stockWatchService.getStockWatchDtosByUser(user);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/get-watch")
    public StockWatchDto getWatchDto(HttpServletRequest httpRequest, @RequestParam long id) {
        StockWatch stockWatch = getWatchDto(id);
        long userId = getUserId(httpRequest);
        if (stockWatchService.userOwnsWatch(userId, stockWatch)) {
            StockWatchDto stockWatchDto = modelMapper.map(stockWatch, StockWatchDto.class);
            return stockWatchDto;
        }
        throw stockWatchNotFound(id);
    }

    private StockWatch getWatchDto(long id) {
        StockWatch stockWatch = stockWatchService.findById(id).orElseThrow(() -> stockWatchNotFound(id));
        return stockWatch;
    }

    @NotNull
    private HttpClientErrorException stockWatchNotFound(long id) {
        return new HttpClientErrorException(HttpStatus.NOT_FOUND, "Stock watch with id " + id + " not found.");
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @DeleteMapping("/delete-watch")
    public DeletionResponse deleteStockWatch (HttpServletRequest httpRequest, @RequestParam long id) {
        StockWatch stockWatch = getWatchDto(id);
        long userId = getUserId(httpRequest);
        if (stockWatchService.userOwnsWatch(userId, stockWatch)) {
            boolean result = stockWatchService.deleteStockWatch(stockWatch);
            if (result) {
                return new DeletionResponse("Success", "Stock watch with id " + id + " deleted.");
            }
        }
        return new DeletionResponse("Failure", "Could not delete stock watch with id " + id + ".");
    }
}

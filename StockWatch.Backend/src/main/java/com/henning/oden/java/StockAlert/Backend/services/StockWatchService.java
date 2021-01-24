package com.henning.oden.java.StockAlert.Backend.services;

import com.henning.oden.java.StockAlert.Backend.dto.StockWatchCreationRequest;
import com.henning.oden.java.StockAlert.Backend.dto.StockWatchDto;
import com.henning.oden.java.StockAlert.Backend.entities.Stock;
import com.henning.oden.java.StockAlert.Backend.entities.StockWatch;
import com.henning.oden.java.StockAlert.Backend.entities.SystemUser;
import com.henning.oden.java.StockAlert.Backend.repos.StockWatchRepository;
import org.hibernate.JDBCException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockWatchService {
    private final StockWatchRepository stockWatches;
    private final ModelMapper modelMapper;

    public StockWatchService (StockWatchRepository watches, ModelMapper modelMapper) {
        stockWatches = watches;
        this.modelMapper = modelMapper;
    }

    public List<StockWatch> findStockWatchesByStock(Stock stock) {
        return findStockWatchesByStockId(stock.getId());
    }

    public List<StockWatch> findStockWatchesByStockId(long id) {
        List<StockWatch> watchesOptional = stockWatches.findByStockId(id);
        return watchesOptional;
    }

    public List<StockWatch> findStockWatchesByUser(SystemUser user) {
        return findStockWatchesByUserId(user.getId());
    }

    public List<StockWatch> findStockWatchesByUserId(long id) {
        List<StockWatch> watchesOptional = stockWatches.findByUserId(id);
        return watchesOptional;
    }

    public List<StockWatch> findAll() {
        return stockWatches.findAll();
    }

    public StockWatch saveStockWatch(StockWatch stockWatch) {
        return stockWatches.saveAndFlush(stockWatch);
    }

    public boolean deleteStockWatch(StockWatch stockWatch) {
        try {
            stockWatches.delete(stockWatch);
            return true;
        } catch (JDBCException e) {
            // todo; Log the exception...
            return false;
        }
    }

    public StockWatchDto saveNewStockWatch(StockWatchCreationRequest creationRequest, long userId, Stock stock) {
        long stockId = stock.getId();
        StockWatch stockWatch = new StockWatch.Builder(userId)
                                              .forStockId(stockId)
                                              .fromCreationRequest(creationRequest)
                                              .build();
        StockWatch savedStockWatch = saveStockWatch(stockWatch);
        return getStockWatchDto(savedStockWatch);
    }

    private StockWatchDto getStockWatchDto(StockWatch savedStockWatch) {
        StockWatchDto stockWatchDto = modelMapper.map(savedStockWatch, StockWatchDto.class);
        return stockWatchDto;
    }

    public Optional<StockWatch> findById(long id) {
        return stockWatches.findById(id);
    }

    public StockWatchDto updateStockWatchService(long id, StockWatchCreationRequest creationRequest) {
        Optional<StockWatch> stockWatchOptional = findById(id);
        if (stockWatchOptional.isPresent()) {
            return updateStockWatch(creationRequest, stockWatchOptional.get());
        }
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Stock watch with id " + id + " not found.");
    }



    private StockWatchDto  updateStockWatch(StockWatchCreationRequest creationRequest, StockWatch stockWatch) {
        modelMapper.map(creationRequest, stockWatch);
        StockWatch savedStockWatch = saveStockWatch(stockWatch);
        return getStockWatchDto(savedStockWatch);
    }

    /**
     * Auto-generated toString() method. Format is unspecified and may change.
     */
    @Override
    public String toString() {
        return "StockWatchService{" +
                "stockWatches=" + stockWatches +
                ", modelMapper=" + modelMapper +
                '}';
    }

    public boolean userOwnsWatch(long userId, StockWatch stockWatch) {
        return stockWatch.getUserId() == userId;
    }

    public List<StockWatchDto> getStockWatchDtosByUser(SystemUser user) {
        List<StockWatch> stockWatches = findStockWatchesByUser(user);
        return stockWatches.stream()
                .map(stockWatch -> modelMapper.map(stockWatch, StockWatchDto.class))
                .collect(Collectors.toList());
    }
}

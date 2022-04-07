package dev.lslm.marketplace.controllers;

import dev.lslm.marketplace.entities.Stock;
import dev.lslm.marketplace.exceptions.StockAlreadyExistsException;
import dev.lslm.marketplace.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/marketplace/stock")
public class StockController {
    @Autowired
    private StockService stockService;

    @PostMapping
    public ResponseEntity<Stock> create(@RequestBody Stock stock) {
        try {
            Stock createdStock = stockService.create(stock);
            return new ResponseEntity<>(createdStock, HttpStatus.CREATED);
        } catch (StockAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PatchMapping("/{id}/increase")
    public ResponseEntity<Stock> increase(@PathVariable long id, @RequestBody Stock stock) {
        Stock updatedStock = stockService.increase(id, stock);

        return new ResponseEntity<>(updatedStock, HttpStatus.OK);
    }
}

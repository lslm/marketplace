package dev.lslm.marketplace.services;

import dev.lslm.marketplace.entities.Product;
import dev.lslm.marketplace.entities.Stock;
import dev.lslm.marketplace.exceptions.StockAlreadyExistsException;
import dev.lslm.marketplace.repository.ProductRepository;
import dev.lslm.marketplace.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    private boolean stockAlreadyExists(List<Stock> stocks, Stock stock) {
        Optional<Stock> maybeStock = stocks.stream()
                .filter(s -> s.getProduct().getId() == stock.getProduct().getId())
                .findFirst();

        return maybeStock.isPresent();
    }

    public Stock create(Stock stock) throws StockAlreadyExistsException {
        List<Stock> stocks = stockRepository.findAll();

        if (stockAlreadyExists(stocks, stock)) {
            throw new StockAlreadyExistsException();
        }
        Stock savedStock = stockRepository.save(stock);

        Product product = productRepository.findById(stock.getProduct().getId()).get();
        savedStock.setProduct(product);

        return savedStock;
    }

    public Stock increase(long id, Stock stock) {
        int increaseQuantity = stock.getQuantity();
        Stock persistedStock = stockRepository.findById(id).get();
        persistedStock.increaseQuantity(increaseQuantity);

        return stockRepository.save(persistedStock);
    }
}

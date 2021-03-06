package ymyoo.stock.service;

import ymyoo.stock.dto.StockAdjustment;
import ymyoo.stock.entity.ReservedStock;

public interface StockService {
    ReservedStock reserveStock(StockAdjustment stockAdjustment);

    void confirmStock(Long id);

    void cancelStock(Long id);
}

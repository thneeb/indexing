package com.nttdata.dtl.controller;

import org.springframework.data.repository.CrudRepository;

public interface StockPriceRepository extends CrudRepository<StockPrice, StockPriceKey> {
}

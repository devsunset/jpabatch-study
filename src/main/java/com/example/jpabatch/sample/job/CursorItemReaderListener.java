package com.example.jpabatch.sample.job;

import lombok.extern.slf4j.Slf4j;

import com.example.jpabatch.sample.domain.Product;

import org.springframework.batch.core.ItemReadListener;

@Slf4j
public class CursorItemReaderListener implements ItemReadListener<Product> {

    @Override
    public void beforeRead() {

    }

    @Override
    public void afterRead(Product item) {
        log.info("Reading Item id={}", item.getId());
    }

    @Override
    public void onReadError(Exception ex) {

    }
}

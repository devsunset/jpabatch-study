package com.example.jpabatch.sample.job;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.example.jpabatch.sample.repository.ProductRepository;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductIdRangePartitioner implements Partitioner {

    private final ProductRepository productRepository;
    private final LocalDate startDate;
    private final LocalDate endDate;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        long min = productRepository.findMinId(startDate, endDate);
        long max = productRepository.findMaxId(startDate, endDate);
        long targetSize = (max - min) / gridSize + 1;

        Map<String, ExecutionContext> result = new HashMap<>();
        long number = 0;
        long start = min;
        long end = start + targetSize - 1;

        while (start <= max) {
            ExecutionContext value = new ExecutionContext();
            result.put("partition" + number, value);

            if (end >= max) {
                end = max;
            }

            value.putLong("minId", start); // 각 파티션마다 사용될 minId
            value.putLong("maxId", end); // 각 파티션마다 사용될 maxId
            start += targetSize;
            end += targetSize;
            number++;
        }

        return result;
    }
}

package org.silsagusi.joonggaemoa.request.naverland.client;

import java.time.Duration;
import java.util.function.Supplier;

import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RetryUtils {
    private static final int MAX_RETRIES = 3;
    private static final long BASE_DELAY_MS = 1000; // 1초

    public static <T> T withRetry(Supplier<T> supplier) {
        int retryCount = 0;
        while (true) {
            try {
                return supplier.get();
            } catch (WebClientResponseException e) {
                if (e.getStatusCode().value() == 429) { // Too Many Requests
                    retryCount++;
                    if (retryCount > MAX_RETRIES) {
                        log.error("Maximum retry attempts reached for 429 error", e);
                        throw e;
                    }
                    long delay = BASE_DELAY_MS * (long) Math.pow(2, retryCount - 1); // Exponential backoff
                    log.warn("Received 429 error, retrying in {} ms (attempt {}/{})", delay, retryCount, MAX_RETRIES);
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Retry interrupted", ie);
                    }
                } else {
                    throw e;
                }
            }
        }
    }
} 
package com.example;

import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OpenObserveLog4jExample {
    private static final Logger logger = LogManager.getLogger(OpenObserveLog4jExample.class);

    public static void main(String[] args) {

        // Add some context to all logs
        ThreadContext.put("requestId", "req-123");

        try {
            logger.info("Application started");

            // Log some sample messages
            logger.debug("Debug message - checking connection status");
            logger.info("Processing new transaction with id: TX789");

            // Log with an exception
            try {
                int result = 10 / 0;
            } catch (Exception e) {
                logger.error("Calculation error occurred", e);
            }

            // Add more context for specific logs
            ThreadContext.put("userId", "user-456");
            logger.warn("User session is about to expire");
            ThreadContext.remove("userId");

            logger.info("Application shutting down");
        } finally {
            // Clean up thread context
            ThreadContext.clearAll();
        }
    }
}
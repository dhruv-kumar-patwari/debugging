package com.zemosolabs.debuggingscenarios;

import java.util.UUID;

public interface IBalanceService {
    double getBalance(UUID customerId);

    void addBalance(UUID customerId, double amount);

    void deductBalance(UUID customerId, double amount);
}

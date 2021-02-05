package com.zemosolabs.debuggingscenarios;

import com.google.common.base.Preconditions;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CheckoutService implements ICheckoutService{
  private final ICartService fCartService;
  private final IBalanceService fBalanceService;

  public CheckoutService(
          final ICartService cartService, final IBalanceService balanceService){
    fCartService = Preconditions.checkNotNull(cartService, "CartService");
    fBalanceService = Preconditions.checkNotNull(balanceService, "BalanceService");
  }

  @Override
  public void checkout(final UUID customerId) {
    Preconditions.checkNotNull(customerId, "CustomerId");
    var cart = new ConcurrentHashMap<>(fCartService.getCart(customerId));
    Preconditions.checkState(
            cart != null && !cart.isEmpty(),
            "Customer doesn't have items in his cart.");
    var amount = cart.keySet().stream()
            .map(itemKey -> cart.get(itemKey).getCost() * cart.get(itemKey).getQuantity())
            .reduce(0.0D, Double::sum);
    fBalanceService.deductBalance(customerId, amount);
    ejectItemsInCart(cart);
    fCartService.clearCart(customerId);
  }

  private void ejectItemsInCart(final Map cart){
    //code to eject items
  }
}

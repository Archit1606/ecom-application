package com.ecommerce.order.services;

import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.models.CartItem;
import com.ecommerce.order.repository.CartItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    //private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    // private final UserRepository userRepository;

    public boolean addToCart(String userId, CartItemRequest request) {

//        // Implementation to add item to cart
//        Optional<Product> productOpt = productRepository.findById(request.getProductId());
//        if (productOpt.isEmpty())
//            return false;
//        Product product = productOpt.get();
//        if (product.getStockQuantity() < request.getQuantity())
//            return false;
//        // Further logic to add the product to the user's cart
//        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
//        if (userOpt.isEmpty())
//            return false;
//        User user = userOpt.get();

        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId, String.valueOf(request.getProductId()));
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());

            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(existingCartItem);

        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUserId(Long.valueOf(userId));
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());

            cartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    public boolean deleteCartItem(String userId, Long productId) {
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, String.valueOf(productId));


        if (cartItem != null) {
            cartItemRepository.delete(cartItem);
            return true;
        }


        return false;
    }

    public List<CartItem> getCart(String userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public void clearCart(String userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}

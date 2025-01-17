package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;

import static com.example.demo.controllers.Constants.USER_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

@Autowired
    private CartController cartController;
    private final UserRepository userRepository = mock(UserRepository.class);
    private final CartRepository cartRepository = mock(CartRepository.class);
    private final ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp() {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);
    }

    @Test
    public void addToCart() {
        User user = new User();
        user.setUsername(USER_NAME);

        Item item = Constants.ITEM_0;

        Cart cart = new Cart();
        cart.setId(0L);
        List<Item> currItems = new ArrayList<>();
        currItems.add(item);
        cart.setItems(currItems);
        cart.setTotal(new BigDecimal("3.00"));
        cart.setUser(user);

        user.setCart(cart);

        when(userRepository.findByUsername(USER_NAME)).thenReturn(user);
        when(itemRepository.findById(0L)).thenReturn(Optional.of(item));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(0L);
        request.setQuantity(1);
        request.setUsername(USER_NAME);

        ResponseEntity<Cart> response = cartController.addTocart(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Cart retrievedCart = response.getBody();
        assertNotNull(retrievedCart);
        Assertions.assertEquals(0L, (long)retrievedCart.getId());
        List<Item> items = retrievedCart.getItems();
        assertNotNull(items);
        Item retrievedItem = items.get(0);
        assertNotNull(retrievedItem);
        assertEquals(item, retrievedItem);
        assertEquals(2, items.size());
        assertEquals(new BigDecimal("6.00"), retrievedCart.getTotal());
        assertEquals(user, retrievedCart.getUser());
    }

    @Test
    public void testAddToCartNullUser() {
        User user = new User();
        user.setUsername(USER_NAME);

        Item item = Constants.ITEM_0;

        Cart cart = new Cart();
        cart.setId(0L);
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        cart.setItems(itemList);
        cart.setTotal(new BigDecimal("3.00"));
        cart.setUser(user);
        user.setCart(cart);

        when(userRepository.findByUsername(USER_NAME)).thenReturn(null);
        when(itemRepository.findById(0L)).thenReturn(java.util.Optional.of(item));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(0L);
        request.setQuantity(1);
        request.setUsername(USER_NAME);

        ResponseEntity<Cart> response = cartController.addTocart(request);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void removeFromCart() {
        User user = new User();
        user.setUsername(USER_NAME);

        Item item = Constants.ITEM_0;

        Cart cart = new Cart();
        cart.setId(0L);
        List<Item> currItems = new ArrayList<>();
        currItems.add(item);
        cart.setItems(currItems);
        cart.setTotal(new BigDecimal("3.00"));
        cart.setUser(user);
        user.setCart(cart);

        when(userRepository.findByUsername(USER_NAME)).thenReturn(user);
        when(itemRepository.findById(0L)).thenReturn(java.util.Optional.of(item));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(0L);
        request.setQuantity(1);
        request.setUsername(USER_NAME);

        ResponseEntity<Cart> response = cartController.removeFromcart(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Cart retrievedCart = response.getBody();
        assertNotNull(retrievedCart);
        assertEquals(0L, (long)retrievedCart.getId());
        List<Item> items = retrievedCart.getItems();
        assertNotNull(items);
        assertEquals(0, items.size());
        assertEquals(new BigDecimal("0.00"), retrievedCart.getTotal());
        assertEquals(user, retrievedCart.getUser());
    }
}
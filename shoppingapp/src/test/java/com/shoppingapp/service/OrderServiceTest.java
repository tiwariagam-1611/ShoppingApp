package com.shoppingapp.service;

import com.shoppingapp.dto.request.OrderItemRequestDTO;
import com.shoppingapp.dto.request.OrderRequestDTO;
import com.shoppingapp.dto.response.OrderResponseDTO;
import com.shoppingapp.exception.ResourceNotFoundException;
import com.shoppingapp.model.Inventory;
import com.shoppingapp.model.Product;
import com.shoppingapp.model.User;
import com.shoppingapp.repository.InventoryRepository;
import com.shoppingapp.repository.ProductRepository;
import com.shoppingapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    private User createUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPhone("1234567890");
        user.setRole("CUSTOMER");
        return userRepository.save(user);
    }

    private Product createProduct(String name, double price) {
        Product product = new Product();
        product.setProductName(name);
        product.setDescription(name + " description");
        product.setCategory("Electronics");
        product.setPrice(price);
        return productRepository.save(product);
    }

    private void createInventory(Product product, int available, int reorderLevel) {
        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setAvailableQuantity(available);
        inventory.setReorderLevel(reorderLevel);
        inventoryRepository.save(inventory);
    }

    @Test
    void checkout_shouldCreateOrderAndReturnResponse() {
        User user = createUser();
        Product product = createProduct("Phone", 100.0);
        createInventory(product, 10, 2);

        OrderItemRequestDTO item = new OrderItemRequestDTO();
        item.setProductId(product.getProductId());
        item.setQuantity(2);

        OrderRequestDTO request = new OrderRequestDTO();
        request.setUserId(user.getUserId());
        request.setItems(List.of(item));

        OrderResponseDTO response = orderService.checkout(request);

        assertEquals("COMPLETED", response.getStatus());
        assertEquals(200.0, response.getTotalAmount());
        assertEquals(1, response.getItems().size());

        int remaining = inventoryRepository.findByProduct_ProductId(product.getProductId())
                .orElseThrow()
                .getAvailableQuantity();
        assertEquals(8, remaining);
    }

    @Test
    void checkout_whenUserMissing_shouldThrow() {
        OrderRequestDTO request = new OrderRequestDTO();
        request.setUserId(99L);
        request.setItems(List.of());

        assertThrows(ResourceNotFoundException.class, () -> orderService.checkout(request));
    }

    @Test
    void checkout_whenProductMissing_shouldThrow() {
        User user = createUser();

        OrderItemRequestDTO item = new OrderItemRequestDTO();
        item.setProductId(999L);
        item.setQuantity(1);

        OrderRequestDTO request = new OrderRequestDTO();
        request.setUserId(user.getUserId());
        request.setItems(List.of(item));

        assertThrows(ResourceNotFoundException.class, () -> orderService.checkout(request));
    }

    @Test
    void getOrderById_whenMissing_shouldThrow() {
        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(88L));
    }

    @Test
    void getAllOrders_shouldMapResult() {
        User user = createUser();
        Product product = createProduct("Tablet", 300.0);
        createInventory(product, 5, 1);

        OrderItemRequestDTO item = new OrderItemRequestDTO();
        item.setProductId(product.getProductId());
        item.setQuantity(1);

        OrderRequestDTO request = new OrderRequestDTO();
        request.setUserId(user.getUserId());
        request.setItems(List.of(item));

        orderService.checkout(request);

        List<OrderResponseDTO> response = orderService.getAllOrders();

        assertEquals(1, response.size());
        assertEquals("COMPLETED", response.get(0).getStatus());
        assertEquals(300.0, response.get(0).getTotalAmount());
    }

    @Test
    void getOrdersByUserId_shouldMapResult() {
        User user = createUser();
        Product product = createProduct("Mouse", 50.0);
        createInventory(product, 6, 2);

        OrderItemRequestDTO item = new OrderItemRequestDTO();
        item.setProductId(product.getProductId());
        item.setQuantity(1);

        OrderRequestDTO request = new OrderRequestDTO();
        request.setUserId(user.getUserId());
        request.setItems(List.of(item));

        orderService.checkout(request);

        List<OrderResponseDTO> response = orderService.getOrdersByUserId(user.getUserId());

        assertEquals(1, response.size());
        assertEquals(50.0, response.get(0).getTotalAmount());
    }
}
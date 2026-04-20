package com.shoppingapp.service;

import com.shoppingapp.dto.request.OrderItemRequestDTO;
import com.shoppingapp.dto.request.OrderRequestDTO;
import com.shoppingapp.dto.response.OrderResponseDTO;
import com.shoppingapp.exception.ResourceNotFoundException;
import com.shoppingapp.model.Order;
import com.shoppingapp.model.Product;
import com.shoppingapp.model.User;
import com.shoppingapp.repository.OrderItemRepository;
import com.shoppingapp.repository.OrderRepository;
import com.shoppingapp.repository.ProductRepository;
import com.shoppingapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private OrderService orderService;

    @Test
    void checkout_shouldCreateOrderAndReturnResponse() {
        User user = new User();
        user.setUserId(1L);

        Product product = new Product();
        product.setProductId(2L);
        product.setProductName("Phone");
        product.setPrice(100.0);

        OrderItemRequestDTO item = new OrderItemRequestDTO();
        item.setProductId(2L);
        item.setQuantity(2);

        OrderRequestDTO request = new OrderRequestDTO();
        request.setUserId(1L);
        request.setItems(List.of(item));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(2L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setOrderId(10L);
            return order;
        });

        OrderResponseDTO response = orderService.checkout(request);

        assertEquals(10L, response.getOrderId());
        assertEquals("COMPLETED", response.getStatus());
        assertEquals(200.0, response.getTotalAmount());
        assertEquals(1, response.getItems().size());

        verify(inventoryService).deductStock(2L, 2);
        verify(orderItemRepository).saveAll(any());
    }

    @Test
    void checkout_whenUserMissing_shouldThrow() {
        OrderRequestDTO request = new OrderRequestDTO();
        request.setUserId(99L);

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.checkout(request));
    }

    @Test
    void getOrderById_whenMissing_shouldThrow() {
        when(orderRepository.findById(88L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(88L));
    }

    @Test
    void getOrdersByUserId_shouldMapResult() {
        Order order = new Order();
        order.setOrderId(1L);
        order.setOrderStatus("COMPLETED");
        order.setTotalAmount(50.0);
        order.setOrderItems(List.of());

        when(orderRepository.findByUserUserId(1L)).thenReturn(List.of(order));

        List<OrderResponseDTO> response = orderService.getOrdersByUserId(1L);

        assertEquals(1, response.size());
        assertEquals(1L, response.get(0).getOrderId());
    }
}

package com.shoppingapp.service;

import com.shoppingapp.dto.request.OrderItemRequestDTO;
import com.shoppingapp.dto.request.OrderRequestDTO;
import com.shoppingapp.dto.response.OrderItemResponseDTO;
import com.shoppingapp.dto.response.OrderResponseDTO;
import com.shoppingapp.exception.ResourceNotFoundException;
import com.shoppingapp.model.*;
import com.shoppingapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryService inventoryService;
    
    @Autowired
    private NotificationService notificationService;

    @Transactional
    public OrderResponseDTO checkout(OrderRequestDTO orderRequest) {

        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + orderRequest.getUserId()));

        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus("PENDING");

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0.0;

        for (OrderItemRequestDTO itemDto : orderRequest.getItems()) {

            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found ID: " + itemDto.getProductId()));

            inventoryService.deductStock(product.getProductId(), itemDto.getQuantity());

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductId(product.getProductId());
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setUnitPrice(product.getPrice());

            totalAmount += (product.getPrice() * itemDto.getQuantity());
            orderItems.add(orderItem);
        }

        order.setTotalAmount(totalAmount);
        order.setOrderItems(orderItems);
        order.setOrderStatus("COMPLETED");

        Order savedOrder = orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);
        notificationService.generateCheckoutConfirmation(
        	    user.getUserId(),
        	    savedOrder.getOrderId()
        	);

        return mapToResponseDTO(savedOrder);
    }

    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDTO> responseList = new ArrayList<>();

        for (Order order : orders) {
            responseList.add(mapToResponseDTO(order));
        }

        return responseList;
    }

    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id));
        return mapToResponseDTO(order);
    }

    public List<OrderResponseDTO> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserUserId(userId);
        List<OrderResponseDTO> responseList = new ArrayList<>();

        for (Order order : orders) {
            responseList.add(mapToResponseDTO(order));
        }

        return responseList;
    }

    private OrderResponseDTO mapToResponseDTO(Order order) {
        OrderResponseDTO response = new OrderResponseDTO();
        response.setOrderId(order.getOrderId());
        response.setStatus(order.getOrderStatus());
        response.setTotalAmount(order.getTotalAmount());

        List<OrderItemResponseDTO> itemDTOs = new ArrayList<>();

        for (OrderItem item : order.getOrderItems()) {
            OrderItemResponseDTO itemDto = new OrderItemResponseDTO();
            itemDto.setProductId(item.getProductId());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setPrice(item.getUnitPrice());
            itemDTOs.add(itemDto);
        }

        response.setItems(itemDTOs);
        return response;
    }
}
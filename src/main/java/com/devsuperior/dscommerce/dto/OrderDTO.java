package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.Order;
import com.devsuperior.dscommerce.entities.OrderItem;
import com.devsuperior.dscommerce.entities.OrderStatus;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class OrderDTO {

    private Long id;
    private Instant moment;
    private OrderStatus status;
    private UserDTO client;
    private Set<OrderItemDTO> items = new HashSet<>();
    private Double total;

    public OrderDTO(Order entity) {
        this.id = entity.getId();
        this.moment = entity.getMoment();
        this.status = entity.getStatus();
        this.client = new UserDTO(entity.getClient());
        for (OrderItem item : entity.getItems()) {
            this.items.add(new OrderItemDTO(item));
        }
        this.total = entity.getTotal();
    }

    public Long getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public UserDTO getClient() {
        return client;
    }

    public Set<OrderItemDTO> getItems() {
        return items;
    }

    public Double getTotal() {
        return total;
    }
}


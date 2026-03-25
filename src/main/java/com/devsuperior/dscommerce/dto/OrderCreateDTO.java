package com.devsuperior.dscommerce.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

public class OrderCreateDTO {

    @Valid
    @NotEmpty
    private List<OrderItemInputDTO> items = new ArrayList<>();

    public OrderCreateDTO() {
    }

    public List<OrderItemInputDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemInputDTO> items) {
        this.items = items;
    }
}


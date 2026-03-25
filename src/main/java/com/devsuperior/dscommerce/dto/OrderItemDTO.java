package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.OrderItem;

public class OrderItemDTO {

    private Long productId;
    private String name;
    private String imgUrl;
    private Double price;
    private Integer quantity;
    private Double subTotal;

    public OrderItemDTO(OrderItem entity) {
        this.productId = entity.getProduct().getId();
        this.name = entity.getProduct().getName();
        this.imgUrl = entity.getProduct().getImgUrl();
        this.price = entity.getPrice();
        this.quantity = entity.getQuantity();
        this.subTotal = entity.getSubTotal();
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getSubTotal() {
        return subTotal;
    }
}


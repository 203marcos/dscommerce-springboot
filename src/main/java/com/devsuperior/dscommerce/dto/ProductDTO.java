package com.devsuperior.dscommerce.dto;


import com.devsuperior.dscommerce.entities.Product;
import jakarta.validation.constraints.*;

public class ProductDTO {
    private Long id;

    @Size(min = 3, max = 80, message = "O nome deve ser entre 3 a 80 caracteres")
    @NotBlank (message = "Campo requerido")
    private String name;

    @Size(min = 10, message = "A descrição precisa ter no minimo 10 caracteres")
    @NotBlank (message = "Campo requerido")
    private String description;

    @Positive(message = "O preço deve ser positivo")
    private Double price;
    private String imgUrl;

    public ProductDTO() {
    }


    public ProductDTO(Long id, String name, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.imgUrl = product.getImgUrl();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}

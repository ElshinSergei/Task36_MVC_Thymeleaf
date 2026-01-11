package org.example.task36_mvc_crudoperation.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import org.example.task36_mvc_crudoperation.entity.Customer;

import java.time.LocalDate;

public class CustomerDTO {

    private Long id;
    @NotBlank(message = "name не может быть пустым")
    private String name;
    @Email(message = "Некорректный email")
    private String email;
    @NotBlank(message = "address не может быть пустым")
    private String address;
    @PastOrPresent(message = "Дата покупки не может быть в будущем")
    private LocalDate orderDate;

    public CustomerDTO() {}

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.address = customer.getAddress();

        if (customer.getPurchases() != null && !customer.getPurchases().isEmpty()) {
            this.orderDate = customer.getPurchases().get(0).getOrderDate();
        }
    }

    public Customer toEntity() {
        Customer customer = new Customer();
        customer.setId(this.id);
        customer.setName(this.name);
        customer.setEmail(this.email);
        customer.setAddress(this.address);
        return customer;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
}

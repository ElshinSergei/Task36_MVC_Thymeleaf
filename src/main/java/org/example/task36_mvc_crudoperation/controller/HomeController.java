package org.example.task36_mvc_crudoperation.controller;

import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import org.example.task36_mvc_crudoperation.dto.CustomerDTO;
import org.example.task36_mvc_crudoperation.entity.Customer;
import org.example.task36_mvc_crudoperation.entity.Purchase;
import org.example.task36_mvc_crudoperation.repository.CustomerRepository;
import org.example.task36_mvc_crudoperation.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/customer")
public class HomeController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private Faker faker;


    @GetMapping("/home")
    public String home(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            Model model) {

        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<Customer> customerPage = customerRepository.findAll(pageable);

        model.addAttribute("customers", customerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", customerPage.getTotalPages());
        model.addAttribute("totalItems", customerPage.getTotalElements());
        model.addAttribute("pageSize", size);
        model.addAttribute("pageName", "Home page");
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);

        return "home";
    }

    @GetMapping("/addAll")
    public String addAll(Model model) {
        generationData(50);
        return "redirect:/customer/home";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("pageName", "Добавление покупателя");
        model.addAttribute("customerDTO", new CustomerDTO());
        return "create";
    }

    @PostMapping("/create")
    public String create(@Valid CustomerDTO customerDTO, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "create";
        }
        Customer customer = customerDTO.toEntity();
        customerRepository.save(customer);
        if(customerDTO.getOrderDate() != null) {
            Purchase purchase = new Purchase();
            purchase.setOrderDate(customerDTO.getOrderDate());
            purchaseRepository.save(purchase);
            customer.addPurchase(purchase);
        }
        customerRepository.save(customer);
        return "redirect:/customer/home";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Optional<Customer> customerEdit = customerRepository.findById(id);
        Customer customer = customerEdit.get();
        CustomerDTO customerDTO = new CustomerDTO(customer);
        model.addAttribute("pageName", "Редактирование покупателя");
        model.addAttribute("customerDTO", customerDTO);
        return "edit";
    }

    @PostMapping("/update/{id}")
    public String update(@Valid CustomerDTO customerDTO, BindingResult bindingResult, Model model) {
        Customer customer = customerDTO.toEntity();
        customerRepository.save(customer);
        if(customerDTO.getOrderDate() != null) {
            Purchase purchase = new Purchase();
            purchase.setOrderDate(customerDTO.getOrderDate());
            purchaseRepository.save(purchase);
            customer.addPurchase(purchase);
        }
        customerRepository.save(customer);
        return "redirect:/customer/home";
    }

    @GetMapping("/clear")
    public String clear(Model model) {
        purchaseRepository.deleteAll();
        customerRepository.deleteAll();
        return "redirect:/customer/home";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id, Model model) {
        customerRepository.deleteById(id);
        return "redirect:/customer/home";
    }

    private void generationData(int count) {
        if(customerRepository.count() == 0) {
            for (int i = 0; i < count; i++) {
                String name = faker.name().fullName();
                String email = faker.internet().emailAddress();
                String address = faker.address().fullAddress();
                Customer customer = new Customer(name,email,address);
                customerRepository.save(customer);
                Long orderNumber = faker.number().numberBetween(1000L, 9999L);
                LocalDate orderDate = faker.date().past(365, TimeUnit.DAYS)
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                purchaseRepository.save(new Purchase(orderNumber, orderDate, customer));
            }
        }
    }

}

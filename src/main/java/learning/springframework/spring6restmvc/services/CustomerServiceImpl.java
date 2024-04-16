package learning.springframework.spring6restmvc.services;

import learning.springframework.spring6restmvc.model.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    HashMap<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        CustomerDTO customer1 = CustomerDTO.builder()
                        .id(UUID.randomUUID())
                        .firstName("Alex")
                        .lastName("Bakhtiari")
                        .email("a@gmail.com")
                        .version(1)
                        .createdDate(LocalDateTime.now())
                        .updateDate(LocalDateTime.now())
                        .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .firstName("Leni")
                .lastName("Galips")
                .email("l-g@gmail.com")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        this.customerMap.put(customer1.getId(), customer1);
        this.customerMap.put(customer2.getId(), customer2);
    }

    @Override
    public List<CustomerDTO> getCustomers() {
        log.debug("In CustomerService - Get all customers");
        return new ArrayList<>(this.customerMap.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        log.debug("In CustomerService - Get customer with id " + id);
        return Optional.of(this.customerMap.get(id));
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        CustomerDTO savedCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        this.customerMap.put(savedCustomer.getId(), savedCustomer);

        return savedCustomer;
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer) {
        CustomerDTO existing = this.customerMap.get(customerId);
        existing.setFirstName(customer.getFirstName());
        existing.setLastName(customer.getLastName());
        existing.setEmail(customer.getEmail());

        this.customerMap.put(existing.getId(), existing);
        return Optional.of(existing);
    }

    @Override
    public Boolean deleteCustomerById(UUID customerId) {
        this.customerMap.remove(customerId);
        return true;
    }

    @Override
    public Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO customer) {
        CustomerDTO existing = this.customerMap.get(customerId);

        if(StringUtils.hasText(customer.getFirstName())) {
            existing.setFirstName(customer.getFirstName());
        }

        if(StringUtils.hasText(customer.getLastName())) {
            existing.setLastName(customer.getLastName());
        }

        if(StringUtils.hasText(customer.getEmail())) {
            existing.setEmail(customer.getEmail());
        }

        this.customerMap.put(existing.getId(), existing);
        return Optional.of(existing);
    }
}

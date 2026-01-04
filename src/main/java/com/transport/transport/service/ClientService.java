package com.transport.transport.service;

import com.transport.transport.model.Client;
import com.transport.transport.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;

    public List<Client> findAll() {
        return repository.findAll();
    }

    public Client findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    public void save(Client client) {
        repository.save(client);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public void markAsPaid(Long id) {
        Client client = findById(id);
        client.setPaid(true);
        repository.save(client);
    }
}

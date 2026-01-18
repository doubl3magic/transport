package com.transport.transport.service;

import com.transport.transport.model.Client;
import com.transport.transport.repository.ClientRepository;
import com.transport.transport.repository.TransportRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final TransportRepository transportRepository;

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

    @Transactional
    public void markAsPaid(Long clientId) {
        Client client = repository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        transportRepository.markAllPaidByClientId(clientId);

        boolean hasUnpaid = transportRepository.existsByClientIdAndPaidFalse(clientId);
        if (!hasUnpaid) {
            client.setPaid(true);
            repository.save(client);
        }
    }
}

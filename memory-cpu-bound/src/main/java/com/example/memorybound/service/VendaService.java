package com.example.memorybound.service;

import com.example.memorybound.model.Venda;
import com.example.memorybound.repository.VendaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendaService {
    @Autowired
    private VendaRepository vendaRepositorio;

    public void init(Long quantidade) {
        int numberOfThreads = 20;
        int trabalhoPorThread = (int) (quantidade / numberOfThreads);
        int trabalhoRestante = (int) (quantidade % numberOfThreads);

        Thread[] threads = new Thread[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            final int inicio = i * trabalhoPorThread;
            final int fim = (i == numberOfThreads - 1) ? inicio + trabalhoPorThread + trabalhoRestante : inicio + trabalhoPorThread;

            threads[i] = new Thread(() -> {
                for (int j = inicio; j < fim; j++) {
                    Venda venda = new Venda();
                    venda.setProduto("Produto " + j);
                    venda.setQuantidade((int) (Math.random() * 100));
                    venda.setPreco(Math.random() * 100);
                    vendaRepositorio.save(venda);
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < numberOfThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public List<Venda> getTodasVendas() {
        return vendaRepositorio.findAll();
    }

    public double calcularReceitaTotal() {
        return vendaRepositorio.findAll().stream()
                .mapToDouble(venda -> venda.getQuantidade() * venda.getPreco())
                .sum();
    }
}

package com.example.memorybound.controll;

import com.example.memorybound.model.Venda;
import com.example.memorybound.service.VendaService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/vendas")
public class VendaController {
    @Autowired
    private VendaService vendaServico;

    @GetMapping("/receita")
    public double getReceitaTotal() {
        return vendaServico.calcularReceitaTotal();
    }

    @PostMapping
    public void initVendas(@Parameter Long quantidadeVendas){
        log.info("Iniciando os vendas do quantidade {}", quantidadeVendas);
        vendaServico.init(quantidadeVendas);
    }
}

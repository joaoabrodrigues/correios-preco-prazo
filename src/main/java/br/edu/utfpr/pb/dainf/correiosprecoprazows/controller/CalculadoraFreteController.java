package br.edu.utfpr.pb.dainf.correiosprecoprazows.controller;

import br.edu.utfpr.pb.dainf.correiosprecoprazows.model.PrecoPrazo;
import br.edu.utfpr.pb.dainf.correiosprecoprazows.service.CalculadoraFreteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculadoraFreteController {

    @GetMapping("preco")
    public PrecoPrazo getPrecoPrazo() {

        CalculadoraFreteService calc = new CalculadoraFreteService();

        return calc.getPrecoPrazo();
    }
}

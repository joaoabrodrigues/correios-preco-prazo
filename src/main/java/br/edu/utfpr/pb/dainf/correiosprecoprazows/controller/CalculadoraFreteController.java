package br.edu.utfpr.pb.dainf.correiosprecoprazows.controller;

import java.math.BigDecimal;
import java.util.List;

import br.edu.utfpr.pb.dainf.correiosprecoprazows.model.DataRequest;
import br.edu.utfpr.pb.dainf.correiosprecoprazows.service.CalculadoraFreteService;
import correios.wsdl.CServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculadoraFreteController {

    private final CalculadoraFreteService calculadoraFreteService;

    public CalculadoraFreteController(CalculadoraFreteService calculadoraFreteService) {
        this.calculadoraFreteService = calculadoraFreteService;
    }

    @GetMapping(path = "preco", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<CServico> getPrecoPrazo(@RequestParam final String servico,
                                        @RequestParam final String cepOrigem,
                                        @RequestParam final String cepDestino,
                                        @RequestParam final String peso,
                                        @RequestParam final Integer formato,
                                        @RequestParam final BigDecimal comprimento,
                                        @RequestParam final BigDecimal altura,
                                        @RequestParam final BigDecimal largura,
                                        @RequestParam final BigDecimal diametro,
                                        @RequestParam final String mp,
                                        @RequestParam final BigDecimal valorDeclarado,
                                        @RequestParam final String ar
            ) {

        return calculadoraFreteService.getPrecoPrazo(DataRequest.builder()
                .servico(servico)
                .cepOrigem(cepOrigem)
                .cepDestino(cepDestino)
                .peso(peso)
                .formato(formato)
                .comprimento(comprimento)
                .altura(altura)
                .largura(largura)
                .diametro(diametro)
                .mp(mp)
                .valorDeclarado(valorDeclarado)
                .ar(ar)
                .build());
    }
}

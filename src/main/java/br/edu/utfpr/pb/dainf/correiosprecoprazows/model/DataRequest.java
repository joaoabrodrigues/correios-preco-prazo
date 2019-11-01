package br.edu.utfpr.pb.dainf.correiosprecoprazows.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DataRequest {

    private String servico;
    private String cepOrigem;
    private String cepDestino;
    private String peso;
    private Integer formato;
    private BigDecimal comprimento;
    private BigDecimal altura;
    private BigDecimal largura;
    private BigDecimal diametro;
    private String mp;
    private BigDecimal valorDeclarado;
    private String ar;
}

package br.edu.utfpr.pb.dainf.correiosprecoprazows.config;

import br.edu.utfpr.pb.dainf.correiosprecoprazows.service.CalculadoraFreteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class CalculadoraFreteConfig {


    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("correios.wsdl");
        return marshaller;
    }

    @Bean
    public CalculadoraFreteService countryClient(Jaxb2Marshaller marshaller) {
        CalculadoraFreteService client = new CalculadoraFreteService();
        client.setDefaultUri("http://ws.correios.com.br/calculador/CalcPrecoPrazo.asmx");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}

package br.edu.utfpr.pb.dainf.correiosprecoprazows.service;

import java.util.List;

import br.edu.utfpr.pb.dainf.correiosprecoprazows.model.DataRequest;
import br.edu.utfpr.pb.dainf.correiosprecoprazows.model.PrecoPrazo;
import correios.wsdl.CResultado;
import correios.wsdl.CServico;
import correios.wsdl.CalcPrecoPrazo;
import correios.wsdl.CalcPrecoPrazoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@Service
public class CalculadoraFreteService extends WebServiceGatewaySupport {

    private static final Logger LOG = LoggerFactory.getLogger(CalculadoraFreteService.class);

    @Value("${codigo.empresa}")
    private String nCdEmpresa;

    @Value("${senha.empresa}")
    private String sDsSenha;

    public PrecoPrazo getPrecoPrazo(final DataRequest dataRequest) {
        final PrecoPrazo p = new PrecoPrazo();

        try {
            final CalcPrecoPrazo calc = buildPrecoPrazo(dataRequest);

            final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

            marshaller.setContextPath("correios.wsdl");
            marshaller.afterPropertiesSet();

            getWebServiceTemplate().setMarshaller(marshaller);
            getWebServiceTemplate().setUnmarshaller(marshaller);
            getWebServiceTemplate().afterPropertiesSet();


            CalcPrecoPrazoResponse response = (CalcPrecoPrazoResponse) getWebServiceTemplate()
                    .marshalSendAndReceive("http://ws.correios.com.br/calculador/CalcPrecoPrazo.asmx", calc,
                            new SoapActionCallback("http://tempuri.org/CalcPrecoPrazo"));

            CResultado result = response.getCalcPrecoPrazoResult();

            List<CServico> cServicos = result.getServicos().getCServico();

            cServicos.forEach(cServico -> {
                p.setPrazo(Integer.valueOf(cServico.getPrazoEntrega()));
                p.setPreco(Double.valueOf(cServico.getValor().replace(".","").replace(",",".") ));
            });

        } catch (final Exception e) {
            LOG.error("Erro ao enviar request ao ws do correio", e);
        }

        return p;
    }

    private CalcPrecoPrazo buildPrecoPrazo (final DataRequest dataRequest) {
        CalcPrecoPrazo calc = new CalcPrecoPrazo();
        calc.setNCdEmpresa(nCdEmpresa);
        calc.setSDsSenha(sDsSenha);

        calc.setNCdFormato(dataRequest.getFormato());
        calc.setNCdServico(dataRequest.getServico());
        calc.setSCepOrigem(dataRequest.getCepOrigem());
        calc.setSCepDestino(dataRequest.getCepDestino());
        calc.setNVlPeso(dataRequest.getPeso());
        calc.setNVlComprimento(dataRequest.getComprimento());
        calc.setNVlAltura(dataRequest.getAltura());
        calc.setNVlLargura(dataRequest.getLargura());
        calc.setNVlDiametro(dataRequest.getDiametro());
        calc.setSCdMaoPropria(dataRequest.getMp());
        calc.setNVlValorDeclarado(dataRequest.getValorDeclarado());
        calc.setSCdAvisoRecebimento(dataRequest.getAr());
        return calc;
    }
}
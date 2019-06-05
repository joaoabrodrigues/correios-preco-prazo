package br.edu.utfpr.pb.dainf.correiosprecoprazows.service;

import br.edu.utfpr.pb.dainf.correiosprecoprazows.model.PrecoPrazo;
import correios.wsdl.CResultado;
import correios.wsdl.CServico;
import correios.wsdl.CalcPrecoPrazo;
import correios.wsdl.CalcPrecoPrazoResponse;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.math.BigDecimal;
import java.util.List;


public class CalculadoraFreteService extends WebServiceGatewaySupport {

    public PrecoPrazo getPrecoPrazo() {
        PrecoPrazo p = new PrecoPrazo();
        try {
            String nCdEmpresa="09146920";
            String sDsSenha="123456";
            String nCdServico="40010";
            String sCepOrigem="85501268";
            String sCepDestino="79480000";
            String nVlPeso="1";
            int nCdFormato=1;
            BigDecimal nVlComprimento=new BigDecimal(30);
            BigDecimal nVlAltura=new BigDecimal(30);
            BigDecimal nVlLargura= new BigDecimal(30);
            BigDecimal nVlDiametro= new BigDecimal(0);
            String sCdMaoPropria="n";
            BigDecimal nVlValorDeclarado=new BigDecimal(30);
            String sCdAvisoRecebimento="n";

            CalcPrecoPrazo calc = new CalcPrecoPrazo();
            calc.setNCdEmpresa(nCdEmpresa);
            calc.setNCdFormato(nCdFormato);
            calc.setNCdServico(nCdServico);
            calc.setSCepOrigem(sCepOrigem);
            calc.setSCepDestino(sCepDestino);
            calc.setSDsSenha(sDsSenha);
            calc.setNVlPeso(nVlPeso);
            calc.setNVlComprimento(nVlComprimento);
            calc.setNVlAltura(nVlAltura);
            calc.setNVlLargura(nVlLargura);
            calc.setNVlDiametro(nVlDiametro);
            calc.setSCdMaoPropria(sCdMaoPropria);
            calc.setNVlValorDeclarado(nVlValorDeclarado);
            calc.setSCdAvisoRecebimento(sCdAvisoRecebimento);

            Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

            marshaller.setContextPath("correios.wsdl");
            marshaller.afterPropertiesSet();

            getWebServiceTemplate().setMarshaller(marshaller);
            getWebServiceTemplate().setUnmarshaller(marshaller);
            getWebServiceTemplate().afterPropertiesSet();


            CalcPrecoPrazoResponse response = (CalcPrecoPrazoResponse) getWebServiceTemplate()
                    .marshalSendAndReceive("http://ws.correios.com.br/calculador/CalcPrecoPrazo.asmx", calc,
                            new SoapActionCallback("http://tempuri.org/CalcPrecoPrazo"));

            CResultado result = response.getCalcPrecoPrazoResult();
            List<CServico> s = result.getServicos().getCServico();
            for (CServico cServico : s) {
                p.setPrazo(Integer.valueOf(cServico.getPrazoEntrega()));
                p.setPreco(Double.valueOf( cServico.getValor().replace(".","").replace(",",".") ));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }
}
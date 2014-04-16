/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.argonavis.merkatus.alicit.edital;

import br.com.argonavis.merkatus.alicit.comprador.BolsaEletronicaCompras;
import br.com.argonavis.merkatus.alicit.comprador.Comprador;
import br.com.argonavis.merkatus.alicit.comprador.ComprasNet;
import java.text.ParseException;
import java.util.Date;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author helderdarocha
 */
public class EditalTest {
    
    Comprador comprador1 = new ComprasNet();
    Comprador comprador2 = new BolsaEletronicaCompras();
    
    Edital pe = new PregaoEletronico(comprador1, PregaoEletronico.Tipo.COMPRA_DIRETA);
    Edital dl = new DispensaLicitacao(comprador2, DispensaLicitacao.Tipo.COTACAO_ELETRONICA);
    
    String nomeDisplay1, nomeDisplay2;
    Codigo numeroEdital1, numeroEdital2, numeroEditalComprador1, numeroEditalComprador2;
    CNPJ cnpjComprador1, cnpjComprador2;
    
    Endereco e1, e2, e3;
    Email email1, email2;
    Telefone t1, t2;
    
    public EditalTest() throws ParseException {
        nomeDisplay1 = "Pregao eletronico CN no. 1234567890";
        nomeDisplay2 = "Dispensa de licitação no. ABC2345";
        numeroEdital1 = new Codigo("1234567890");
        numeroEdital2 = new Codigo("ABC2345");
        numeroEditalComprador1 = new Codigo("123456", comprador1.getMascara(null)); // Compras NET 6 digitos
        numeroEditalComprador2 = new Codigo("12345678901"+2014+"OC12345", comprador2.getMascara(new Date())); // BEC 22 chars com validacao
        cnpjComprador1 = new CNPJ("04.239.747/0001-58");
        cnpjComprador2 = new CNPJ("04857345000133");
        
        pe.setIdentificacao(nomeDisplay1, numeroEdital1, numeroEditalComprador1, cnpjComprador1);
        dl.setIdentificacao(nomeDisplay2, numeroEdital2, numeroEditalComprador2, cnpjComprador2);
        
        e1 = new Endereco("Rua Direita", "123", "sala 12", "centro", "Sao Paulo", "SP", "01002-001");
        e2 = new Endereco("Rua Esquerda, 456, sala 12, centro", "Sao Paulo", "SP", "01002-001");
        e3 = new Endereco("123.34567", "-90.34567");
        try {
            if (e3.setupWithGeoLocation() < 0) e3 = e2; // use fallback test address if no address obtained
        } catch (Exception e) {
            System.out.println("Pesquisa GPS falhou");
            e3 = e2;
        }
        email1 = new Email("helder@argonavis.com.br");
        email2 = new Email("midas@comprasnet.com.br");
        t1 = new Telefone("11", "992938293");
        t2 = new Telefone("85", "32938293");
        
        pe.setContatos(e1, e2, email1, t1, t1);
        dl.setContatos(e2, e3, email2, t1, t2);
    }

    @org.junit.Test
    public void testGetNomeDisplay() {
        assertEquals(nomeDisplay1, pe.getNomeDisplay());
        assertEquals(nomeDisplay2, dl.getNomeDisplay());
    }

    @org.junit.Test
    public void testGetNumeroEdital() {
        assertEquals(numeroEdital1, pe.getNumeroEdital());
        assertEquals(numeroEdital2, dl.getNumeroEdital());
    }

    @org.junit.Test
    public void testGetNumeroEditalComprador() {
        assertEquals(numeroEditalComprador1, pe.getNumeroEditalComprador());
        assertEquals(numeroEditalComprador2, dl.getNumeroEditalComprador());
    }

    @org.junit.Test
    public void testGetCnpjComprador() {
        assertEquals(cnpjComprador1, pe.getCnpjComprador());
        assertEquals(cnpjComprador2, dl.getCnpjComprador());
    }

    @org.junit.Test
    public void testGetEnderecoEnvioDocumentacao() {
        assertEquals(e1, pe.getEnderecoEnvioDocumentacao());
        assertEquals(e2, dl.getEnderecoEnvioDocumentacao());
    }

    @org.junit.Test
    public void testGetEnderecoEntrega() {
        assertEquals(e2, pe.getEnderecoEntrega());
        assertEquals(e3, dl.getEnderecoEntrega());
    }

    @org.junit.Test
    public void testGetEmail() {
        assertEquals(email1, pe.getEmail());
        assertEquals(email2, dl.getEmail());
    }

    @org.junit.Test
    public void testGetTelefoneEntrega() {
        assertEquals(t1, pe.getTelefoneEntrega());
        assertEquals(t1, dl.getTelefoneEntrega());
    }

    @org.junit.Test
    public void testGetTelefoneCobranca() {
        assertEquals(t1, pe.getTelefoneCobranca());
        assertEquals(t2, dl.getTelefoneCobranca());
    }

    @org.junit.Test
    public void testGetComprador() {
        assertEquals(comprador1, pe.getComprador());
        assertEquals(comprador2, dl.getComprador());
    }
    
}

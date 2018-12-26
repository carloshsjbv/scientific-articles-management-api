package br.com.carlos.projeto.conclusao.curso.config.infra.cors;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package br.com.carlos.projeto.conclusao.curso.infra.cors;
//
//import java.io.IOException;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletResponse;
//import org.springframework.web.filter.GenericFilterBean;
//
///**
// * Classe responsável pela configuração dos Headers referentes às requisições
// * vindas de outros servidores.
// *
// * Ainda passível de aprofundamento no estudo. Tal configuração pode ser feita
// * na aplicação web.
// *
// * @author Carlos H
// */
//public class CORSFilter extends GenericFilterBean implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        // Permite requisições de quaisquer origens
//        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
//
//        // Permite todos os métodos de requisições HTTP
//        httpResponse.setHeader("Access-Control-Allow-Methods", "*");
//
//        httpResponse.setHeader("Access-Control-Allow-Headers", "*");
//
//        httpResponse.setHeader("Access-Control-Allow-Credentials", "false");
//        httpResponse.setHeader("Access-Control-Max-Age", "3600");
//
//        System.out.println("========== CROS ORIGIN CONFIG RUNNING ==============");
//
//        filterChain.doFilter(request, response);
//
//    }
//
//}

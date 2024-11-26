package com.pi4jst.app;

import java.util.Scanner;

import com.pi4jst.model.entities.Refrigerador;

public class ProgramR {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Refrigerador refrigerador = new Refrigerador();

        System.out.println("===== Informações Refrigerador =====");

        try {
            while (true) {

                // Verificar status da porta
                refrigerador.verificarPorta(sc);

                // Verificar status sensor químico
                refrigerador.verificarSensorQuimico(sc);

                // Informar temperatura
                double temperaturaAtual = refrigerador.lerTempertarua();
                System.out.printf("~> Temperatura atual: %.2f°C\n", temperaturaAtual);

                // Informar status da porta
                if (refrigerador.getPortaAberta()) {
                    System.out.println("~> Status da porta: Aberta");
                }
                else {
                    System.out.println("~> Status da porta: Fechada");
                }

                // Controlar compressor
                refrigerador.controlarCompressor(temperaturaAtual);
                if (refrigerador.getCompressorLigado()) {
                    System.out.println("~> Compressor: Ligado");
                }
                else {
                    System.out.println("~> Compressor: Desligado");
                }

                // Informar maturação
                if (refrigerador.getSensorQuimico()) {
                    System.out.println("~> Matuação: Atingida");
                }
                else {
                    System.out.println("~> Maturação: Estável");
                }

                System.out.println("------------------------------------------");

                Thread.sleep(3000);
            }
        }
        catch(InterruptedException e) {
            System.out.println("Sistema interrompido");
        }

        sc.close();
        
    }

}
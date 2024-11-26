package com.pi4jst.model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.pi4jst.model.enums.ValidadeStatus;

public class Refrigerador {

    // Limite de temperatura para ligar o compressor
    private static final double TEMPERATURA_LIMITE = 3.0;

    // Status da porta
    private static boolean portaAberta = false;

    // Status do compressor
    private static boolean compressorLigado = false;

    // Status do sensor químico
    private static boolean sensorQuimico = false;

    private List<Produto> produtos = new ArrayList<>();

    public boolean getCompressorLigado() {
        return compressorLigado;
    }
    public boolean getPortaAberta() {
        return portaAberta;
    }
    public boolean getSensorQuimico() {
        return sensorQuimico;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    // Método para saber a quantidade total em estoque
    public int quantidadeTotalEstoque() {
        int total = 0;
        for (Produto p : produtos) {
            total += p.getQuantidade();
        }
        return total;
    }

    // Método para saber a quantidade total de um produto x
    public int quantidadeTotalProdutoX(Produto produto) {
        int total = 0;
        List<Produto> result = produtos.stream().filter(x->x.getId()==produto.getId()).collect(Collectors.toList());
        for (Produto p : result) {
            total += p.getQuantidade();
        }
        return total;
    }

    // Método Luz da Validade dos produtos
    public void alertarValidade() {
        if (produtos.stream().filter(x->x.getStatusValidade()!=ValidadeStatus.VALIDO).findFirst().orElse(null)!=null) {
            if (produtos.stream().filter(x->x.getStatusValidade()!=ValidadeStatus.VENCENDO).findFirst().orElse(null)!=null) {
                System.out.println("~> Status de validade: VENCIDO");
            }
            else {
                System.out.println("~> Status de validade: VENCENDO");
            }
        }
        else {
            System.out.println("~> Status de validade: VALIDO");
        }
    }

    // Método para verificar a porta
    public void verificarPorta(Scanner sc) {
        System.out.print("A porta está aberta? (sim/nao): ");
        String resposta = sc.nextLine().trim().toLowerCase();
        portaAberta = resposta.equals("sim");
    }

    // Método para verificar o sensor químico
    public void verificarSensorQuimico(Scanner sc) {
        System.out.print("O sensor identificou gás? (sim/nao): ");
        String resposta = sc.nextLine().trim().toLowerCase();
        sensorQuimico = resposta.equals("sim");
    }

    // Método para ler temperatura (simulação)
    public double lerTempertarua() {
        Random random = new Random();
        return random.nextDouble(4.5);
    }

    // Método para controle do compressor
    public void controlarCompressor(double temperaturaAtual){
        if (temperaturaAtual > TEMPERATURA_LIMITE) {
            if (!compressorLigado) {
                System.out.println("! Temperatura alta. Ligando compressor...");
                compressorLigado = true;
            }
        }
        else {
            if (compressorLigado) {
                System.out.println("! Temperatura normal. Desligando compressor...");
                compressorLigado = false;
            }
        }
    }

}
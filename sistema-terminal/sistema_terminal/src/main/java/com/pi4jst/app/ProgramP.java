package com.pi4jst.app;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.pi4jst.model.entities.Produto;
import com.pi4jst.model.entities.Refrigerador;

public class ProgramP {

    public static void main(String[] args) {

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Scanner sc = new Scanner(System.in);

        Refrigerador refrigerador = new Refrigerador();

        while (true) {
            System.out.println("===== Menu de Opções =====");
            System.out.println("1) Adicionar produto");
            System.out.println("2) Remover produto");
            System.out.println("3) Quantidade de um produto");
            System.out.println("4) Total em estoque");

            System.out.print("\nOpção: ");
            int resposta = Integer.parseInt(sc.nextLine());

            switch (resposta) {
                case 1:
                    System.out.print("ID: ");
                    int idAdd = Integer.parseInt(sc.nextLine());
                    System.out.print("Nome: ");
                    String nomeAdd = sc.nextLine();
                    System.out.print("Data de validade (dd/MM/yyyy): ");
                    LocalDate dataDeValidade = LocalDate.parse(sc.nextLine(), fmt);
                    System.out.print("Quantidade: ");
                    int quantidadeAdd = Integer.parseInt(sc.nextLine());

                    refrigerador.getProdutos().add(new Produto(idAdd, nomeAdd, dataDeValidade, quantidadeAdd));

                    break;
            
                case 2:
                    System.out.print("ID: ");
                    int idRem = Integer.parseInt(sc.nextLine());
                    System.out.print("Nome: ");
                    String nomeRem = sc.nextLine();
                    System.out.print("Quantidade: ");
                    int quantidadeRem = Integer.parseInt(sc.nextLine());

                    refrigerador.getProdutos().add(new Produto(idRem, nomeRem, null, -quantidadeRem));

                    break;


                case 3:
                    System.out.print("ID: ");
                    Integer idQP = Integer.parseInt(sc.nextLine());
                    Produto p = refrigerador.getProdutos().stream().filter(x->x.getId()==idQP).findFirst().orElse(null);
                    int quantidadeTotalProdutoX = refrigerador.quantidadeTotalProdutoX(p);
                    System.out.println("Quantidade total do produto " + idQP + ": " + quantidadeTotalProdutoX);

                    break;

                case 4:
                    int quantidadeTotalEstoque = refrigerador.quantidadeTotalEstoque();
                    System.out.println("Quantidade total em estoque: " + quantidadeTotalEstoque);
            }

            System.out.print("\nSair(s) ou voltar(v)? ");
            char resposta2 = sc.nextLine().trim().toLowerCase().charAt(0);
            System.out.println();
            if (resposta2 == 's') {
                break;
            }
            else {
                continue;
            }

        }

        sc.close();
        
    }

}

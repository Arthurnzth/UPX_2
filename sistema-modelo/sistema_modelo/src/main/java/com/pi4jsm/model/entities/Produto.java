package com.pi4jsm.model.entities;

import java.time.LocalDate;
import com.pi4jsm.model.enums.ValidadeStatus;

public class Produto {

    private Integer id; 
    private String nome;
    private LocalDate dataDeValidade; 
    private Integer quantidade; 
    private ValidadeStatus statusValidade; 

    private Produto (Integer id, String nome, LocalDate dataDeValidade, Integer quantidade){
        this.id = id;
        this.nome = nome;
        this.dataDeValidade = dataDeValidade;
        this.quantidade = quantidade; 
    }

    
    public Integer getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }

    public LocalDate getDataDeValidade(){
        return dataDeValidade;
    }
    public void setDataDeValidade(LocalDate dataDeValidade){
        this.dataDeValidade = dataDeValidade;
    }

    public Integer getQuantidade(){
        return quantidade;
    }
    public void setQuantidade(Integer quantidade){
        this.quantidade = quantidade;
    }

    public ValidadeStatus getStatusValidade(){
        return statusValidade;
    }
}

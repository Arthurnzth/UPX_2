package com.pi4jst.model.entities;

import java.time.Duration;
import java.time.LocalDate;
import com.pi4jst.model.enums.ValidadeStatus;

public class Produto {

    private Integer id; 
    private String nome;
    private LocalDate dataDeValidade; 
    private Integer quantidade; 
    private ValidadeStatus statusValidade; 

    public Produto (Integer id, String nome, LocalDate dataDeValidade, Integer quantidade) {
        this.id = id;
        this.nome = nome;
        this.dataDeValidade = dataDeValidade;
        this.quantidade = quantidade; 
    }
    public Produto() {

    }

    public Integer getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public LocalDate getDataDeValidade() {
        return dataDeValidade;
    }
    public void setDataDeValidade(LocalDate dataDeValidade) {
        this.dataDeValidade = dataDeValidade;
    }
    public Integer getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
    public ValidadeStatus getStatusValidade() {
        return statusValidade;
    }

    // Método 
    public void verificarValidade() {
        double dias = Duration.between(dataDeValidade, LocalDate.now()).toDays();
        
        if(dias > 15){
            this.statusValidade = ValidadeStatus.VALIDO;
        }
        else{
            if(dias >= 0){
                this.statusValidade = ValidadeStatus.VENCENDO;
            }
            else{
                this.statusValidade = ValidadeStatus.VENCIDO;
            }
        }
    }

}
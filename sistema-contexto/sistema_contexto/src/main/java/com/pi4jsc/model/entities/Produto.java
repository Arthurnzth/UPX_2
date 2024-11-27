package com.pi4jsc.model.entities;

import java.time.LocalDate;

import com.pi4jsc.model.enums.TipoDeCarne;
import com.pi4jsc.model.enums.ValidadeStatus;

public class Produto {

    private int id;
    private String nome;
    private Integer quantidade;
    private TipoDeCarne tipoDeCarne;
    private ValidadeStatus ValidadeStatus;
    private LocalDate dataDeVallidade;
    private LocalDate dataDeCompra;

    public Produto (int id, String nome, Integer quantidade, TipoDeCarne tipoDeCarne,ValidadeStatus ValidadeStatus, LocalDate dataDeCompra){
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.tipoDeCarne = tipoDeCarne;
        this.ValidadeStatus = ValidadeStatus;
        this.dataDeCompra = dataDeCompra;
    }

    public Produto(){

    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade){
        this.quantidade = quantidade;
    }


    public TipoDeCarne getTipoDeCarne() {
        return tipoDeCarne;
    }

    public void setTipoDeCarne(TipoDeCarne tipoDeCarne){
        this.tipoDeCarne = tipoDeCarne;
    }

    public ValidadeStatus getValidadeStatus() {
        return ValidadeStatus;
    }

    public void setValidadeStatus(ValidadeStatus ValidadeStatus){
        this.ValidadeStatus = ValidadeStatus;
    }


    public LocalDate getDataDeVallidade() {
        return dataDeVallidade;
    }

    public LocalDate getDataDeCompra() {
        return dataDeCompra;
    }

    public void setDataDeCompra(LocalDate dataDeCompra){
        this.dataDeCompra = dataDeCompra;
    }

    public void calcularValidade(){
        
    }


}

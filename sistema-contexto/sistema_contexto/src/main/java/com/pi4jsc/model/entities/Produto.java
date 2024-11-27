package com.pi4jsc.model.entities;

import java.time.Duration;
import java.time.LocalDate;

import com.pi4jsc.model.enums.TipoDeCarne;
import com.pi4jsc.model.enums.ValidadeStatus;

public class Produto {

    private int id;
    private String nome;
    private Integer quantidade;
    private TipoDeCarne tipoDeCarne;
    private ValidadeStatus validadeStatus;
    private LocalDate dataDeCompra;

    public Produto (int id, String nome, Integer quantidade, TipoDeCarne tipoDeCarne, LocalDate dataDeCompra){
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.tipoDeCarne = tipoDeCarne;
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
        return validadeStatus;
    }

    public void setValidadeStatus(ValidadeStatus ValidadeStatus){
        this.validadeStatus = ValidadeStatus;
    }

    public LocalDate getDataDeCompra() {
        return dataDeCompra;
    }

    public void setDataDeCompra(LocalDate dataDeCompra){
        this.dataDeCompra = dataDeCompra;
    }

    public LocalDate calcularValidade(){
        if(tipoDeCarne == TipoDeCarne.AVE || tipoDeCarne == TipoDeCarne.BOVINO){
            return dataDeCompra.plusMonths(10);
        }
        else{
            if(tipoDeCarne == TipoDeCarne.SUINO || tipoDeCarne == TipoDeCarne.PEIXE){
                return dataDeCompra.plusMonths(5);
            }
            else{
                if(tipoDeCarne == TipoDeCarne.CACA || tipoDeCarne == TipoDeCarne.CAPRINO || tipoDeCarne == TipoDeCarne.OVINO){
                    return dataDeCompra.plusMonths(4);
                }
                else{
                    return dataDeCompra.plusMonths(1);
                }
            }   
        }
    }

    public void verificarValidade(){
        double dias = Duration.between(calcularValidade(), LocalDate.now()).toDays();

        if(dias > 15){
            this.validadeStatus = ValidadeStatus.VALIDO;
        }
        else{
            if(dias >= 0){
                this.validadeStatus = ValidadeStatus.VENCENDO;
            }
            else{
                this.validadeStatus = ValidadeStatus.VENCIDO;
            }
        }
    }
}

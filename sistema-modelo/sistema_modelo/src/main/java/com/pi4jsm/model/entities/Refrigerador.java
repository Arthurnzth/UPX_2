package com.pi4jsm.model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;


public class Refrigerador {

    private final GpioController gpio;
    private final GpioPinDigitalInput sensorPorta;
    private final GpioPinDigitalInput sensorQuimico;
    private final GpioPinDigitalOutput luzSensorPorta;
    private final GpioPinDigitalOutput compressor;
    private final GpioPinDigitalOutput luzOutputQuimico;
    private final GpioPinDigitalOutput luzValidade;

    private List<Produto> produtos = new ArrayList<>();

    public Refrigerador(){
        // Cria uma instância do GpioFactory para controlar os pinos
        this.gpio = GpioFactory.getInstance();

        // Inicializa e define os estados dos atributos, além de configurar em cada pino
        this.sensorPorta = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, "Sensor da Porta");
        this.sensorQuimico = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, "Sensor quimico");
        this.luzSensorPorta = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "Luz do Sensor da Porta", PinState.LOW);
        this.compressor = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "Compressor", PinState.LOW);
        this.luzOutputQuimico = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, "Luz do Sensor Quimico", PinState.LOW);
        this.luzValidade = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, "Luz validade", PinState.LOW);

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

    // Método Luz da Porta
    public void verificarPorta(){
        if(sensorPorta.isHigh()){
            luzSensorPorta.high();
        }
        else{
            luzSensorPorta.low();
        }
    };









}

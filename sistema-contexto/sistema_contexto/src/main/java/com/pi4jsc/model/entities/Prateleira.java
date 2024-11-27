package com.pi4jsc.model.entities;

import com.pi4jsc.model.enums.TipoDeCarne;
import com.pi4jsc.model.enums.ValidadeStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class Prateleira {
    private final GpioController gpio;
    private final GpioPinDigitalInput sensorMaturacao;
    private final GpioPinDigitalOutput luzOutputMaturacao;
    private final GpioPinDigitalOutput luzValidade;

    private List<Produto> produtos = new ArrayList<>();
    private TipoDeCarne categoria;
    private int id;

    public Prateleira(){
        // Cria uma instância do GpioFactory para controlar os pinos
        this.gpio = GpioFactory.getInstance();

        // Inicializa e define os estados dos atributos, além de configurar em cada pino
        this.sensorMaturacao = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, "Sensor de Maturação");
        this.luzOutputMaturacao = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "Luz do Sensor de Maturação", PinState.LOW);
        this.luzValidade = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "Luz Validade", PinState.LOW);

    }
    // Método GetSet para Produtos
    public List<Produto> getProdutos() {
        return produtos;
    }

    // Método GetSet para Categoria
    public TipoDeCarne getCategoria() {
        return categoria;
    }

    // Método GetSet para Id
    public int getId(){
        return id;
    }

    // Método para Verificar Maturação
    public void verificarMaturacao() {
        if (sensorMaturacao.isHigh()){
            luzOutputMaturacao.high();
        }
        else{
            luzOutputMaturacao.low();
        }
    }

    // Método para Alertar Validade
    public void alertarValidade(){
        if (produtos.stream().filter(x->x.getValidadeStatus()!=ValidadeStatus.VALIDO).findFirst().orElse(null)!=null) {
            luzValidade.high();
        }
        else {
            luzValidade.low();
        }
    }


}

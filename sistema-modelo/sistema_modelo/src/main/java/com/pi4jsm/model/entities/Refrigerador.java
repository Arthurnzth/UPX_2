package com.pi4jsm.model.entities;

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
    private final GpioPinDigitalOutput luzPorta;
    private final GpioPinDigitalOutput compressor;
    private final GpioPinDigitalOutput luzOutputQuimico;

    public Refrigerador(){
        // Cria uma instância do GpioFactory para controlar os pinos
        this.gpio = GpioFactory.getInstance();

        // Inicializa e define os estados dos atributos, além de configurar em cada pino
        this.sensorPorta = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, "Sensor da Porta");
        this.sensorQuimico = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, "Sensor quimico");
        this.luzPorta = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "Luz da Porta", PinState.LOW);
        this.compressor = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "Compressor", PinState.LOW);
        this.luzOutputQuimico = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, "Luz do Sensor Quimico", PinState.LOW);

    }
}

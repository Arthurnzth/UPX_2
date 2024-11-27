package com.pi4jsc.model.entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4jsm.model.enums.ValidadeStatus;

public class Refrigerador {
    
    // Lembrar de colocar o sensor PATH

    private final GpioController gpio;
    private final GpioPinDigitalInput sensorPortaRefrigerador;
    private final GpioPinDigitalOutput luzSensorPortaRefrigador;
    private final GpioPinDigitalOutput compressorRefrigerador;

    

}

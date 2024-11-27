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
import com.pi4jsc.model.enums.ValidadeStatus;

public class EquipamentoDeConservacao {
    // Caminho para o arquivo do sensor de temperatura
    private static final String SENSOR_PATH = "/sys/bus/w1/devices/28-XXXXXXXXXXXX/w1_slave"; // necessário Raspberry Pi & Sensor DS18B20

    private final GpioController gpio;
    private final GpioPinDigitalInput sensorPorta;
    private final GpioPinDigitalOutput luzSensorPorta;
    private final GpioPinDigitalOutput compressor;

    private int id;
    private List<Prateleira> prateleiras = new ArrayList<>();

    public EquipamentoDeConservacao(){
        // Cria uma instância do GpioFactory para controlar os pinos
        this.gpio = GpioFactory.getInstance();
        
        // Inicializa e define os estados dos atributos, além de configurar em cada pino
        this.sensorPorta = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, "Sensor da Porta");
        this.luzSensorPorta = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "Luz do Sensor da Porta", PinState.LOW);
        this.compressor = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "Compressor", PinState.LOW);

    }
    
    // Método GetSet para retornar Prateleiras
    public List<Prateleira> getPrateleiras(){
        return prateleiras;
    }
    
    // Método GetSet para retornar Id
    public int getId(){
        return id;
    }

    // Método para calcular a Quantidade Total de Produtos no Estoque
    public int quantidadeTotalEstoque() {
        int total = 0;
        for(Prateleira pa : prateleiras){
            for(Produto p : pa.getProdutos()){
                total += p.getQuantidade();
            }
        }
        return total;
    }
    
    // Método para calcular a Quantidade Total de Produtos por Categoria desejada 
    public int quantidadeTotalCategoria(TipoDeCarne categoria){
        int total = 0;
        for(Prateleira pa : prateleiras){
            List<Produto> result = pa.getProdutos().stream().filter(x->x.getTipoDeCarne() == categoria).collect(Collectors.toList());
            for(Produto po : result){
                total += po.getQuantidade();
            }
        }
        return total;
    }
    
    // Método para verificar, se a porta está aberta ou não
    public void verificarPorta(){
        if(sensorPorta.isHigh()){
            luzSensorPorta.high();
        }
        else{
            luzSensorPorta.low();
        }
    }
    
    // Método para Controlar o Compressor com base na Temperatura Atual
    public void controlarCompressor() throws IOException{
        double temperaturaAtual = lerTemperatura();
        if(temperaturaAtual > 4) {
            compressor.high();
        }
        else{
            compressor.low();
        }
    }
    // Método para ler a Temperatura dentro do Equipamento.
    public double lerTemperatura() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(SENSOR_PATH));

        String line1 = br.readLine();
        String line2 = br.readLine();
        br.close();

        // Verificar se a leitura foi bem sucedida
        if(!line1.contains("YES")) {
            throw new IOException("Erro ao ler o sensor de temperatura.");
        }

        // Ler temperatura na segunda linha
        int tempIndex = line2.indexOf("t=");
        if(tempIndex != -1){
            String tempString = line2.substring(tempIndex + 2);
            double tempCelsius = Double.parseDouble(tempString) / 1000.0;
            return tempCelsius;
        }
        else{
            throw new IOException("Erro ao processor o valor da temperatura.");
        }
    }

    // Método para finalizar
    public void finalizar() {
        gpio.shutdown();
    }

}



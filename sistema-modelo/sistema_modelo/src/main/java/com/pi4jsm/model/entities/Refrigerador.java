package com.pi4jsm.model.entities;

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

    // Caminho para o arquivo do sensor de temperatura
    private static final String SENSOR_PATH = "/sys/bus/w1/devices/28-XXXXXXXXXXXX/w1_slave"; // necessário Raspberry Pi & Sensor DS18B20

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
    public void verificarPorta() {
        if (sensorPorta.isHigh()) {
            luzSensorPorta.high();
        }
        else{
            luzSensorPorta.low();
        }
    }

    // Método Luz da Validade dos produtos
    public void alertarValidade() {
        if (produtos.stream().filter(x->x.getStatusValidade()!=ValidadeStatus.VALIDO).findFirst().orElse(null)!=null) {
            luzValidade.high();
        }
        else {
            luzValidade.low();
        }
    }

    // Método Luz da Maturação
    public void informarMaturacao() {
        if (sensorQuimico.isHigh()) {
            luzOutputQuimico.high();
        }
        else {
            luzOutputQuimico.low();
        }
    }
    
    // Método para controle do compressor
    public void controlarCompressor() throws IOException {
        double temperaturaAtual = lerTempertarua();
        if (temperaturaAtual > 4) {
            compressor.high();
        }
        else {
            compressor.low();
        }
    }

    // Método para ler temperatura
    public double lerTempertarua() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(SENSOR_PATH));

        String line1 = br.readLine();
        String line2 = br.readLine();
        br.close();

        // Verificar se a leitura foi bem sucedida
        if (!line1.contains("YES")) {
            throw new IOException("Erro ao ler o sensor de temperatura");
        }

        // Ler temperatura na segunda linha
        int tempIndex = line2.indexOf("t=");
        if (tempIndex != -1) {
            String tempString = line2.substring(tempIndex + 2);
            double tempCelsius = Double.parseDouble(tempString) / 1000.0;
            return tempCelsius;
        }
        else {
            throw new IOException("Erro ao processar o valor da temperatura.");
        }
    }

    // Método para repetir o programa
    public void loop() throws InterruptedException, IOException {
        while (true) {
            controlarCompressor();
            informarMaturacao();
            alertarValidade();
            verificarPorta();

            Thread.sleep(1000);
        }
    }

    // Método para finalizar
    public void finalizar() {
        gpio.shutdown();
    }

}

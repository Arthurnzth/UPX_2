package com.pi4jsm.model.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeitorDeTemperatura {

    // Caminho para o arquivo do sensor
    private static final String SENSOR_PATH = "/sys/bus/w1/devices/28-XXXXXXXXXXXX/w1_slave"; // necessário Raspberry Pi & Sensor DS18B20

    public double readTemperature() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(SENSOR_PATH));

        String line1 = reader.readLine();
        String line2 = reader.readLine();
        reader.close();

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

}

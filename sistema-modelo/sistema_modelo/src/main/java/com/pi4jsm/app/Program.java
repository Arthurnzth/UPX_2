package com.pi4jsm.app;

import java.io.IOException;

import com.pi4jsm.model.entities.Refrigerador;

public class Program {

    public static void main(String[] args) {

        Refrigerador refrigerador = new Refrigerador();

        try {
            refrigerador.loop();
        }
        catch (InterruptedException e) {

        }
        catch (IOException e) {

        }
        finally {
            refrigerador.finalizar();
        }
        
    }

}

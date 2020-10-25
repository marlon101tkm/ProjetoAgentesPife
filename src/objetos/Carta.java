package objetos;


import jade.util.leap.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Win10
 */
public class Carta implements Serializable{
    int valor , naipe;

    public Carta(int valor, int naipe) {
        this.valor = valor;
        this.naipe = naipe;
    }

    public Carta() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}

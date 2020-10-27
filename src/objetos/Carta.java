package objetos;


import jade.util.leap.Serializable;

public class Carta implements Serializable{
    int valor , naipe;

    public Carta(int valor, int naipe) {
        this.valor = valor;
        this.naipe = naipe;
    }

    @Override
    public String toString() {
        return "Carta{" + "valor=" + valor + ", naipe=" + naipe + '}';
    }
    

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getNaipe() {
        return naipe;
    }

    public void setNaipe(int naipe) {
        this.naipe = naipe;
    }
    
    
    
    
    
}

package objetos;


import jade.util.leap.Serializable;

public class Carta implements Serializable,Comparable<Carta> {
    int valor , naipe;
    boolean emDupla = false , emTrinca = false;

    public boolean isEmDupla() {
        return emDupla;
    }

    public void setEmDupla(boolean emDupla) {
        this.emDupla = emDupla;
    }

    public boolean isEmTrinca() {
        return emTrinca;
    }

    public void setEmTrinca(boolean emTrinca) {
        this.emTrinca = emTrinca;
    }

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

    @Override
    public int compareTo(Carta o) {
       if (o.getValor()> this.valor) {
		return 1;
	} else if (o.getValor()< this.valor){
		return -1;
	} else {
		return 0;
	}
    }

   
    
    
    
    
    
}

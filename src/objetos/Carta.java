package objetos;


import jade.util.leap.Serializable;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Carta implements Serializable,Comparable<Carta> {
   public int valor, naipe;
   private int grupoTrinca;
   public boolean emDupla = false , emTrinca = false;
   public ImageIcon  imagem;

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

    /**
     * @return the grupoTrinca
     */
    public int getGrupoTrinca() {
        return grupoTrinca;
    }

    /**
     * @param grupoTrinca the grupoTrinca to set
     */
    public void setGrupoTrinca(int grupoTrinca) {
        this.grupoTrinca = grupoTrinca;
    }

    public Object get(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    public void draw(Graphics g, Component c, int x , int y) {
        imagem.paintIcon(c, g, x, y);
    }
    
    
    
    
}

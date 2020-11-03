/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package objetos;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Pichau
 */
public class ValidaTrincas1 {
    boolean ativo = false;
    LinkedList<Carta> mao = new LinkedList<Carta>();
    int qtdTrinca = 0;
    boolean jogoTerminou = false;
    
    public int cartamenosImp() {
        Carta descartar = null;
        for (Carta carta : mao) {
            if (!carta.isEmTrinca() || !carta.isEmDupla()) {
                descartar = carta;
            }
        }

        if (descartar == null) {
            for (Carta carta : mao) {
                if (!carta.isEmTrinca()) {
                    descartar = carta;
                }
            }
        }

        return mao.indexOf(descartar);
    }

    public int checaNipeIqual(Carta c1, Carta c2) {
        for (Carta carta : mao) {
            if (carta.getNaipe() == c1.getNaipe()) {
                if (carta.getValor() == c2.getValor() + 1) {

                    return mao.indexOf(carta);

                } else if (carta.getValor() == c1.getValor() - 1) {
                    return mao.indexOf(carta);
                }
            }
        }

        return -1;
    }

    public int checaValIqual(Carta c1, Carta c2) {
        for (Carta carta : mao) {
            if (carta.getValor() == c1.getValor()) {
                if (carta.getNaipe() != c1.getNaipe() && carta.getNaipe() != c2.getNaipe()) {
                    return mao.indexOf(carta);
                }
            }
        }

        return -1;
    }

    public void checaTrincas() {        
        checaSequencia();
    }
    
    public void checaSequencia(){
        LinkedList<Carta> espadas = new LinkedList<Carta>();
        LinkedList<Carta> paus = new LinkedList<Carta>();
        LinkedList<Carta> ouro = new LinkedList<Carta>();
        LinkedList<Carta> copas = new LinkedList<Carta>();
        
        for(Carta c : mao){
            if(c.getNaipe()==0){
                espadas.add(c);              
            } else if(c.getNaipe()==1){
                paus.add(c);                
            } else if(c.getNaipe()==2){
                ouro.add(c);                
            } else {
                copas.add(c);                
            }
        }
        
        Collections.sort(espadas);
        Collections.sort(paus);
        Collections.sort(ouro);
        Collections.sort(copas);                               
        
        if(espadas.size()>=3){
            for(int i=espadas.size()-1; i>0; i--){
                int ref = espadas.get(i).getValor();
                int indexRef = i;
                boolean trinca = true;                                                                
                
                if(i>=2){
                    
                    for(int c=i-1; c>i-3; c--){
                        // Nao pode comparar cartas que ja estao em trincas
                        if(!espadas.get(c).emTrinca){
                            ref++;                                       
                            if(espadas.get(c).getValor() != ref){
                                c=i-3;
                                trinca = false;
                            }    
                        } else {
                            c=i-3;
                            trinca = false;
                        }                                      
                    } 

                    if(trinca){    

                        for(int a=indexRef; a>indexRef-3; a--){                        
                            espadas.get(a).setEmTrinca(true);                        
                        }
                        i=i-2;
                    }                     
                }         
            }    
        }
        
        if(copas.size()>=3){
            for(int i=copas.size()-1; i>0; i--){
                int ref = copas.get(i).getValor();
                int indexRef = i;
                boolean trinca = true;                                                                
                
                if(i>=2){
                    
                    for(int c=i-1; c>i-3; c--){
                        // Nao pode comparar cartas que ja estao em trincas
                        if(!copas.get(c).emTrinca){
                            ref++;                                       
                            if(copas.get(c).getValor() != ref){
                                c=i-3;
                                trinca = false;
                            }    
                        } else {
                            c=i-3;
                            trinca = false;
                        }                                      
                    } 

                    if(trinca){    

                        for(int a=indexRef; a>indexRef-3; a--){                        
                            copas.get(a).setEmTrinca(true);                        
                        }
                        i=i-2;
                    } 
                    
                }         
            }    
        }
        
        if(paus.size()>=3){
            for(int i=paus.size()-1; i>0; i--){
                int ref = paus.get(i).getValor();
                int indexRef = i;
                boolean trinca = true;                                                                
                
                if(i>=2){
                    
                    for(int c=i-1; c>i-3; c--){
                        // Nao pode comparar cartas que ja estao em trincas
                        if(!paus.get(c).emTrinca){
                            ref++;                                       
                            if(paus.get(c).getValor() != ref){
                                c=i-3;
                                trinca = false;
                            }    
                        } else {
                            c=i-3;
                            trinca = false;
                        }                                      
                    } 

                    if(trinca){    

                        for(int a=indexRef; a>indexRef-3; a--){                        
                            paus.get(a).setEmTrinca(true);                        
                        }
                        i=i-2;
                    } 
                    
                }         
            }    
        }
        
        if(ouro.size()>=3){
            for(int i=ouro.size()-1; i>0; i--){
                int ref = ouro.get(i).getValor();
                int indexRef = i;
                boolean trinca = true;                                                                
                
                if(i>=2){
                    
                    for(int c=i-1; c>i-3; c--){
                        // Nao pode comparar cartas que ja estao em trincas
                        if(!ouro.get(c).emTrinca){
                            ref++;                                       
                            if(ouro.get(c).getValor() != ref){
                                c=i-3;
                                trinca = false;
                            }    
                        } else {
                            c=i-3;
                            trinca = false;
                        }                                      
                    } 

                    if(trinca){    

                        for(int a=indexRef; a>indexRef-3; a--){                        
                            ouro.get(a).setEmTrinca(true);                        
                        }
                        i=i-2;
                    }                     
                }         
            }    
        }
     
    
    }
    
    public void geraMao(){
        Random random = new Random();              
        
        for (int i=0; i<9; i++){            
          //  if(i<3){
            //    mao.add(new Carta(i,0));
           //} else  
              mao.add(new Carta(random.nextInt(12), random.nextInt(3)));
        }
                
    }
    
    public void imprimeMao(){
        for (int i=0; i<9; i++){
            System.out.println(mao.get(i).getValor()+ " - " + mao.get(i).getNaipe() + " - " + mao.get(i).emTrinca);
        }
    }
    
    public static void main(String[] args) {                               
        ValidaTrincas1 a = new ValidaTrincas1();
        
        a.geraMao();
        a.imprimeMao();
        a.checaTrincas();
        System.out.println("");
        a.imprimeMao();
        
    }
    
}

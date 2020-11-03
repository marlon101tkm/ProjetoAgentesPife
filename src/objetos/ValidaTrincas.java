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
public class ValidaTrincas {
    boolean ativo = false;
    LinkedList<Carta> mao = new LinkedList<>();
    int qtdTrinca = 0;
    boolean jogoTerminou = false;        

    public void checaTrincas() {        
        checaSequencia();
        checaValorIgual();
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
    
    public void checaValorIgual(LinkedList<Carta> aux){
        // Passa três vezes a verificação porque cada verificação pode encontrar apenas uma trinca
        // Passando tres vezes vai poder achar tres trincas (se for o caso)
        if(aux.size()>=3){
            for(int v=0; v<3; v++){
                /* Os booleans + o qtd são fundamentais pra achar o padrão de trinca
                    Os booleans impedem que o mesmo naipe seja validado mais de uma vez
                    O qtd é fundamental pra dizer "Ok, encontramos 3 naipes diferentes, pode parar"
                */
                
                boolean paus = false;
                boolean ouro = false;
                boolean copas = false;
                boolean espadas = false;
                int qtd=0;
                
                // O index foi criado pra obter a posição do objeto pra depois exatamente ele ser setado como True
                LinkedList<Integer> index = new LinkedList<Integer>();                                              

                for(int i=0; i<aux.size(); i++){
                    if(aux.get(i).getNaipe()==0 && !aux.get(i).emTrinca && !espadas && qtd<3){
                        espadas=true;
                        qtd++;
                        index.add(i);
                    } else if(aux.get(i).getNaipe()==1 && !aux.get(i).emTrinca && !paus && qtd<3){
                        paus=true;
                        qtd++;
                        index.add(i);
                    } else if(aux.get(i).getNaipe()==2 && !aux.get(i).emTrinca && !copas && qtd<3){
                        copas=true;
                        qtd++;
                        index.add(i);
                    } else if(aux.get(i).getNaipe()==3 && !aux.get(i).emTrinca && !ouro && qtd<3){
                        ouro=true;
                        qtd++;
                        index.add(i);
                    }                     
                }

                if(qtd==3){
                    for(Integer i : index){
                        aux.get(i).setEmTrinca(true);
                    }
                }                
            }            
        } 
        
    }
    
    public void checaValorIgual(){
       LinkedList<Carta> Zero = new LinkedList<Carta>();
       LinkedList<Carta> Um = new LinkedList<Carta>();
       LinkedList<Carta> Dois = new LinkedList<Carta>();
       LinkedList<Carta> Tres = new LinkedList<Carta>();
       LinkedList<Carta> Quatro = new LinkedList<Carta>();
       LinkedList<Carta> Cinco = new LinkedList<Carta>();
       LinkedList<Carta> Seis = new LinkedList<Carta>();
       LinkedList<Carta> Sete = new LinkedList<Carta>();
       LinkedList<Carta> Oito = new LinkedList<Carta>();
       LinkedList<Carta> Nove = new LinkedList<Carta>();
       LinkedList<Carta> Dez = new LinkedList<Carta>();
       LinkedList<Carta> Onze = new LinkedList<Carta>();
       LinkedList<Carta> Doze = new LinkedList<Carta>();
       LinkedList<Carta> Treze = new LinkedList<Carta>();
    
       // Separa as cartas agrupando pelo valor dela pra depois verificar se existem naipes diferentes
       for(Carta c : mao){
            if(c.getValor() == 0){
                Zero.add(c);              
            } else if(c.getValor()==1){
                Um.add(c);                
            } else if(c.getValor()==2){
                Dois.add(c);                
            }  else if(c.getValor()==3){
                Tres.add(c);                
            }  else if(c.getValor()==4){
                Quatro.add(c);                
            }  else if(c.getValor()==5){
                Cinco.add(c);                
            }  else if(c.getValor()==6){
                Seis.add(c);                
            }  else if(c.getValor()==7){
                Sete.add(c);                
            }  else if(c.getValor()==8){
                Oito.add(c);                
            }  else if(c.getValor()==9){
                Nove.add(c);                
            }  else if(c.getValor()==10){
                Dez.add(c);                
            }  else if(c.getValor()==11){
                Onze.add(c);                
            }  else if(c.getValor()==12){
                Doze.add(c);                
            } else {
                Treze.add(c);                
            }
        }
        
        checaValorIgual(Zero);
        checaValorIgual(Um);
        checaValorIgual(Dois);
        checaValorIgual(Tres);
        checaValorIgual(Quatro);
        checaValorIgual(Cinco);
        checaValorIgual(Seis);
        checaValorIgual(Sete);
        checaValorIgual(Oito);
        checaValorIgual(Nove);
        checaValorIgual(Dez);
        checaValorIgual(Onze);
        checaValorIgual(Doze);
        checaValorIgual(Treze);                                  
        
    }
    
    public void geraMao(){
        Random random = new Random();              
        
        for (int i=0; i<9; i++){            
            mao.add(new Carta(random.nextInt(13)+1, random.nextInt(4)+1));
        }
                
    }
    
    public void checaDupla(){
        LinkedList<Carta> aux = new LinkedList<>();
        Carta card;
        LinkedList<Integer> index = new LinkedList<>();
        while (mao.size()>0) {
            
            if (!mao.getFirst().emDupla) {
                card = mao.remove();
                for (Carta c : mao) {
                    if (card.valor == c.valor) {
                        if(card.naipe != c.naipe){
                            index.add(mao.indexOf(c));
                        }
                    }
                }
                
                for (int i : index) {
                    aux.add(mao.remove(i));
                }
                aux.add(card);
            }
            for (Carta carta : aux) {
                carta.emDupla = true;
            }
        }
        
        for (Carta carta : aux) {
            mao.add(carta);
        }

    }
    public void imprimeMao(){
        Collections.sort(mao);
        
        for (int i=0; i<9; i++){
            System.out.println(mao.get(i).getValor()+ " - " + mao.get(i).getNaipe() + " - " + mao.get(i).emDupla);
        }
    }
    
    public static void main(String[] args) {                               
        ValidaTrincas a = new ValidaTrincas();
        
        a.geraMao();
        a.imprimeMao();
//        a.checaTrincas();
        a.checaDupla();
        System.out.println("");
        a.imprimeMao();
        
    }
    
}

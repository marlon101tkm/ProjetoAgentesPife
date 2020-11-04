package Agentes;

import comportamentos.ComportamentoSequencial;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import java.util.Collections;
import java.util.LinkedList;
import objetos.Carta;

public class Jogador extends Agent {

    boolean ativo = false;
    protected LinkedList<Carta> mao = new LinkedList<>();
    int qtdTrinca = 0, grupoTrinca = 1;
    String vencedor = "";
    boolean jogoTerminou = false;
        
    public void checaTrincas() {        
        regularizaCartas();
        checaSequencia();
        checaValorIgual();
        checaQtdTrinca();
        //mostraMao();
    }    
    
    protected void regularizaCartas(){
        grupoTrinca = 1;
        for(Carta c:mao){
            c.setEmDupla(false);
            c.setEmTrinca(false);
            c.setGrupoTrinca(0);
        }
    }
    
    protected int cartamenosImp() {
        Carta descartar = null;
        for (Carta carta : mao) {
            if (!carta.isEmTrinca() && !carta.isEmDupla()) {
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
    
   protected void checaQtdTrinca(){
       int cartaTrinca = 0;
       
       for (Carta carta : mao) {
            if (carta.isEmTrinca()) {
               cartaTrinca++; 
            }
        }       
       if(cartaTrinca>=9){
           qtdTrinca = 3;
       }
       
   }

   
    
    public void checaSequencia(LinkedList<Carta> aux){
        // Aux tem que ser maior que 3 cartas, caso contrário, não é uma trinca
        if(aux.size()>=3){
            for(int i=aux.size()-1; i>0; i--){
                int ref = aux.get(i).getValor();
                int indexRef = i;
                boolean trinca = true;                                                                
                
                if(i>=2){
                    
                    for(int c=i-1; c>i-3; c--){
                        // Nao pode comparar cartas que ja estao em trincas
                        if(!aux.get(c).isEmTrinca()){
                            // Ref é o valor referencia que a proxima carta deve ter pra ser considerado sequencial
                            ref++;                                       
                            if(aux.get(c).getValor() != ref){
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
                            aux.get(a).setEmTrinca(true); 
                            aux.get(a).setEmDupla(false);
                            aux.get(a).setGrupoTrinca(grupoTrinca);                            
                        }
                        i=i-2;
                        grupoTrinca++;
                    }                     
                }         
            }    
        }
        
        // Aux tem que ser maior que 2 cartas, caso contrário, não é uma dupla
        if(aux.size()>=2){
            for(int i=aux.size()-1; i>0; i--){
                int ref = aux.get(i).getValor();
                int indexRef = i;
                boolean trinca = true;                                                                
                
                if(i>=2){
                    
                    for(int c=i-1; c>i-2; c--){
                        // Nao pode comparar cartas que ja estao em trincas
                        if(!aux.get(c).isEmDupla()){
                            // Ref é o valor referencia que a proxima carta deve ter pra ser considerado sequencial
                            ref++;                                       
                            if(aux.get(c).getValor() != ref){
                                c=i-2;
                                trinca = false;
                            }    
                        } else {
                            c=i-2;
                            trinca = false;
                        }                                      
                    } 

                    if(trinca){    

                        for(int a=indexRef; a>indexRef-2; a--){                                                    
                            if(aux.get(a).isEmTrinca()){
                                break;
                            } else {
                                aux.get(i).setEmDupla(true);
                            }                                                                                            
                        }
                        i=i-1;
                    }                     
                }         
            }    
        }
    }
    
    public void checaSequencia(){
        LinkedList<Carta> espadas = new LinkedList<Carta>();
        LinkedList<Carta> paus = new LinkedList<Carta>();
        LinkedList<Carta> ouro = new LinkedList<Carta>();
        LinkedList<Carta> copas = new LinkedList<Carta>();
        
        espadas.clear();
        paus.clear();
        ouro.clear();
        copas.clear();
        
        for(Carta c : mao){
            if(c.getNaipe()==1){
                espadas.add(c);              
            } else if(c.getNaipe()==2){
                paus.add(c);                
            } else if(c.getNaipe()==3){
                ouro.add(c);                
            } else if (c.getNaipe()==4) {
                copas.add(c);                
            }
        }
        
        Collections.sort(espadas);
        Collections.sort(paus);
        Collections.sort(ouro);
        Collections.sort(copas);                               
        
        checaSequencia(espadas);
        checaSequencia(paus);
        checaSequencia(ouro);
        checaSequencia(copas);
    }
    
    protected void mostraMao(){
        for(Carta c : mao){            
            System.out.println(c.getValor() + " - " + c.getNaipe() + " - " + c.isEmTrinca() + " - " + c.isEmDupla());
        }
    }
    
    public void checaValorIgual(LinkedList<Carta> aux){
        //Precisa ser maior que 3 pra poder achar uma trinca
        if(aux.size()>=3){
            // Passa três vezes a verificação porque cada verificação pode encontrar apenas uma trinca
            // Passando tres vezes vai poder achar tres trincas (se for o caso)
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
                    if(aux.get(i).getNaipe()==0 && !aux.get(i).isEmTrinca() && !espadas && qtd<3){
                        espadas=true;
                        qtd++;
                        index.add(i);
                    } else if(aux.get(i).getNaipe()==1 && !aux.get(i).isEmTrinca() && !paus && qtd<3){
                        paus=true;
                        qtd++;
                        index.add(i);
                    } else if(aux.get(i).getNaipe()==2 && !aux.get(i).isEmTrinca() && !copas && qtd<3){
                        copas=true;
                        qtd++;
                        index.add(i);
                    } else if(aux.get(i).getNaipe()==3 && !aux.get(i).isEmTrinca() && !ouro && qtd<3){
                        ouro=true;
                        qtd++;
                        index.add(i);
                    }                     
                }

                if(qtd==3){
                    for(Integer i : index){
                        aux.get(i).setEmTrinca(true);
                        aux.get(i).setEmDupla(false);
                        aux.get(i).setGrupoTrinca(grupoTrinca);
                    }
                    
                    grupoTrinca++;
                }                
            }            
        } 
        
        // Precisa ser maior que 2 para poder achar uma dupla
        if(aux.size()>=2){
            // Passa 6 vezes a verificação porque cada verificação pode encontrar apenas uma dupla
            // Passando 6 vezes vai poder achar 6 duplas (se for o caso)
            for(int v=0; v<2; v++){
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
                    if(aux.get(i).getNaipe()==0 && !aux.get(i).isEmTrinca() && !espadas && qtd<2 && !aux.get(i).isEmDupla()){
                        espadas=true;
                        qtd++;
                        index.add(i);
                    } else if(aux.get(i).getNaipe()==1 && !aux.get(i).isEmTrinca() && !paus && qtd<2 && !aux.get(i).isEmDupla()){
                        paus=true;
                        qtd++;
                        index.add(i);
                    } else if(aux.get(i).getNaipe()==2 && !aux.get(i).isEmTrinca() && !copas && qtd<2 && !aux.get(i).isEmDupla()){
                        copas=true;
                        qtd++;
                        index.add(i);
                    } else if(aux.get(i).getNaipe()==3 && !aux.get(i).isEmTrinca() && !ouro && qtd<2 && !aux.get(i).isEmDupla()){
                        ouro=true;
                        qtd++;
                        index.add(i);
                    }                     
                }

                if(qtd==2){
                    for(Integer i : index){
                        if(aux.get(i).isEmTrinca()){
                            break;
                        } else {
                            aux.get(i).setEmDupla(true);
                        }                        
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

    protected void setup() {

        //        comportamento senquencial faz ele executar um comportamento de cada vez
        ComportamentoSequencial comp = new ComportamentoSequencial(this);
        addBehaviour(comp);
        //recebe a ditribuição do carteador 
        comp.adicionaComp(new Behaviour() {

            @Override
            public void action() {
                try {
                    ACLMessage msg = blockingReceive();
                    if (msg != null) {
                        ACLMessage reply = msg.createReply();
                        Carta card = (Carta) msg.getContentObject();
                        mao.add(card);
//                        System.out.println(getAID().getLocalName() + " num card: " + mao.size());
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent(String.valueOf(mao.size()));
                        myAgent.send(reply);
                    } else {
                        block();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean done() {

                return mao.size() == 9;
            }

            @Override
            public int onEnd() {

                System.out.println(myAgent.getAID().getLocalName() + " Num Cartas: " + mao.size());
//                for (Carta carta : mao) {
//                    System.out.println(carta.toString());
//                }
                return 0;
            }
        });

        comp.adicionaComp(new Behaviour() {
            Carta card;
            @Override
            public void action() {
                try {
                    //recebe uma carta do juiz 
                    ACLMessage msg = blockingReceive();
                    if (msg != null) {
//                        System.out.println(msg.getProtocol());
                        if (msg.getProtocol().equalsIgnoreCase("anuncia_fim")) {
                            String resp = msg.getContent();
                            jogoTerminou = true;
                            vencedor = resp;
                        } else {
                            ACLMessage reply = msg.createReply();
                            card = (Carta) msg.getContentObject();
//                        System.out.println(myAgent.getAID().getLocalName());
                            System.out.println(myAgent.getAID().getLocalName() + " Carta Recebida " + card.toString());
                            mao.add(card);
                            checaTrincas();
                            System.out.println(myAgent.getAID().getLocalName() + " Qtd trincas: " + qtdTrinca);
                            //checa quantidade de trincas
                            if (qtdTrinca >= 3) {
                                //manda solicitação de vitoria
                                reply.setPerformative(ACLMessage.INFORM);
                                reply.setProtocol("pede_vitoria");
                                reply.setContentObject(mao);
                                myAgent.send(reply);
//                            msg = blockingReceive();
//                            if (msg != null) {
//                                String resp = msg.getContent();
//                                
//                                if (msg.getProtocol().equals("anuncia_fim")) {
//                                    jogoTerminou = true;
//                                   
//                                }
//                            }
                            } else {
                                // devolver a carta menos importante 
                                reply.setPerformative(ACLMessage.INFORM);
                                reply.setProtocol("faz_jogada");
                                card = mao.remove(cartamenosImp());
                                System.out.println(myAgent.getAID().getLocalName() + " Carta enviada" + card.toString());
                                card.setEmDupla(false);
                                card.setEmTrinca(false);
                                reply.setContentObject(card);
                                myAgent.send(reply);

                            }
                        }
//                        
                    } else {
                        block();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override

            public boolean done() {
                return jogoTerminou;
            }

            @Override
            public int onEnd() {
                System.out.println(vencedor + " Venceu ");

//                for (Carta carta : mao) {
//                    System.out.println(carta.toString());
//                }
                return 0;
            }
        }
        );

    }

}

package Agentes;

import comportamentos.ComportamentoSequencial;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import objetos.Carta;

public class Juiz extends Agent {

    protected LinkedList<Carta> baralho = new LinkedList<Carta>();
    protected LinkedList<Carta> descarte = new LinkedList<Carta>();
    protected LinkedList<Carta> mao = new LinkedList<Carta>();
    String[] nomesJog = {"Jogador1", "Jogador2", "Jogador3", "Jogador4"};
    int i = 0;
    String nomeVencedor;
    Carta cartaEnviada, cartaRecebida;
    boolean jogoTerminou = false;
    boolean primeiraRodada = true;
    int qtdTrinca;

    public void checaTrincas() {
        int qtdEmTrinca = 0;
        for (Carta carta : mao) {
            if (carta.emTrinca) {
                qtdEmTrinca++;
            }
        }
        qtdTrinca = qtdEmTrinca / 3;
    }        

    public void imprimeMaoVencedora() {
        LinkedList<Carta> trincaUm = new LinkedList<Carta>();
        LinkedList<Carta> trincaDois = new LinkedList<Carta>();
        LinkedList<Carta> trincaTres = new LinkedList<Carta>();
        LinkedList<Carta> aux = new LinkedList<Carta>();
        Carta resto = null;
        
        trincaUm.clear();
        trincaDois.clear();
        trincaTres.clear();        
        
        System.out.println("Vencedor: " + nomeVencedor);
        
        for (Carta carta : mao) {
            if(carta.getGrupoTrinca()==1){
                trincaUm.add(carta);
            } else if(carta.getGrupoTrinca()==2){
                trincaDois.add(carta);
            } else if(carta.getGrupoTrinca()==3){
                trincaTres.add(carta);
            } else {
                resto = carta;
            }
        }
        
        Collections.sort(trincaUm);
        Collections.sort(trincaDois);
        Collections.sort(trincaTres);
        
        aux.addAll(trincaUm);
        aux.addAll(trincaDois);
        aux.addAll(trincaTres);
        aux.add(resto);
        
        for(Carta c : aux){
            System.out.println(c.getValor() + " - " + c.getNaipe() + " - " + c.getGrupoTrinca());
        }
        
        
    }

    protected void setup() {

//        comportamento senquencial faz ele executar um comportamento de cada vez
        ComportamentoSequencial comp = new ComportamentoSequencial(this);
        addBehaviour(comp);

        comp.adicionaComp(new Behaviour() {

            @Override
            public void action() {
                try {
                    ACLMessage msg = blockingReceive();
                    if (msg != null) {
                        baralho = (LinkedList<Carta>) msg.getContentObject();
                    } else {
                        block();
                    }
                } catch (UnreadableException e) {
                    e.printStackTrace();
                }
            }

            @Override

            public boolean done() {
                return baralho.size() == 68;
            }

            @Override
            public int onEnd() {

                System.out.println("Baralho Juiz" + baralho.size());
                return 0;
            }
        }
        );

        comp.adicionaComp(new Behaviour() {
            ACLMessage msg, reply;

            @Override
            public void action() {

                try {
                    //quando o baralho acabar pega as cartas da pilha de descarte substitui no baraho e embaralha 
                    if (baralho.size() == 0 || descarte.size()==0) {
                        baralho.addAll(descarte);
                        descarte.clear();
                        Collections.shuffle(baralho);
                        primeiraRodada = true;
                    }
                    msg = new ACLMessage(ACLMessage.INFORM);
                    //na primeria rodada pega primeiro do baralho
                    if (primeiraRodada) {
//                      System.out.println(nomesJog[i]);
                        msg.addReceiver(new AID(nomesJog[i], AID.ISLOCALNAME));
                        msg.setProtocol("envia_carta");
                        cartaEnviada = (Carta) baralho.pop();
                        System.out.println("Primeira carta enviada" + cartaEnviada.toString() + " para jogador: " + nomesJog[i]);
                        msg.setContentObject(cartaEnviada);
                        myAgent.send(msg);
                        msg = blockingReceive();
                        if (msg != null) {
                            reply = msg.createReply();
//                            recebe a resposta sendo uma jogada comun ou pedido de vitoria
                            if (msg.getProtocol().equalsIgnoreCase("faz_jogada")) {
                                cartaRecebida = (Carta) msg.getContentObject();
                                System.out.println("Primeira carta Recebida" + cartaRecebida.toString() + " do jogador: " + nomesJog[i]);
                                descarte.push(cartaRecebida);
                                primeiraRodada = false;
                            } else if (msg.getProtocol().equalsIgnoreCase("pede_vitoria")) {
                                mao = (LinkedList<Carta>) msg.getContentObject();
                                checaTrincas();
                                System.err.println(qtdTrinca);
                                if (qtdTrinca >= 3) {
//                                    reply.removeReceiver(new AID(nomesJog[i], AID.ISLOCALNAME));
                                    for (int j = 0; j < 4; j++) {
                                        reply.addReceiver(new AID(nomesJog[j], AID.ISLOCALNAME));
                                    }
                                    reply.setContent(nomesJog[i]);
                                    reply.setProtocol("anuncia_fim");
                                    myAgent.send(reply);
                                    jogoTerminou = true;
                                    nomeVencedor = nomesJog[i];

                                } else {
                                    qtdTrinca = 0;
                                }
                                primeiraRodada = false;
                            }
                        } else {
                            block();
                        }
                    } else {
                        //nas jogadas subsequentes pega primeiro da pilha de descarte
//                        System.out.println(nomesJog[i]);
                        msg.addReceiver(new AID(nomesJog[i], AID.ISLOCALNAME));
                        msg.setProtocol("envia_carta");
                        
                        Random r = new Random();                                                
                        if(r.nextInt(100)%2==0 && descarte.size() > 0){
                            cartaEnviada = (Carta) descarte.pop();
                        } else {
                            cartaEnviada = (Carta) baralho.pop();
                        }

                        System.out.println(" Carta Enviada descarte:" + cartaEnviada.toString() + " para jogador: " + nomesJog[i]);
                        msg.setContentObject(cartaEnviada);
                        myAgent.send(msg);
                        msg = blockingReceive();
                        if (msg != null) {
                            reply = msg.createReply();
//                            System.out.println(msg.getProtocol());
                            if (msg.getProtocol().equalsIgnoreCase("faz_jogada")) {
                                cartaRecebida = (Carta) msg.getContentObject();
                                System.out.println(" Carta Recebida descarte:" + cartaRecebida.toString() + " do jogador: " + nomesJog[i]);
                                //se a carta recebida for iqual enviada então envia  uma carta do baralho
                                descarte.push(cartaRecebida);
//                                if (cartaRecebida.valor == cartaEnviada.valor && cartaRecebida.naipe == cartaEnviada.naipe) {
                                    if(cartaRecebida.equals(cartaEnviada)){
                                    msg.addReceiver(new AID(nomesJog[i], AID.ISLOCALNAME));
                                    msg.setProtocol("envia_carta");
                                    cartaEnviada = (Carta) baralho.pop();
                                    System.out.println(" Carta Enviada baralho:" + cartaEnviada.toString() + " para jogador: " + nomesJog[i]);
                                    msg.setContentObject(cartaEnviada);
                                    myAgent.send(msg);
                                    msg = blockingReceive();
                                    if (msg != null) {
                                        reply = msg.createReply();
                                        if (msg.getProtocol().equalsIgnoreCase("faz_jogada")) {
                                            cartaRecebida = (Carta) msg.getContentObject();
                                            System.out.println(" Carta Recebida baralho:" + cartaRecebida.toString() + " do jogador: " + nomesJog[i]);
                                            descarte.push(cartaRecebida);
//                                            ele repete esse codigo  ali embaixo pq ele precisa checar a vitoria a cada envio de cartas

                                        } else if (msg.getProtocol().equalsIgnoreCase("pede_vitoria")) {
                                            mao = (LinkedList<Carta>) msg.getContentObject();

                                            checaTrincas();
                                            System.err.println(qtdTrinca);
                                            if (qtdTrinca >= 3) {
//                                                reply.removeReceiver(new AID(nomesJog[i], AID.ISLOCALNAME));
                                                for (int j = 0; j < 4; j++) {
                                                    reply.addReceiver(new AID(nomesJog[j], AID.ISLOCALNAME));
                                                }
                                                reply.setContent(nomesJog[i]);
                                                reply.setProtocol("anuncia_fim");
                                                myAgent.send(reply);
                                                jogoTerminou = true;
                                                nomeVencedor = nomesJog[i];
                                            } else {
                                                qtdTrinca = 0;
                                            }
                                        }
                                    } else {
                                        block();
                                    }
                                }
                            } else if (msg.getProtocol().equalsIgnoreCase("pede_vitoria")) {
                                mao = (LinkedList<Carta>) msg.getContentObject();
//                                for (Carta carta : mao) {
//                                    System.out.println(carta.toString());
//                                }
                                checaTrincas();
                                System.err.println(qtdTrinca);
                                if (qtdTrinca >= 3) {
//                                    reply.removeReceiver(new AID(nomesJog[i], AID.ISLOCALNAME));
                                    for (int j = 0; j < 4; j++) {
                                        reply.addReceiver(new AID(nomesJog[j], AID.ISLOCALNAME));
                                    }

                                    reply.setContent(nomesJog[i]);
                                    reply.setProtocol("anuncia_fim");
                                    myAgent.send(reply);
                                    jogoTerminou = true;
                                    nomeVencedor = nomesJog[i];
                                } else {
                                    qtdTrinca = 0;
                                }

                            }

                        } else {
                            block();
                        }

                    }
                    i++;
                    if (i == 4) {
                        i = 0;
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

                imprimeMaoVencedora();
                return 0;
            }
        }
        );

    }

}

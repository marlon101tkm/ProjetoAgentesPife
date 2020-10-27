package Agentes;

import comportamentos.DistribuirCartas;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;

import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.util.leap.ArrayList;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Collection;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetos.Carta;

public class Carteador extends Agent {

    protected LinkedList<Carta> baralho = new LinkedList<Carta>();
    String[] nomesJog = {"Jogador1", "Jogador2", "Jogador3", "Jogador4"};

    public void geraCartas() {

        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 14; j++) {
                baralho.add(new Carta(j, i));
            }
        }
        Collections.shuffle(baralho);
    }

    protected void setup() {
        geraCartas();
        geraCartas();
        
        // não to chamando uma chmando a classe Distribuir Cartas pra não ter que passar o baralho por parametro
        
        //Esse comportamento Distribui as cartas pros jogadores 
        addBehaviour(new Behaviour() {
            boolean distribuiu = false;

//            LinkedList<String> baralho = new LinkedList<String>();
            @Override
            public void action() {
                try {
                    //Esse Trecho é o original de primeira Vez que eu enviei
                    /*
                    int i = 39;
                    ServiceDescription servico = new ServiceDescription();
                    servico.setType("recebe_carta");
                    DFAgentDescription dfd = new DFAgentDescription();
                    dfd.addServices(servico);
                    DFAgentDescription[] resultado = DFService.search(myAgent, dfd);
                  
                    while (i > 0) {
                        if (resultado.length != 0) {
                            for (DFAgentDescription jogador : resultado) {

                                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                                msg.addReceiver(jogador.getName());
                                System.out.println("Carta enviada: Jogador:" + jogador.getName() + "" + baralho.peek().toString());
                                msg.setContentObject(baralho.pop());
                                myAgent.send(msg);

                                i--;
                            }
                        }
                    }
                    distribuiu = true;
                    System.out.println("Terminou Execução: " + baralho.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
              
                     */
                    
                    //Esse Trecho é de um novo teste atualmente funciona 
                    int i = 39;
                    while (i > 0) {
                        //nessa forma aqui ele não esta buscando nas paginas amarelas
//                        ele ta usando um lista de nome que eu setei 
                        for (String jogador : nomesJog) {
                            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                            msg.addReceiver(new AID(jogador, AID.ISLOCALNAME));
//                            esse print é pra saber o que ele ta enviando 
                             System.out.println("Carta enviada: Jogador:" + jogador + "" + baralho.peek().toString());
                            msg.setContentObject(baralho.pop());
                            myAgent.send(msg);

                            i--;
                        }

                    }
                    distribuiu = true;
//                    System.out.println("Terminou Execução: " + baralho.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override

            public boolean done() {
                return distribuiu;
            }

        }
        );
            
//        esse comportamento seria pra enviar o resto do baralho que foi diztribuido para o Juiz 
// ele da alguns erros 
        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                try {
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    msg.addReceiver(new AID("Juiz", AID.ISLOCALNAME));
                    msg.setContentObject(baralho);
                    myAgent.send(msg);
                } catch (IOException ex) {
                    Logger.getLogger(Carteador.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            @Override
            public boolean done() {
                return baralho.size() == 0;
            }
        });

    }

    protected void registraServico(ServiceDescription sd) {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);

        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }

}

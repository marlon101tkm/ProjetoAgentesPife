/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comportamentos;

import Agentes.Jogador;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import objetos.Carta;

/**
 *
 * @author Win10
 */
public class DistribuirCartas extends Behaviour {

    LinkedList<Carta> baralho;
    private MessageTemplate mt;
    boolean distribuiu = false;
    boolean maoCompleta = false;
    int contJog;
    int caso = 0;
//    String[] nomesJog = {"Jogador1", "Jogador2", "Jogador3", "Jogador4"};
    AID[] agentesJog;

    public DistribuirCartas(LinkedList<Carta> baralho, Agent a, AID[] agentesJog) {
        super(a);
        this.baralho = baralho;
        this.agentesJog = agentesJog;
    }

    public LinkedList<Carta> getBaralho() {
        return baralho;
    }

    @Override
    public void action() {

        // Envie o pedido de compra para melhor vendedor
        switch (caso) {
            case 0:
             try {

                // envia sua identificacao para tds os vendedores
                ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                for (int i = 0; i < agentesJog.length; ++i) {
                    cfp.addReceiver(agentesJog[i]);

                    cfp.setContentObject(baralho.pop());
                    cfp.setConversationId("distribuindo_cartas");
                    cfp.setReplyWith("cfp" + System.currentTimeMillis()); // valor unico
                    myAgent.send(cfp);
                    // Prepara o modelo de receber respostas
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId("distribuido_cartas"),
                            MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
                }
                caso = 0;
//                ACLMessage order = new ACLMessage(ACLMessage.CFP);
//                if (agentesJog != null) {
//                    for (int i = 0; i < agentesJog.length; i++) {
//                        order.addReceiver(agentesJog[i]);
//                        order.setContentObject(baralho.pop());
//                        order.setConversationId("Distribuindo_cartas");
//                        order.setReplyWith("Orden" + System.currentTimeMillis());
//                        myAgent.send(order);
//                        // modelo para resposta de ordem de compra
//                        mt = MessageTemplate.and(MessageTemplate.MatchConversationId("Distribuindo_cartas"),
//                                MessageTemplate.MatchInReplyTo(order.getReplyWith()));
//                        caso = 1;
//                    }
//                }else{
//                    block();
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            break;
            case 1:
                // recebe tdas as respostas / recusado por agentes vendedores
                maoCompleta = true;
                ACLMessage reply = myAgent.receive(mt);// MENSAGEM NO FORMATO ACL
                if (reply != null) {
                    // responde
                    if (reply.getPerformative() == ACLMessage.PROPOSE) {

                        int qtdCartas = Integer.parseInt(reply.getContent());
                        System.out.println(reply.getSender().getLocalName() + " Num Cartas: " + qtdCartas);
                        if (qtdCartas != 9) {
                            maoCompleta = false;
                        }
                    }
                    contJog++;//incrementa a oferta
                    if (contJog == agentesJog.length && !maoCompleta) {

                        caso = 0;
                    } else {
                        distribuiu = true;
                    }
                } else {
                    block();
                }
                break;
        }

//        try {
//            LinkedList<Integer> listaJogNum = new LinkedList<Integer>();
//            for (String jogador : nomesJog) {
//                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
//                msg.addReceiver(new AID(jogador, AID.ISLOCALNAME));
//                msg.setContentObject(baralho.pop());
//                myAgent.send(msg);
//                ACLMessage resp = myAgent.receive();
//                if (resp != null) {
//                    listaJogNum.add((Integer) resp.getContentObject());
////                    System.out.println(resp.getSender().getLocalName() + " Qtd Cartas: " + (int) resp.getContentObject());
//                } else {
//                    block();
//                }
//            }
//            for (Integer integer : listaJogNum) {
//                distribuiu = true;
//                if (integer != 9) {
//                    distribuiu = false;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public int onEnd() {
        System.out.println("enviou tudo pros jogadores");
        return 0;
    }

    @Override
    public boolean done() {
        return distribuiu;
    }
}

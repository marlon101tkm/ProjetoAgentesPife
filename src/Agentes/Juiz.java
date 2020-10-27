package Agentes;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.proto.SubscriptionInitiator;
import jade.util.leap.ArrayList;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import objetos.Carta;

public class Juiz extends Agent {

    protected LinkedList<Carta> baralho = new LinkedList<Carta>();
    protected LinkedList<Carta> descarte = new LinkedList<Carta>();
    boolean recebeu = false;

    protected void setup() {
        
        
//        esse comportamento esta recebendo o resto da distribuição do Carteador
//        esse ta dando erros  
        addBehaviour(new Behaviour() {

            @Override
            public void action() {
                try {
                    ACLMessage msg = receive();
                    if (msg != null) {
//                        Carta carta = (Carta) msg.getContentObject();
                        baralho =  (LinkedList<Carta>) msg.getContentObject(); ;
//                        if (baralho.size() == 65) {
//                            recebeu = true;
//                        }
                    }
                    System.out.println(baralho.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean done() {
                return true;
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

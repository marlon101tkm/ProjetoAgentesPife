package Agentes;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.proto.SubscriptionInitiator;
import jade.util.leap.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Win10
 */
public class Juiz extends Agent {
    
    
    public  Deque<Jogador> iniciaJogadores(){
        Deque<Jogador> jo = new  ArrayDeque<Jogador>();
        
        jo.add(new Jogador());
        jo.add(new Jogador());
        jo.add(new Jogador());
        jo.add(new Jogador());
        
        
        return jo;
        
    }    
    
    protected void setup() {
       
        Deque<Jogador> jogadores = iniciaJogadores();
        Carteador carteador = new Carteador(jogadores);
        
        
    }

    protected void PedeNotificacao(final ServiceDescription sd, final String Pedido) {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.addServices(sd);
        ACLMessage mgs = DFService.createSubscriptionMessage(this, getDefaultDF(), dfd, null);
        addBehaviour(new SubscriptionInitiator(this, mgs) {

            protected void handleInform(ACLMessage inform) {
                try {
                    DFAgentDescription[] dfds = DFService.decodeNotification(inform.getContent());
                    ACLMessage mensagem = new ACLMessage(ACLMessage.INFORM);
                    mensagem.addReceiver(dfds[0].getName());
                    mensagem.setContent(Pedido);
                    myAgent.send(mensagem);
                } catch (FIPAException e) {
                    e.printStackTrace();
                }
            }
        }
        );
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

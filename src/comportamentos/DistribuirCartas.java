/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comportamentos;

import Agentes.Jogador;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
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
    boolean distribuiu = false;

    public DistribuirCartas(LinkedList<Carta> baralho, Agent a) {
        super(a); 
        this.baralho = baralho;
    }

    @Override
    public void action() {
       
        try {
            int i = 39;
            ServiceDescription servico = new ServiceDescription();
            servico.setType("recebe_carta");
            DFAgentDescription dfd = new DFAgentDescription();
            dfd.addServices(servico);
            DFAgentDescription[] resultado = DFService.search(myAgent, dfd);
//            System.out.println(resultado.length);
            while (i > 0) {
                
                for (DFAgentDescription jogador : resultado) {
                  
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    msg.addReceiver(jogador.getName());
                    System.out.println("Carta enviada: Jogador:"+jogador.getName() +" Naipe:"+baralho.peek().getNaipe() +" Numero: "+baralho.peek().getValor());
                    msg.setContentObject(baralho.pop());
                    myAgent.send(msg);
                    i--;
                }

            }

            distribuiu = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public boolean done() {
        return distribuiu;
    }

    protected void busca(final ServiceDescription sd, final Carta Pedido) {

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.addServices(sd);

        try {
            DFAgentDescription[] resultado = DFService.search(myAgent, dfd);
            if (resultado.length != 0) {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(resultado[0].getName());
                msg.setContentObject(Pedido);
                myAgent.send(msg);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

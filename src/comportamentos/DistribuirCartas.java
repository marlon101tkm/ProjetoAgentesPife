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
import objetos.Carta;

/**
 *
 * @author Win10
 */
public class DistribuirCartas extends Behaviour {

    Deque<Jogador> listJog;
    Deque<Carta> baralho;
    boolean distribuiu = false;

    public DistribuirCartas(Deque<Jogador> listJog, Deque<Carta> baralho, Agent a) {
        super(a);
        this.listJog = listJog;
        this.baralho = baralho;
    }

    @Override
    public void action() {
        int i = 39;
        while (i > 0) {

            for (Jogador jogador : listJog) {
                ServiceDescription servico = new ServiceDescription();
                servico.setOwnership(jogador.getLocalName());
                servico.setType("recebe_cartas");
                busca(servico, baralho.pop());
                i--;
            }

        }
        
        distribuiu = true;
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

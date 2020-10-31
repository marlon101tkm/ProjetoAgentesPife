/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comportamentos;

import Agentes.Juiz;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetos.Carta;

/**
 *
 * @author Win10
 */
public class RecebeBaralho extends Behaviour {
    
    protected LinkedList<Carta> baralho = new LinkedList<Carta>();

    public RecebeBaralho(Agent a) {
        super(a);
    }

    public LinkedList<Carta> getBaralho() {
        return baralho;
    }
    
    
    
    public void action() {
        try {
            ACLMessage msg = myAgent.receive();
            if (msg != null) {
                baralho.addAll((LinkedList<Carta>) msg.getContentObject());
            } else {
                block();
            }
        } catch (UnreadableException ex) {
            Logger.getLogger(Juiz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean done() {
        return true;
    }

    public int onEnd() {
//        System.out.println(" Recebeu Baralho" + baralho.size());
        return 0;
    }
}

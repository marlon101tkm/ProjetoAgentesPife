/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comportamentos;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.LinkedList;
import objetos.Carta;

/**
 *
 * @author Win10
 */
public class EnviaBaralho extends OneShotBehaviour {

    protected LinkedList<Carta> baralho = new LinkedList<Carta>();

    public EnviaBaralho(Agent a, LinkedList<Carta> baralho) {
        super(a);
        this.baralho = baralho;
    }

    @Override
    public void action() {
        try {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.addReceiver(new AID("Juiz", AID.ISLOCALNAME));
            msg.setContentObject(baralho);
            myAgent.send(msg);
        } catch (Exception e) {
        }
    }

}

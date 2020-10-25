package comportamentos;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Win10
 */
import objetos.Carta;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EntregaCarta extends Behaviour {

    Carta carta;
    AID idJog;
    public EntregaCarta(Agent a , Carta carta , AID idJog ) {
        super(a);
        this.carta = carta;
        this.idJog = idJog;
    }

    @Override
    public void action() {
        ACLMessage msg = new ACLMessage();
        msg.addReceiver(idJog);
        try {
            msg.setContentObject(carta);
        } catch (IOException ex) {
            Logger.getLogger(EntregaCarta.class.getName()).log(Level.SEVERE, null, ex);
        }
        myAgent.send(msg);
    }

    @Override
    public boolean done() {
        
        return true;
    }

}

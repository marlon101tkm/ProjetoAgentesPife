/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comportamentos;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

/**
 *
 * @author Win10
 */
public class DistribuiCarta extends AchieveREInitiator{

    public DistribuiCarta(Agent a, ACLMessage msg) {
        super(a, msg);
    }
    
    @Override
    protected void handleAgree(ACLMessage agree){
        
    
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comportamentos;

import jade.core.Agent;
import jade.core.behaviours.*;

/**
 *
 * @author Win10
 */
public class ComportamentoSequencial extends SequentialBehaviour {

    public ComportamentoSequencial(Agent a) {
        super(a);
    }
    
    
    
    public int onEnd() {
        myAgent.doDelete();
        return 0;
        
    }
 
    public void adicionaComp( Behaviour comp){
        this.addSubBehaviour(comp);
    }
    
}

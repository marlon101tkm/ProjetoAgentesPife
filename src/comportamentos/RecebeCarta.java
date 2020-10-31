package comportamentos;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.FIPAAgentManagement.*;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;
import java.util.LinkedList;
import objetos.Carta;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Win10
 */
public class RecebeCarta extends Behaviour {

    public LinkedList<Carta> mao = new LinkedList<Carta>();

    public RecebeCarta(Agent a) {
        super(a);
    }

    public LinkedList<Carta> getMao() {
        return mao;
    }

    @Override
    public void action() {
        try {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // processar a mensagem recebida
                mao.add((Carta) msg.getContentObject());
                for (Carta carta : mao) {
                    carta.toString();
                }
                ACLMessage resposta = msg.createReply();
                resposta.setPerformative(ACLMessage.PROPOSE);// ps: resposta em ACL 
                resposta.setContent(String.valueOf(mao.size()));//sao em forma de performativas
                myAgent.send(resposta);
            } else {
                block();
            }
        } catch (Exception e) {

        }
    }

    public int onEnd() {
//        System.out.println(myAgent.getLocalName() + " num Cartas: " + mao.size());

        return 0;
    }

    @Override
    public boolean done() {

        return mao.size() == 9;
    }

}

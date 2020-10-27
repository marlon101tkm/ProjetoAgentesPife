package Agentes;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.LinkedList;
import objetos.Carta;

public class Jogador extends Agent {

    boolean ativo = false;
    protected LinkedList<Carta> mao = new LinkedList<Carta>();
    String tipoServiço;

    protected void setup() {

//        ServiceDescription servico = new ServiceDescription();
//        servico.setType("recebe_carta");
//        servico.setName(this.getLocalName());
//        registraServico(servico);
    

        
//        esse comportamento recebe uma carta de cada vez que o carteador distribui 
//        ele para quando atingir o numero de cartas na mão igual a 9
//esse esta funcinado
        addBehaviour(new Behaviour() {

            @Override
            public void action() {
                try {
                    ACLMessage msg = receive();
                    if (msg != null) {
                        Carta recebida = (Carta) msg.getContentObject();
                        mao.add(recebida);
                        //esses prints aqui é só pra vc ver o que ele ta recebendo
                        System.out.println(myAgent.getLocalName() + " Cartas na Mão: " + mao.size());
                        System.out.println(myAgent.getLocalName() +" "+recebida.toString());


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean done() {

                return mao.size() == 9;
            }

        });
        
       
        

    }

    protected void registraServico(ServiceDescription sd) {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);

//            System.out.println("Registrou Serviço"+dfd.getAllServices());
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }

}

package Agentes;

import comportamentos.ComportamentoSequencial;
import comportamentos.DistribuirCartas;
import comportamentos.RecebeCarta;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.proto.SubscriptionInitiator;
import jade.util.Logger;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import objetos.Carta;

public class Jogador extends Agent {

    boolean ativo = false;
    protected LinkedList<Carta> mao = new LinkedList<Carta>();
    int qtdTrinca = 0;
    boolean jogoTerminou = false;

    protected int cartamenosImp() {
        Carta descartar = null;
        for (Carta carta : mao) {
            if (!carta.isEmTrinca() || !carta.isEmDupla()) {
                descartar = carta;
            }
        }

        if (descartar == null) {
            for (Carta carta : mao) {
                if (!carta.isEmTrinca()) {
                    descartar = carta;
                }
            }
        }

        return mao.indexOf(descartar);
    }

    protected int checaNipeIqual(Carta c1, Carta c2) {
        for (Carta carta : mao) {
            if (carta.getNaipe() == c1.getNaipe()) {
                if (carta.getValor() == c2.getValor() + 1) {

                    return mao.indexOf(carta);

                } else if (carta.getValor() == c1.getValor() - 1) {
                    return mao.indexOf(carta);
                }
            }
        }

        return -1;
    }

    protected int checaValIqual(Carta c1, Carta c2) {
        for (Carta carta : mao) {
            if (carta.getValor() == c1.getValor()) {
                if (carta.getNaipe() != c1.getNaipe() && carta.getNaipe() != c2.getNaipe()) {
                    return mao.indexOf(carta);
                }
            }
        }

        return -1;
    }

    protected void checaTrincas() {
        Collections.sort(mao);
        LinkedList<Carta> dupla = new LinkedList<Carta>();
        LinkedList<Carta> trinca = new LinkedList<Carta>();
        LinkedList<Carta> neutro = new LinkedList<Carta>();
        int i = 0;
        Carta primeria, proxima;
        while (mao.size() == 0) {
            primeria = mao.removeFirst();
            for (Carta carta : mao) {
                if (primeria.getNaipe() == carta.getNaipe()) {
                    if (primeria.getValor() + 1 == carta.getValor()) {
                        proxima = mao.remove(mao.indexOf(carta));
                        i = checaNipeIqual(primeria, proxima);
                        if (i < -1) {
                            Carta interna = mao.remove(i);
                            primeria.setEmDupla(true);
                            proxima.setEmDupla(true);
                            interna.setEmDupla(true);
                            primeria.setEmTrinca(true);
                            proxima.setEmTrinca(true);
                            interna.setEmTrinca(true);
                            trinca.add(primeria);
                            trinca.add(proxima);
                            trinca.add(interna);
                            System.out.println(this.getAID().getLocalName() + "Trinca: ");
                            System.out.println(primeria.toString());
                            System.out.println(proxima.toString());
                            System.out.println(interna.toString());

                            qtdTrinca++;
                        } else {
                            primeria.setEmDupla(true);
                            proxima.setEmDupla(true);
                            dupla.add(primeria);
                            dupla.add(proxima);
                            System.out.println(this.getAID().getLocalName() + "Dupla: ");
                            System.out.println(primeria.toString());
                            System.out.println(proxima.toString());
                        }

                    } else if (primeria.getValor() - 1 == carta.getValor()) {
                        proxima = mao.remove(mao.indexOf(carta));
                        i = checaNipeIqual(primeria, proxima);
                        if (i < -1) {
                            Carta interna = mao.remove(i);
                            primeria.setEmDupla(true);
                            proxima.setEmDupla(true);
                            interna.setEmDupla(true);
                            primeria.setEmTrinca(true);
                            proxima.setEmTrinca(true);
                            interna.setEmTrinca(true);
                            trinca.add(primeria);
                            trinca.add(proxima);
                            trinca.add(interna);
                            System.out.println(this.getAID().getLocalName() + "Trinca: ");
                            System.out.println(primeria.toString());
                            System.out.println(proxima.toString());
                            System.out.println(interna.toString());
                            qtdTrinca++;
                        } else {
                            primeria.setEmDupla(true);
                            proxima.setEmDupla(true);
                            dupla.add(primeria);
                            dupla.add(proxima);
                            System.out.println(this.getAID().getLocalName() + "Dupla: ");
                            System.out.println(primeria.toString());
                            System.out.println(proxima.toString());
                        }
                    }
                } else {
                    if (primeria.getValor() == carta.getValor()) {
                        proxima = mao.remove(mao.indexOf(carta));
                        i = checaValIqual(primeria, proxima);
                        if (i < -1) {
                            Carta interna = mao.remove(i);
                            primeria.setEmDupla(true);
                            proxima.setEmDupla(true);
                            interna.setEmDupla(true);
                            primeria.setEmTrinca(true);
                            proxima.setEmTrinca(true);
                            interna.setEmTrinca(true);
                            trinca.add(primeria);
                            trinca.add(proxima);
                            trinca.add(interna);
                            System.out.println(this.getAID().getLocalName() + "Trinca: ");
                            System.out.println(primeria.toString());
                            System.out.println(proxima.toString());
                            System.out.println(interna.toString());
                            qtdTrinca++;
                        } else {
                            primeria.setEmDupla(true);
                            proxima.setEmDupla(true);
                            dupla.add(primeria);
                            dupla.add(proxima);
                            System.out.println(this.getAID().getLocalName() + "Dupla: ");
                            System.out.println(primeria.toString());
                            System.out.println(proxima.toString());
                        }
                    }

                }

            }
        }
        mao.addAll(trinca);
        mao.addAll(dupla);
    }

    protected void setup() {

        //        comportamento senquencial faz ele executar um comportamento de cada vez
        ComportamentoSequencial comp = new ComportamentoSequencial(this);
        addBehaviour(comp);
        //recebe a ditribuição do carteador 
        comp.adicionaComp(new Behaviour() {

            @Override
            public void action() {
                try {
                    ACLMessage msg = blockingReceive();
                    if (msg != null) {
                        ACLMessage reply = msg.createReply();
                        Carta card = (Carta) msg.getContentObject();
                        mao.add(card);
//                        System.out.println(getAID().getLocalName() + " num card: " + mao.size());
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent(String.valueOf(mao.size()));
                        myAgent.send(reply);
                    } else {
                        block();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean done() {

                return mao.size() == 9;
            }

            @Override
            public int onEnd() {

                System.out.println(myAgent.getAID().getLocalName() + " Num Cartas: " + mao.size());
//                for (Carta carta : mao) {
//                    System.out.println(carta.toString());
//                }
                return 0;
            }
        });

        comp.adicionaComp(new Behaviour() {

            @Override
            public void action() {
                try {
                    //recebe uma carta do juiz 
                    ACLMessage msg = blockingReceive();
                    if (msg != null) {
                        ACLMessage reply = msg.createReply();
                        Carta card = (Carta) msg.getContentObject();
                        mao.add(card);
                        checaTrincas();
                        //checa quantidade de trincas
                        if (qtdTrinca >= 3) {
                            //manda solicitação de vitoria
                            reply.setPerformative(ACLMessage.INFORM);
                            reply.setProtocol("pede_vitoria");
                            reply.setContentObject(mao);
                            myAgent.send(reply);
                            msg = blockingReceive();
                            if (msg != null) {
                                String resp = msg.getContent();
                                if (resp.equals("venceu")) {
                                    jogoTerminou = true;
                                }
                            }

                        } else {
                            // devolver a carta menos importante 
                            reply.setPerformative(ACLMessage.INFORM);
                            reply.setProtocol("faz_jogada");
                            card = mao.remove(cartamenosImp());
                            card.setEmDupla(false);
                            card.setEmTrinca(false);
                            reply.setContentObject(card);
                            myAgent.send(reply);

                        }
//                        

                    } else {
                        block();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean done() {
//                S

                return jogoTerminou;
            }

            @Override
            public int onEnd() {

                System.out.println(myAgent.getAID().getLocalName() + " Venceu ");
//                for (Carta carta : mao) {
//                    System.out.println(carta.toString());
//                }
                return 0;
            }
        });

    }

}

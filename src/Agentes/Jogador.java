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
    protected LinkedList<Carta> mao = new LinkedList<>();
    int qtdTrinca = 0;
    String vencedor = "";
    boolean jogoTerminou = false;

    protected int cartamenosImp() {
        Carta descartar = null;
        for (Carta carta : mao) {
            if (!carta.isEmTrinca() && !carta.isEmDupla()) {
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

    public void checaTrincas() {
        int qtdEmtrinca = 0;
        checaSequencia();
        checaValorIgual();
        for (Carta carta : mao) {
            if (carta.emTrinca) {
                qtdEmtrinca++;
            }
        }
        if (qtdEmtrinca >= 3) {
            qtdTrinca += qtdEmtrinca / 3;
        }

    }

    

    public void checaSequencia() {
        LinkedList<Carta> espadas = new LinkedList<Carta>();
        LinkedList<Carta> paus = new LinkedList<Carta>();
        LinkedList<Carta> ouro = new LinkedList<Carta>();
        LinkedList<Carta> copas = new LinkedList<Carta>();

        for (Carta c : mao) {
            switch (c.getNaipe()) {
                case 1:
                    espadas.add(c);
                    break;
                case 2:
                    paus.add(c);
                    break;
                case 3:
                    ouro.add(c);
                    break;
                default:
                    copas.add(c);
                    break;
            }

        }

        Collections.sort(espadas);
        Collections.sort(paus);
        Collections.sort(ouro);
        Collections.sort(copas);

        if (espadas.size() >= 3) {
            for (int i = espadas.size() - 1; i > 0; i--) {
                int ref = espadas.get(i).getValor();
                int indexRef = i;
                boolean trinca = true;

                if (i >= 2) {

                    for (int c = i - 1; c > i - 3; c--) {
                        // Nao pode comparar cartas que ja estao em trincas
                        if (!espadas.get(c).emTrinca) {
                            ref++;
                            if (espadas.get(c).getValor() != ref) {
                                c = i - 3;
                                trinca = false;
                            }
                        } else {
                            c = i - 3;
                            trinca = false;
                        }
                    }

                    if (trinca) {

                        for (int a = indexRef; a > indexRef - 3; a--) {
                            espadas.get(a).setEmTrinca(true);
                        }
                        i = i - 2;
                    }
                }
            }
        }

        if (copas.size() >= 3) {
            for (int i = copas.size() - 1; i > 0; i--) {
                int ref = copas.get(i).getValor();
                int indexRef = i;
                boolean trinca = true;

                if (i >= 2) {

                    for (int c = i - 1; c > i - 3; c--) {
                        // Nao pode comparar cartas que ja estao em trincas
                        if (!copas.get(c).emTrinca) {
                            ref++;
                            if (copas.get(c).getValor() != ref) {
                                c = i - 3;
                                trinca = false;
                            }
                        } else {
                            c = i - 3;
                            trinca = false;
                        }
                    }

                    if (trinca) {

                        for (int a = indexRef; a > indexRef - 3; a--) {
                            copas.get(a).setEmTrinca(true);
                        }
                        i = i - 2;
                    }
                }
            }
        }

        if (paus.size() >= 3) {
            for (int i = paus.size() - 1; i > 0; i--) {
                int ref = paus.get(i).getValor();
                int indexRef = i;
                boolean trinca = true;

                if (i >= 2) {

                    for (int c = i - 1; c > i - 3; c--) {
                        // Nao pode comparar cartas que ja estao em trincas
                        if (!paus.get(c).emTrinca) {
                            ref++;
                            if (paus.get(c).getValor() != ref) {
                                c = i - 3;
                                trinca = false;
                            }
                        } else {
                            c = i - 3;
                            trinca = false;
                        }
                    }

                    if (trinca) {

                        for (int a = indexRef; a > indexRef - 3; a--) {
                            paus.get(a).setEmTrinca(true);
                        }
                        i = i - 2;
                    }

                }
            }
        }

        if (ouro.size() >= 3) {
            for (int i = ouro.size() - 1; i > 0; i--) {
                int ref = ouro.get(i).getValor();
                int indexRef = i;
                boolean trinca = true;

                if (i >= 2) {

                    for (int c = i - 1; c > i - 3; c--) {
                        // Nao pode comparar cartas que ja estao em trincas
                        if (!ouro.get(c).emTrinca) {
                            ref++;
                            if (ouro.get(c).getValor() != ref) {
                                c = i - 3;
                                trinca = false;
                            }
                        } else {
                            c = i - 3;
                            trinca = false;
                        }
                    }

                    if (trinca) {

                        for (int a = indexRef; a > indexRef - 3; a--) {
                            ouro.get(a).setEmTrinca(true);
                        }
                        i = i - 2;
                    }
                }
            }
        }
    }

    public void checaValorIgual(LinkedList<Carta> aux) {
        // Passa três vezes a verificação porque cada verificação pode encontrar apenas uma trinca
        // Passando tres vezes vai poder achar tres trincas (se for o caso)
        if (aux.size() >= 3) {
            for (int v = 0; v < 3; v++) {
                /* Os booleans + o qtd são fundamentais pra achar o padrão de trinca
                    Os booleans impedem que o mesmo naipe seja validado mais de uma vez
                    O qtd é fundamental pra dizer "Ok, encontramos 3 naipes diferentes, pode parar"
                 */

                boolean paus = false;
                boolean ouro = false;
                boolean copas = false;
                boolean espadas = false;
                int qtd = 0;

                // O index foi criado pra obter a posição do objeto pra depois exatamente ele ser setado como True
                LinkedList<Integer> index = new LinkedList<Integer>();

                for (int i = 0; i < aux.size(); i++) {
                    if (aux.get(i).getNaipe() == 1 && !aux.get(i).emTrinca && !espadas && qtd < 3) {
                        espadas = true;
                        qtd++;
                        index.add(i);
                    } else if (aux.get(i).getNaipe() == 2 && !aux.get(i).emTrinca && !paus && qtd < 3) {
                        paus = true;
                        qtd++;
                        index.add(i);
                    } else if (aux.get(i).getNaipe() == 3 && !aux.get(i).emTrinca && !copas && qtd < 3) {
                        copas = true;
                        qtd++;
                        index.add(i);
                    } else if (aux.get(i).getNaipe() == 4 && !aux.get(i).emTrinca && !ouro && qtd < 3) {
                        ouro = true;
                        qtd++;
                        index.add(i);
                    }
                }

                if (qtd == 3) {
                    for (Integer i : index) {
                        aux.get(i).setEmTrinca(true);
                    }
                }
            }
        }

    }

    public void checaValorIgual() {
        LinkedList<Carta> Zero = new LinkedList<Carta>();
        LinkedList<Carta> Um = new LinkedList<Carta>();
        LinkedList<Carta> Dois = new LinkedList<Carta>();
        LinkedList<Carta> Tres = new LinkedList<Carta>();
        LinkedList<Carta> Quatro = new LinkedList<Carta>();
        LinkedList<Carta> Cinco = new LinkedList<Carta>();
        LinkedList<Carta> Seis = new LinkedList<Carta>();
        LinkedList<Carta> Sete = new LinkedList<Carta>();
        LinkedList<Carta> Oito = new LinkedList<Carta>();
        LinkedList<Carta> Nove = new LinkedList<Carta>();
        LinkedList<Carta> Dez = new LinkedList<Carta>();
        LinkedList<Carta> Onze = new LinkedList<Carta>();
        LinkedList<Carta> Doze = new LinkedList<Carta>();
        LinkedList<Carta> Treze = new LinkedList<Carta>();

        // Separa as cartas agrupando pelo valor dela pra depois verificar se existem naipes diferentes
        for (Carta c : mao) {
            switch (c.getValor()) {
                case 1:
                    Um.add(c);
                    break;
                case 2:
                    Dois.add(c);
                    break;
                case 3:
                    Tres.add(c);
                    break;
                case 4:
                    Quatro.add(c);
                    break;
                case 5:
                    Cinco.add(c);
                    break;
                case 6:
                    Seis.add(c);
                    break;
                case 7:
                    Sete.add(c);
                    break;
                case 8:
                    Oito.add(c);
                    break;
                case 9:
                    Nove.add(c);
                    break;
                case 10:
                    Dez.add(c);
                    break;
                case 11:
                    Onze.add(c);
                    break;
                case 12:
                    Doze.add(c);
                    break;
                default:
                    Treze.add(c);
                    break;

            }
        }

//        checaValorIgual(Zero);
        checaValorIgual(Um);
        checaValorIgual(Dois);
        checaValorIgual(Tres);
        checaValorIgual(Quatro);
        checaValorIgual(Cinco);
        checaValorIgual(Seis);
        checaValorIgual(Sete);
        checaValorIgual(Oito);
        checaValorIgual(Nove);
        checaValorIgual(Dez);
        checaValorIgual(Onze);
        checaValorIgual(Doze);
        checaValorIgual(Treze);

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
            Carta card;
            @Override
            public void action() {
                try {
                    //recebe uma carta do juiz 
                    ACLMessage msg = blockingReceive();
                    if (msg != null) {
//                        System.out.println(msg.getProtocol());
                        if (msg.getProtocol().equalsIgnoreCase("anuncia_fim")) {
                            String resp = msg.getContent();
                            jogoTerminou = true;
                            vencedor = resp;
                        } else {
                            ACLMessage reply = msg.createReply();
                            card = (Carta) msg.getContentObject();
//                        System.out.println(myAgent.getAID().getLocalName());
                            System.out.println(myAgent.getAID().getLocalName() + " Carta Recebida " + card.toString());
                            mao.add(card);
                            checaTrincas();
                            System.out.println(myAgent.getAID().getLocalName() + " Qtd trincas: " + qtdTrinca);
                            //checa quantidade de trincas
                            if (qtdTrinca >= 3) {
                                //manda solicitação de vitoria
                                reply.setPerformative(ACLMessage.INFORM);
                                reply.setProtocol("pede_vitoria");
                                reply.setContentObject(mao);
                                myAgent.send(reply);
//                            msg = blockingReceive();
//                            if (msg != null) {
//                                String resp = msg.getContent();
//                                
//                                if (msg.getProtocol().equals("anuncia_fim")) {
//                                    jogoTerminou = true;
//                                   
//                                }
//                            }
                            } else {
                                // devolver a carta menos importante 
                                reply.setPerformative(ACLMessage.INFORM);
                                reply.setProtocol("faz_jogada");
                                card = mao.remove(cartamenosImp());
                                System.out.println(myAgent.getAID().getLocalName() + " Carta enviada" + card.toString());
                                card.setEmDupla(false);
                                card.setEmTrinca(false);
                                reply.setContentObject(card);
                                myAgent.send(reply);

                            }
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
                return jogoTerminou;
            }

            @Override
            public int onEnd() {
                System.out.println(vencedor + " Venceu ");

//                for (Carta carta : mao) {
//                    System.out.println(carta.toString());
//                }
                return 0;
            }
        }
        );

    }

}

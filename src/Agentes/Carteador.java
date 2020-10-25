package Agentes;

import comportamentos.DistribuirCartas;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;

import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.util.leap.ArrayList;
import java.util.ArrayDeque;

import java.util.Collections;
import java.util.Deque;
import java.util.List;
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
public class Carteador extends Agent {

    private Deque<Carta> baralho = new ArrayDeque<Carta>();
    private Deque<Jogador> listJogadores;

    public Carteador(Deque<Jogador> listJogadores) {
        this.listJogadores = listJogadores;
    }
    
    
    public void geraCartas() {

        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 14; j++) {
                baralho.add(new Carta(j, i));
            }
        }
        Collections.shuffle((List) baralho);
    }
    
    
    
    protected void setup() {
        geraCartas();
        addBehaviour(new DistribuirCartas(listJogadores, baralho, this));
    }

    protected void registraServico(ServiceDescription sd) {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);

        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import javax.swing.JFrame;

/**
 *
 * @author Win10
 */
public class TelaJogo extends JFrame {
    
    public void TelaJogo(){
        setSize(1200,900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new TelaJogo();
    }
    
}

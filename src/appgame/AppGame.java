/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package appgame;

import javax.swing.JFrame;

/**
 *
 * @author Admin
 */
public class AppGame {
    public static void main(String[] args) {
            JFrame window = new JFrame();
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);
            window.setTitle("Phieu Luu Cung Cu An");
            
            GamePanel gamePanel = new GamePanel();
            window.add(gamePanel);
            
            window.pack();
            
            window.setLocationRelativeTo(null);
            window.setVisible(true);
           //gọi setup obj thông qua thằng gamePanel thằng gamePanel thì gọi sang thằng AssetSetter
            gamePanel.setupGame(); 
            gamePanel.StartGameThread();
    }
    
}
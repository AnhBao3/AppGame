/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package appgame;

import com.sun.tools.javac.Main;

import javax.swing.*;

/**
 *
 * @author Admin
 */
public class AppGame {
        public static JFrame window;
    public static void main(String[] args) {

            window = new JFrame();
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);
            window.setTitle("Phieu Luu Cung Ku An");
            GamePanel gamePanel = new GamePanel();
            window.add(gamePanel);
            gamePanel.config.loadconfig();
            if(gamePanel.fullScreenOn==true){
                    window.setUndecorated(true);
            }
            
            window.pack();
            
            window.setLocationRelativeTo(null);
            window.setVisible(true);
           //gọi setup obj thông qua thằng gamePanel thằng gamePanel thì gọi sang thằng AssetSetter
            gamePanel.setupGame(); 
            gamePanel.StartGameThread();
    }
}

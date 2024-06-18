/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import appgame.GamePanel;
import entity.Entity;

import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Admin
 */
public class OBJ_Boots extends Entity {
            GamePanel gp;
    public OBJ_Boots(GamePanel gp){
        super(gp);
        name = "Boots";
        down1 = setup("/res/objects/boots",gp.tileSize,gp.tileSize);
    }
}

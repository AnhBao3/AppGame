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
public class OBJ_Heart extends Entity {

    GamePanel gp;

    public OBJ_Heart(GamePanel gp) {
        super(gp);
        name = "Heart";
        image = setup("/res/objects/full_heart",gp.tileSize,gp.tileSize);
        image2 = setup("/res/objects/half_heart",gp.tileSize,gp.tileSize);
        image3 = setup("/res/objects/blank_heart",gp.tileSize,gp.tileSize);
    }

}

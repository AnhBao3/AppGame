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
public class OBJ_Door extends Entity {
        GamePanel gp;
    public static final String objName = "Door";

    public OBJ_Door(GamePanel gp){
       super(gp);
       this.gp = gp;
       type = type_obstacle;
       name = objName;
       down1 = setup("/res/objects/door",gp.tileSize,gp.tileSize);
       collision =true;

       solidArea.x =0;
       solidArea.y =16;
       solidArea.width =48;
       solidArea.height =32;
       solidAreaDefaultX = solidArea.x;
       solidAreaDefaultY = solidArea.y;
   }
   public void interact(){
       gp.gameState = gp.dialogueState;
       gp.ui.currentDialogue = "Cần chìa khóa";
   }
}

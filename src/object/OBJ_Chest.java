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
public class OBJ_Chest extends Entity {
    GamePanel gp;
    public static final String objName = "Chest";

    public OBJ_Chest(GamePanel gp){
        super(gp);
        this.gp = gp;
        type = type_obstacle;
        image = setup("/res/objects/chest",gp.tileSize,gp.tileSize);
        image2 = setup("/res/objects/chest_opened",gp.tileSize,gp.tileSize);
        name = objName;
        down1 = image;
        collision = true;

        solidArea.x =4;
        solidArea.y =16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }
    public void setLoot(Entity loot){
        this.loot = loot;
    }
    public void interact( ){
        gp.gameState = gp.dialogueState;
        if(opened==false){
            gp.playSE(3);
            StringBuilder sb = new StringBuilder();
            sb.append("Mở rương thành công !");
            if(gp.player.canObtainItem(loot)== false){
                sb.append("\nKhông thể nhận thêm được nữa");
            }
            else {
                sb.append("\nLoot được "+loot.name +"!");
                down1 = image2;
                opened = true;
            }
            gp.ui.currentDialogue = sb.toString();
        }
        else {
            gp.ui.currentDialogue = "Rỗng";
        }
    }
}

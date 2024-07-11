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
public class OBJ_Key extends Entity {
    GamePanel gp;
    public OBJ_Key(GamePanel gp){
        super(gp);
        this.gp = gp;
        name = "Chìa khóa";
        down1 = setup("/res/objects/key",gp.tileSize,gp.tileSize);
        description ="["+ name + "]\ndùng để mở cửa";
        price = 50;
        type = type_consumable;
        stackable = true;
    }
    public boolean use(Entity entity) {
        gp.gameState = gp.dialogueState;

        int objIndex = getDetected(entity, gp.obj, "Door");
        if(objIndex !=999){
            gp.ui.currentDialogue = "Dùng "+name+" để mở cửa";
            gp.playSE(3);
            gp.obj[gp.currentMap][objIndex] = null;
            return true;
        }
        else {
            gp.ui.currentDialogue = "Không tìm thấy";
            return  false;
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import appgame.GamePanel;
import entity.Entity;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;

/**
 *
 * @author Admin
 */
public class OBJ_Boots extends Entity {
    GamePanel gp;
    Timer timer;
    TimerTask task;
    int remainingTime;
    public static final String objName = "Boots";

    public OBJ_Boots(GamePanel gp){
        super(gp);
        this.gp = gp;
        type = type_consumable;
        name =objName;
        value =5;
        down1 = setup("/res/objects/wing_boot",gp.tileSize,gp.tileSize);
    }
    public boolean use(Entity e) {
        gp.playSE(1);
        gp.ui.addMessage("+ " + value + " Tốc độ");
        int tempSpeed = gp.player.speed;
        gp.player.speed += value;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gp.player.speed = tempSpeed;
                gp.ui.addMessage("Hết hiệu lực");
            }
        }, 30000);
        return true;
    }
}

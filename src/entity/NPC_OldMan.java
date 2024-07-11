/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import appgame.GamePanel;

import java.awt.*;
import java.util.Random;

/**
 *
 * @author Admin
 */
public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 1;// tốc độ
        solidArea = new Rectangle();
        solidArea.x =9;
        solidArea.y =16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        getImage();
        setDialogue(); //test
    }

    public void getImage() {
        //set hình ảnh người chơi
        up1 = setup("/res/npc/oldman_up_1",gp.tileSize,gp.tileSize);
        up2 = setup("/res/npc/oldman_up_2",gp.tileSize,gp.tileSize);
        down1 = setup("/res/npc/oldman_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/res/npc/oldman_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("/res/npc/oldman_left_1",gp.tileSize,gp.tileSize);
        left2 = setup("/res/npc/oldman_left_2",gp.tileSize,gp.tileSize);
        right1 = setup("/res/npc/oldman_right_1",gp.tileSize,gp.tileSize);
        right2 = setup("/res/npc/oldman_right_2",gp.tileSize,gp.tileSize);
    }
    public void setAction() {
        if(onPath ==true){

//            int goalCol = 12;
//            int goalRow = 9;
            int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
            searchPath(goalCol,goalRow);
        }
        else {
            actionLockCounter++;

            if (actionLockCounter == 120) {
                Random random = new Random();
                int a = 10;
                int i = random.nextInt(200) + 1; // chọn những con só ngầu nhên từ 1 - 100
                if (i <= 25-a) {
                    direction = "up";
                }
                if (i > 25-a && i <= 50-a) {
                    direction = "down";
                }
                if (i > 50-a && i <= 75-a) {
                    direction = "left";
                }
                if (i > 75-a && i <= 100-a) {
                    direction = "right";
                }
                actionLockCounter = 0;
                //mỗi 120 khung hình thì nó mới đổi hướng duy chuyển của NPC
            }
        }
    }
    public void searchPath(){

    }
    public void setDialogue() {
        //mãng chứa thông báo
        dialogues[0] = "Em Chao co";
        dialogues[1] = "demo2";
        dialogues[2] = "demo3";
        dialogues[3] = "demo4";

    }

    public void speak() {
            super.speak();
           onPath =true;
    }
}

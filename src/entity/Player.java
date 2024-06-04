/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import appgame.GamePanel;
import appgame.KeyHandler;
import appgame.UtilityTool;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Admin
 */
public class Player extends Entity {

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        //set hitbox cho người chơi
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
        
        //trạng thái người chơi
        maxLife = 6;
        life = maxLife;
    }

    public void getPlayerImage() {
        //set hình ảnh người chơi
        up1 = setup("/res/player/boy_up_1");
        up2 = setup("/res/player/boy_up_2");
        down1 = setup("/res/player/boy_down_1");
        down2 = setup("/res/player/boy_down_2");
        left1 = setup("/res/player/boy_left_1");
        left2 = setup("/res/player/boy_left_2");
        right1 = setup("/res/player/boy_right_1");
        right2 = setup("/res/player/boy_right_2");
    }

    public void update() {
        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
            if (keyH.upPressed == true) {
                direction = "up";
            } else if (keyH.downPressed == true) {
                direction = "down";
            } else if (keyH.leftPressed == true) {
                direction = "left";
            } else if (keyH.rightPressed == true) {
                direction = "right";
            }
            //check va chạm
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // check va cham với obj
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //check npc 
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);

            interactNPC(npcIndex);

            //check event
            gp.eHander.checkEvent();
            gp.keyH.enterPressed = false;
            if (collisionOn == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed; // nếu ấn W thì sẽ trừ tọa độ x

                        break;
                    case "down":
                        worldY += speed; // nếu nhấn s thì sẽ cộng tọa độ Y

                        break;
                    case "left":
                        worldX -= speed;

                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        // trong java X sẽ là đại diện cho đi vào bên phải, và Y là đi xuống

    }

    public void draw(Graphics g2) {
        //g2.setColor(Color.white);
        //g2.fillRect(x,y,gp.tileSize,gp.tileSize);

        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, null);
    }

    public void pickUpObject(int i) {
        ///999 là ko đụng phải obj
        if (i != 999) {
        }
    }

    private void interactNPC(int i) {
        if (i != 999) {
            if (gp.keyH.enterPressed == true) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
    }

}

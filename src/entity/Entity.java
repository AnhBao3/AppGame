/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import appgame.GamePanel;
import appgame.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Admin
 */
public abstract class Entity {

    GamePanel gp;
    public int worldX, worldY;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public String direction = "down";
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 49);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    String dialogues[] = new String[20]; //số lượng dialogue có thể có
    int dialogueIndex = 0;
    public BufferedImage image,image2,image3;
    public boolean invincible = false;
    public int invincibleCounter =0;
    public boolean collision = false;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    int dyingCounter = 0;
    boolean hpBarOn = false;
    int hpBarCounter;
    public int shotAvailableCounter =0;

    //trạng thái người chơi
    public int maxLife;
    public int life;
    public int speed;
    public String name;
    public int maxMana;
    public int mana;
    public int ammo;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentSheld;
    public String description ="";
    public Projectile projectile;

    //thuoc tinh cua do vat
    public int value;
    public int attackValue;
    public int defenderValue;
    public int useCost;

    //type
    public int type;
    public final int type_player = 0;
    public final int type_npc =1;
    public final int type_monster =2;
    public final int type_sword =3;
    public final int type_axe =4;
    public final int type_shield =5;
    public final int type_consumable =6;
    public final int type_pickupOnly =7;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public BufferedImage setup(String imagePath,int width,int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    public void checkDrop(){}
    public void dropIem(Entity droppedItem){
        for(int i=0;i<gp.obj.length;i++){
            if(gp.obj[i] == null){
                gp.obj[i] = droppedItem;
                gp.obj[i].worldX = worldX; // noi quai vat chet
                gp.obj[i].worldY = worldY;
                break;
            }
        }
    }
    public void damageReaction(){}
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            switch (direction) {
                case "up":
                    if (spriteNum == 1) {image = up1;}
                    if (spriteNum == 2) {image = up2;}
                    break;
                case "down":
                    if (spriteNum == 1) {image = down1;}
                    if (spriteNum == 2) {image = down2;}
                    break;
                case "left":
                    if (spriteNum == 1) {image = left1;}
                    if (spriteNum == 2) {image = left2;}
                    break;
                case "right":
                    if (spriteNum == 1) {image = right1;}
                    if (spriteNum == 2) {image = right2;}
                    break;
            }

            //thanh mau monster
            if(type ==2 && hpBarOn == true){
                double oneScale = (double)gp.tileSize/maxLife; //do dai cua thanh mau vd: mau toi da cua quai la 2 thi no se co 48 pixel va chia mau = 2 se duoc 2 cuc mau
                double hpBarValue = oneScale*life;
                if(hpBarValue <0){
                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12);
                }
                else {
                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12);

                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);
                    hpBarCounter++;
                    if (hpBarCounter > 600) {
                        hpBarCounter = 0;
                        hpBarOn = false;
                    }
                }
            }

            if (invincible == true) {
                hpBarOn = true;
                hpBarCounter = 0;
                changAlpha(g2,0.4f);
            }
            if(dying==true){
                dyingAnimation(g2);
            }
            g2.drawImage(image, screenX, screenY, null);
            changAlpha(g2,1f);
        }
    }

    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        int i =5;
        if(dyingCounter<=i){changAlpha(g2,0f);}
        if(dyingCounter>i && dyingCounter <=i*2){changAlpha(g2,1f);}
        if(dyingCounter>i*2 && dyingCounter <=i*3){changAlpha(g2,0f);}
        if(dyingCounter>i*3 && dyingCounter <=i*4){changAlpha(g2,1f);}
        if(dyingCounter>i*4 && dyingCounter <=i*5){changAlpha(g2,0f);}
        if(dyingCounter>i*5 && dyingCounter <=i*6){changAlpha(g2,1f);}
        if(dyingCounter>i*6 && dyingCounter <=i*7){changAlpha(g2,0f);}
        if(dyingCounter>i*7 && dyingCounter <=i*8){changAlpha(g2,1f);}
        if(dyingCounter>i*8){alive =false;}
    }
    public void changAlpha(Graphics2D g2, float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
    public void setAction() {
        // kế thừa trong npc oldman
    }

    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }

    public void update() {
        setAction();
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this,gp.npc);
        gp.cChecker.checkEntity(this,gp.monster);
        gp.cChecker.checkEntity(this,gp.iTile);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        if(this.type ==type_monster && contactPlayer==true) {
            //check neu monster tan cong nguoi choi
            damagePlayer(attack);
        }
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
        if (spriteCounter > 35) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }
    }
    public void damagePlayer(int attack){
        if(gp.player.invincible ==false){
            //gay dame
            gp.playSE(7);
            int damage = attack -gp.player.defense;
            if(damage <0){
                damage = 0;
            }
            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }
    public void use(Entity entity){}
}

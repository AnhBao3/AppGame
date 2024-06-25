/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import appgame.GamePanel;
import appgame.KeyHandler;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;
import java.util.ArrayList;

/**
 * @author Admin
 */
public class Player extends Entity {

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public boolean attackCanceled = false;
    public ArrayList<Entity> inventory = new ArrayList<Entity>();
    public final int maxInventorySize = 20;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
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

//        attackArea.width = 36;
//        attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";

        //trạng thái người chơi
        level =1;
        maxLife = 6;
        life = maxLife;
        strength = 1;
        exp = 0;
        ammo = 10;
        dexterity = 1;
        nextLevelExp = 5;
        coin = 0;
        maxMana = 4;
        mana = maxMana;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentSheld = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_Egg(gp);
        //projectile = new OBJ_Slime(gp);

        attack = getAttack();
        defense = getDefense();
    }
    public void setItems(){
        inventory.add(currentWeapon);
        inventory.add(currentSheld);
        inventory.add(new OBJ_Key(gp));
    }
    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }
    public int getDefense(){
        return defense = dexterity * currentSheld.defenderValue;
    }
    public void getPlayerImage() {
        //set hình ảnh người chơi
        up1 = setup("/res/player/boy_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/res/player/boy_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/res/player/boy_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/res/player/boy_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/res/player/boy_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/res/player/boy_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/res/player/boy_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/res/player/boy_right_2", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage() {
        if(currentWeapon.type == type_sword){
            attackUp1 = setup("/res/player/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/res/player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/res/player/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/res/player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/res/player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("/res/player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/res/player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/res/player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
        }
      if(currentWeapon.type == type_axe){
          attackUp1 = setup("/res/player/boy_attack_axe_up_1", gp.tileSize, gp.tileSize * 2);
          attackUp2 = setup("/res/player/boy_attack_axe_up_2", gp.tileSize, gp.tileSize * 2);
          attackDown1 = setup("/res/player/boy_attack_axe_down_1", gp.tileSize, gp.tileSize * 2);
          attackDown2 = setup("/res/player/boy_attack_axe_down_2", gp.tileSize, gp.tileSize * 2);
          attackLeft1 = setup("/res/player/boy_attack_axe_left_1", gp.tileSize * 2, gp.tileSize);
          attackLeft2 = setup("/res/player/boy_attack_axe_left_2", gp.tileSize * 2, gp.tileSize);
          attackRight1 = setup("/res/player/boy_attack_axe_right_1", gp.tileSize * 2, gp.tileSize);
          attackRight2 = setup("/res/player/boy_attack_axe_right_2", gp.tileSize * 2, gp.tileSize);
      }
    }

    public void update() {
        if (attacking == true) {
            attacking();
        }
        else if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {
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
            //check monsster
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);
            //check interactive tile
            int iTileIndex = gp.cChecker.checkEntity(this,gp.iTile);
            //check event
            gp.eHander.checkEvent();
            if (collisionOn == false && keyH.enterPressed == false) {
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
            if(keyH.enterPressed == true && attackCanceled == false ){
                gp.playSE(8);
                attacking = true;
                spriteCounter =0;
            }
            attackCanceled = false;
            gp.keyH.enterPressed = false;
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
        //dùng bien tam de Counter mat mau nhanh khi dung vao monster
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(gp.keyH.shotKeyPressed == true && projectile.alive == false && shotAvailableCounter ==30 && projectile.haveResource(this)==true){
            //set default
            projectile.set(worldX,worldY,direction,true,this);
            //them vao danh sach

            //
            projectile.subtractResource(this);
            gp.projectileList.add(projectile);

            shotAvailableCounter=0;
            
            gp.playSE(12);
        }
        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }
        if(life>maxLife){
            life=maxLife;
        }
        if(mana>maxMana){
            mana=maxMana;
        }
        // trong java X sẽ là đại diện cho đi vào bên phải, và Y là đi xuống

    }


    public void attacking() {
        spriteCounter++;
        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;
            //luu lai vi tri worldx, world y, solid area
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;
            // dieu chinh vi tri cua nguoi choi truoc khi thay doi
            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }
            //attackArea tro thanh solidArea cua nguoi choi
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            //check va cham voi quai vat sau khi up date vi tri
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex,attack);

            int iTileIndex = gp.cChecker.checkEntity(this,gp.iTile);
            damageInteractiveTile(iTileIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    public void damageInteractiveTile(int i){
        if(i!=999 && gp.iTile[i].destructible == true
                && gp.iTile[i].isCorrectItem(this)==true && gp.iTile[i].invincible ==false ){
            gp.iTile[i].playSE();
            gp.iTile[i].life--;
            gp.iTile[i].invincible = true;
            if(gp.iTile[i].life ==0){
                gp.iTile[i] = gp.iTile[i].getDestroyedFrom();
            }
        }
    }
    public void damageMonster(int i, int attack) {
        if (i != 999) {
            if(gp.monster[i].invincible == false){
                //dame gay len monster
                gp.playSE(6);

                int damage = attack -gp.monster[i].defense;
                if(damage <0){
                    damage = 0;
                }
                gp.monster[i].life -=damage;
                gp.ui.addMessage(damage + " Sát thương!");
                gp.monster[i].invincible =true;
                gp.monster[i].damageReaction();
                if(gp.monster[i].life <=0){
                    gp.monster[i].dying =true;
                    gp.ui.addMessage("Hạ gục " + gp.monster[i].name+"!");
                    gp.ui.addMessage("Kinh nghiệm +" + gp.monster[i].exp);
                    exp += gp.monster[i].exp;
                    checkLevelUp();
                }
            }
        }
    }
    public void selectItem(){
        int itemIndex = gp.ui.getItemIndexOnSlot();

        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);

            if(selectedItem.type == type_sword || selectedItem.type == type_axe){
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if(selectedItem.type == type_shield){
                currentSheld = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_consumable){
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }
    public void checkLevelUp() {
        if(exp>= nextLevelExp){
            level++;
            nextLevelExp = nextLevelExp*2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
            gp.playSE(10);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "Lên cấp "+ level;
        }
    }
    private void contactMonster(int i) {
        if (i != 999) {
            if (invincible == false && gp.monster[i].dying == false) {
                gp.playSE(7);
                int damage = gp.monster[i].attack -defense;
                if(damage <0){
                    damage = 0;
                }
                life -= damage;
                invincible = true;
            }
        }
    }
    public void draw(Graphics2D g2) {
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (attacking == false) {
                    if (spriteNum == 1) {image = up1;}
                    if (spriteNum == 2) {image = up2;}
                }
                if (attacking == true) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {image = attackUp1;}
                    if (spriteNum == 2) {image = attackUp2;}
                }
                break;
            case "down":
                if (attacking == false) {
                    if (spriteNum == 1) {image = down1;}
                    if (spriteNum == 2) {image = down2;}
                }
                if (attacking == true) {
                    if (spriteNum == 1) {image = attackDown1;}
                    if (spriteNum == 2) {image = attackDown2;}
                }
                break;
            case "left":
                if (attacking == false) {
                    if (spriteNum == 1) {image = left1;}
                    if (spriteNum == 2) {image = left2;}
                }
                if (attacking == true) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {image = attackLeft1;}
                    if (spriteNum == 2) {image = attackLeft2;}
                }
                break;
            case "right":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                }
                if (attacking == true) {
                    if (spriteNum == 1) {
                        image = attackRight1;
                    }
                    if (spriteNum == 2) {
                        image = attackRight2;
                    }
                }
                break;
        }
        if (invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
    public void pickUpObject(int i) {
        ///999 là ko đụng phải obj
        if (i != 999) {
            //pick coin
            if(gp.obj[i].type == type_pickupOnly){
                gp.obj[i].use(this);
                gp.obj[i] = null;
            }
            //pick va add vao inven
            else {
                String text;
                if(inventory.size() != maxInventorySize){
                    inventory.add(gp.obj[i]); // them vao inventory!
                    gp.playSE(1);
                    text = "Nhận " + gp.obj[i].name + "!";
                }
                else {
                    text = "Full đồ !";
                }
                gp.ui.addMessage(text);
                gp.obj[i] = null;
            }
        }
    }
    private void interactNPC(int i) {
        if (gp.keyH.enterPressed == true) {
            if (i != 999) {
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
    }
}

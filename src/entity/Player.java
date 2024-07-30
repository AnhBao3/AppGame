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
    int standCounter = 0;
    public boolean attackCanceled = false;
    public final int maxInventorySize = 20;
    public boolean lightUpdated = false;
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
    }
    public void setDefaultValues() {
//        worldX = gp.tileSize * 23;
//        worldY = gp.tileSize * 21;
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        gp.currentMap = 0;
        defaultSpeed = 4;
        speed = defaultSpeed;
        direction = "down";


        //trạng thái người chơi
        level = 1;
        maxLife = 10;
        life = maxLife;
        strength = 2;
        exp = 0;
        ammo = 10;
        dexterity = 1;
        nextLevelExp = 5;
        coin = 100;
        maxMana = 4;
        mana = maxMana;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentSheld = new OBJ_Shield_Wood(gp);
        currentLight = null;
        projectile = new OBJ_Egg(gp);
        //projectile = new OBJ_Slime(gp);

        attack = getAttack();
        defense = getDefense();

        getImage();
        getAttackImage();
        setItems();
        getGuardImage();
    }
    public void setDefaultPostions(){
        if(gp.dungeonOn==true){
            gp.currentMap =2;
            worldX = gp.tileSize * 9;
            worldY = gp.tileSize * 40;
            direction = "down";
        }
        else if(gp.dungeonOn==false){
            gp.currentMap =0;
            worldX = gp.tileSize * 23;
            worldY = gp.tileSize * 21;
            direction = "down";
        }

    }
    public void restoreStatus(){
        life = maxLife;
        mana = maxMana;
        speed = defaultSpeed;
        invincible = false;
        transparent = false;
        attacking = false;
        guarding = false;
        knockBack = false;
        lightUpdated = true;
    }
    public void setItems(){
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentSheld);
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Lantern(gp));
    }
    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        motion1_duration = currentWeapon.motion1_duration;
        motion2_duration = currentWeapon.motion2_duration;
        return attack = strength * currentWeapon.attackValue;
    }
    public int getDefense(){
        return defense = dexterity * currentSheld.defenderValue;
    }
    public int getCurrentWeaponSlot(){
        int currentWeaponSlot =0;
        for(int i=0;i<inventory.size();i++){
            if(inventory.get(i)==currentWeapon){
                currentWeaponSlot = i;
            }
        }
        return currentWeaponSlot;
    }
    public int getCurrentSheldSlot(){
        int currentSheldSlot =0;
        for(int i=0;i<inventory.size();i++){
            if(inventory.get(i)==currentSheld){
                currentSheldSlot = i;
            }
        }
        return currentSheldSlot;
    }
    public void getImage() {
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
    public void getSleepingImage(BufferedImage image){
        up1 = image;
        up2 = image;
        down1 = image;
        down2 = image;
        left1 = image;
        left2 = image;
        right1 = image;
        right2 = image;
    }
    public void getAttackImage() {
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
        if(currentWeapon.type == type_pickaxe){
            attackUp1 = setup("/res/player/boy_pick_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/res/player/boy_pick_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/res/player/boy_pick_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/res/player/boy_pick_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/res/player/boy_pick_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("/res/player/boy_pick_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/res/player/boy_pick_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/res/player/boy_pick_right_2", gp.tileSize * 2, gp.tileSize);
        }
    }
    public void getGuardImage(){
        guardUp = setup("/res/player/boy_guard_up", gp.tileSize, gp.tileSize);
        guardDown = setup("/res/player/boy_guard_down", gp.tileSize, gp.tileSize);
        guardLeft = setup("/res/player/boy_guard_left", gp.tileSize, gp.tileSize);
        guardRight = setup("/res/player/boy_guard_right", gp.tileSize, gp.tileSize);

    }
    public void update() {
        if(knockBack == true){
            collisionOn = false;
            gp.cChecker.checkTile(this);
            gp.cChecker.checkObject(this, true);
            gp.cChecker.checkEntity(this, gp.npc);
            gp.cChecker.checkEntity(this, gp.monster);
            gp.cChecker.checkEntity(this,gp.iTile);
            if(collisionOn == true){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
            else if(collisionOn == false){
                switch (knockBackDirection) {
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
            knockBackCounter++;
            if(knockBackCounter==10){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        }
        else if (attacking == true) {
            attacking();
        }
        else if(keyH.spacePressed == true){
            guarding = true;
            guardCounter++;
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
            if(keyH.godModeOn == false ) {
                gp.cChecker.checkTile(this);
            }

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
            gp.cChecker.checkEntity(this,gp.iTile);
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
            guarding = false;
            guardCounter = 0;

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
        else {
            standCounter++;
            if(standCounter ==20){
                spriteNum =1;
                standCounter =0;
            }
            guarding =false;
            guardCounter =0;
        }
        //dùng bien tam de Counter mat mau nhanh khi dung vao monster
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                transparent = false;
                invincibleCounter = 0;
            }
        }
        if(gp.keyH.shotKeyPressed == true && projectile.alive == false && shotAvailableCounter ==30 && projectile.haveResource(this)==true){
            //set default
            projectile.set(worldX,worldY,direction,true,this);
            //them vao danh sach

            //
            projectile.subtractResource(this);

            for(int i=0;i<gp.projectile[1].length;i++){
                if(gp.projectile[gp.currentMap][i]==null){
                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }
            }
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
        if(keyH.godModeOn == false ){
            if(life<=0){
                gp.gameState = gp.gameOverState;
                gp.ui.commandNum =-1;
                gp.playSE(14);
                gp.stopMusic();
            }
        }
        // trong java X sẽ là đại diện cho đi vào bên phải, và Y là đi xuống

    }
    public void damageProjectile(int i) {
        if(i!=999){
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile,projectile);
        }
    }
    public void damageInteractiveTile(int i){
        if(i!=999 && gp.iTile[gp.currentMap][i].destructible == true
                && gp.iTile[gp.currentMap][i].isCorrectItem(this)==true && gp.iTile[gp.currentMap][i].invincible ==false ){
            gp.iTile[gp.currentMap][i].playSE();
            gp.iTile[gp.currentMap][i].life--;
            gp.iTile[gp.currentMap][i].invincible = true;
            //generateParticle
            generateParticle(gp.iTile[gp.currentMap][i],gp.iTile[gp.currentMap][i]);
            if(gp.iTile[gp.currentMap][i].life ==0){
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedFrom();
            }
        }
    }
    public void damageMonster(int i,Entity attacker, int attack,int knockBackPower) {
        if (i != 999) {
            if(gp.monster[gp.currentMap][i].invincible == false){
                //dame gay len monster
                gp.playSE(6);
                if(knockBackPower>0){
                    setKnockBack(gp.monster[gp.currentMap][i],attacker, knockBackPower);
                }
                if(gp.monster[gp.currentMap][i].offBalance == true){
                    attack *= 5;
                }
                int damage = attack -gp.monster[gp.currentMap][i].defense;
                if(damage <0){
                    damage = 0;
                }
                gp.monster[gp.currentMap][i].life -=damage;
                gp.ui.addMessage(damage + " Sát thương!");
                gp.monster[gp.currentMap][i].invincible =true;
                gp.monster[gp.currentMap][i].damageReaction();
                if(gp.monster[gp.currentMap][i].life <=0){
                    gp.monster[gp.currentMap][i].dying =true;
                    gp.ui.addMessage("Hạ gục " + gp.monster[gp.currentMap][i].name+"!");
                    gp.ui.addMessage("Kinh nghiệm +" + gp.monster[gp.currentMap][i].exp);
                    exp += gp.monster[gp.currentMap][i].exp;
                    checkLevelUp();
                }
            }
        }
    }
    public void selectItem(){
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol,gp.ui.playerSlotRow);

        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);

            if(selectedItem.type == type_sword || selectedItem.type == type_axe || selectedItem.type == type_pickaxe){
                currentWeapon = selectedItem;
                attack = getAttack();
                getAttackImage();
            }
            if(selectedItem.type == type_shield){
                currentSheld = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_light){
                if(currentLight==selectedItem){
                    currentLight = null;
                }
                else {
                    currentLight = selectedItem;
                }
                lightUpdated = true;
            }
            if(selectedItem.type == type_consumable){
                if(selectedItem.use(this) == true){
                    if (selectedItem.amount > 1) {
                        selectedItem.amount--;
                    }
                    else {
                        inventory.remove(itemIndex);
                    }
                }
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
            if (invincible == false && gp.monster[gp.currentMap][i].dying == false) {
                gp.playSE(7);
                int damage = gp.monster[gp.currentMap][i].attack -defense;
                if(damage <1){
                    damage = 1;
                }
                life -= damage;
                invincible = true;
                transparent = true;
            }
        }
    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;
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
                if(guarding == true){
                    image =guardUp;
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
                if(guarding == true){
                    image =guardDown;
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
                if(guarding == true){
                    image =guardLeft;
                }
                break;
            case "right":
                if (attacking == false) {
                    if (spriteNum == 1) {image = right1;}
                    if (spriteNum == 2) {image = right2;}
                }
                if (attacking == true) {
                    if (spriteNum == 1) {image = attackRight1;}
                    if (spriteNum == 2) {image = attackRight2;}
                }
                if(guarding == true){
                    image =guardRight;
                }
                break;
        }
        if (transparent == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        if(drawing == true){
            g2.drawImage(image, tempScreenX, tempScreenY, null);
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
    public void pickUpObject(int i) {
        ///999 là ko đụng phải obj
        if (i != 999) {
            //pick coin
            if(gp.obj[gp.currentMap][i].type == type_pickupOnly){
                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i] = null;
            }
            //obstacle
            else if(gp.obj[gp.currentMap][i].type == type_obstacle){
                if(keyH.enterPressed==true){
                    attackCanceled = true;
                    gp.obj[gp.currentMap][i].interact();
                }
            }
            //pick va add vao inven
            else {
                String text;
                if(canObtainItem(gp.obj[gp.currentMap][i]) == true){
                    gp.playSE(1);
                    text = "Nhận " + gp.obj[gp.currentMap][i].name + "!";
                }
                else {
                    text = "Full đồ !";
                }
                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][i] = null;
            }
        }
    }
    public void interactNPC(int i) {
        if(i!=999){
            if(gp.keyH.enterPressed==true){
                attackCanceled =true;
                gp.obj[gp.currentMap][i].speak();
            }
            gp.npc[gp.currentMap][i].move(direction);
        }
    }
    public int searchItemInInventory(String itemName){
        int itemIndex = 999;
        for(int i=0;i<inventory.size();i++){
            if(inventory.get(i).name.equals(itemName)){
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }
    public boolean canObtainItem(Entity item){
        boolean canObtain = false;

        Entity newItem = gp.eGenerator.getObject(item.name);

        if(newItem.stackable==true){
            int index = searchItemInInventory(newItem.name);
            if(index!=999){
                inventory.get(index).amount++;
                canObtain = true;
            }
            else {
                if(inventory.size()!=maxInventorySize){
                    inventory.add(newItem);
                    canObtain = true;
                }
            }
        }
        else {
            if(inventory.size()!=maxInventorySize){
                inventory.add(newItem);
                canObtain = true;
            }
        }
        return canObtain;
    }
}

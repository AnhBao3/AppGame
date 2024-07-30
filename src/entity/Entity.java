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
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author Admin
 */
public abstract class Entity {

    GamePanel gp;
    public int worldX, worldY;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2, guardUp, guardDown, guardLeft,guardRight;
    public String direction = "down";
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 49);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    public String dialogues[] = new String[20]; //số lượng dialogue có thể có
    int dialogueIndex = 0;
    public BufferedImage image,image2,image3;
    public boolean invincible = false;
    public int invincibleCounter =0;
    public boolean collision = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    int dyingCounter = 0;
    public boolean hpBarOn = false;
    public int hpBarCounter;
    public int shotAvailableCounter =0;
    int knockBackCounter = 0;
    public Entity attacker;
    public String knockBackDirection;
    public boolean guarding = false;
    public int guardCounter = 0;
    int offBalanceCounter =0;
    public boolean offBalance = false;
    public Entity linkedEntity;
    public boolean boss;
    public boolean temp=false;
    public boolean drawing =true;

    //trạng thái người chơi
    public int maxLife;
    public int life;
    public int defaultSpeed;
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
    public Entity currentLight;
    public String description ="";
    public Projectile projectile;
    public boolean onPath = false;
    public boolean knockBack = false;
    public int motion1_duration;
    public int motion2_duration;
    public boolean transparent = false;
    public Entity loot;
    public boolean opened = false;
    public boolean inRage = false;
    public boolean sleep = false;

    //thuoc tinh cua do vat
    public ArrayList<Entity> inventory = new ArrayList<Entity>();
    public int value;
    public int attackValue;
    public int defenderValue;
    public int useCost;
    public int price;
    public int knockBackPower =0;
    public boolean stackable = false;
    public int amount = 1;
    public int lightRadius;

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
    public final int type_obstacle = 8;
    public final int type_light = 9;
    public final int type_pickaxe = 10;

    public int getLeftX(){
        return worldX+solidArea.x;
    }
    public int getRightX(){
        return worldX+solidArea.x + solidArea.width;
    }
    public int getTopY(){
        return worldY+solidArea.y;
    }
    public int getBottomY(){
        return worldY+solidArea.y+solidArea.height;
    }
    public int getCol(){
        return (worldX+solidArea.x)/gp.tileSize;
    }
    public int getRow(){
        return (worldY+solidArea.y)/gp.tileSize;
    }
    public int getCenterX(){
        int centerX = worldX + left1.getWidth()/2;
        return centerX;
    }
    public int getCenterY(){
        int centerY = worldY + up1.getHeight()/2;
        return centerY;
    }
    public int getXdistance(Entity target){
        int xDistance = Math.abs(getCenterX()-target.getCenterX());
        return xDistance;
    }
    public int getYdistance(Entity target){
        int yDistance = Math.abs(getCenterY()-target.getCenterY());
        return yDistance;
    }
    public int getScreenX(){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        return screenX;
    }
    public int getScreenY(){
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        return screenY;
    }
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
        for(int i=0;i<gp.obj[1].length;i++){
            if(gp.obj[gp.currentMap][i] == null){
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX; // noi quai vat chet
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }
    public void damageReaction(){}
    public boolean inCamera(){
        boolean inCamera = false;
        if (worldX + gp.tileSize*5 > gp.player.worldX - gp.player.screenX
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize*5 > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            inCamera = true;
        }
        return inCamera;
    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;


        if (inCamera()==true) {
            int tempScreenX = getScreenX();
            int tempScreenY = getScreenY();

            switch (direction) {
                case "up":
                    if (attacking == false) {
                        if (spriteNum == 1) {image = up1;}
                        if (spriteNum == 2) {image = up2;}
                    }
                    if (attacking == true) {
                        tempScreenY = getScreenY() - up1.getHeight();
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
                        tempScreenX = getScreenX() - left1.getWidth();
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
                hpBarOn = true;
                hpBarCounter = 0;
                changAlpha(g2,0.4f);
            }
            if(dying==true){
                dyingAnimation(g2);
            }
            g2.drawImage(image, tempScreenX, tempScreenY, null);
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
    public void setLoot(Entity loot){
    }
    public void resetCounter(){
             int spriteCounter = 0;
             int actionLockCounter = 0;
             int invincibleCounter =0;
            int dyingCounter = 0;
            int hpBarCounter;
            int shotAvailableCounter =0;
            int knockBackCounter = 0;
            int guardCounter = 0;
            int offBalanceCounter =0;
    }
    public void changAlpha(Graphics2D g2, float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
    public void setAction() {
        // kế thừa trong npc oldman
    }
    public void checkCollision(){
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
    public void interact(){}
    public void update() {
        if(sleep ==false){
            if(knockBack == true){
                checkCollision();

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
            else if(attacking==true){
                attacking();
            }
            else {
                setAction();
                checkCollision();
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
            if(offBalance == true){
                offBalanceCounter++;
                if(offBalanceCounter>60){
                    offBalance = false;
                    offBalanceCounter = 0;
                }
            }
        }

    }
    public Color getParticleColor(){
        Color color = null;
        return color;
    }
    public int getParticleSize(){
        int size = 0; //6 pixel
        return size;
    }
    public int getParticleSpeed(){
        int speed = 0;
        return speed;
    }
    public int getParticleMaxLife(){
        int maxLife =0;
        return maxLife;
    }
    public void generateParticle(Entity generator, Entity target){
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();
        Particle p1 = new Particle(gp,target,color,size,speed,maxLife,-2,-1);
        Particle p2 = new Particle(gp,target,color,size,speed,maxLife,2,-1);
        Particle p3 = new Particle(gp,target,color,size,speed,maxLife,-2,1);
        Particle p4 = new Particle(gp,target,color,size,speed,maxLife,2,1);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }
    public void attacking() {
        spriteCounter++;
        if (spriteCounter <= motion1_duration) {
            spriteNum = 1;
        }
        if (spriteCounter > motion1_duration && spriteCounter <= motion2_duration) {
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
            if(type == type_monster){
                if(gp.cChecker.checkPlayer(this)==true){
                    damagePlayer(attack);
                }
            }
            else { //player
                int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                gp.player.damageMonster(monsterIndex,this,attack,currentWeapon.knockBackPower);

                int iTileIndex = gp.cChecker.checkEntity(this,gp.iTile);
                gp.player.damageInteractiveTile(iTileIndex);

                int projecttileIndex = gp.cChecker.checkEntity(this,gp.projectile);
                gp.player.damageProjectile(projecttileIndex);
            }


            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if (spriteCounter > motion2_duration) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    public void damagePlayer(int attack) {
        if (gp.player.invincible == false) {
            // Calculate damage
            int damage = attack - gp.player.defense;

            // Check if the player is guarding and facing the opposite direction
            String canGuardDirection = getOppositeDirection(direction);
            if (gp.player.guarding == true && gp.player.direction.equals(canGuardDirection)) {
                if (gp.player.guardCounter < 10) {
                    damage = 0; // No damage if guarding successfully
                    gp.playSE(18);
                    setKnockBack(this, gp.player, knockBackPower);
                    offBalance = true;
                    spriteCounter = -60;
                } else {
                    damage = (attack - gp.player.defense) / 2; // Reduce damage when guarding but guardCounter is high
                    gp.playSE(17);
                }
            } else {
                gp.playSE(7);
                if (damage < 1) {
                    damage = 1; // Ensure a minimum of 1 damage if not guarding
                }
            }

            if (damage != 0) {
                gp.player.transparent = true;
                setKnockBack(gp.player, this, knockBackPower);
            }

            gp.player.life -= damage; // Subtract damage from player's life
            gp.player.invincible = true; // Make the player invincible for a short period
        }
    }

    public void checkAttackOrNot(int rate, int straight, int holizontal){
        boolean targetInRange =false;
        int xDis = getXdistance(gp.player);
        int yDis = getYdistance(gp.player);
        switch (direction){
            case"up":
                if(gp.player.getCenterY() < getCenterY() && yDis < straight && xDis < holizontal){
                    targetInRange = true;
                }
                break;
            case"down":
                if(gp.player.getCenterY() > getCenterY() && yDis < straight && xDis < holizontal){
                    targetInRange = true;
                }
                break;
            case"left":
                if(gp.player.getCenterX() < getCenterX() && xDis < straight && yDis < holizontal){
                    targetInRange = true;
                }
                break;
            case"right":
                if(gp.player.getCenterX() > getCenterX() && xDis < straight && yDis < holizontal){
                    targetInRange = true;
                }
                break;
        }
        if(targetInRange == true){
            int i = new Random().nextInt(rate);
            if(i==0){
                attacking = true;
                spriteNum = 1;
                spriteCounter = 0;
                shotAvailableCounter = 0;
            }
        }
    }
    public void setKnockBack(Entity target,Entity attacker, int knockBackPower){
        this.attacker = attacker;
        target.knockBackDirection=attacker.direction;
        target.speed+=knockBackPower;
        target.knockBack = true;
    }
    public boolean use(Entity entity){
        return  false;
    }
    public void searchPath(int goalCol, int goalRow){
        int startCol = (worldX+solidArea.x)/gp.tileSize;
        int startRow = (worldY+solidArea.y)/gp.tileSize;
        gp.pFinder.setNodes(startCol,startRow,goalCol,goalRow);
        if(gp.pFinder.search()==true){
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;
            //entity
            int enLeftX = worldX+solidArea.x;
            int enRightX = worldY+solidArea.x + solidArea.width;
            int enTopY = worldY+solidArea.y;
            int enBottomY = worldY+solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            }
            else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            }
            else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                if(enLeftX > nextX){
                    direction = "left";
                }
                if(enLeftX < nextX){
                    direction = "right";
                }
            }
            else if(enTopY > nextY && enLeftX > nextX ) {
                direction = "up";
                checkCollision();
                if(collisionOn == true) {
                    direction = "left";
                }
            }
            else if(enTopY > nextY && enLeftX < nextX ) {
                direction = "up";
                checkCollision();
                if(collisionOn == true) {
                    direction = "right";
                }
            }
            else if(enTopY< nextY && enLeftX > nextX ) {
                direction = "down";
                checkCollision();
                if(collisionOn == true) {
                    direction = "left";
                }
            }
            else if(enTopY > nextY && enLeftX < nextX ) {
                direction = "down";
                checkCollision();
                if(collisionOn == true) {
                    direction = "right";
                }
            }
//            int nextCol = gp.pFinder.pathList.get(0).col;
//            int nextRow = gp.pFinder.pathList.get(0).row;
//            if(nextCol == goalCol && nextRow == goalRow){
//                onPath = false;
//            }
        }
    }
    public int getDetected(Entity user, Entity target[][],String targetName){
        int index =999;
        int nextWorldX  = user.getLeftX();
        int nextWorldY  = user.getTopY();
        switch (user.direction) {
            case "up": nextWorldY = user.getTopY()-gp.player.speed; break;
            case "down": nextWorldY = user.getBottomY()+gp.player.speed; break;
            case "left": nextWorldX = user.getLeftX()-gp.player.speed; break;
            case "right": nextWorldX = user.getRightX()+gp.player.speed; break;
        }
        int col = nextWorldX/gp.tileSize;
        int row = nextWorldY/gp.tileSize;

        for(int i=0;i<target[1].length;i++){
            if(target[gp.currentMap][i] != null){
                if(target[gp.currentMap][i].getCol() == col &&
                target[gp.currentMap][i].getRow() == row &&
                target[gp.currentMap][i].name.equals(targetName)){
                   index =i;
                   break;
                }
            }
        }
        return index;
    }
    public int getTileDistance(Entity target){
        int tileDistance = (getXdistance(target)+getYdistance(target))/gp.tileSize;
        return tileDistance;
    }
    public int getGoalCol(Entity target){
        int goalCol = (target.worldX + gp.player.solidArea.x)/gp.tileSize;
        return goalCol;
    }
    public int getGoalRow(Entity target){
        int goalRow = (target.worldY + gp.player.solidArea.y)/gp.tileSize;
        return goalRow;
    }
    public void checkStopChasingOrNot(Entity target,int distance, int rate){
        if(getTileDistance(target)>distance){
            int i = new Random().nextInt(rate);
            if(i==0){
                onPath = true;
            }
        }
    }
    public void getRandomDirection(int interval){
        actionLockCounter++;

        if (actionLockCounter == interval) {
            Random random = new Random();
            int a = 10;
            int i = random.nextInt(200) + 1; // chọn những con só ngầu nhên từ 1 - 100
            if (i <= 25-a) {direction = "up";}
            if (i > 25-a && i <= 50-a) {direction = "down";}
            if (i > 50-a && i <= 75-a) {direction = "left";}
            if (i > 75-a && i <= 100-a) {direction = "right";}
            actionLockCounter = 0;
            //mỗi 120 khung hình thì nó mới đổi hướng duy chuyển của NPC
        }
    }
    public void checkStartChasingOrNot(Entity target,int distance, int rate){
        if(getTileDistance(target)<distance){
            int i = new Random().nextInt(rate);
            if(i==0){
                onPath = true;
            }
        }
    }
    public void checkShootOrNot(int rate, int shotInterval) {
        int i = new Random().nextInt(rate);
        if (i == 0 && projectile.alive == false && shotAvailableCounter >= shotInterval) {
            projectile.set(worldX, worldY, direction, true, this);
            for (int ii = 0; ii < gp.projectile[gp.currentMap].length; ii++) {
                if (gp.projectile[gp.currentMap][ii] == null) {
                    gp.projectile[gp.currentMap][ii] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
        }
    }
    public String getOppositeDirection(String direction){
        String oppositeDirection ="";
        switch (direction) {
            case "up":
                oppositeDirection = "down";
                break;
            case "down":
                oppositeDirection = "up";
                break;
            case "left":
                oppositeDirection = "right";
                break;
            case "right":
                oppositeDirection = "left";
                break;
        }
        return oppositeDirection;
    }
    public void move(String direction){}
    public void moveTowardPlayer(int interval){
        actionLockCounter++;
        if(actionLockCounter > interval){
            if(getXdistance(gp.player)>getYdistance(gp.player)){
                if(gp.player.getCenterX()<getCenterX()){
                    direction = "left";
                } else {
                    direction = "right";
                }
            }
            else if(getXdistance(gp.player)<getYdistance(gp.player)){
                if(gp.player.getCenterX()<getCenterX()){
                    direction = "up";
                } else {
                    direction = "down";
                }
            }
            actionLockCounter = 0;
        }
    }
}
package appgame;

import data.Progress;
import entity.Entity;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][][];
    int previousEventX, previousEventY;
    boolean canTouchEvent = true; //??t ra mot neu sk x?y ra r?i th? s? kh?ng x?y ra n?a
    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        int map =0;
        int col = 0;
        int row = 0;
        while(map < gp.maxMap && col <gp.maxWorldCol && row<gp.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y; //event nh? l? v? mu?n nh?n v?t ph?i ?i v?o trong tile ?? ?? k?ch ho?t nh?
            col++;
            if(col == gp.maxWorldCol) {
                col = 0;
                row++;
                if(row == gp.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
         }
    }
    // dung hit methods yeu c?u vi tri va tu the di la right
    public void checkEvent(){
        int xDistance = Math.abs(gp.player.worldX -previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance,yDistance);
        if(distance>gp.tileSize){
            canTouchEvent = true;
        }
        //set su kien
        if(canTouchEvent==true) {
            if (hit(0,27, 16, "right") == true) {damagePit(gp.dialogueState);}
            else if (hit(0,23, 12, "up") == true) {healingPool(gp.dialogueState);}
            else if (hit(0,10, 39, "any") == true) {teleport(1,12,13,gp.indoor);}
            else if (hit(1,12,13,  "any") == true) {teleport(0,10,39,gp.outside);}
            else if (hit(1,12,9,"up")==true) {speak(gp.npc[1][0]);}
            else if (hit(0,12,9,"any")==true) {teleport(2,9,41,gp.dungeon);}
            else if (hit(2,9,41,"any")==true) {teleport(0,12,9,gp.outside);}
            else if (hit(2,8,7,"any")==true) {teleport(3,26,41,gp.dungeon);}
            else if (hit(3,26,41,"any")==true) {teleport(2,8,7,gp.dungeon);}
            else if (hit(3,25,27,"any")==true) {skeletonLord();}


        }
    }
    //check event xung ??t
    public boolean hit(int map, int col,int row,String reqDirection){
        boolean hit =false;
        if(map == gp.currentMap){
            gp.player.solidArea.x =gp.player.worldX+gp.player.solidArea.x;
            gp.player.solidArea.y =gp.player.worldY+gp.player.solidArea.y;
            eventRect[map][col][row].x = col*gp.tileSize+eventRect[map][col][row].x;
            eventRect[map][col][row].y = row*gp.tileSize+eventRect[map][col][row].y;

            //n?u player va ch?m v?i event
            if(gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false){
                if(gp.player.direction.contentEquals(reqDirection)|| reqDirection.contentEquals("any")){
                    hit = true;
                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }
            //gi?ng v?i ?ung object
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        return hit;
    }
    public void damagePit(int gameState){
        gp.gameState= gameState;
        gp.ui.currentDialogue = "Fall";
        gp.player.life-=1; // con
        //eventRect[col][row].eventDone =true; // n?u ?? t??ng t?c roi thi s? kh?ng tu??ng tac n?a
        canTouchEvent = false;
    }
    public void healingPool(int gameState){
        if(gp.keyH.enterPressed == true){
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            gp.ui.currentDialogue = "Uống nước!";
            gp.player.life =gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.aSetter.setMonster();
            gp.saveLoad.save();
        }
        gp.keyH.enterPressed = false;
    }
    public void teleport(int map, int col, int row,int area){
        gp.gameState = gp.transitionState;
        gp.nextArea = area;
        tempMap = map;
        tempCol = col;
        tempRow = row;
        canTouchEvent = false;
        gp.playSE(13);
    }
    public void speak(Entity entity){
        if(gp.keyH.enterPressed==true){
            gp.gameState = gp.dialogueState;
            gp.player.attackCanceled = true;
            entity.speak();
        }
    }
    public void skeletonLord(){
        if(gp.bossBattleOn == false && Progress.skeletonLordDefeated == false){
            gp.gameState = gp.cutscreneState;
            gp.csManager.sceneNum = gp.csManager.skeletonLord;
        }
    }
}

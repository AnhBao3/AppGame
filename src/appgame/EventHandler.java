package appgame;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][];
    int previousEventX, previousEventY;
    boolean canTouchEvent = true; //??t ra mot neu sk x?y ra r?i th? s? kh?ng x?y ra n?a
    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while(col<gp.maxWorldCol && row<gp.maxWorldRow) {
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y; //event nh? l? v? mu?n nh?n v?t ph?i ?i v?o trong tile ?? ?? k?ch ho?t nh?
            col++;
            if(col == gp.maxWorldCol) {
                col = 0;
                row++;
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
        if(canTouchEvent==true) {
            if (hit(27, 16, "right") == true) {
                damagePit(27, 16, gp.dialogueState);
            }
            if (hit(23, 12, "up") == true) {
                heallingPool(gp.dialogueState);
            }
            if (hit(23, 19, "right") == true) {
                damagePit(27, 16, gp.dialogueState);
            }
        }
    }
    //check event xung ??t
    public boolean hit(int col,int row,String reqDirection){
        boolean hit =false;
        gp.player.solidArea.x =gp.player.worldX+gp.player.solidArea.x;
        gp.player.solidArea.y =gp.player.worldY+gp.player.solidArea.y;
        eventRect[col][row].x = col*gp.tileSize+eventRect[col][row].x;
        eventRect[col][row].y = row*gp.tileSize+eventRect[col][row].y;

        //n?u player va ch?m v?i event
        if(gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false){
            if(gp.player.direction.contentEquals(reqDirection)|| reqDirection.contentEquals("any")){
                hit = true;
                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }
        //gi?ng v?i ?ung object
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
        return hit;
    }
    public void damagePit(int col, int row, int gameState){
        gp.gameState= gameState;
        gp.ui.currentDialogue = "Fall";
        gp.player.life-=1; // con
        //eventRect[col][row].eventDone =true; // n?u ?? t??ng t?c roi thi s? kh?ng tu??ng tac n?a
        canTouchEvent = false;
    }
    public void heallingPool(int gameState){
        if(gp.keyH.enterPressed == true){
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            gp.ui.currentDialogue = "U?ng n??c";
            gp.player.life =gp.player.maxLife;
        }
        gp.keyH.enterPressed = false;
    }
    public void teleport(int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialogue ="D?ch chuy?n";
        gp.player.worldX = gp.tileSize*37;
        gp.player.worldY = gp.tileSize*10;
    }
}

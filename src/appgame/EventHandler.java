package appgame;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    Rectangle eventRect;
    int eventRectDefaultX, eventRectDefaultY;
    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new Rectangle();
        eventRect.x = 23;
        eventRect.y = 23;
        eventRect.width = 2;
        eventRect.height = 2;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y; //event nhỏ là vì muốn nhân vật phải đi vào trong tile đó để kích hoạt nhỏ

    }
    // dung hit methods yeu cầu vi tri va tu the di la right
    public void checkEvent(){
        if(hit(27,16,"right") == true){
            teleport(gp.dialogueState);
        }
        if(hit(23,12,"up")==true){
            heallingPool(gp.dialogueState);
        }
    }
    //check event xung đột
    public boolean hit(int eventCol,int eventRow,String reqDirection){
        boolean hit =false;
        gp.player.solidArea.x =gp.player.worldX+gp.player.solidArea.x;
        gp.player.solidArea.y =gp.player.worldY+gp.player.solidArea.y;
        eventRect.x = eventCol*gp.tileSize+eventRect.x;
        eventRect.y = eventRow*gp.tileSize+eventRect.y;

        //nếu player va chạm với event
        if(gp.player.solidArea.intersects(eventRect)){
            if(gp.player.direction.contentEquals(reqDirection)|| reqDirection.contentEquals("any")){
                hit = true;
            }
        }
        //giống với đung object
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;
        return hit;
    }
    public void damagePit(int gameState){
        gp.gameState= gameState;
        gp.ui.currentDialogue = "Té";
        gp.player.life-=1;
    }
    public void heallingPool(int gameState){
        if(gp.keyH.enterPressed == true){
            gp.gameState = gameState;
            gp.ui.currentDialogue = "Uống nước";
            gp.player.life =gp.player.maxLife;
        }
        gp.keyH.enterPressed = false;
    }
    public void teleport(int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialogue ="Dịch chuyeyển";
        gp.player.worldX = gp.tileSize*37;
        gp.player.worldY = gp.tileSize*10;
    }
}

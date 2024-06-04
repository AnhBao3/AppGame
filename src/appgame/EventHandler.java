package appgame;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    Rectangle eventRect;
    int eventReactDefaultX, eventReactDefaultY;
    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new Rectangle();
        eventRect.x = 23;
        eventRect.y = 23;
        eventRect.width = 2;
        eventRect.height = 2;
        eventReactDefaultX = eventRect.x;
        eventReactDefaultY = eventRect.y; //event nhỏ là vì muốn nhân vật phải đi vào trong tile đó để kích hoạt nhỏ

    }
    public void checkEvent(){

    }
    //check event xung đột
    public boolean hit(int eventCol,int eventRow,String reqDirection){
        boolean hit =false;
        gp.player.solidArea.x =gp.player.worldX+gp.player.solidArea.x;
        gp.player.solidArea.y =gp.player.worldY+gp.player.solidArea.y;
        eventRect.x += eventCol*gp.tileSize+eventRect.x;
        eventRect.y += eventRow*gp.tileSize+eventRect.y;

        //nếu player va chạm với event
        if(gp.player.solidArea.intersects(eventRect)){
            if(gp.player.direction.contentEquals(reqDirection)|| reqDirection.contentEquals("any")){
                hit = true;
            }
        }
        return hit;
    }
}

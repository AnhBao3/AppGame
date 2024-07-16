package object;

import appgame.GamePanel;
import entity.Entity;

public class OBJ_Door_Iron extends Entity {
    GamePanel gp;
    public static final String objName = "DoorSat";

    public OBJ_Door_Iron(GamePanel gp){
        super(gp);
        this.gp = gp;
        type = type_obstacle;
        name = objName;
        down1 = setup("/res/objects/door_iron",gp.tileSize,gp.tileSize);
        collision =true;

        solidArea.x =0;
        solidArea.y =16;
        solidArea.width =48;
        solidArea.height =32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
    public void interact(){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "Không thể mở";
    }
}

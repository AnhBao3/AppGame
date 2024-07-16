package object;

import appgame.GamePanel;
import entity.Entity;

public class OBJ_Poition_Red extends Entity {
    GamePanel gp;
    public static final String objName = "Bình máu";

    public OBJ_Poition_Red(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_consumable;
        name = objName;
        value = 5;
        down1 = setup("/res/objects/bottle_red",gp.tileSize,gp.tileSize);
        defenderValue = 5;
        description ="["+ name + "]\ndùng để hồi "+ value +" máu";
        price = 5;
        stackable = true;
    }
    public boolean use(Entity e) {
        //xu ly xem obj nay se lam gi
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "Dùng "+ name + ", được hồi "+value +" máu";
        e.life +=value;
        gp.playSE(2);
        return true;
    }
}

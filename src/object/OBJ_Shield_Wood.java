package object;

import appgame.GamePanel;
import entity.Entity;

public class OBJ_Shield_Wood extends Entity {
    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp);
        type = type_shield;
        name = "Khiên cổ vật";
        down1 = setup("/res/objects/shield_wood",gp.tileSize,gp.tileSize);
        defenderValue = 1;
        description ="["+ name + "]\nmột tấm khiên đã cũ";
        price = 100;
    }
}

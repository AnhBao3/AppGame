package object;

import appgame.GamePanel;
import entity.Entity;

public class OBJ_Shield_Wood extends Entity {
    public static final String objName = "Khiên cổ vật";

    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp);
        type = type_shield;
        name = objName;
        down1 = setup("/res/objects/shield_wood",gp.tileSize,gp.tileSize);
        defenderValue = 5;
        description ="["+ name + "]\nmột tấm khiên đã cũ";
        price = 5;
    }
}

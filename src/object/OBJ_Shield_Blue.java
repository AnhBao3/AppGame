package object;

import appgame.GamePanel;
import entity.Entity;

public class OBJ_Shield_Blue extends Entity {
    public static final String objName = "Khiên doran";

    public OBJ_Shield_Blue(GamePanel gp) {
        super(gp);
        type = type_shield;
        name = objName;
        down1 = setup("/res/objects/shield_gold",gp.tileSize,gp.tileSize);
        defenderValue = 25;
        description ="["+ name + "]\nkhiên xịn loại mới tinh!";
        price = 50;
    }
}

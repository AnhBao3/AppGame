package object;

import appgame.GamePanel;
import entity.Entity;

public class OBJ_Shield_Blue extends Entity {
    public OBJ_Shield_Blue(GamePanel gp) {
        super(gp);
        type = type_shield;
        name = "Khiên doran";
        down1 = setup("/res/objects/shield_gold",gp.tileSize,gp.tileSize);
        defenderValue = 5;
        description ="["+ name + "]\nkhiên xịn loại mới tinh!";
    }
}

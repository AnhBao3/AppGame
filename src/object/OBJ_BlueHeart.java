package object;

import appgame.GamePanel;
import entity.Entity;

public class OBJ_BlueHeart extends Entity {
    GamePanel gp;
    public static final String objName = "Blue Heart";
    public OBJ_BlueHeart(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_pickupOnly;
        name = objName;
        down1 = setup("/res/objects/gem",gp.tileSize,gp.tileSize);
    }

}

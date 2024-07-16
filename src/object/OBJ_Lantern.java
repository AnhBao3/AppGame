package object;

import appgame.GamePanel;
import entity.Entity;

public class OBJ_Lantern extends Entity {
    public static final String objName = "Lồng đèn";

    public OBJ_Lantern(GamePanel gp) {
        super(gp);

        type = type_light;
        name=objName;
        down1 = setup("/res/objects/lantern",gp.tileSize,gp.tileSize);
        description ="[Lồng đèn]\ndùng để soi sáng";
        price = 15;
        lightRadius = 350;
    }
}

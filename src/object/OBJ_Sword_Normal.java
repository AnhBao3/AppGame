package object;

import appgame.GamePanel;
import entity.Entity;

public class OBJ_Sword_Normal extends Entity {
    public static final String objName = "Gươm vô danh";

    public OBJ_Sword_Normal(GamePanel gp) {
        super(gp);
        type = type_sword;
        name = objName;
        down1 = setup("/res/objects/sword_normal",gp.tileSize,gp.tileSize);
        attackValue = 10;
        attackArea.width =36;
        attackArea.height = 36;
        description ="["+ name + "]\nmột thanh kiếm đã cũ";
        price = 50;
        knockBackPower = 2;
        motion1_duration =10;
        motion2_duration =20;
    }
}

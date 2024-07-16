package object;

import appgame.GamePanel;
import entity.Entity;

public class OBJ_Pickaxe extends Entity {
    public static final String objName = "Cúp sắt";
    public OBJ_Pickaxe(GamePanel gp) {
        super(gp);
        type = type_pickaxe;
        name = objName;
        down1 = setup("/res/objects/pickaxe",gp.tileSize,gp.tileSize);
        attackValue = 5;
        attackArea.width =30;
        attackArea.height = 30;
        description ="["+ name + "]\ndùng để phá tường";
        price = 75;
        knockBackPower = 10;
        motion1_duration =10;
        motion2_duration =20;
    }
}

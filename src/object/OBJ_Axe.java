package object;

import appgame.GamePanel;
import entity.Entity;

public class OBJ_Axe extends Entity {
    public static final String objName = "Rìu sắt";
    public OBJ_Axe(GamePanel gp) {
        super(gp);
        type = type_axe;
        name = objName;
        down1 = setup("/res/objects/axe",gp.tileSize,gp.tileSize);
        attackValue = 5;
        attackArea.width =30;
        attackArea.height = 30;
        description ="["+ name + "]\ndùng để chặt cây đôi khi \ndùng để đánh nhau";
        price = 25;
        knockBackPower = 10;
        motion1_duration =20;
        motion2_duration =40;
    }
}

package object;

import appgame.GamePanel;
import entity.Entity;

public class OBJ_Axe extends Entity {
    public OBJ_Axe(GamePanel gp) {
        super(gp);
        type = type_axe;
        name = "Rìu sắt";
        down1 = setup("/res/objects/axe",gp.tileSize,gp.tileSize);
        attackValue = 2;
        attackArea.width =30;
        attackArea.height = 30;
        description ="["+ name + "]\ndùng để chặt cây đôi khi \ndùng để đánh nhau";
    }
}

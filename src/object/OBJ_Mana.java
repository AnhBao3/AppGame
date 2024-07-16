package object;

import appgame.GamePanel;
import entity.Entity;
public class OBJ_Mana extends Entity {
    public static final String objName = "OBJ_Mana";

    GamePanel gp;
    public OBJ_Mana(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_pickupOnly;
        name = objName;
        value =1;
        down1= setup("/res/objects/mana",gp.tileSize,gp.tileSize);
        image = setup("/res/objects/mana",gp.tileSize,gp.tileSize);
        image2 = setup("/res/objects/mananull",gp.tileSize,gp.tileSize);
    }
    public boolean use(Entity entity){
        gp.playSE(2);
        gp.ui.addMessage("+ "+value+" Năng lượng");
        entity.mana+=value;
        return true;
    }
}

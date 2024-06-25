package object;

import appgame.GamePanel;
import entity.Entity;
public class OBJ_Mana extends Entity {
    GamePanel gp;
    public OBJ_Mana(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_pickupOnly;
        name = "OBJ_Mana";
        value =1;
        down1= setup("/res/objects/mana",gp.tileSize,gp.tileSize);
        image = setup("/res/objects/mana",gp.tileSize,gp.tileSize);
        image2 = setup("/res/objects/mananull",gp.tileSize,gp.tileSize);
    }
    public void use(Entity entity){
        gp.playSE(2);
        gp.ui.addMessage("+ "+value+" Năng lượng");
        entity.mana+=value;
    }
}

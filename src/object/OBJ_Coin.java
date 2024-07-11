package object;

import appgame.GamePanel;
import entity.Entity;

public class OBJ_Coin extends Entity {
    GamePanel gp;
    public OBJ_Coin(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_pickupOnly;
        name ="Tiền";
        value =1;
        down1 = setup("/res/objects/coin",gp.tileSize,gp.tileSize);
    }
    public boolean use(Entity e) {
        gp.playSE(1);
        gp.ui.addMessage("+ "+value+" Vàng");
        gp.player.coin +=value;
        return true;
    }
}

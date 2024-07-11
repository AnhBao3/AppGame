package object;

import appgame.GamePanel;
import entity.Entity;
public class OBJ_Tent extends Entity {
    GamePanel gp;
    public OBJ_Tent(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_consumable;
        name="Lều";
        down1 = setup("/res/objects/tent",gp.tileSize,gp.tileSize);
        description="[Lều]\nNgủ đến sáng hôm sau";
        stackable=true;
    }
    public boolean use(Entity entity) {
        gp.gameState = gp.sleepState;
        gp.playSE(15);
        gp.player.life = gp.player.maxLife;
        gp.player.mana = gp.player.maxMana;
        gp.player.getSleepingImage(down1);
        return true;
    }
}

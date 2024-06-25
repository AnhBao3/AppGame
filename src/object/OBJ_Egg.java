package object;

import appgame.GamePanel;
import entity.Entity;
import entity.Projectile;

public class OBJ_Egg extends Projectile {
    GamePanel gp;
    public OBJ_Egg(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name ="Trứng gà";
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack =2;
        useCost = 1;
        alive =false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/res/objects/egg",gp.tileSize,gp.tileSize);
        up2 = setup("/res/objects/egg",gp.tileSize,gp.tileSize);
        down1 = setup("/res/objects/egg",gp.tileSize,gp.tileSize);
        down2 = setup("/res/objects/egg",gp.tileSize,gp.tileSize);
        left1 = setup("/res/objects/egg",gp.tileSize,gp.tileSize);
        left2 = setup("/res/objects/egg",gp.tileSize,gp.tileSize);
        right1 = setup("/res/objects/egg",gp.tileSize,gp.tileSize);
        right2 = setup("/res/objects/egg",gp.tileSize,gp.tileSize);
    }
    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        if(user.mana>=useCost){
            haveResource = true;
        }
        return haveResource;
    }
    public void subtractResource(Entity user){
        user.mana -= useCost;
    }
}

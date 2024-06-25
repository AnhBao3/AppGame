package object;

import appgame.GamePanel;
import entity.Entity;
import entity.Projectile;

public class OBJ_Slime extends Projectile {
    GamePanel gp;
    public OBJ_Slime(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name ="Chất nhờn";
        speed = 8;
        maxLife = 80;
        life = maxLife;
        attack =2;
        useCost = 1;
        alive =false;
        getImage();
    }
    public void getImage() {
        up1 = setup("/res/objects/slimeball",gp.tileSize,gp.tileSize);
        up2 = setup("/res/objects/slimeball",gp.tileSize,gp.tileSize);
        down1 = setup("/res/objects/slimeball",gp.tileSize,gp.tileSize);
        down2 = setup("/res/objects/slimeball",gp.tileSize,gp.tileSize);
        left1 = setup("/res/objects/slimeball",gp.tileSize,gp.tileSize);
        left2 = setup("/res/objects/slimeball",gp.tileSize,gp.tileSize);
        right1 = setup("/res/objects/slimeball",gp.tileSize,gp.tileSize);
        right2 = setup("/res/objects/slimeball",gp.tileSize,gp.tileSize);
    }
    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        if(user.ammo>=useCost){
            haveResource = true;
        }
        return haveResource;
    }
    public void subtractResource(Entity user){
        user.ammo -= useCost;
    }
}

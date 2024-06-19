package monster;

import appgame.GamePanel;
import entity.Entity;

import java.util.Random;

public class MON_GreenSlime extends Entity {

    GamePanel gp;
    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = 2;
        name = "Green Slime";
        speed = 1;
        maxLife = 5;
        life = maxLife;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }
    public void getImage(){
        up1 = setup("/res/monster/slime_up_1",gp.tileSize,gp.tileSize);
        up2 = setup("/res/monster/slime_up_2",gp.tileSize,gp.tileSize);
        down1 = setup("/res/monster/slime_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/res/monster/slime_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("/res/monster/slime_left_1",gp.tileSize,gp.tileSize);
        left2 = setup("/res/monster/slime_left_2",gp.tileSize,gp.tileSize);
        right1 = setup("/res/monster/slime_right_1",gp.tileSize,gp.tileSize);
        right2 = setup("/res/monster/slime_right_2",gp.tileSize,gp.tileSize);
    }
    public void setAction(){
        actionLockCounter++;

        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // chọn những con só ngầu nhên từ 1 - 100
            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
            //mỗi 120 khung hình thì nó mới đổi hướng duy chuyển của NPC
        }
    }
    public void damageReaction(){
        actionLockCounter++;
        direction = gp.player.direction;
    }
}

package monster;

import appgame.GamePanel;
import entity.Entity;
import object.OBJ_Coin;
import object.OBJ_Heart;
import object.OBJ_Mana;
import object.OBJ_Slime;

import java.util.Random;

public class MON_GreenSlime extends Entity {

    GamePanel gp;
    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_monster;
        name = "Zac chất nhờn";
        speed = 1;
        maxLife = 5;
        life = maxLife;
        attack = 5;
        defense =0;
        exp = 2;
        projectile = new OBJ_Slime(gp);
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
        int i = new Random().nextInt(100)+1;
        if(i > 99 && projectile.alive == false && shotAvailableCounter ==30){
            projectile.set(worldX,worldY,direction,true,this);
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
        }
    }
    public void damageReaction(){
        actionLockCounter++;
        direction = gp.player.direction;
    }
    public void checkDrop(){

        int i = new Random().nextInt(100)+1;

        //set monster drop
        if(i<50){
            dropIem(new OBJ_Coin(gp));
        }
        if(i>=50 && i<75){
            dropIem(new OBJ_Heart(gp));
        }
        if(i>=75 && i<100){
            dropIem(new OBJ_Mana(gp));
        }
    }
}

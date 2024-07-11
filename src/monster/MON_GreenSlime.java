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
        defaultSpeed = 1;
        speed = defaultSpeed;
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

        if(onPath==true){
            //check if it stop chasing
            checkStopChasingOrNot(gp.player,15,100);
            //search
            searchPath(getGoalCol(gp.player),getGoalRow(gp.player));

            checkShootOrNot(200,30);
        } else {
            checkStartChasingOrNot(gp.player,5,100);
            getRandomDirection();
        }
    }
    public void damageReaction(){
        actionLockCounter++;
//        direction = gp.player.direction;
        onPath = true;
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

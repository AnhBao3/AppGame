package monster;

import appgame.GamePanel;
import entity.Entity;
import object.OBJ_Coin;
import object.OBJ_Heart;
import object.OBJ_Mana;
import object.OBJ_Slime;

import java.util.Random;

public class MON_Orc extends Entity {
    GamePanel gp;
    public MON_Orc(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_monster;
        name = "Orc";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife =500;
        life = maxLife;
        attack = 8;
        defense =2;
        exp = 10;
        knockBackPower = 5;

        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 40;
        solidArea.height = 44;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 48;
        attackArea.height = 48;
        motion1_duration = 40;
        motion2_duration = 85;
        getImage();
        getAttackImage();
    }
    public void getImage(){
        up1 = setup("/res/monster/orc_up_1",gp.tileSize,gp.tileSize);
        up2 = setup("/res/monster/orc_up_2",gp.tileSize,gp.tileSize);
        down1 = setup("/res/monster/orc_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/res/monster/orc_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("/res/monster/orc_left_1",gp.tileSize,gp.tileSize);
        left2 = setup("/res/monster/orc_left_2",gp.tileSize,gp.tileSize);
        right1 = setup("/res/monster/orc_right_1",gp.tileSize,gp.tileSize);
        right2 = setup("/res/monster/orc_right_2",gp.tileSize,gp.tileSize);
    }
    public void getAttackImage(){
        attackUp1 = setup("/res/monster/orc_attack_up_1", gp.tileSize, gp.tileSize * 2);
        attackUp2 = setup("/res/monster/orc_attack_up_2", gp.tileSize, gp.tileSize * 2);
        attackDown1 = setup("/res/monster/orc_attack_down_1", gp.tileSize, gp.tileSize * 2);
        attackDown2 = setup("/res/monster/orc_attack_down_2", gp.tileSize, gp.tileSize * 2);
        attackLeft1 = setup("/res/monster/orc_attack_left_1", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = setup("/res/monster/orc_attack_left_2", gp.tileSize * 2, gp.tileSize);
        attackRight1 = setup("/res/monster/orc_attack_right_1", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setup("/res/monster/orc_attack_right_2", gp.tileSize * 2, gp.tileSize);
    }

    public void setAction(){

        if(onPath==true){
            //check if it stop chasing
            checkStopChasingOrNot(gp.player,15,100);
            //search
            searchPath(getGoalCol(gp.player),getGoalRow(gp.player));
        } else {
            checkStartChasingOrNot(gp.player,5,100);
            getRandomDirection(120);
        }
        if(attacking == false){
            checkAttackOrNot(30,gp.tileSize*4,gp.tileSize);
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

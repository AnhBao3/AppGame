package monster;

import appgame.GamePanel;
import data.Progress;
import entity.Entity;
import object.OBJ_Coin;
import object.OBJ_Door_Iron;
import object.OBJ_Heart;
import object.OBJ_Mana;

import java.util.Random;

public class MON_SkeletonLord extends Entity {
    GamePanel gp;
    public static final String monName = "Skeleton Lord";
    public MON_SkeletonLord(GamePanel gp) {
        super(gp);
        boss =true;
        this.gp = gp;
        type = type_monster;
        name = monName;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife =5000;
        life = maxLife;
        attack = 10;
        defense =2;
        exp = 50;
        knockBackPower = 5;
        sleep = true;

        int size = gp.tileSize*5;
        solidArea.x = 48;
        solidArea.y = 48;
        solidArea.width = size - 48*2;
        solidArea.height = size - 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 170;
        attackArea.height = 170;
        motion1_duration = 25;
        motion2_duration = 85;
        getImage();
        getAttackImage();
    }
    public void getImage(){
        int i =5;
        if(inRage == false){
            up1 = setup("/res/monster/skeletonlord_up_1",gp.tileSize*i,gp.tileSize*i);
            up2 = setup("/res/monster/skeletonlord_up_2",gp.tileSize*i,gp.tileSize*i);
            down1 = setup("/res/monster/skeletonlord_down_1",gp.tileSize*i,gp.tileSize*i);
            down2 = setup("/res/monster/skeletonlord_down_2",gp.tileSize*i,gp.tileSize*i);
            left1 = setup("/res/monster/skeletonlord_left_1",gp.tileSize*i,gp.tileSize*i);
            left2 = setup("/res/monster/skeletonlord_left_2",gp.tileSize*i,gp.tileSize*i);
            right1 = setup("/res/monster/skeletonlord_right_1",gp.tileSize*i,gp.tileSize*i);
            right2 = setup("/res/monster/skeletonlord_right_2",gp.tileSize*i,gp.tileSize*i);
        }
        if(inRage == true){
            up1 = setup("/res/monster/skeletonlord_phase2_up_1",gp.tileSize*i,gp.tileSize*i);
            up2 = setup("/res/monster/skeletonlord_phase2_up_2",gp.tileSize*i,gp.tileSize*i);
            down1 = setup("/res/monster/skeletonlord_phase2_down_1",gp.tileSize*i,gp.tileSize*i);
            down2 = setup("/res/monster/skeletonlord_phase2_down_2",gp.tileSize*i,gp.tileSize*i);
            left1 = setup("/res/monster/skeletonlord_phase2_left_1",gp.tileSize*i,gp.tileSize*i);
            left2 = setup("/res/monster/skeletonlord_phase2_left_2",gp.tileSize*i,gp.tileSize*i);
            right1 = setup("/res/monster/skeletonlord_phase2_right_1",gp.tileSize*i,gp.tileSize*i);
            right2 = setup("/res/monster/skeletonlord_phase2_right_2",gp.tileSize*i,gp.tileSize*i);
        }

    }
    public void getAttackImage(){
        int i =5;
        if(inRage == false) {
            attackUp1 = setup("/res/monster/skeletonlord_attack_up_1", gp.tileSize*i, gp.tileSize*i * 2);
            attackUp2 = setup("/res/monster/skeletonlord_attack_up_2", gp.tileSize*i, gp.tileSize * 2*i);
            attackDown1 = setup("/res/monster/skeletonlord_attack_down_1", gp.tileSize*i, gp.tileSize * 2*i);
            attackDown2 = setup("/res/monster/skeletonlord_attack_down_2", gp.tileSize*i, gp.tileSize * 2*i);
            attackLeft1 = setup("/res/monster/skeletonlord_attack_left_1", gp.tileSize * 2*i, gp.tileSize*i);
            attackLeft2 = setup("/res/monster/skeletonlord_attack_left_2", gp.tileSize * 2*i, gp.tileSize*i);
            attackRight1 = setup("/res/monster/skeletonlord_attack_right_1", gp.tileSize * 2*i, gp.tileSize*i);
            attackRight2 = setup("/res/monster/skeletonlord_attack_right_2", gp.tileSize * 2*i, gp.tileSize*i);
        }
        if(inRage == true){
            attackUp1 = setup("/res/monster/skeletonlord_phase2_attack_up_1", gp.tileSize*i, gp.tileSize*i * 2);
            attackUp2 = setup("/res/monster/skeletonlord_phase2_attack_up_2", gp.tileSize*i, gp.tileSize * 2*i);
            attackDown1 = setup("/res/monster/skeletonlord_phase2_attack_down_1", gp.tileSize*i, gp.tileSize * 2*i);
            attackDown2 = setup("/res/monster/skeletonlord_phase2_attack_down_2", gp.tileSize*i, gp.tileSize * 2*i);
            attackLeft1 = setup("/res/monster/skeletonlord_phase2_attack_left_1", gp.tileSize * 2*i, gp.tileSize*i);
            attackLeft2 = setup("/res/monster/skeletonlord_phase2_attack_left_2", gp.tileSize * 2*i, gp.tileSize*i);
            attackRight1 = setup("/res/monster/skeletonlord_phase2_attack_right_1", gp.tileSize * 2*i, gp.tileSize*i);
            attackRight2 = setup("/res/monster/skeletonlord_phase2_attack_right_2", gp.tileSize * 2*i, gp.tileSize*i);
        }

    }

    public void setAction(){
        if(inRage==false && life < maxLife/2){
            inRage = true;
            getImage();
            getAttackImage();
            defaultSpeed++;
            speed = defaultSpeed;
            attack *=2;
        }
        if(getTileDistance(gp.player)<10){
            moveTowardPlayer(60);

        } else {
            getRandomDirection(60);
        }
        if(attacking == false){
            checkAttackOrNot(60,gp.tileSize*7,gp.tileSize*5);
        }
    }
    public void damageReaction(){
        actionLockCounter++;

    }
    public void setDialogue(){
        dialogues[0] = "Muốn lấy kho báu?,\ntrước tiên hãy chiến đấu với ta!";

    }
    public void checkDrop(){
        gp.bossBattleOn = false;
        Progress.skeletonLordDefeated =true;
        gp.stopMusic();
        gp.playMusic(20);
        for(int i=0;i<gp.obj[1].length;i++){
            if(gp.obj[gp.currentMap][i]!=null && gp.obj[gp.currentMap][i].name.equals(OBJ_Door_Iron.objName)){
                gp.playSE(22);
                gp.obj[gp.currentMap][i] = null;
            }
        }
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

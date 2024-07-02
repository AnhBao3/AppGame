package entity;

import appgame.GamePanel;
import object.*;

import java.awt.*;

public class NPC_Merchant extends Entity {
    public NPC_Merchant(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 1;

        solidArea = new Rectangle();
        solidArea.x =9;
        solidArea.y =16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        getImage();
        setDialogue();
        setItems();
    }

    public void getImage() {
        //set hình ảnh người chơi
        up1 = setup("/res/npc/merchant_down_1",gp.tileSize,gp.tileSize);
        up2 = setup("/res/npc/merchant_down_2",gp.tileSize,gp.tileSize);
        down1 = setup("/res/npc/merchant_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/res/npc/merchant_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("/res/npc/merchant_down_1",gp.tileSize,gp.tileSize);
        left2 = setup("/res/npc/merchant_down_2",gp.tileSize,gp.tileSize);
        right1 = setup("/res/npc/merchant_down_1",gp.tileSize,gp.tileSize);
        right2 = setup("/res/npc/merchant_down_2",gp.tileSize,gp.tileSize);
    }
    public void setDialogue() {
        //mãng chứa thông báo
        dialogues[0] = "Tìm thấy ta rồi à!,\nNhóc muốn gì?";
    }
    public void setItems(){
        inventory.add(new OBJ_Poition_Red(gp));
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Shield_Blue(gp));
        inventory.add(new OBJ_Shield_Wood(gp));
        inventory.add(new OBJ_Axe(gp));
        inventory.add(new OBJ_Sword_Normal(gp));
    }
    public void speak(){
        super.speak();
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;
    }
}

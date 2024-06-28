/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgame;

import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import object.*;
import tile_interactive.IT_DryTree;

/**
 *
 * @author Admin
 */
public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        int i = 0;
        gp.obj[i] = new OBJ_Coin(gp);
        gp.obj[i].worldX = gp.tileSize * 35;
        gp.obj[i].worldY = gp.tileSize * 8;
        i++;
        gp.obj[i] = new OBJ_Mana(gp);
        gp.obj[i].worldX = gp.tileSize * 36;
        gp.obj[i].worldY = gp.tileSize * 8;
        i++;
        gp.obj[i] = new OBJ_Heart(gp);
        gp.obj[i].worldX = gp.tileSize * 37;
        gp.obj[i].worldY = gp.tileSize * 8;
        i++;
        gp.obj[i] = new OBJ_Axe(gp);
        gp.obj[i].worldX = gp.tileSize * 38;
        gp.obj[i].worldY = gp.tileSize * 8;
        i++;
        gp.obj[i] = new OBJ_Shield_Blue(gp);
        gp.obj[i].worldX = gp.tileSize * 39;
        gp.obj[i].worldY = gp.tileSize * 8;
        i++;
        gp.obj[i] = new OBJ_Poition_Red(gp);
        gp.obj[i].worldX = gp.tileSize * 40;
        gp.obj[i].worldY = gp.tileSize * 8;
        i++;
        gp.obj[i] = new OBJ_Boots(gp);
        gp.obj[i].worldX = gp.tileSize * 40;
        gp.obj[i].worldY = gp.tileSize * 10;
    }
    public void setNPC(){
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;

    }
    public void setMonster(){

        int i = 0;

        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize*23;
        gp.monster[i].worldY = gp.tileSize*36;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize*23;
        gp.monster[i].worldY = gp.tileSize*37;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize*22;
        gp.monster[i].worldY = gp.tileSize*38;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize*21;
        gp.monster[i].worldY = gp.tileSize*35;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize*23;
        gp.monster[i].worldY = gp.tileSize*42;



//        gp.monster[0] = new MON_GreenSlime(gp);
//        gp.monster[0].worldX = gp.tileSize*11;
//        gp.monster[0].worldY = gp.tileSize*10;
//
//        gp.monster[1] = new MON_GreenSlime(gp);
//        gp.monster[1].worldX = gp.tileSize*11;
//        gp.monster[1].worldY = gp.tileSize*11;
    }
    public void setInteractiveTile(){
        int i =0;
        gp.iTile[i] = new IT_DryTree(gp,27,12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp,28,12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp,29,12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp,30,12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp,31,12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp,32,12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp,33,12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp,22,35);
        i++;
        gp.iTile[i] = new IT_DryTree(gp,23,35);
        i++;
        gp.iTile[i] = new IT_DryTree(gp,24,35);
        i++;
        gp.iTile[i] = new IT_DryTree(gp,29,40);
    }
}

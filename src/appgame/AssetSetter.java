/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgame;

import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

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

    }
    public void setNPC(){
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;

    }
    public void setMonster(){
        gp.monster[0] = new MON_GreenSlime(gp);
        gp.monster[0].worldX = gp.tileSize*23;
        gp.monster[0].worldY = gp.tileSize*36;

        gp.monster[1] = new MON_GreenSlime(gp);
        gp.monster[1].worldX = gp.tileSize*23;
        gp.monster[1].worldY = gp.tileSize*37;

//        gp.monster[0] = new MON_GreenSlime(gp);
//        gp.monster[0].worldX = gp.tileSize*11;
//        gp.monster[0].worldY = gp.tileSize*10;
//
//        gp.monster[1] = new MON_GreenSlime(gp);
//        gp.monster[1].worldX = gp.tileSize*11;
//        gp.monster[1].worldY = gp.tileSize*11;
    }
}

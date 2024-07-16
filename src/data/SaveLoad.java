package data;

import appgame.GamePanel;
import entity.Entity;
import object.*;

import java.io.*;

public class SaveLoad {
    GamePanel gp;

    public SaveLoad(GamePanel gp) {
        this.gp = gp;
    }



    public void save() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));

            DataStorage ds = new DataStorage();

            // Player attributes
            ds.level = gp.player.level;
            ds.maxLife = gp.player.maxLife;
            ds.life = gp.player.life;
            ds.maxMana = gp.player.maxMana;
            ds.mana = gp.player.mana;
            ds.strength = gp.player.strength;
            ds.dexterity = gp.player.dexterity;
            ds.exp = gp.player.exp;
            ds.nextLevelExp = gp.player.nextLevelExp;
            ds.coin = gp.player.coin;

            // Player inventory
            for (int i = 0; i < gp.player.inventory.size(); i++) {
                ds.itemNames.add(gp.player.inventory.get(i).name);
                ds.itemAmounts.add(gp.player.inventory.get(i).amount);
            }
            ds.currentWeaponSlot = gp.player.getCurrentWeaponSlot();
            ds.currentSheldSlot = gp.player.getCurrentSheldSlot();

            // Map objects
            ds.mapObjectNames = new String[gp.maxMap][gp.obj[0].length];
            ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[0].length];
            ds.mapObjectWorldY = new int[gp.maxMap][gp.obj[0].length];
            ds.mapObjectLootNames = new String[gp.maxMap][gp.obj[0].length];
            ds.mapObjectOpened = new boolean[gp.maxMap][gp.obj[0].length];

            for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
                for (int i = 0; i < gp.obj[0].length; i++) {
                    if (gp.obj[mapNum][i] == null) {
                        ds.mapObjectNames[mapNum][i] = "NA";
                    } else {
                        ds.mapObjectNames[mapNum][i] = gp.obj[mapNum][i].name;
                        ds.mapObjectWorldX[mapNum][i] = gp.obj[mapNum][i].worldX;
                        ds.mapObjectWorldY[mapNum][i] = gp.obj[mapNum][i].worldY;
                        if (gp.obj[mapNum][i].loot != null) {
                            ds.mapObjectLootNames[mapNum][i] = gp.obj[mapNum][i].loot.name;
                        }
                        ds.mapObjectOpened[mapNum][i] = gp.obj[mapNum][i].opened;
                    }
                }
            }

            oos.writeObject(ds);
            oos.close();
            System.out.println("Game saved successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));

            // Read DataStorage
            DataStorage ds = (DataStorage) ois.readObject();

            // Assign player attributes
            gp.player.level = ds.level;
            gp.player.maxLife = ds.maxLife;
            gp.player.life = ds.life;
            gp.player.maxMana = ds.maxMana;
            gp.player.mana = ds.mana;
            gp.player.strength = ds.strength;
            gp.player.dexterity = ds.dexterity;
            gp.player.exp = ds.exp;
            gp.player.nextLevelExp = ds.nextLevelExp;
            gp.player.coin = ds.coin;

            // Clear current inventory and load saved items
            gp.player.inventory.clear();
            for (int i = 0; i < ds.itemNames.size(); i++) {
                Entity item = gp.eGenerator.getObject(ds.itemNames.get(i));
                if (item != null) {
                    item.amount = ds.itemAmounts.get(i);
                    gp.player.inventory.add(item);
                }
            }

            // Set current weapon and shield
            if (ds.currentWeaponSlot >= 0 && ds.currentWeaponSlot < gp.player.inventory.size()) {
                gp.player.currentWeapon = gp.player.inventory.get(ds.currentWeaponSlot);
            }
            if (ds.currentSheldSlot >= 0 && ds.currentSheldSlot < gp.player.inventory.size()) {
                gp.player.currentSheld = gp.player.inventory.get(ds.currentSheldSlot);
            }

            // Recalculate attack and defense
            gp.player.getAttack();
            gp.player.getDefense();
            gp.player.getAttackImage();

            // Load map objects
            for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
                for (int i = 0; i < gp.obj[0].length; i++) {
                    if (ds.mapObjectNames[mapNum][i].equals("NA")) {
                        gp.obj[mapNum][i] = null;
                    } else {
                        Entity obj = gp.eGenerator.getObject(ds.mapObjectNames[mapNum][i]);
                        if (obj != null) {
                            obj.worldX = ds.mapObjectWorldX[mapNum][i];
                            obj.worldY = ds.mapObjectWorldY[mapNum][i];
                            if (ds.mapObjectLootNames[mapNum][i] != null) {
                                obj.loot = gp.eGenerator.getObject(ds.mapObjectLootNames[mapNum][i]);
                            }
                            obj.opened = ds.mapObjectOpened[mapNum][i];
                            if (obj.opened) {
                                obj.down1 = obj.image2;
                            }
                            gp.obj[mapNum][i] = obj;
                        } else {
                            System.out.println("Failed to load object: " + ds.mapObjectNames[mapNum][i]);
                        }
                    }
                }
            }

            ois.close();
            System.out.println("Game loaded successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

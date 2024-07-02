package tile_interactive;

import appgame.GamePanel;
import entity.Entity;
import tile.Tile;

import java.awt.*;

public class IT_DryTree extends InteractiveTile{
    GamePanel gp;
    public IT_DryTree(GamePanel gp, int col, int row) {
        super(gp,col,row);
        this.gp = gp;
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
        down1 = setup("/res/tiles_interactive/drytree",gp.tileSize,gp.tileSize);
        destructible = true;
        life = 1;
    }
    public boolean isCorrectItem(Entity entity){
        boolean isCorrectItem =false;
        if(entity.currentWeapon.type == type_axe){
            isCorrectItem = true;
        }
        return isCorrectItem;
    }
    public void playSE(){
        gp.playSE(13);
    }
    public InteractiveTile getDestroyedFrom(){
        InteractiveTile tile = new IT_Trunk(gp,worldX/gp.tileSize,worldY/gp.tileSize);
        return tile;
    }
    public Color getParticleColor(){
        Color color = new Color(65,50,30);
        return color;
    }
    public int getParticleSize(){
        int size = 6; //6 pixel
        return size;
    }
    public int getParticleSpeed(){
        int speed = 1;
        return speed;
    }
    public int getParticleMaxLife(){
        int maxLife =20;
        return maxLife;
    }
}

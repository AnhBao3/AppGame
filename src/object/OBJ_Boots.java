/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import appgame.GamePanel;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Admin
 */
public class OBJ_Boots extends SuperObject {
            GamePanel gp;
    public OBJ_Boots(GamePanel gp){
        name = "Boots";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/boots.png"));
            uTool.scaleImage(image,gp.tileSize,gp.tileSize);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}

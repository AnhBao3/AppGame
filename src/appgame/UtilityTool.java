/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgame;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Admin
 */
public class UtilityTool {
        public BufferedImage scaleImage(BufferedImage original,int width, int height){
                        BufferedImage scaledImage = new BufferedImage(width,height,original.getType()); //khung vẽ trống
            Graphics2D g2 = scaledImage.createGraphics();  //hình ảnh được chia theo tỉ lệ của khung
            g2.drawImage(original, 0, 0, width, height,null); //vẽ với kích thước này
            g2.dispose();
            return scaledImage;
        }
}

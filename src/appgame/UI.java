/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import entity.Entity;
import object.OBJ_Heart;

/**
 *
 * @author Admin
 */
public class UI {
    Font fontPixel;
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    //BufferedImage KeyImage;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean messageOn = false;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; //  0: 
    BufferedImage heart_full, heart_half, heart_blank;
    public int slotCol =0;
    public int slotRow =0;

    public UI(GamePanel gp) {
        this.gp = gp;
        //arial_40 = new Font("Arial", Font.PLAIN, 40);
        //arial_80B = new Font("Arial", Font.BOLD, 80);

        InputStream is = getClass().getResourceAsStream("/res/font/FVF Fernando 08.ttf");
        try {
            fontPixel = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(fontPixel);
        g2.setColor(Color.white);
        //titleState
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        //playstate
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
            drawMessage();
        }
        //pauseState
        if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScree();
        }
        if (gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawDialogueScreeen();
        }
        //chi so nguoi choi
        if(gp.gameState == gp.characterState){
            drawCharacterScreem();
            drawInventory();
        }
    }

    public void drawInventory() {
        //khung
        int frameX = gp.tileSize*9;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*6;
        int frameHeight = gp.tileSize*5;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        //slot do
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize+3;
        // draw player item
        for(int i =0;i<gp.player.inventory.size();i++){
            if(gp.player.inventory.get(i) == gp.player.currentWeapon ||
            gp.player.inventory.get(i)==gp.player.currentSheld){
                g2.setColor(new Color(231, 76, 60));
                g2.fillRoundRect(slotX,slotY,gp.tileSize,gp.tileSize,10,10);
            }
            g2.drawImage(gp.player.inventory.get(i).down1,slotX,slotY,null);
            slotX += slotSize;
            if(i == 4 || i == 9 || i == 14){
                slotX = slotXstart;
                slotY += slotSize;
            }
        }
        //CURSOR
        int cursorX = slotXstart+ (slotSize*slotCol);
        int cursorY = slotYstart +(slotSize*slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        //draw cursor
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX,cursorY,cursorWidth,cursorHeight,10,10);

        // Decription frame
        int dFrameX = frameX;
        int dFrameY = frameY +frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gp.tileSize*3;
        //text
        int textX =dFrameX+20;
        int textY = dFrameY +gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(15F));

        int itemIndex = getItemIndexOnSlot();
        if(itemIndex<gp.player.inventory.size()){

            drawSubWindow(dFrameX,dFrameY,dFrameWidth,dFrameHeight);

            for(String line: gp.player.inventory.get(itemIndex).description.split("\n")){
                g2.drawString(line,textX,textY);
                textY +=32;
            }
        }

    }
    public int getItemIndexOnSlot(){
        int itemIndex = slotCol + (slotRow*5);
        return itemIndex;
    }
    public void drawMessage() {
        int messageX = gp.tileSize-5;
        int messageY = gp.tileSize*4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,20F));
        for(int i =0;i<message.size();i++){
            if(message.get(i) !=null){
                g2.setColor(Color.black );
                g2.drawString(message.get(i),messageX+2,messageY+2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i),messageX,messageY);
                int counter = messageCounter.get(i) +1;
                messageCounter.set(i,counter);
                messageY +=50;
                if(messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawCharacterScreem() {
        //tạo khung
        final int frameX = gp.tileSize-5;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*6;
        final int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(17F));
        int textX = frameX + 15;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;
        //tên
        g2.drawString("Cấp Độ",textX,textY);
        textY+= lineHeight;
        g2.drawString("Máu",textX,textY);
        textY+= lineHeight;
        g2.drawString("Sức Mạnh Công Kích",textX,textY);
        textY+= lineHeight;
        g2.drawString("Chịu đựng" +
                "",textX,textY);
        textY+= lineHeight;
        g2.drawString("Chỉ Số Tấn Công",textX,textY);
        textY+= lineHeight;
        g2.drawString("Chỉ Số Phòng Thủ",textX,textY);
        textY+= lineHeight;
        g2.drawString("Kinh Nghiệm",textX,textY);
        textY+= lineHeight;
        g2.drawString("Cấp Tiếp Theo",textX,textY);
        textY+= lineHeight;
        g2.drawString("Vàng",textX,textY);
        textY+= lineHeight+20;
        g2.drawString("Vũ Khí",textX,textY);
        textY+= lineHeight+15;
        g2.drawString("Khiên",textX,textY);

        //gia tri
        int tailX = (frameX+frameWidth) - 30;
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+= lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+= lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+= lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+= lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+= lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+= lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+= lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+= lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY+= lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1,tailX - gp.tileSize, textY-14,null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentSheld.down1,tailX-gp.tileSize,textY-14,null);


    }
    public int getXforAlignToRightText(String text,int tailX){
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
    public void drawPauseScree() {
        String text = "TẠM DỪNG";
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        int x = getXforCenteredText(text);

        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
    // màn hình nói chuyện
    public void drawDialogueScreeen() {
        // khung noi chuyen
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
        x += gp.tileSize;
        y += gp.tileSize;

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40; // chia văn bản trong hộp thoại, và đưa vào line sau  
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        //vẽ hình tròn chữ nhật, yêu cầu fill màu vào trong
        g2.fillRoundRect(x, y, width, height, 35, 35);
        c = new Color(255, 255, 255);
        //vẽ khung ngoài
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public void drawTitleScreen() {

        if (titleScreenState == 0) {
            g2.setColor(new Color(52, 152, 219)); //rgb(52, 152, 219)
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            //Game title 
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            String text = "Đi Tìm Kho Báu Của TML";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;

            g2.setColor(Color.black);
            g2.drawString(text, x + 5, y + 5);

            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
            y += gp.tileSize * 2;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
            text = "Tạo mới";
            x = getXforCenteredText(text);
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }
            text = "Chơi lại";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }
            text = "Thoát";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        } else if (titleScreenState == 1) {
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(50F));
            g2.setColor(new Color(52, 152, 219)); //rgb(52, 152, 219)
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 35F));
            g2.setColor(Color.white);

            String text = "chọn lớp của bạn";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;
            g2.drawString(text, x, y);
            text = "Đấu sĩ";
            x = getXforCenteredText(text);
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }
            text = "Trộm";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }
            text = "Pháp sư";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }
            text = "Trở về";
            x = getXforCenteredText(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);
            if (commandNum == 3) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }
    }

    public void drawPlayerLife() {
        
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;
        // trái tim rỗng </3
        while(i<gp.player.maxLife/2){
            g2.drawImage(heart_blank, x, y,null);
            i++;
            x+= gp.tileSize; // 1 trái tim là 2 mạng
        }    
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;
        
        //trái tim đày
        while(i<gp.player.life){        
            g2.drawImage(heart_half, x, y,null);            
            i++;
            if(i<gp.player.life){
                g2.drawImage(heart_full,x,y,null);
            }
            i++;
            x+= gp.tileSize;
        }
    }
    public void addMessage(String text){
        message.add(text);
        messageCounter.add(0);
    }
}

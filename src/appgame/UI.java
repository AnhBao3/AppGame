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
import object.OBJ_Coin;
import object.OBJ_Heart;
import object.OBJ_Mana;
import org.w3c.dom.Text;

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
    BufferedImage heart_full, heart_half, heart_blank, manafull, manablank,coin;
    public int playerSlotCol =0;
    public int playerSlotRow =0;
    public int npcSlotCol =0;
    public int npcSlotRow =0;
    int subState = 0;
    int counter = 0;
    public Entity npc;


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
        Entity mana = new OBJ_Mana(gp);
        manafull = mana.image;
        manablank = mana.image2;
        Entity Bcoin = new OBJ_Coin(gp);
        coin = Bcoin.down1;
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
            drawCharacterScreen();
            drawInventory(gp.player,true);
        }
        if(gp.gameState == gp.optionState){
            drawOptionScreen();
        }
        if(gp.gameState == gp.gameOverState){
            drawGameOverScreen();
        }
        if(gp.gameState == gp.transitionState){
            drawTransition();
        }
        if(gp.gameState == gp.tradeState){
            drawTradeScreen();
        }
    }

    public void drawTradeScreen() {
        switch (subState){
            case 0: trade_select();break;
            case 1: trade_buy();break;
            case 2: trade_sell();break;
        }
        gp.keyH.enterPressed = false;
    }
    public void trade_select(){

        drawDialogueScreeen();

        //DRAW WINDOW
        int x = gp.tileSize *15;
        int y = gp.tileSize *4;
        int width = gp.tileSize*3;
        int height = (int)(gp.tileSize*3.5);
        drawSubWindow(x,y,width,height);

        //draw text
        x+= gp.tileSize;
        y+= gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(20f));
        g2.drawString("Mua",x,y);
        if(commandNum==0){
            g2.drawString(">",x-24,y);
            if(gp.keyH.enterPressed==true){
                subState =1;
            }
        }
        y+= gp.tileSize;
        g2.drawString("Bán",x,y);
        if(commandNum==1){
            g2.drawString(">",x-24,y);
            if(gp.keyH.enterPressed==true){
                subState =2;
            }
        }
        y+= gp.tileSize;
        g2.drawString("Rời",x,y);
        if(commandNum==2){
            g2.drawString(">",x-24,y);
            if(gp.keyH.enterPressed==true){
                commandNum =0;
                gp.gameState = gp.dialogueState;
                currentDialogue = "Chúc may mắn!";
            }
        }
        y+= gp.tileSize;
    }
    public void trade_buy(){
        //draw player inventory
        drawInventory(gp.player,false);

        //npc inven
        drawInventory(npc,true);

        int x = gp.tileSize *2;
        int y = gp.tileSize *9;
        int width = gp.tileSize*6;
        int height = gp.tileSize*2;
        drawSubWindow(x,y,width,height);
        g2.drawString("[ESC] Thoát",x+24,y+60);

         x = gp.tileSize *12;
         y = gp.tileSize *9;
         width = gp.tileSize*6;
         height = gp.tileSize*2;
        drawSubWindow(x,y,width,height);
        g2.drawString("Tiền: "+gp.player.coin,x+24,y+60);
        //gia cua sp
        int itemIndex = getItemIndexOnSlot(npcSlotCol,npcSlotRow);
        if(itemIndex<npc.inventory.size()){
            x = (int)(gp.tileSize*5.5);
            y = (int)(gp.tileSize*5.5);
            width = (int)(gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubWindow(x,y,width,height);
            g2.drawImage(coin,x+10,y+8,32,32,null);

            int price = npc.inventory.get(itemIndex).price;
            String text = ""+price;
            x = getXforAlignToRightText(text,gp.tileSize*8-20);
            g2.drawString(text,x,y+34);

            if(gp.keyH.enterPressed==true){
                if(npc.inventory.get(itemIndex).price > gp.player.coin){
                    subState =0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue ="Không đủ tiền!";
                    drawDialogueScreeen();
                }
                else if(gp.player.inventory.size()==gp.player.maxInventorySize){
                    subState =0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue ="Không đủ chỗ chứa!";
                    drawDialogueScreeen();
                }
                else{
                    gp.player.coin -= npc.inventory.get(itemIndex).price;
                    gp.player.inventory.add(npc.inventory.get(itemIndex));
                }
            }
        }
    }
    public void trade_sell(){
        drawInventory(gp.player,true);

        int x;
        int y;
        int width;
        int height;
        x = gp.tileSize *2;
        y = gp.tileSize *9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubWindow(x,y,width,height);
        g2.drawString("[ESC] Thoát",x+24,y+60);

        x = gp.tileSize *12;
        y = gp.tileSize *9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubWindow(x,y,width,height);
        g2.drawString("Tiền: "+gp.player.coin,x+24,y+60);
        //gia cua sp
        int itemIndex = getItemIndexOnSlot(playerSlotCol,playerSlotRow);
        if(itemIndex<gp.player.inventory.size()){
            x = (int)(gp.tileSize*15.5);
            y = (int)(gp.tileSize*5.5);
            width = (int)(gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubWindow(x,y,width,height);
            g2.drawImage(coin,x+10,y+8,32,32,null);

            int price = gp.player.inventory.get(itemIndex).price/2;
            String text = ""+price;
            x = getXforAlignToRightText(text,gp.tileSize*18-20);
            g2.drawString(text,x,y+34);
            //sell item
            if(gp.keyH.enterPressed==true){
                if(gp.player.inventory.get(itemIndex) == gp.player.currentWeapon||
                gp.player.inventory.get(itemIndex) == gp.player.currentSheld){
                    commandNum =0;
                    subState=0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue ="Không thể bán";
                }
                else {
                    gp.player.inventory.remove(itemIndex);
                    gp.player.coin+=price;
                }
            }
        }
    }


    public void drawTransition() {
        counter++;
        g2.setColor(new Color(0,0,0,counter*5));
        g2.fillRect(0, 0, gp.getWidth(), gp.getHeight());
        if(counter == 50){
            counter = 0;
            gp.gameState = gp.playState;
            gp.currentMap = gp.eHander.tempMap;
            gp.player.worldX = gp.tileSize * gp.eHander.tempCol;
            gp.player.worldY = gp.tileSize * gp.eHander.tempRow;
            gp.eHander.previousEventX = gp.player.worldX;
            gp.eHander.previousEventY = gp.player.worldY;
        }
    }

    public void drawGameOverScreen() {
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,110f));
        text ="Game Over";
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize*4;
        g2.drawString(text,x,y);
        g2.setColor(Color.white);
        g2.drawString(text,x-4,y-4);

        //Retry
        g2.setFont(g2.getFont().deriveFont(25f));
        text = "Thử lại";
        x = getXforCenteredText(text);
        y+= gp.tileSize*4;
        g2.drawString(text,x,y);
        if(commandNum == 0){
            g2.drawString(">",x-40,y);
        }
        //Tro ve mang hinh chinh
        text = "Thoát";
        x = getXforCenteredText(text);
        y+= 55;
        g2.drawString(text,x,y);
        if(commandNum == 1){
            g2.drawString(">",x-40,y);
        }
    }
    public void drawInventory(Entity entity,boolean cursor) {

        int frameX =0;
        int frameY =0;
        int frameWidth=0;
        int frameHeight=0;
        int slotCol=0;
        int slotRow=0;
        if(entity==gp.player){
            frameX = gp.tileSize*12;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize*6;
            frameHeight = gp.tileSize*5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        }
        else{
            frameX = gp.tileSize*2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize*6;
            frameHeight = gp.tileSize*5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }
        //khung

        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        //slot do
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize+3;
        // draw player item
        for(int i =0;i<entity.inventory.size();i++){
            if(entity.inventory.get(i) == entity.currentWeapon ||
                    entity.inventory.get(i)==entity.currentSheld){
                g2.setColor(new Color(231, 76, 60));
                g2.fillRoundRect(slotX,slotY,gp.tileSize,gp.tileSize,10,10);
            }
            g2.drawImage(entity.inventory.get(i).down1,slotX,slotY,null);
            slotX += slotSize;
            if(i == 4 || i == 9 || i == 14){
                slotX = slotXstart;
                slotY += slotSize;
            }
        }
        //CURSOR
        if(cursor == true){
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

            int itemIndex = getItemIndexOnSlot(slotCol,slotRow);
            if(itemIndex<entity.inventory.size()){

                drawSubWindow(dFrameX,dFrameY,dFrameWidth,dFrameHeight);

                for(String line: entity.inventory.get(itemIndex).description.split("\n")){
                    g2.drawString(line,textX,textY);
                    textY +=32;
                }
            }
        }
    }
    public int getItemIndexOnSlot(int slotCol,int slotRow){
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
    public void drawCharacterScreen() {
        //tạo khung
        final int frameX = gp.tileSize*2-5;
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
        g2.drawString("Năng lượng",textX,textY);
        textY+= lineHeight;
        g2.drawString("Sức Mạnh Công Kích",textX,textY);
        textY+= lineHeight;
        g2.drawString("Chịu đựng",textX,textY);
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
        textY+= lineHeight+10;
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

        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
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

        g2.drawImage(gp.player.currentWeapon.down1,tailX - gp.tileSize, textY-24,null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentSheld.down1,tailX-gp.tileSize,textY-24,null);


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

        g2.setFont(new Font("Arial", Font.PLAIN,20));
        g2.setColor(Color.white);
        x =10;
        y =400;
        int lineHeight =20;
        g2.drawString("Mo Iventory: C ",x,y); y+=lineHeight;
        g2.drawString("Ban Trung: F ",x,y); y+=lineHeight;
        g2.drawString("Attack: Enter ",x,y); y+=lineHeight;

    }
    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
    // màn hình nói chuyện
    public void drawDialogueScreeen() {
        // khung noi chuyen
        int x = gp.tileSize * 3;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 6);
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
        //mana
        x = (gp.tileSize/2)-5;
        y = (int)(gp.tileSize*1.5);
        i =0;
        while (i<gp.player.maxMana){
            g2.drawImage(manablank, x, y,null);
            i++;
            x+= 35;
        }
        //draw mana
        x = (gp.tileSize/2)-5;
        y = (int)(gp.tileSize*1.5);
        i =0;
        while (i<gp.player.mana){
            g2.drawImage(manafull, x, y,null);
            i++;
            x+=35;
        }
    }
    public void drawOptionScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(20F));

        //cua so
        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0:
                option_top(frameX, frameY);
                break;
            case 1:
                option_fullScreenNotification(frameX, frameY);
                break;
            case 2:
                option_control(frameX, frameY);
                break;
                case 3: option_endgameConfirmation(frameX,frameY); break;
        }
        gp.keyH.enterPressed = false;
    }
    public void option_top(int frameX, int frameY){
        int textX;
        int textY;

        //title
        String text ="Cài đặt";
        textX = getXforCenteredText(text);
        textY = frameY+ gp.tileSize;
        g2.drawString(text, textX, textY);
        // fullscreen option
        textX = frameX +gp.tileSize;
        textY += gp.tileSize*2;
        g2.drawString( "Toàn màn hình", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true) {
                gp.fullScreenOn = !gp.fullScreenOn;
                gp.keyH.enterPressed = false;
                subState = 1;
            }
        }

        // music
        textY += gp.tileSize;
        g2.drawString("Nhạc nền", textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX-25, textY);
        }
        //se
        textY += gp.tileSize;
        g2.drawString("Hiệu ứng", textX, textY);
        if(commandNum == 2){
            g2.drawString(">", textX-25, textY);
        }
        //control
        textY += gp.tileSize;
        g2.drawString("Điều khiển", textX, textY);
        if(commandNum == 3){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed==true){
                subState = 2;
                commandNum = 0;
            }
        }
        // end game
        textY += gp.tileSize;
        g2.drawString("Kết thúc", textX, textY);
        if(commandNum == 4){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed==true){
                subState = 3;
                commandNum = 0;
            }
        }

        textY += gp.tileSize*2;
        g2.drawString("Thoát", textX, textY);
        if(commandNum == 5){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed==true){
                gp.gameState = gp.playState;
                commandNum =0;
            }
        }
        //full screen
        textX = frameX + (int)(gp.tileSize*5);
        textY = frameY + gp.tileSize*2+24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY,24,24);
        if(gp.fullScreenOn == true){
            g2.fillRect(textX, textY,24,24);
        }
        //MUSIC
        textY += gp.tileSize;
        g2.drawRect(textX, textY,120,24); // 120/5 = 24
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);
        //se
        textY += gp.tileSize;
        g2.drawRect(textX, textY,120,24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        gp.config.saveconfig();
    }
    public void option_fullScreenNotification(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "Reset trò chơi để áp \ndụng cài đặt";
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        textY = frameY + gp.tileSize * 9;
        g2.drawString("Trờ về", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 0; //reset
                gp.keyH.enterPressed = false; //reset enter
            }
        }
    }
    public void option_control(int frameX, int frameY){
       int textX;
       int textY;

       String text = "Điều khiển";
       textX = getXforCenteredText(text);
       textY = frameY + gp.tileSize;
       g2.drawString(text, textX, textY);

       textX = frameX +gp.tileSize;
       textY += gp.tileSize;
       g2.drawString("Di chuyển", textX, textY); textY+=gp.tileSize;
       g2.drawString("Chọn/Tấn công ", textX, textY); textY+=gp.tileSize;
       g2.drawString("Bắn", textX, textY); textY+=gp.tileSize;
       g2.drawString("Mở Inventory", textX, textY); textY+=gp.tileSize;
       g2.drawString("Tạm dừng", textX, textY); textY+=gp.tileSize;
       g2.drawString("Cài đặt", textX, textY); textY+=gp.tileSize;

       textX = frameX +gp.tileSize*6;
       textY = frameY +gp.tileSize*2;

        g2.drawString("WASD", textX, textY); textY+=gp.tileSize;
        g2.drawString("ENTER", textX, textY); textY+=gp.tileSize;
        g2.drawString("F", textX, textY); textY+=gp.tileSize;
        g2.drawString("C", textX, textY); textY+=gp.tileSize;
        g2.drawString("P", textX, textY); textY+=gp.tileSize;
        g2.drawString("ESC", textX, textY); textY+=gp.tileSize;

        textX = frameX +gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Trờ về", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
                commandNum = 3;//reset
                gp.keyH.enterPressed = false; //reset enter
            }
        }
    }
    public void option_endgameConfirmation(int frameX, int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "Thoát trò chơi?";
        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }
        //yes
        String text = "Có";
        textX = getXforCenteredText(text);
        textY += gp.tileSize*3;
        g2.drawString(text, textX, textY);
        if(commandNum==0){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed==true) {
                subState = 0;
                gp.gameState = gp.titleState;
            }
        }
        text ="Không";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum==1){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed==true) {
                subState = 0;
                commandNum = 4;
            }
        }
    }
    public void addMessage(String text){
        message.add(text);
        messageCounter.add(0);
    }
}

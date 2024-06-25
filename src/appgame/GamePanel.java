/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgame;

import entity.Entity;
import entity.Player;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JPanel;

import tile.TileManager;
import tile_interactive.InteractiveTile;

/**
 *
 * @author Admin
 */
public class GamePanel extends JPanel implements Runnable {

    // MÀN HÌNH 
    final int originalTileSize = 16; // 16x16 màn hình set to nhỏ ở đây
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 46x46 size trên màng hình 16x3
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12; // 16x12

    public final int screenWidth = tileSize * maxScreenCol;  //48 x 16 = 768
    public final int screenHeight = tileSize * maxScreenRow; //48 x 12 = 576

    //WORLD SETTING
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    //FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);

    //SOUND 
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public EventHandler eHander = new EventHandler(this);
    public UI ui = new UI(this);
    Thread gameThread;

    //Thực thể và NPC
    public Player player = new Player(this, keyH);
    //Hiển thị 10 đối tượng cùng 1 lúc, VD: khi nhận objA thì objA sẽ mất khỏi màn hình để có thể thêm 1 obj.. vào ô trống
    public Entity obj[] = new Entity[20];
    public Entity npc[] = new Entity[10];
    public InteractiveTile iTile[] = new InteractiveTile[50];
    public Entity monster[] = new Entity[20];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    ArrayList<Entity> entiList = new ArrayList<>();//thực thể lớn


    //Trạng thái game có thể là đang ở menu, có thể là đang ở trong game
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void StartGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /*
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS; // cứ sau 0,01666666s thì sẽ vẽ lại màng hình, để có thể vẽ được 60 lần mỗi giây
        double nextDrawTime = System.nanoTime() + drawInterval;
        while(gameThread!=null){
            
            //1 update thong tin như vị trí nhân vật
            update();
            
            //2 vẽ màn hình với update thông tin
            repaint();
            
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
                if(remainingTime<0){
                    remainingTime = 0; }
                Thread.sleep((long) remainingTime);
                
                nextDrawTime += drawInterval;
            } catch (InterruptedException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
     */
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }

    public void update() {
        if (gameState == playState) {
            //player
            player.update();

            //NOC
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }
            for(int i=0;i<monster.length;i++) {
                if(monster[i] != null) {
                    if(monster[i].alive ==true && monster[i].dying == false){
                        monster[i].update();
                    }
                    if(monster[i].alive ==false){
                        monster[i].checkDrop();
                        monster[i] = null;
                    }
                }
            }
            for(int i=0;i<projectileList.size();i++) {
                if(projectileList.get(i) != null) {
                    if(projectileList.get(i).alive ==true){
                        projectileList.get(i).update();
                    }
                    if(projectileList.get(i).alive==false){
                        projectileList.remove(i);
                    }
                }
            }
            for(int i=0;i<iTile.length;i++){
                if(iTile[i] != null) {
                    iTile[i].update();
                }
            }
        }
        if (gameState == pauseState) {
            // chưa làm gì
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        long drawStart = 0;
        if(keyH.showDebugTex ==true){
            drawStart = System.nanoTime();
        }

        if (gameState == titleState) {
                ui.draw(g2);
        } else {
            //tile
            tileM.draw(g2);
            //tile interactive
            for(int i=0;i<iTile.length;i++){
                if(iTile[i]!=null){
                    iTile[i].draw(g2);
                }
            }

            //add vaào danh sach lơn
            entiList.add(player);
            for(int i =0;i<npc.length;i++){
                if(npc[i]!=null){
                    entiList.add(npc[i]);
                }
            }
            for(int i =0;i<obj.length;i++){
                if(obj[i]!=null){
                    entiList.add(obj[i]);
                }
            }

            for(int i =0;i<monster.length;i++){
                if(monster[i]!=null){
                    entiList.add(monster[i]);
                }
            }

            for(int i =0;i<projectileList.size();i++){
                if(projectileList.get(i)!=null){
                    entiList.add(projectileList.get(i));
                }
            }

            Collections.sort(entiList,new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY,e2.worldY);

                    return 0;
                }
            });
            //draw entity
            for(int i =0;i<entiList.size();i++){
                entiList.get(i).draw(g2);
            }
            //empty entity list
            entiList.clear();

            player.draw(g2);
            //UI
            ui.draw(g2);

            if(keyH.showDebugTex == true){
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;
                g2.setFont(new Font("Arial", Font.PLAIN,20));
                g2.setColor(Color.white);
                int x =10;
                int y =400;
                int lineHeight =20;
                g2.drawString("WorldX: "+player.worldX,x,y); y+=lineHeight;
                g2.drawString("WorldY: "+player.worldY,x,y); y+=lineHeight;
                g2.drawString("Col: "+(player.worldX + player.solidArea.x)/tileSize,x,y); y+=lineHeight;
                g2.drawString("Row: "+(player.worldY + player.solidArea.y)/tileSize,x,y); y+=lineHeight;
                g2.drawString("Draw Time: "+passed,x,y);
            }
            g2.dispose();
        }

    }

    public void setupGame() {
        //set ob
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        //set nhạc nền
        gameState = titleState;
    }
    
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }

}

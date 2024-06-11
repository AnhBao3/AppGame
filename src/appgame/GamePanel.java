/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgame;

import entity.Entity;
import entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JPanel;

import tile.TileManager;

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
    public Entity obj[] = new Entity[10];
    public Entity npc[] = new Entity[10];
    ArrayList<Entity> entiList = new ArrayList<>();//thực thể lớn

    //Trạng thái game có thể là đang ở menu, có thể là đang ở trong game
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int titleState = 0;

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
        }
        if (gameState == pauseState) {
            // chưa làm gì
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //DEBUG
        long drawStart = 0;
        if (keyH.checkDrawTime == true) {
            drawStart = System.nanoTime();
        }
        //hình nền
        if (gameState == titleState) {
                ui.draw(g2);
        } else {
            //tile
            tileM.draw(g2);

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
            for(int i =0;i<entiList.size();i++){
                entiList.remove(i);
            }
            //UI
            ui.draw(g2);

        }

    }

    public void setupGame() {
        //set ob
        aSetter.setObject();
        aSetter.setNPC();
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

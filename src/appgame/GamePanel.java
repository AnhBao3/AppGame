/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgame;

import ai.PathFinder;
import com.sun.tools.javac.Main;
import data.SaveLoad;
import entity.Entity;
import entity.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JPanel;

import enviroment.EnviromentManager;
import tile.Map;
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
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12; // 16x12

    public final int screenWidth = tileSize * maxScreenCol;  //48 x 20 = 960
    public final int screenHeight = tileSize * maxScreenRow; //48 x 12 = 576
    //full screen
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;

    //WORLD SETTING
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxMap =10;
    public int currentMap =0;

    //FPS
    int FPS = 60;

    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);

    //SOUND 
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public EventHandler eHander = new EventHandler(this);
    config config = new config(this);
    public UI ui = new UI(this);
    public PathFinder pFinder = new PathFinder(this);
    EnviromentManager eManager = new EnviromentManager(this);
    Thread gameThread;
    public EntityGenerator eGenerator = new EntityGenerator(this);

    //Thực thể và NPC
    public Player player = new Player(this, keyH);
    //Hiển thị 10 đối tượng cùng 1 lúc, VD: khi nhận objA thì objA sẽ mất khỏi màn hình để có thể thêm 1 obj.. vào ô trống
    public Entity obj[][] = new Entity[maxMap][20];
    public Entity npc[][] = new Entity[maxMap][10];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
    public Entity monster[][] = new Entity[maxMap][20];
    public ArrayList<Entity> particleList = new ArrayList<>();
    public Entity projectile[][] = new Entity[maxMap][20];
    //public ArrayList<Entity> projectileList = new ArrayList<>();
    ArrayList<Entity> entiList = new ArrayList<>();//thực thể lớn
    Map map = new Map(this);
    SaveLoad saveLoad = new SaveLoad(this);
    public CutsceneManager csManager = new CutsceneManager(this);
    public boolean dungeonOn = false;


    //Trạng thái game có thể là đang ở menu, có thể là đang ở trong game
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionState = 5;
    public final int gameOverState =6;
    public final int transitionState =7;
    public final int tradeState =8;
    public final int sleepState =9;
    public final int mapState =10;
    public final int cutscreneState =11;

    public boolean bossBattleOn = false;

    public int currentArea;
    public int nextArea;
    public final int outside = 50;
    public final int indoor = 51;
    public final int dungeon = 52;

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
                drawToTempScreen();
                drawToScreen();
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
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }
            for(int i=0;i<monster[1].length;i++) {
                if(monster[currentMap][i] != null) {
                    if(monster[currentMap][i].alive ==true && monster[currentMap][i].dying == false){
                        monster[currentMap][i].update();
                    }
                    if(monster[currentMap][i].alive ==false){
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }
            for(int i=0;i<projectile[1].length;i++) {
                if(projectile[currentMap][i] != null) {
                    if(projectile[currentMap][i].alive ==true){
                        projectile[currentMap][i].update();
                    }
                    if(projectile[currentMap][i].alive==false){
                        projectile[currentMap][i] = null;
                    }
                }
            }
            for(int i=0;i<particleList.size();i++) {
                if(particleList.get(i) != null) {
                    if(particleList.get(i).alive ==true){
                        particleList.get(i).update();
                    }
                    if(particleList.get(i).alive==false){
                        particleList.remove(i);
                    }
                }
            }
            for(int i=0;i<iTile[1].length;i++){
                if(iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }
            eManager.update();
        }
        if (gameState == pauseState) {
            // chưa làm gì
        }
    }


    public void setupGame() {
        //set ob
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        eManager.setup();
        currentArea = outside;
        nextArea = outside;
        //set nhạc nền
        gameState = titleState;
        tempScreen = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
        if(fullScreenOn==true){
            setFullScreen();
        }
    }
    public void resetGame(boolean restart){
        currentMap = outside;
        removeTempEntity();
        stopMusic();
        bossBattleOn = false;
        player.setDefaultPostions();
        player.restoreStatus();
        player.resetCounter();
        aSetter.setNPC();
        aSetter.setMonster();
        if(restart==true){
            player.setDefaultValues();
            aSetter.setObject();
            aSetter.setInteractiveTile();
            eManager.lighting.resetDay();
        }
    }

    public void drawToTempScreen(){
        long drawStart = 0;
        if(keyH.showDebugTex ==true){
            drawStart = System.nanoTime();
        }

        if (gameState == titleState) {
            ui.draw(g2);
        }
        //map
        else if(gameState==mapState){
            map.drawFullMapScreen(g2);
        }
        else {
            //tile
            tileM.draw(g2);
            //tile interactive
            for(int i=0;i<iTile[1].length;i++){
                if(iTile[currentMap][i]!=null){
                    iTile[currentMap][i].draw(g2);
                }
            }

            //add vaào danh sach lơn
            entiList.add(player);
            for(int i =0;i<npc[1].length;i++){
                if(npc[currentMap][i]!=null){
                    entiList.add(npc[currentMap][i]);
                }
            }
            for(int i =0;i<obj[1].length;i++){
                if(obj[currentMap][i]!=null){
                    entiList.add(obj[currentMap][i]);
                }
            }

            for(int i =0;i<monster[1].length;i++){
                if(monster[currentMap][i]!=null){
                    entiList.add(monster[currentMap][i]);
                }
            }

            for(int i =0;i<projectile[1].length;i++){
                if(projectile[currentMap][i]!=null){
                    entiList.add(projectile[currentMap][i]);
                }
            }

            for(int i =0;i<particleList.size();i++){
                if(particleList.get(i)!=null){
                    entiList.add(particleList.get(i));
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
            //mini map
            map.drawMiniMap(g2);
            csManager.draw(g2);
            eManager.draw(g2);
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
                g2.drawString("Draw Time: "+passed,x,y);y+=lineHeight;
                g2.drawString("God mode: "+keyH.godModeOn,x,y);
            }
        }

    }
    public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen,0,0,screenWidth2,screenHeight2,null);
        g.dispose();
    }
    public void setFullScreen(){
        //Lay thong tin cua man hinh
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(AppGame.window);

        //lay chieu rong va chieu dai cua mang hinh
        screenWidth2 = AppGame.window.getWidth();
        screenHeight2 = AppGame.window.getHeight();
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
    public void changeArea(){
        if(nextArea != currentArea){
            stopMusic();
            if(nextArea==outside){
                playMusic(0);
            }
            if(nextArea==indoor){
                playMusic(19);
            }
            if(nextArea==dungeon){
                playMusic(20);
            }
        }
        currentArea = nextArea;
        aSetter.setMonster();
    }
    public void removeTempEntity(){
        for(int mapNum = 0;mapNum<maxMap;mapNum++){
            for(int i=0;i<obj[1].length;i++){
                if(obj[mapNum][i]!=null&&obj[mapNum][i].temp==true){
                    obj[mapNum][i]=null;
                }
            }
        }
    }
}

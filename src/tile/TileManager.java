package tile;

import appgame.GamePanel;
import appgame.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][][];
    boolean drawPath = true;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        // loại gạch là 10
        tile = new Tile[100];
        getTileImage();
        loadMap("/res/maps/worldV3.txt",0);
        loadMap("/res/maps/interior01.txt",1);// chỉnh file world
        loadMap("/res/maps/dungeon01.txt",2);
        loadMap("/res/maps/dungeon02.txt",3);

    }

    public void getTileImage() {
        //dùng để định nghĩa các block trong game
        //ko dùng 0-9 vì world map sẽ bị lộn xộn ko ngay ngắn
            setup(9,"000",false);
            setup(0,"001",false);
            setup(1,"002",false);
            setup(2,"003",false);
            setup(3,"004",false);
            setup(4,"005",false);
            setup(5,"006",false);
            setup(6,"007",false);
            setup(7,"008",false);
            setup(8,"009",false);

            
            setup(10,"010",false);
            setup(11,"011",false);
            setup(12,"012",true);
            setup(13,"013",true);
            setup(14,"014",true);
            setup(15,"015",true);
            setup(16,"016",true);
            setup(17,"017",true);
            setup(18,"018",true);
            setup(19,"019",true);
            setup(20,"020",true);
            setup(21,"021",true);
            setup(22,"022",true);
            setup(23,"023",true);
            setup(24,"024",true);
            setup(25,"025",true);
            setup(26,"026",false);
            setup(27,"027",false);
            setup(28,"028",false);
            setup(29,"029",false);
            setup(30,"030",false);
            setup(31,"031",false);
            setup(32,"032",false);
            setup(33,"033",false);
            setup(34,"034",false);
            setup(35,"035",false);
            setup(36,"036",false);
            setup(37,"037",false);
            setup(38,"038",false);
            setup(39,"039",false);
            setup(40,"040",true);
            setup(41,"041",true);
            setup(42,"042",true);
            setup(43,"043",true);
            setup(44,"044",true);
            setup(45,"045",true);
            setup(46,"046",true);
            setup(47,"047",false);
            setup(48,"048",false);
            setup(49,"049",false);
            setup(50,"050",false);
            setup(51,"051",false);
            setup(52,"052",false);
            setup(53,"053",false);
            setup(54,"054",false);
            setup(55,"055",false);
            setup(56,"056",false);
            setup(57,"057",false);
            setup(58,"058",false);
            setup(59,"059",false);
            setup(60,"060",false);
            setup(61,"061",false);
            setup(62,"062",false);
            setup(63,"063",false);
            setup(64,"064",false);
            setup(65,"065",false);
            setup(66,"066",false);
            setup(67,"067",false);
            setup(68,"068",false);
            setup(69,"069",true);
            setup(70,"070",false);
            setup(71,"071",false);

    }
public void setup(int index,String imageName, boolean collision){
    UtilityTool uTool = new UtilityTool();
    try{
        tile[index] = new Tile();
        tile[index].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/" +imageName+ ".png"));
        tile[index].image = uTool.scaleImage(tile[index].image,gp.tileSize,gp.tileSize);
        tile[index].collision = collision;
    } catch(IOException e){
    e.printStackTrace();
}
}
    public void draw(Graphics2D g2) {
        //học khó điên
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
            
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;
            
            if(worldX+gp.tileSize > gp.player.worldX - gp.player.screenX && 
                worldX-gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY+gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY-gp.tileSize < gp.player.worldY + gp.player.screenY ){
                            g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }
            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
        if(drawPath == true){
            g2.setColor(new Color(255,0,0,70));
            for(int i =0;i<gp.pFinder.pathList.size();i++){
                int worldX = gp.pFinder.pathList.get(i).col*gp.tileSize;
                int worldY = gp.pFinder.pathList.get(i).row*gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;
                g2.fillRect(screenX,screenY,gp.tileSize,gp.tileSize);
            }
        }
    }

    public void loadMap(String filePath,int map) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath); // nhập dữ liệu từ file maps
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); // đọc nội dung file

            int col = 0;
            int row = 0;

            while (row < gp.maxWorldCol && row < gp.maxWorldCol) {
                String line = br.readLine(); // đọc 1 dòng duy nhất
                String[] numbers = line.split(" ");
                while (col < gp.maxWorldCol) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

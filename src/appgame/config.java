package appgame;

import java.io.*;

public class config {
    GamePanel gp;
    public config(GamePanel gp) {
        this.gp = gp;
    }
    public void saveconfig(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
            if(gp.fullScreenOn == true){
                bw.write("On");
            }
            if(gp.fullScreenOn == false) {
                bw.write("Off");
            }
            bw.newLine();
            //Music
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();
            bw.write(String.valueOf(gp.se.volumeScale));
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadconfig(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));
            String s = br.readLine();

            // full screen;
            if(s.equals("On")){
                gp.fullScreenOn = true;
            }
            if(s.equals("Off")){
                gp.fullScreenOn = false;
            }
            s = br.readLine();
            gp.music.volumeScale = Integer.parseInt(s);
            s = br.readLine();
            gp.se.volumeScale = Integer.parseInt(s);
            br.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

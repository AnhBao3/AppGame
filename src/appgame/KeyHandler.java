/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

/**
 *
 * @author Admin
 */
public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed,shotKeyPressed;
    boolean checkDrawTime = false;
    boolean showDebugTex =false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();
        if (gp.gameState == gp.titleState) {
            titleState(code);
        }
        else if (gp.gameState == gp.playState) {
            playState(code);
        }
        else if (gp.gameState == gp.pauseState) {
            pauseState(code);
        } //khi đang trong dialogueState thi nhan enter se tro ve playstate
        else if (gp.gameState == gp.dialogueState) {
            dialogueState(code);
        }
        else if (gp.gameState == gp.characterState) {
            characterState(code);
        }
        else if (gp.gameState == gp.optionState) {
            optionState(code);
        }
        else if (gp.gameState == gp.gameOverState) {
            gameOverState(code);
        }
        else if (gp.gameState == gp.tradeState) {
            tradeState(code);
        }
        else if (gp.gameState == gp.mapState) {
            mapState(code);
        }
    }
    public void tradeState(int code) {
        if(code == KeyEvent.VK_ENTER){
            enterPressed =true;
        }
        if(gp.ui.subState==0){
            if(code == KeyEvent.VK_W){
                gp.ui.commandNum--;
                if(gp.ui.commandNum<0){
                    gp.ui.commandNum=2;
                }
                gp.playSE(11);
            }
            if(code == KeyEvent.VK_S){
                gp.ui.commandNum++;
                if(gp.ui.commandNum>2){
                    gp.ui.commandNum=0;
                }
                gp.playSE(11);
            }
        }
        if(gp.ui.subState==1){
            npcInventory(code);
            if(code == KeyEvent.VK_ESCAPE){
                gp.ui.subState=0;
            }
        }
        if(gp.ui.subState==2){
            playerInventory(code);
            if(code == KeyEvent.VK_ESCAPE){
                gp.ui.subState=0;
            }
        }
    }
    public void gameOverState(int code) {
        if(code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if(gp.ui.commandNum<0){
                gp.ui.commandNum = 1;
            }
            gp.playSE(11);
        }
        if(code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if(gp.ui.commandNum>1){
                gp.ui.commandNum = 0;
            }
            gp.playSE(11);
        }
        if(code == KeyEvent.VK_ENTER) {
            if(gp.ui.commandNum==0){
                gp.gameState = gp.playState;
                gp.retry();
                gp.playMusic(0);
            }
            else if(gp.ui.commandNum==1){
                gp.gameState = gp.titleState;
                gp.restart();
            }
        }
    }

    public void optionState(int code) {
        if(code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
        int maxCommandNum = 0;
        switch(gp.ui.subState){
            case 0: maxCommandNum =5; break;
            case 3: maxCommandNum =1; break;
        }
        if(code == KeyEvent.VK_W){
            gp.ui.commandNum--;
            gp.playSE(11);
            if(gp.ui.commandNum <0){
                gp.ui.commandNum = maxCommandNum;
            }
        }

        if(code == KeyEvent.VK_S){
            gp.ui.commandNum++;
            gp.playSE(11);
            if(gp.ui.commandNum>maxCommandNum){
                gp.ui.commandNum =0;
            }
        }
        if(code == KeyEvent.VK_A){
            if(gp.ui.subState == 0){
                if(gp.ui.commandNum==1 && gp.music.volumeScale>0){
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(11);
                }
                if(gp.ui.commandNum==2 && gp.se.volumeScale>0){
                    gp.se.volumeScale--;
                    gp.playSE(11);
                }
            }
        }
        if(code == KeyEvent.VK_D){
            if(gp.ui.subState == 0){
                if(gp.ui.commandNum==1 && gp.music.volumeScale<5){
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(11);
                }
                if(gp.ui.commandNum==2 && gp.se.volumeScale<5){
                    gp.se.volumeScale++;
                    gp.playSE(11);
                }
            }
        }
    }

    public void titleState(int code)
    {
        if (gp.ui.titleScreenState == 0) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.ui.titleScreenState =1;

                }
                if (gp.ui.commandNum == 1) {

                }
                if (gp.ui.commandNum == 2) {
                    System.exit(0);
                }
            }
        }
        else if (gp.ui.titleScreenState == 1) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 3;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 3) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.gameState = gp.playState;
                    System.out.println("renekton");
                    gp.playMusic(0);

                }
                if (gp.ui.commandNum == 1) {
                    gp.gameState = gp.playState;
                    System.out.println("twitch");
                    gp.playMusic(0);

                }
                if (gp.ui.commandNum == 2) {
                    gp.gameState = gp.playState;
                    System.out.println("ahri");
                    gp.playMusic(0);

                }
                if (gp.ui.commandNum == 3) {
                    gp.ui.titleScreenState =0;
                }
            }
        }

    }
    public void playState(int code){
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_M) {
            gp.stopMusic();
        }
        if (code== KeyEvent.VK_N) {
            gp.playMusic(5);
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if (code == KeyEvent.VK_T) {
            if (showDebugTex == false) {
                showDebugTex = true;
            } else if (showDebugTex == true) {
                showDebugTex = false;
            }
        }
        if(code == KeyEvent.VK_R){
            gp.tileM.loadMap("/res/maps/worldV3.txt",0);
        }
        if(code == KeyEvent.VK_C){
            gp.gameState = gp.characterState;
        }
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.pauseState;
        }
        if(code == KeyEvent.VK_F){
            shotKeyPressed =true;
        }
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.optionState;
        }
        if(code == KeyEvent.VK_M){
            gp.gameState = gp.mapState;
        }
        if(code == KeyEvent.VK_X){
            if(gp.map.miniMapOn == false){
                gp.map.miniMapOn =true;
            }
            else {
                gp.map.miniMapOn =false;
            }
        }

    }
    public void mapState(int code){
        if(code == KeyEvent.VK_M){
            gp.gameState = gp.playState;

        }
    }
    public void pauseState(int code){
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.playState;
        }
    }
    public void dialogueState(int code){
        if (code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.playState;
        }
    }
    public void characterState(int code){
        if(code == KeyEvent.VK_C){
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER){
            gp.player.selectItem();
        }
        playerInventory(code);
    }
    public void playerInventory(int code){
        if(code == KeyEvent.VK_W){
            if(gp.ui.playerSlotRow!=0){
                gp.ui.playerSlotRow--;
                gp.playSE(11);
            }
        }
        if(code == KeyEvent.VK_S){
            if(gp.ui.playerSlotRow!=3){
                gp.ui.playerSlotRow++;
                gp.playSE(11);
            }
        }
        if(code == KeyEvent.VK_A){
            if(gp.ui.playerSlotCol!=0){
                gp.ui.playerSlotCol--;
                gp.playSE(11);
            }
        }
        if(code == KeyEvent.VK_D){
            if(gp.ui.playerSlotCol!=4){
                gp.ui.playerSlotCol++;
                gp.playSE(11);
            }
        }
    }
    public void npcInventory(int code){
        if(code == KeyEvent.VK_W){
            if(gp.ui.npcSlotRow!=0){
                gp.ui.npcSlotRow--;
                gp.playSE(11);
            }
        }
        if(code == KeyEvent.VK_S){
            if(gp.ui.npcSlotRow!=3){
                gp.ui.npcSlotRow++;
                gp.playSE(11);
            }
        }
        if(code == KeyEvent.VK_A){
            if(gp.ui.npcSlotCol!=0){
                gp.ui.npcSlotCol--;
                gp.playSE(11);
            }
        }
        if(code == KeyEvent.VK_D){
            if(gp.ui.npcSlotCol!=4){
                gp.ui.npcSlotCol++;
                gp.playSE(11);
            }
        }

    }
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if(code == KeyEvent.VK_F){
            shotKeyPressed =false;
        }
    }
}

package com.zj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Tank {

    int TankX ;
    int TankY;
    int tankSpeed ;
    int tankSize;
    GameClient gameClient;
    String tankImg ;
    boolean idGood;
    boolean alive = true;

    //定义四个方向按钮
    boolean leftBtn = false;
    boolean rightBtn = false;
    boolean upBtn = false;
    boolean downBtn = false;
    //定义坦克的方向状态
    Direction direction = Direction.STOP;

    //炮弹的方向
    Direction shellDirection;
    enum Direction{
        U,UR,R,RD,D,LD,L,LU,STOP
    }

    public void paint(Graphics graphics){
        if (!this.alive){
            return;
        }
        ImageIcon imageIcon = new ImageIcon(tankImg);
        Image image = imageIcon.getImage();
        graphics.drawImage(image, TankX, TankY, tankSize, tankSize, gameClient);

        tankMove();
    }

    public Tank(int tankX, int tankY, int tankSpeed, int tankSize, GameClient gameClient, String tankImg) {
        TankX = tankX;
        TankY = tankY;
        this.tankSpeed = tankSpeed;
        this.tankSize = tankSize;
        this.gameClient = gameClient;
        this.tankImg = tankImg;
    }

    public Tank(int tankX, int tankY, int tankSpeed, int tankSize, GameClient gameClient, String tankImg, boolean idGood) {
        TankX = tankX;
        TankY = tankY;
        this.tankSpeed = tankSpeed;
        this.tankSize = tankSize;
        this.gameClient = gameClient;
        this.tankImg = tankImg;
        this.idGood = idGood;
    }

    public void controlDirection(int KeyCode){
        switch(KeyCode){
            case KeyEvent.VK_LEFT:{
                leftBtn = true;
                break;
            }
            case KeyEvent.VK_RIGHT:{
                rightBtn = true;
                break;
            }
            case KeyEvent.VK_UP:{
                upBtn = true;
                break;
            }
            case KeyEvent.VK_DOWN:{
                downBtn = true;
                break;
            }
        }
        ensureDrection();
    }
    public void controlDirection(KeyEvent keyEvent){
        switch(keyEvent.getKeyCode()){
            case KeyEvent.VK_LEFT:{
                leftBtn = false;
                break;
            }
            case KeyEvent.VK_RIGHT:{
                rightBtn = false;
                break;
            }
            case KeyEvent.VK_UP:{
                upBtn = false;
                break;
            }
            case KeyEvent.VK_DOWN:{
                downBtn = false;
                break;
            }
            case KeyEvent.VK_F:{
                Shell shell = this.fire();
                gameClient.shells.add(shell);
                break;
            }
        }
        ensureDrection();
    }
    private void ensureDrection(){
        if (!upBtn&&!rightBtn&&!downBtn&&!leftBtn){
            direction = Direction.STOP;
        }
        if (upBtn&&!rightBtn&&!downBtn&&!leftBtn){
            direction = Direction.U;
        }
        if (upBtn&&rightBtn&&!downBtn&&!leftBtn){
            direction = Direction.UR;
        }
        if (!upBtn&&rightBtn&&!downBtn&&!leftBtn){
            direction = Direction.R;
        }
        if (!upBtn&&rightBtn&&downBtn&&!leftBtn){
            direction = Direction.RD;
        }
        if (!upBtn&&!rightBtn&&downBtn&&!leftBtn){
            direction = Direction.D;
        }
        if (!upBtn&&!rightBtn&&downBtn&&leftBtn){
            direction = Direction.LD;
        }
        if (!upBtn&&!rightBtn&&!downBtn&&leftBtn){
            direction = Direction.L;
        }
        if (upBtn&&!rightBtn&&!downBtn&&leftBtn){
            direction = Direction.LU;
        }
    }
    private void tankMove(){
        int oldTankX = this.TankX;
        int oldTankY = this.TankY;
        switch (direction){
            case U:{
                TankY = TankY -tankSpeed;
                tankImg = "images/up.png";
                break;
            }
            case UR:{
                TankX = TankX + tankSpeed;
                TankY = TankY - tankSpeed;
                tankImg = "images/right-up.png";
                break;
            }
            case R:{
                TankX = TankX + tankSpeed;
                tankImg = "images/right.png";
                break;
            }
            case RD:{
                TankX = TankX + tankSpeed;
                TankY = TankY + tankSpeed;
                tankImg = "images/right-down.png";
                break;
            }
            case D:{
                TankY = TankY + tankSpeed;
                tankImg = "images/down.png";
                break;
            }
            case LD:{
                TankX = TankX - tankSpeed;
                TankY = TankY + tankSpeed;
                tankImg = "images/left-down.png";
                break;
            }
            case L:{
                TankX = TankX - tankSpeed;
                tankImg = "images/left.png";
                break;
            }
            case LU:{
                TankX = TankX - tankSpeed;
                TankY = TankY - tankSpeed;
                tankImg = "images/left-up.png";
                break;
            }

        }
        if (direction !=Direction.STOP){
            shellDirection = direction;
        }
        //判断出界
        if (TankX<=0||TankX>=gameClient.GAME_CLIENT_WIDTH - tankSize){
            this.TankX = oldTankX;
        }
        if (TankY<=30||TankY>=gameClient.GAME_CLIENT_HEIGHT - tankSize){
            this.TankY = oldTankY;
        }
    }
    public Shell fire(){
        return new Shell(TankX+tankSize/2-30,TankY+tankSize/2-30,tankSpeed*3,60,gameClient,"images/bomb-right.png",shellDirection);
    }
    public Rectangle getRectangle(){
        return new Rectangle(TankX,TankY,tankSize,tankSize);
    }

}

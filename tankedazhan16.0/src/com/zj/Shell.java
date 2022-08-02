package com.zj;

import javax.swing.*;
import java.awt.*;

public class Shell {

    int shellX ;
    int shellY;
    int shellSpeed ;
    int shellSize;
    GameClient gameClient;
    String shellImg ;
    Tank.Direction direction;
    boolean alive = true;


    public Shell(int shellX, int shellY, int shellSpeed, int shellSize, GameClient gameClient, String shellImg) {
        super();
        this.shellX = shellX;
        this.shellY = shellY;
        this.shellSpeed = shellSpeed;
        this.shellSize = shellSize;
        this.gameClient = gameClient;
        this.shellImg = shellImg;
    }

    public Shell(int shellX, int shellY, int shellSpeed, int shellSize, GameClient gameClient, String shellImg,Tank.Direction direction) {
        super();
        this.shellX = shellX;
        this.shellY = shellY;
        this.shellSpeed = shellSpeed;
        this.shellSize = shellSize;
        this.gameClient = gameClient;
        this.shellImg = shellImg;
        this.direction = direction;
    }
    public void paint(Graphics graphics){
        if (!alive){
            gameClient.shells.remove(this);
            return;
        }
        ImageIcon imageIcon = new ImageIcon(shellImg);
        Image image = imageIcon.getImage();
        graphics.drawImage(image, shellX, shellY, shellSize, shellSize, gameClient);

        shellMove();
    }
    private void shellMove(){
        switch (direction){
            case U:{
                shellY = shellY -shellSpeed;
                shellImg = "images/bomb-up.png";
                break;
            }
            case UR:{
                shellX = shellX + shellSpeed;
                shellY = shellY - shellSpeed;
                shellImg = "images/bomb-right-up.png";
                break;
            }
            case R:{
                shellX = shellX + shellSpeed;
                shellImg = "images/bomb-right.png";
                break;
            }
            case RD:{
                shellX = shellX + shellSpeed;
                shellY = shellY + shellSpeed;
                shellImg = "images/bomb-right-down.png";
                break;
            }
            case D:{
                shellY = shellY + shellSpeed;
                shellImg = "images/bomb-down.png";
                break;
            }
            case LD:{
                shellX = shellX - shellSpeed;
                shellY = shellY + shellSpeed;
                shellImg = "images/bomb-left-down.png";
                break;
            }
            case L:{
                shellX = shellX - shellSpeed;
                shellImg = "images/bomb-left.png";
                break;
            }
            case LU:{
                shellX = shellX - shellSpeed;
                shellY = shellY - shellSpeed;
                shellImg = "images/bomb-left-up.png";
                break;
            }
        }

        if (shellX<=0||shellX>=gameClient.GAME_CLIENT_WIDTH||shellY<=0||shellY>=gameClient.GAME_CLIENT_HEIGHT){
            gameClient.shells.remove(this);
        }
    }

    public Rectangle getRectangle(){
        return new Rectangle(shellX,shellY,shellSize,shellSize);
    }
    public void attackTank(Tank tank){
        if (this.getRectangle().intersects(tank.getRectangle())&&this.alive&&tank.alive){
            this.alive = false;
            tank.alive = false;
            gameClient.bomb = new Bomb(shellX,shellY,gameClient);
        }
    }

}

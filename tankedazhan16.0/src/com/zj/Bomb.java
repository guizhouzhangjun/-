package com.zj;

import javax.swing.*;
import java.awt.*;

//抽象化爆炸类并定义爆炸类的属性与行为
public class Bomb {
    //定义Bomb类的X，Y轴
    int bombX;
    int bombY;
    GameClient gameClient;

    public Bomb(int bombX, int bombY, GameClient gameClient) {
        super();
        this.bombX = bombX;
        this.bombY = bombY;
        this.gameClient=gameClient;
    }

    //为实现Bomb类的爆炸效果，所以使用数组定义其尺寸
    int[] bombSize={5,10,15,20,25,30,35,40,45,50,50,45,40,35,30,25,20,15,10,5};
    //定义Bomb类的爆炸步骤
    int step=0;
    String bombImg="images/bomb.png";
    //定义Bomb类的布尔值
    boolean alive=true;

    //Bomb类的构造方法，画出爆炸
    public void paint(Graphics graphics){
        //当爆炸播放完，其布尔值为false
        if(step==bombSize.length-1){
            alive=false;
        }
        //当爆炸存活时则执行paint栈的代码，否则不在画出爆炸
        if(!alive){
            return;
        }
        //实例化ImageIcon，并引入图片
        ImageIcon imageIcon=new ImageIcon(bombImg);
        //通过实例Image，得到图片
        Image image=imageIcon.getImage();
        /*通过Graphics方法得到图片在面板上，并设置图片的大小
         *并绘制到GameClient类里
         */
        graphics.drawImage(image, bombX, bombY, bombSize[step], bombSize[step], gameClient);
        //通过数组实现爆炸的播放
        step++;
    }
}

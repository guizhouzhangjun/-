package com.zj;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

//设置界面为不可改变的大小，避免后面定位失败；并定义界面的关闭方法
public class GameClient extends Frame {      //定义继承接口游戏界面的类
    public static void main(String[] args) {      //调用主方法
        GameClient gameClient=new GameClient();   //实例化类
        gameClient.createFrame();                 //调用继承接口的方法
    }
    static final int GAME_CLIENT_WIDTH = 1500;
    static final int GAME_CLIENT_HEIGHT = 900;


    Tank myTank = new Tank(10,40,5,80,this,"images/right.png",true);
    Tank enemyTank = new Tank(800,600,5,100,this,"images/boss-down.png",false);

    Bomb bomb = null;
    List<Shell> shells = new ArrayList<Shell>();

    @Override
    public void paint(Graphics graphics ){
        //画游戏背景
        ImageIcon imageIcon = new ImageIcon("images/bg-test.png");
        Image image =imageIcon.getImage();
        graphics.drawImage(image,0,0,GAME_CLIENT_WIDTH,GAME_CLIENT_HEIGHT,this);

        graphics.setColor(Color.red);
        graphics.drawString("当前方向炮弹总数为： "+shells.size(),10,60);


        myTank.paint(graphics);   //画坦克
        enemyTank.paint(graphics);
        for (int i = 0;i<shells.size();i++){
            Shell shell = shells.get(i);
            if (null!=shell){
                shell.paint(graphics);
                shell.attackTank(enemyTank);
            }
        }
        if (bomb != null){
            bomb.paint(graphics);
        }
    }
    @Override
    public void update(Graphics graphics ){
        Image image =createImage(GAME_CLIENT_WIDTH,GAME_CLIENT_HEIGHT);
        Graphics newGraphics = image.getGraphics();
        paint(newGraphics);
        newGraphics.dispose();
        graphics.drawImage(image,0,0,this);
    }
    //运行中存在缓冲的问题，所以调用update方法
//    public void update(Graphics graphics) {
//        Image imageBg=createImage(GAME_CLIENT_WIDTH,GAME_CLIENT_HEIGHT);  //设置了一个Image
//        Graphics newGraphics=imageBg.getGraphics();
//        Color color=newGraphics.getColor();   //得到了其默认的颜色
//        newGraphics.setColor(Color.green);
//        //设置同窗体大小的矩形（通过静态常量）
//        newGraphics.fillRect(0, 0, GAME_CLIENT_WIDTH, GAME_CLIENT_HEIGHT);
//        //添加到窗体中
//        graphics.drawImage(imageBg,0,0,GAME_CLIENT_WIDTH,GAME_CLIENT_HEIGHT,this);
//        //绘制出整个Image
//        paint(graphics);
//
//    }
    public void createFrame(){               //定义类的构造方法
        this.setTitle("自定义坦克大战");              //定义界面的标题
        this.setBounds(200, 100, GAME_CLIENT_WIDTH, GAME_CLIENT_HEIGHT);  //定义界面的位置与大小
        this.setVisible(true);               //使界面可视
        this.setResizable(false);            //使界面不可调整大小
        this.addWindowListener(new WindowAdapter(){

            //定义界面的关闭方法
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

        });


        this.addKeyListener(new MyKeyAdapter());



        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);
        thread.start();
    }
    private class MyRunnable implements Runnable{
        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(24);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                repaint();
            }
        }
    }
    private class MyKeyAdapter extends KeyAdapter { //定义继承KeyAdapter接口的类

        @Override
        public void keyPressed(KeyEvent keyEvent) {//调用了类的方法
            int keyCode = keyEvent.getKeyCode();
            myTank.controlDirection(keyCode);
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
            myTank.controlDirection(keyEvent);
        }
    }
}

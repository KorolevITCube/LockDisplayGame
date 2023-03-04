package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;

public class MyFrame extends JFrame {
    private Robot robot;
    private Timer timer;
    private int count = 0;
    private Frame window;

    public MyFrame(){
        try {
            robot = new Robot();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error! - " + e.getMessage());
        }

        timer = new Timer(10_000,new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // Делаем скриншот
                saveScreenShoot();
            }
        });
        timer.start();
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setVisible(true);
        setVisible(false);
    }

    private void saveScreenShoot(){
        count++;
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int height = dimension.height;
        int width = dimension.width;

        try{
            BufferedImage img = robot.createScreenCapture(new Rectangle(0,0,width,height));
            ImageIO.write(img,"PNG",new File("src/main/resources/images"+count+".png"));
            System.out.println(Calendar.getInstance().getTime().toString()+" screenshot was saved");
        }catch(Exception e){
            e.printStackTrace();
        }

        if(count >= 6){
            timer.stop();
            window = new Frame();
            window.setResizable(false);
            window.setBounds(0,0,width,height);
            window.setBackground(Color.RED);
            window.setAlwaysOnTop(true);
            window.setUndecorated(true);
            window.setOpacity(0.5f);
            window.addWindowListener(new WindowAdapter() {
                @Override
                public void windowIconified(WindowEvent e) {
                    window.setExtendedState(Frame.MAXIMIZED_BOTH);
                }
            });
            window.setVisible(true);

            timer = new Timer(10, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    window.toFront();
                }
            });
            timer.start();
        }
    }
}

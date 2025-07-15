import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class GamePanel extends JPanel {
    final int boardWidth = 500, boardHeight = 500;
    int x, y;
    int width = 40, height = 40;
    BufferedImage image, blood;
    Random random;
    Timer timer1, timer2;
    boolean death = false;

    // Objects
    KeyHandling keyH = new KeyHandling();
    Sound sound = new Sound();

    public GamePanel(){
        this.setPreferredSize(new Dimension(boardWidth, boardHeight));
        this.addMouseListener(keyH);
        random = new Random();
        randomPoints();
        timer1 = new Timer(1000/2, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
//                spiderDeath();
                randomPoints();
                repaint();
            }
        });
        timer2 = new Timer(1000/60, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                spiderDeath();
            }
        });
        timer2.start();
        timer1.start();
        getImage();
    }

    public void getImage(){
        try{
            image = ImageIO.read(getClass().getResourceAsStream("spider.png"));
            blood = ImageIO.read(getClass().getResourceAsStream("blood.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void randomPoints(){
        System.out.println("Yes");
        x = random.nextInt(461);
        y = random.nextInt(461);
    }

    public void spiderDeath(){
        System.out.println(x+" < "+keyH.mouseX+" && "+y+" < "+keyH.mouseY+" && "+keyH.mouseX+" < "+(x+width)+" && "+keyH.mouseY+" < "+(y+height));
        if(x < keyH.mouseX && y < keyH.mouseY &&  keyH.mouseX < x+width && keyH.mouseY < y+height){
            System.out.println("Spider is Dead");
            timer1.stop();
            timer2.stop();
            sound.playSound("soundFile.wav");
            death = true;
            repaint();
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        draw(g2);
    }

    public void draw(Graphics2D g2){
        g2.drawImage(image, x, y, width, height, null);
//        g2.setColor(Color.red);
//        g2.fillOval(0, 0, 40, 40);

        if(death){
            g2.drawImage(blood, x, y, width, height, null);
        }
    }
}

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Panel extends JPanel {
    final int topBar = 30;
    public int sizeX = 1000;
    public int sizeY = 600;
    int amountOfRaindrops = 10;
    int[][] raindrops = new int[amountOfRaindrops][2];
    long deltaTime = 25;
    int lengthX = 6;
    int lengthY = 18;
    int raindropWidth = (int)(Math.sqrt((lengthX*lengthX)+(lengthY*lengthY))/3);
    int raindropSpeed = 5;
    int floorHeight = 20;
    int slateFade = 255;
    public Panel()  {
        Timer updateTimer = new Timer("updateTimer");
        updateTimer.schedule(new TimerTask(){
            @Override
            public void run()   {
                repaint();
                moveRainDrops();
                updatePuddle();
                if(slateFade>0)
                    slateFade--;
            }
        }, 0, deltaTime);
        for (int[] raindrop:raindrops) {
            raindrop[0] = (int)(Math.random()*sizeX);
            raindrop[1] = (int)(Math.random()*sizeY);
        }
    }

    ArrayList<int[]> puddles = new ArrayList<>();
    private void moveRainDrops()    {
        for (int[] raindrop:raindrops) {
            raindrop[0] += raindropSpeed;
            raindrop[1] += raindropSpeed*3;
            if(raindrop[1]+lengthY>sizeY-floorHeight-topBar)   {
                raindrop[0] = (int)(Math.random()*sizeX);
                raindrop[1]=0;
                puddles.add(new int[]{raindrop[0], 0, 0,255});
            }
        }
    }
    double puddleGrowSpeed = 1;
    int puddleFadeSpeed = 5;
    int maxPuddleSize = 15;
    private void updatePuddle() {
        for (int[] puddle:puddles) {
            if(puddle[1]<maxPuddleSize&&puddle[2]==0)
                puddle[1]+=puddleGrowSpeed;
            else if(puddle[1]>=maxPuddleSize&&puddle[2]==0)
                puddle[2] = 1;
            else if(puddle[2] == 1&&puddle[3]>0)
                puddle[3] -= puddleFadeSpeed;
        }
    }


    private void renderRainDrop(Graphics g)   {
        for (int[] raindrop:raindrops) {
            for (int i = 0; i < raindropWidth; i++) {
                g.drawLine(raindrop[0]%sizeX+i, raindrop[1]+i, (raindrop[0]%sizeX)+lengthX+i, raindrop[1]+lengthY+i);
            }
        }
    }

    private void renderPuddle(Graphics g)   {
        for (int[] puddle:puddles) {
            g.setColor(new Color(35, 169, 230, puddle[3]));
            g.fillOval(puddle[0]%sizeX, sizeY-topBar-floorHeight, puddle[1], puddle[1]/2);
        }
    }

    private void renderTime(Graphics g) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        g.setFont(new Font("Roboto-Thin", Font.PLAIN, 200));
        g.setColor(Color.white);
        g.drawString(""+dtf.format(LocalDateTime.now()), sizeX/2-(int)(200*1.5), sizeY/2);
    }

    public void paintComponent(Graphics g)    {
        g.setColor(Color.GRAY.darker());
        g.fillRect(0, 0, sizeX, sizeY);
        g.setColor(Color.WHITE);
        g.fillRect(0, 540, sizeX, floorHeight);
        renderPuddle(g);
        renderRainDrop(g);
        renderTime(g);
        g.setColor(new Color(0,0,0,slateFade));
        g.fillRect(0,0, sizeX, sizeY);
    }
}

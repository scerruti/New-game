import javax.swing.*;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import static javax.imageio.ImageIO.read;

public class Thomas extends GameObject {
    private static final URL THOMAS_THEME = Thomas.class.getResource("ThomasThemeSong.wav");

    private BufferedImage[] spriteImageArray;
    private BufferedImage[] reverseImageArray;
    private int imageCounter = 0;
    private boolean goingLeft = false;
    private boolean goingRigtht = false;
    private boolean lastWayFacing = true;
    private boolean jumping = false;

    public void loadImages() {
        try {
            if (spriteImageArray == null) {
                spriteImageArray = new BufferedImage[8];
                spriteImageArray[0] = read(getClass().getResource("Thomas1.png"));
                spriteImageArray[1] = read(getClass().getResource("Thomas2.png"));
                spriteImageArray[2] = read(getClass().getResource("Thomas3.png"));
                spriteImageArray[3] = read(getClass().getResource("Thomas4.png"));
                spriteImageArray[4] = read(getClass().getResource("Thomas5.png"));
                spriteImageArray[5] = read(getClass().getResource("Thomas6.png"));
                spriteImageArray[6] = read(getClass().getResource("Thomas7.png"));
                spriteImageArray[7] = read(getClass().getResource("Thomas8.png"));
            }
            if (reverseImageArray == null) {
                reverseImageArray = new BufferedImage[8];
                reverseImageArray[0] = read(getClass().getResource("Reversed Thomas1.png"));
                reverseImageArray[1] = read(getClass().getResource("Reversed Thomas2.png"));
                reverseImageArray[2] = read(getClass().getResource("Reversed Thomas3.png"));
                reverseImageArray[3] = read(getClass().getResource("Reversed Thomas4.png"));
                reverseImageArray[4] = read(getClass().getResource("Reversed Thomas5.png"));
                reverseImageArray[5] = read(getClass().getResource("Reversed Thomas6.png"));
                reverseImageArray[6] = read(getClass().getResource("Reversed Thomas7.png"));
                reverseImageArray[7] = read(getClass().getResource("Reversed Thomas8.png"));
            }
        } catch (IOException e) {
            System.out.println("error reading from thomas sprite array");
        }
    }

    void playTheme() {
        AudioClip thomasThemeSong = JApplet.newAudioClip(THOMAS_THEME);
        thomasThemeSong.loop();
    }

    public void draw(Graphics2D graphics, AffineTransform backgroundTx) {
        /*
         * Draw Thomas with sprite files
         */
        graphics.setTransform(backgroundTx);
        transform.setToTranslation(ThomasShootEmUpController.SCREEN_WIDTH / 3, ThomasShootEmUpController.SCREEN_HEIGHT - 420);
        graphics.setTransform(transform);
        try {
            imageCounter = imageCounter % 8;
            BufferedImage thomasSpriteImage = spriteImageArray[imageCounter];
            BufferedImage reverseThomasImage = reverseImageArray[imageCounter];

            transform.setToTranslation(ThomasShootEmUpController.SCREEN_WIDTH / 3, ThomasShootEmUpController.SCREEN_HEIGHT - 420 + yOffset);
            graphics.setTransform(transform);
            int boxWidth;
            int boxHeight;
            Rectangle box;
            if (isGoingLeft() || lastWayFacing) {
                graphics.drawImage(thomasSpriteImage, 0, 0, null);
                lastWayFacing = true;
                boxWidth = thomasSpriteImage.getWidth(null);
                boxHeight = thomasSpriteImage.getHeight(null);
                box = new Rectangle(0, 0, boxWidth, boxHeight);
                shape = box.getBounds();
                graphics.setColor(Color.green);
            }
            if (isGoingRight() || !lastWayFacing) {
                graphics.drawImage(reverseThomasImage, 0, 0, null);
                lastWayFacing = false;
                boxWidth = thomasSpriteImage.getWidth(null);
                boxHeight = thomasSpriteImage.getHeight(null);
                box = new Rectangle(0, 0, boxWidth, boxHeight);
                shape = box.getBounds();
                graphics.setColor(Color.green);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("error reading thomas thomasSpriteImage from thomas sprite thomasSpriteImage array");
        }
    }

    boolean isGoingRight() {
        return goingRigtht;
    }

    boolean isGoingLeft() {
        return goingLeft;
    }

    void stop() {
        goingLeft = false;
        goingRigtht = false;
    }

    void jump() {
        jumping = true;
    }

    void endJump() {
        jumping = false;
    }

    boolean isJumping() {
        return jumping;
    }

    void goLeft() {
        goingLeft = true;
        goingRigtht = false;
    }

    void goRight() {
        goingLeft = false;
        goingRigtht = true;
    }

    void changeHeight(int jumpingVelocity) {
        yOffset += jumpingVelocity;
    }

    void advanceImage() {
        imageCounter = (imageCounter + 1) % spriteImageArray.length;
    }

    void reverseImage() {
        imageCounter = (imageCounter + 1) % reverseImageArray.length;
    }

}

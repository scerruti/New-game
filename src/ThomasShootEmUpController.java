import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;

//TODO: MAKE THE TRACKS APPEAR IN THEIR CORRECT PLACES

/***********************************************************************************************
 * David Frieder's Thomas Game Copyright 2018 David Frieder 4/28/2018 rev 2.1
 * Upper upperTrack/Thomas collision working
 ***********************************************************************************************/
public class ThomasShootEmUpController extends JComponent implements ActionListener, Runnable, KeyListener {
    static final int SCREEN_WIDTH = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    static final int SCREEN_HEIGHT = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    private final Thomas thomas = new Thomas();
    private JFrame mainGameWindow = new JFrame("NewGame");// Makes window with
    private AffineTransform backgroundTx = new AffineTransform();
    private Timer animationTicker = new Timer(40, this);
    private Timer jumpingTicker = new Timer(1000 / 60, this);
    private int initialJumpingVelocity = -31;
    private int jumpingVelocity = initialJumpingVelocity;
    private Graphics2D g2;
    private Road road = new Road();
    private UpperTrack upperTrack = new UpperTrack();
    private LowerTrack lowerTrack = new LowerTrack();

    //TODO give the ground and/or lower upperTrack tiles the same property as the upper upperTrack tiles, and change the jumping rules so that he falls as long as the box doesn't
    // intersect the tracks

    /***********************************************************************************************
     * Main
     ***********************************************************************************************/
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ThomasShootEmUpController());
    }

    /***********************************************************************************************
     * Run
     ***********************************************************************************************/
    @Override
    public void run() {
        //loadImages();
        thomas.loadImages();
        road.loadImages();
        upperTrack.loadImages();
        lowerTrack.loadImages();
        setUpMainGameWindow();
        thomas.playTheme();
        animationTicker.start();
        jumpingTicker.start();
    }

    /***********************************************************************************************
     * Paint
     ***********************************************************************************************/
    public void paint(Graphics g) {
        repaint();
        g2 = (Graphics2D) g;
        road.draw(g2, backgroundTx);// ........................ Draw Road
        upperTrack.draw(g2, backgroundTx);// ................. Draw Upper Tracks
        lowerTrack.draw(g2, backgroundTx);// ................. Draw Lower Tracks
        thomas.draw(g2, backgroundTx);
    }


    /***********************************************************************************************
     * Action Performed.....Respond to animation ticker and paint ticker
     ***********************************************************************************************/
    @Override
    public void actionPerformed(ActionEvent e) {
        thomas.translateTo(thomas.getyOffset());
        if (g2 == null) return;

        if (e.getSource() == animationTicker) {
            if (thomas.isGoingLeft()) {
                thomas.advanceImage();
                backgroundTx.setToTranslation(backgroundTx.getTranslateX() + 20, 0);
                if (backgroundTx.getTranslateX() > SCREEN_WIDTH) {
                    backgroundTx.setToTranslation(-SCREEN_WIDTH, 0);
                }
                repaint();
            } else if (thomas.isGoingRight()) {
                thomas.reverseImage();
                backgroundTx.setToTranslation(backgroundTx.getTranslateX() - 20, 0);
                if (backgroundTx.getTranslateX() < -SCREEN_WIDTH) {
                    backgroundTx.setToTranslation(SCREEN_WIDTH, 0);
                }
                repaint();
            }
        } else if (e.getSource() == jumpingTicker) {
            if (thomas.isJumping()) {
                thomas.changeHeight(jumpingVelocity);
                int gravityAcceleration = 1;
                jumpingVelocity += gravityAcceleration;

                if (thomas.intersects(upperTrack)) {
                    System.out.println("intersects");
                    if (jumpingVelocity > 0 && thomas.isAbove(upperTrack)) {
                        //thomasYOffset = -7*upperTrackYOffset - thomasBoxHeight;
                        jumpingVelocity = initialJumpingVelocity;
                        thomas.endJump();
                    }
                }

                if (thomas.getyOffset() > 0) {
                    jumpingVelocity = initialJumpingVelocity;
                    thomas.resetyOffset();
                    thomas.endJump();
                }
                repaint();
            }
        }
    }

    /***********************************************************************************************
     * Respond to key typed
     ***********************************************************************************************/
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /***********************************************************************************************
     * Respond to key pressed
     ***********************************************************************************************/
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) // going right
        {
            thomas.goRight();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) // going left
        {
            thomas.goLeft();
            animationTicker.start();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && e.getKeyCode() == KeyEvent.VK_RIGHT) {
            thomas.stop();
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            thomas.jump();
        }
    }

    /***********************************************************************************************
     * Respond to key released
     ***********************************************************************************************/
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) // going left
        {
            thomas.stop();
        }
    }

    /***********************************************************************************************
     * Set up main JFrame
     ***********************************************************************************************/
    private void setUpMainGameWindow() {
        mainGameWindow.setTitle("Thomas the tank");
        mainGameWindow.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        mainGameWindow.add(this);// Adds the paint method to the JFrame
        mainGameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainGameWindow.getContentPane().setBackground(new Color(200, 235, 255));
        mainGameWindow.setVisible(true);
        mainGameWindow.addKeyListener(this);
    }
}
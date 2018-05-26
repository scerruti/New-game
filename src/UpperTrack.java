import java.awt.*;
import java.awt.geom.AffineTransform;

public class UpperTrack extends Track {
    UpperTrack() {
        super();
        yOffset = ThomasShootEmUpController.SCREEN_HEIGHT * 0.2;
    }

    @Override
    public void draw(Graphics2D graphics, AffineTransform backgroundTx) {
        graphics.setTransform(backgroundTx);
        graphics.translate(0, ThomasShootEmUpController.SCREEN_HEIGHT / 2); // center in screen
        graphics.scale(1.5, 1.5);
        for (int i = 0; i < 2; i++) // fits upperTrack images to screen width
        {
            graphics.drawImage(image, 0, 0, null);
            graphics.translate(image.getWidth(null), 0);
            yOffset = image.getHeight(null);
        }
        Rectangle box = new Rectangle(
                -2 * image.getWidth(null),
                0,
                2 * image.getWidth(null),
                image.getHeight(null));
        shape = box.getBounds();
        transform = graphics.getTransform();
    }
}

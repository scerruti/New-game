import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

public abstract class GameObject {
    Image image;
    String imageFile = "";
    AffineTransform transform = new AffineTransform();
    Rectangle shape;
    double yOffset;

    public void loadImages() {
        if (image == null && !imageFile.isEmpty()) {
            image = Toolkit.getDefaultToolkit().createImage(getClass().getResource(imageFile));
        }
    }

    private AffineTransform getTransform() {
        return transform;
    }

    private Rectangle getShape() {
        return shape;
    }

    double getyOffset() {
        return yOffset;
    }

    void resetyOffset() {
        this.yOffset = (double) 0;
    }

    public void draw(Graphics2D graphics, AffineTransform backgroundTx) {
        graphics.setTransform(backgroundTx);
        graphics.translate(-ThomasShootEmUpController.SCREEN_WIDTH, ThomasShootEmUpController.SCREEN_HEIGHT - 200);
        graphics.scale(1.5, 1.5);
        for (int i = 0; i < (2 * (ThomasShootEmUpController.SCREEN_WIDTH / image.getWidth(null))) + 2; i++) // fits
        {
            graphics.drawImage(image, 0, 0, null);
            graphics.translate(image.getWidth(null), 0);
        }
    }

    void translateTo(double y) {
        transform.setToTranslation((double) 0, y);
    }

    boolean intersects(GameObject otherObject) {
        Area areaA = new Area(this.getShape());
        Area areaB = new Area(otherObject.getShape());
        System.out.println(areaA.getBounds().toString());
        System.out.println(areaB.getBounds().toString());
        System.out.println();
        areaA.transform(this.getTransform());
        areaB.transform(otherObject.getTransform());
        System.out.println(areaA.getBounds().toString());
        System.out.println(areaB.getBounds().toString());
        System.out.println();
        System.out.println();
        areaA.intersect(areaB);
        return !areaA.isEmpty();
    }

    boolean isAbove(GameObject otherObject) {
        return yOffset < otherObject.yOffset;
    }
}

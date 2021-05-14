package model.trees.r;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class RTreeGraphics extends JFrame {

    private final RTree rTree;

    public RTreeGraphics(RTree rTree) {
        this.rTree = rTree;

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("RTree Visualization");
        this.setSize(1000, 1000);
        this.setBackground(Color.WHITE);
        this.setLocationRelativeTo(null);

        this.getContentPane().add(new JRTree());
    }

    public void showTree() {
        this.setVisible(true);
    }

    private Color randomColor() {
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);

        return new Color(r, g, b);
    }

    private class JRTree extends JPanel{

        private static final int multiplier = 100;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawTreeRecursive(rTree.getRoot(),(Graphics2D) g);
        }

        private void drawTreeRecursive(RContainer container, Graphics2D g) {
            float x, y;
            float width, height;

            // Traverse the tree
            for (RNode node : container.getNodes()) {
                if (node instanceof RRectangle) {
                    RRectangle rRectangle = (RRectangle) node;

                    x = rRectangle.getPoint().x * multiplier;
                    y = 800 - ((rRectangle.getPoint().y) * multiplier);
                    width = (rRectangle.getWidth() * multiplier) + 10;
                    height = (rRectangle.getHeight() * multiplier) + 10;

                    g.setColor(randomColor());
                    g.setStroke(new BasicStroke(5));
                    g.draw(new Rectangle2D.Float(x, y, width, height));

                    drawTreeRecursive(rRectangle.getChild(), g);
                }
                else {
                    x = node.getPoint().x * multiplier;
                    y = 800 - ((node.getPoint().y) * multiplier);

                    g.setColor(Color.BLACK);
                    g.drawString(node.toString(), x, y);
                    g.fill(new Ellipse2D.Float(x, y, 10, 10));
                }
            }
        }
    }
}

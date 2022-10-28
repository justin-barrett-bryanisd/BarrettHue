
import java.awt.Color;
import java.awt.Point;
import java.util.HashSet;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jbarrett
 */
public class GridInformation {

    Color[][] colors;
    Color[][] solution;
    HashSet<Point> blocked;

    public GridInformation(int rowsI, int colsI, HashSet<Point> b) {
        colors = new Color[rowsI][colsI];
        solution = new Color[rowsI][colsI];
        for (int r = 0; r < colors.length; r++) {
            for (int c = 0; c < colors[r].length; c++) {
                if (Math.random() < .3) {
                    colors[r][c] = Color.blue;
                } else if (Math.random() < .5) {
                    colors[r][c] = Color.red;
                } else {
                    colors[r][c] = Color.yellow;
                }
            }
        }
        blocked = b;
    }

    public Color getColor(int r, int c) {
        return colors[r][c];
    }

    public void swapColors(int r1, int c1, int r2, int c2) {
        Color temp = colors[r1][c1];
        colors[r1][c1] = colors[r2][c2];
        colors[r2][c2] = temp;
    }

    public void setColor(int r, int c, Color color) {
        colors[r][c] = color;
    }

    public void createGradient(Color start, Color end) {
        colors[colors.length - 1][colors[colors.length - 1].length - 1] = end;
        colors[0][0] = start;
        double redDiff = end.getRed() - start.getRed();
        double blueDiff = end.getBlue() - start.getBlue();
        double greenDiff = end.getGreen() - start.getGreen();
        for (int r = 0; r < colors.length; r++) {
            for (int c = 0; c < colors[r].length; c++) {
                int red = (int) Math.round(start.getRed() + 1.0 * redDiff / colors.length * r);
                int green = (int) Math.round(start.getGreen() + 1.0 * greenDiff / colors[0].length * c);
                int blue = (int) Math.round(start.getBlue() + 1.0 * blueDiff / (colors.length + colors[0].length) * (r + c));
                colors[r][c] = new Color(red, green, blue);
                solution[r][c] = new Color(red, green, blue);
            }
        }
    }

    public void createPuzzle() {
        Random random = new Random();
        Color start = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        Color end = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        while (colorDiff(start, end) < 355) {
            start = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
            end = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        }
        createGradient(start, end);

        for (int i = 0; i < colors.length * colors[0].length * 2; i++) {
            int n1 = random.nextInt(colors.length), n2 = random.nextInt(colors[0].length), n3 = random.nextInt(colors.length), n4 = random.nextInt(colors[0].length);
            if (!blocked.contains(new Point(n1, n2)) && !blocked.contains(new Point(n3, n4))) {
                swapColors(n1, n2, n3, n4);
            }
        }

    }

    public int colorDiff(Color start, Color end) {
        int colorDiff=Math.abs(start.getRed() - end.getRed()) + Math.abs(start.getBlue() - end.getBlue()) + Math.abs(start.getGreen() - end.getGreen());
        System.out.println("ColorDiff:"+colorDiff);
        return Math.abs(start.getRed() - end.getRed()) + Math.abs(start.getBlue() - end.getBlue()) + Math.abs(start.getGreen() - end.getGreen());
    }

    public boolean solved() {
        for (int r = 0; r < colors.length; r++) {
            for (int c = 0; c < colors[r].length; c++) {
                if (!colors[r][c].equals(solution[r][c])) {
                    //System.out.println(r + " " + c);
                    return false;
                }
            }
        }
        System.out.println("CONGRATS");
        return true;
    }

}

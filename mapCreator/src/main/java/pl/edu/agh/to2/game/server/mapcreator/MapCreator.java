package pl.edu.agh.to2.game.server.mapcreator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by blueeyedhush on 11/26/15.
 */
public class MapCreator {
    private char[][] array2D = null;

    public static void main(String[] args) throws IOException {
        if(args.length < 2) {
            System.out.println("2 arguments required: .bmp file to process and output .map file name");
            return;
        }

        MapCreator creator = new MapCreator();
        creator.loadBmpFile(args[0]);
        creator.printMapToFile(args[1]);
    }

    public void loadBmpFile(String BMPFileName) throws IOException {
        Path bmpPath = Paths.get(BMPFileName);
        BufferedImage image = ImageIO.read(bmpPath.toUri().toURL());

        array2D = new char[image.getHeight()][image.getWidth()]; //*

        for (int xPixel = 0; xPixel < image.getHeight(); xPixel++) //*
        {
            for (int yPixel = 0; yPixel < image.getWidth(); yPixel++) //*
            {
                int color = image.getRGB(yPixel, xPixel); //*
                if (color == Color.BLACK.getRGB()) {
                    array2D[xPixel][yPixel] = 'x';
                } else {
                    array2D[xPixel][yPixel] = '.'; // ?
                }
            }
        }
    }

    public void printMapToFile(String filename) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(filename));
        for(int x = 0; x < array2D.length; x++)
        {
            for (int y = 0; y < array2D[x].length; y++)
            {
                out.write(array2D[x][y]);
            }
            out.write("\n");
        }
        out.close();
    }
}

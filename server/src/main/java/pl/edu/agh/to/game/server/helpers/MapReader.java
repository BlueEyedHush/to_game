package pl.edu.agh.to.game.server.helpers;

import com.google.common.base.Charsets;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MapReader {
    private static final int ACTIVE = 'x';
    private static final int INACTIVE = '.';

    public static boolean[][] getBoard(Path filepath) throws IOException {
        /* assumes filepath exists */
        try (BufferedReader reader = Files.newBufferedReader(filepath, Charsets.UTF_8)) {
            String[] lines = reader.lines().toArray(String[]::new);
            int width = lines[0].length();
            int height = lines.length;
            boolean[][] board = new boolean[width][height];
            for (int i = 0; i < lines.length; i++) {
            /* same size as the rest? */
                if (lines[i].length() != width) {
                    throw new RuntimeException("Board has at least one row of different length! (" + i + ")");
                }

                char[] row = lines[i].toCharArray();
                for (int j = 0; j < row.length; j++) {
                    /* transpose matrix so that x is first and y second coordinate */
                    board[j][i] = (row[j] == INACTIVE);
                }
            }

            return board;
        }
    }
}

package pl.edu.agh.to.game.server.helpers;

import com.google.common.base.Charsets;
import pl.edu.agh.to.game.common.state.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MapReader {
    private static final int ACTIVE = 'x';
    private static final int INACTIVE = '.';

    public static boolean[][] getBoard(Path filepath) throws IOException {
        /* assumes filepath exists */
        BufferedReader reader = Files.newBufferedReader(filepath, Charsets.UTF_8);
        int[][] intTempArray = reader.lines()
                .map(l -> l.chars().map(c -> (c == ACTIVE) ? 1 : 0).toArray())
                .toArray(int[][]::new);
        reader.close();

        int width = intTempArray[0].length;
        for(int i = 1; i < intTempArray.length; i++) {
            if(intTempArray[i].length != width) {
                throw new IllegalArgumentException("Board has at least one row of different length! (" + i + ")");
            }
        }

        /* @ToDo: create collector which does that */
        boolean[][] booleanArray = new boolean[intTempArray.length][];
        for(int i = 0; i < booleanArray.length; i++) {
            boolean[] row = new boolean[intTempArray[i].length];
            for(int j = 0; j < row.length; j++) {
                row[i] = (intTempArray[i][j] == 1);
            }
        }

        return booleanArray;
    }
}

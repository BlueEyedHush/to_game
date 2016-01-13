package pl.edu.agh.to.game.bot.board;

import org.apache.commons.lang3.ArrayUtils;
import pl.edu.agh.to.game.common.state.Vector;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestBoardFromFileFactory {
    private static final String COMMENT_LINE_MARKER = "#";
    private static final String FINISH_MARKER = "M";
    private static final String START_MARKER = "S";
    private static final char forbiddenPositionMarker = 'X';

    private Path path;
    private List<String> lines;
    private int scale;

    public TestBoardFromFileFactory(String filename, int scale) {
        this.scale = scale;
        filename = filename.startsWith("/") ? filename : "/" + filename;
        String pathStr = TestBoard.class.getResource(filename).getPath();
        this.path = FileSystems.getDefault().getPath(new File(pathStr).getAbsolutePath());
    }

    public TestBoardFromFileFactory(String filename) {
        this(filename, 1);
    }



    public TestBoard create() {
        try (Stream<String> stream = Files.lines(path)) {
            lines = stream.collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Vector finish = getPositionOfTag(FINISH_MARKER);
        Vector startingPosition = getPositionOfTag(START_MARKER);
        int optimalMoves = getOptimal()*1000;
        boolean[][] map = getBoardArray();
        boolean[][] scaledMap = getScaledBoard(map);
        return new TestBoard(finish, scaledMap, startingPosition, optimalMoves);
    }

    private Vector getPositionOfTag(String tag) {
        return IntStream.range(0, lines.size())
                .filter((lineNo) -> ! lines.get(lineNo).contains(COMMENT_LINE_MARKER))
                .filter((lineNo) -> lines.get(lineNo).contains(tag))
                .mapToObj((lineNo) -> new Vector(lineNo*scale, lines.get(lineNo).indexOf(tag)*scale))
                .findFirst().get();
    }

    private int getOptimal() {
        String optimalMovesStr = lines.stream()
                .filter((line) -> line.startsWith(COMMENT_LINE_MARKER))
                .findFirst()
                .get()
                .substring(COMMENT_LINE_MARKER.length());
        return Integer.parseInt(optimalMovesStr);
    }

    private boolean[][] getBoardArray() {
        return lines.stream()
                .filter((l) -> !l.contains(COMMENT_LINE_MARKER))
                .map(this::parseLine)
                .toArray(boolean[][]::new);
    }

    private boolean[][] getScaledBoard(boolean[][]board) {
        boolean[][] scaledBoard = new boolean[board.length*scale][board[0].length*scale];
        for (int origY = 0; origY < board.length; origY++) {
            for (int origX = 0; origX < board[0].length; origX++) {
                for (int i = 0; i < scale; i++) {
                    for (int j = 0; j < scale; j++) {
                        scaledBoard[origY*scale + i][origX*scale + j] = board[origY][origX];
                    }
                }
            }
        }

        return scaledBoard;
    }

    private boolean[] parseLine(String line) {
        Boolean[] positionsInLine = line.chars()
                .mapToObj((i) -> (char) i)
                .map((c) -> !c.equals(forbiddenPositionMarker))
                .toArray(Boolean[]::new);
        return ArrayUtils.toPrimitive(positionsInLine);
    }
}

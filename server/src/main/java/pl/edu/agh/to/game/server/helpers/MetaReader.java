package pl.edu.agh.to.game.server.helpers;

import com.google.common.base.Charsets;
import pl.edu.agh.to.game.common.state.Vector;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by blueeyedhush on 10.12.15.
 */

public class MetaReader {
    public static Pair<Vector, List<Metadata>> readMetadataFrom(Path filepath) throws IOException {
        /* assumes filepath exists */
        try(BufferedReader reader = Files.newBufferedReader(filepath, Charsets.UTF_8)) {
            List<List<String>> lines = reader.lines().map(MetaReader::splitOnCommasAndTrim).collect(Collectors.toList());

            if(lines.size() < 2) {
                throw new RuntimeException("To few entries in .meta file");
            }

            List<String> mapMetadata = lines.get(0);
            Vector finish = vectorFromStrings(mapMetadata.get(0), mapMetadata.get(1));

            List<List<String>> carInfos = lines.subList(1, lines.size());
            List<Metadata> carMetadatas = carInfos.stream()
                    .map(MetaReader::metadataFromFields)
                    .collect(Collectors.toList());

            return Pair.of(finish, carMetadatas);
        }
    }

    private static Metadata metadataFromFields(List<String> fields) {
        if(fields.size() != 2 || fields.size() != 3) {
            throw new RuntimeException("Incorrectly formatted .meta file");
        }

        Vector initPos = vectorFromStrings(fields.get(0), fields.get(1));
        return (fields.size() == 2) ? Metadata.player(initPos) : Metadata.bot(initPos, fields.get(2));
    }

    private static List<String> splitOnCommasAndTrim(String str) {
        String[] substrings = str.split(",");
        return Arrays.stream(substrings).map(String::trim).collect(Collectors.toList());
    }

    private static Vector vectorFromStrings(String x, String y) {
        int intX = Integer.valueOf(x);
        int intY = Integer.valueOf(y);
        return new Vector(intX, intY);
    }
}

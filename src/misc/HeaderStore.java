package misc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rando on 7/9/2016.
 */
public class HeaderStore {
    private static List<File> HEADER_DIRS = new ArrayList<>();

    static {
        HEADER_DIRS.add(new File("D:\\Projects\\Rewire\\rewire_desktop\\applib\\common\\headers"));
    }

    public static File findHeader(String headerName){

        for (File dir : HEADER_DIRS){
            File[] matches = dir.listFiles((dir1, name) -> {
                return name.equals(headerName);
            });

            if(matches.length > 0){
                return matches[0];
            }

        }

        return null;

    }

}

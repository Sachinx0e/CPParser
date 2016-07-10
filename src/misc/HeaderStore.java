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
        HEADER_DIRS.add(new File("D:\\Projects\\Rewire\\rewire_windows\\applib\\common\\headers"));
        String rewirelib_headers = "D:\\Projects\\Rewire\\rewire_windows\\rewirelib\\Headers\\";
        HEADER_DIRS.add(new File(rewirelib_headers + "application"));
        HEADER_DIRS.add(new File(rewirelib_headers + "categories"));
        HEADER_DIRS.add(new File(rewirelib_headers + "checkins"));
        HEADER_DIRS.add(new File(rewirelib_headers + "habits"));
        HEADER_DIRS.add(new File(rewirelib_headers + "http"));
        HEADER_DIRS.add(new File(rewirelib_headers + "misc"));
        HEADER_DIRS.add(new File(rewirelib_headers + "targets"));
        HEADER_DIRS.add(new File(rewirelib_headers + "reasons"));
        HEADER_DIRS.add(new File(rewirelib_headers + "reminders"));
        HEADER_DIRS.add(new File(rewirelib_headers + "rewards"));
        HEADER_DIRS.add(new File(rewirelib_headers + "units"));
        HEADER_DIRS.add(new File(rewirelib_headers + "users"));
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

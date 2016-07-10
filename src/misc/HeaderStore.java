package misc;

import keywords.Keyword;

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
        HEADER_DIRS.add(new File(rewirelib_headers));
        /**
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
         **/
    }

    public static File findHeader(String headerName,String headerDirName){

        for (int i = 0 ; i < HEADER_DIRS.size() ;i++){
            File headerSearchDir = HEADER_DIRS.get(i);
            String headerNameParsed = headerName;
            if(headerDirName.length() > 0){
                headerSearchDir = new File(headerSearchDir,headerDirName);
            }

            final String finalHeaderNameParsed = headerNameParsed;
            File[] matches = headerSearchDir.listFiles((dir1, name) -> {
                return name.equals(finalHeaderNameParsed);
            });

            if(matches != null && matches.length > 0){
                return matches[0];
            }

        }

        return null;

    }

}

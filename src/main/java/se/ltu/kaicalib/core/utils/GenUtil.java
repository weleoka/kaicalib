package se.ltu.kaicalib.core.utils;

import java.util.Arrays;
import java.util.List;

public class GenUtil {

    /**
     * Pass in a comma separated list of values and they will be returned as a String list
     *
     * todo Create similar method that instead takes a mixed number args of mixed types
     *  and tries to make toString on them and then assemble into an Array.
     *  This is needed because the MessageSource has such limited constructor options.
     *
     *
     * @return
     */
    public static String[] toArr(String args) {
        List<String> strList = Arrays.asList(args.split(","));
        String[] arr = strList.toArray(new String[strList.size()]);

        return arr;
    }
}

package org.YiiCommunity.GitterBot.utils;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JarUtils {
    public static boolean unpackResource(File jar, String fileName, File destinationFolder) {
        try {
            URLClassLoader cl = URLClassLoader.newInstance(new URL[]{jar.toURI().toURL()});

            URL url = cl.getResource(fileName);
            if (url != null) {
                if (!destinationFolder.exists())
                    destinationFolder.mkdir();
                Files.copy(cl.getResourceAsStream(fileName), Paths.get(destinationFolder.getAbsoluteFile().toPath().toString() + "/" + fileName));
                return true;
            } else {
                L.$D(L.ANSI_RED + "File " + fileName + " not don't in " + jar.getAbsolutePath());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

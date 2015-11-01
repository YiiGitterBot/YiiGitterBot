package org.YiiCommunity.GitterBot.utils;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.jar.JarFile;

public class JarUtils {
    public static boolean unpackResource(File jar, String fileName, File destinationFolder) {
        try {
            JarFile file = new JarFile(jar);
            if (file.getJarEntry(fileName) != null) {
                if (!destinationFolder.exists())
                    destinationFolder.mkdir();
                Files.copy(file.getInputStream(file.getJarEntry(fileName)), Paths.get(destinationFolder.getAbsoluteFile().toPath().toString() + "/" + fileName));
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

package org.YiiCommunity.GitterBot.loaders;

import org.YiiCommunity.GitterBot.utils.L;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

public class LibrariesLoader {

    public void load() {
        L.$("Loading libraries");
        URLClassLoader sysLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class sysClass = URLClassLoader.class;
        Method sysMethod = null;
        try {
            sysMethod = sysClass.getDeclaredMethod("addURL", new Class[]{URL.class});
            sysMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return;
        }
        for (File item : getModulesJars()) {
            try {
                L.$("Loading " + item.toURI().toURL());

                sysMethod.invoke(sysLoader, new Object[]{item.toURI().toURL()});

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        L.$("Libraries loaded");
    }

    public Collection<File> getModulesJars() {
        if (!Files.isDirectory(Paths.get("libs")))
            new File("libs").mkdir();
        return FileUtils.listFiles(new File("libs"), new JarsFilter(), new IOFileFilter() {
            @Override
            public boolean accept(File file) {
                return true;
            }

            @Override
            public boolean accept(File dir, String name) {
                return true;
            }
        });
    }

    private class JarsFilter implements IOFileFilter {
        @Override
        public boolean accept(File file) {
            return file.isFile() && file.getName().toLowerCase().endsWith(".jar");
        }

        @Override
        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(".jar");
        }
    }
}

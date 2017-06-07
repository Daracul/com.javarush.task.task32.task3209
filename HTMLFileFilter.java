package com.javarush.task.task32.task3209;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created by amalakhov on 07.06.2017.
 */
public class HTMLFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        if (f!=null) {
            if (f.isDirectory()){
                return true;
            }
            else {
                String end = f.getName();
                int dotIndex = end.lastIndexOf('.');
                String fileEnd = end.substring(dotIndex + 1);
                if (fileEnd.equalsIgnoreCase("html") || fileEnd.equalsIgnoreCase("htm")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "HTML и HTM файлы";
    }
}

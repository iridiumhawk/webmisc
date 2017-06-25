package com.cherkasov.web.htmleditor;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created by hawk on 03.10.2016.
 */
public class HTMLFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        if (f.isDirectory() || (f.getName().toLowerCase().endsWith(".html") ||
                f.getName().toLowerCase().endsWith(".htm"))) {
            return true;
        } else return false;
    }

    @Override
    public String getDescription() {
        return "HTML и HTM файлы";
    }
}

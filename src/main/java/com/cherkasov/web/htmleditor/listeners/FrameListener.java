package com.cherkasov.web.htmleditor.listeners;


import com.cherkasov.web.htmleditor.View;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by hawk on 26.08.2016.
 */
public class FrameListener extends WindowAdapter {
    View view;

    public FrameListener(View view) {
        this.view = view;
    }

    /**
     * Invoked when a window is in the process of being closed.
     * The close operation can be overridden at this point.
     *
     * @param e
     */
    @Override
    public void windowClosing(WindowEvent e) {
       view.exit();
    }
}

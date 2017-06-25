package com.cherkasov.web.htmleditor.actions;


import com.cherkasov.web.htmleditor.View;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by hawk on 27.08.2016.
 */
public class UndoAction extends AbstractAction {
    private View view;

    /**
     * Creates an {@code Action}.
     */
    public UndoAction(View view) {
        this.view = view;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        view.undo();
    }
}

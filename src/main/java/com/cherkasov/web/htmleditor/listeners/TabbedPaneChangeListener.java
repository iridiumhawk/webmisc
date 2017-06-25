package com.cherkasov.web.htmleditor.listeners;

import com.cherkasov.web.htmleditor.View;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Created by hawk on 26.08.2016.
 */
public class TabbedPaneChangeListener implements ChangeListener{
    View view;

    public TabbedPaneChangeListener(View view) {
        this.view = view;
    }

    /**
     * Invoked when the target of the listener has changed its state.
     *
     * @param e a ChangeEvent object
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        view.selectedTabChanged();
    }
}

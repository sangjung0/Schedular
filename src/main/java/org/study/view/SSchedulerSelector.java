package org.study.view;

import org.study.Constants;
import org.study.scheduler.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

public class  SSchedulerSelector extends JComboBox<String> {
    private static final  Class<? extends Scheduler>[] SCHEDULE_ALGORITHM = Constants.SCHEDULE_ALGORITHM;
    private static final String[] SCHEDULE_ALGORITHM_TOOLTIP = Constants.SCHEDULE_ALGORITHM_TOOLTIP;
    private static final float FONT_SIZE = Constants.BUTTON_FONT_SIZE;

    private final Map<String, Class<? extends Scheduler>> map;
    private Class<? extends Scheduler> selected;

    public SSchedulerSelector(){
        map = new HashMap<>();
        selected = SCHEDULE_ALGORITHM[0];
        for (Class<? extends Scheduler> c : SCHEDULE_ALGORITHM){
            map.put(c.getSimpleName(), c);
            addItem(c.getSimpleName());
        }
        setRenderer(new ToolTipComboBoxRenderer(SCHEDULE_ALGORITHM_TOOLTIP));

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<?> comboBox = (JComboBox<?>) e.getSource();
                selected = map.get((String) comboBox.getSelectedItem());
            }
        });

        // style
        setFont(getFont().deriveFont(FONT_SIZE));
    }

    public String getSchedulerName(Class<? extends Scheduler> s) {return s.getSimpleName();}
    public String getSelectedName(){
        return getSchedulerName(selected);
    }
    public Class<? extends Scheduler> getSelected() {
        return selected;
    }
    public Iterator<Class<? extends Scheduler>> iterator(){return new SchedulerIterator();}

    private static class SchedulerIterator implements Iterator<Class<? extends Scheduler>>{

        int index = 0;

        @Override
        public boolean hasNext() {
            return SCHEDULE_ALGORITHM.length > index;
        }

        @Override
        public Class<? extends Scheduler> next() {
            if(!hasNext()) throw new NoSuchElementException();
            return SCHEDULE_ALGORITHM[index++];
        }
    }

    private static class ToolTipComboBoxRenderer extends DefaultListCellRenderer {
        private final String[] tooltips;

        public ToolTipComboBoxRenderer(String[] tooltips) {
            this.tooltips = tooltips;
        }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (index > -1 && c instanceof JComponent) {
                ((JComponent) c).setToolTipText(tooltips[index]);
            }
            return c;
        }
    }
}

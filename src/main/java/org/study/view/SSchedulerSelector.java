package org.study.view;

import org.study.scheduler.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class  SSchedulerSelector extends JComboBox<String> {
    @SuppressWarnings("unchecked") //수정 할 것
    private static final  Class<? extends Scheduler>[] SCHEDULE_ALGORITHM = new Class[]{
            FCFS.class,
            HRRN.class,
            MultilevelFeedbackQueue.class,
            MultilevelQueue.class,
            RoundRobin.class,
            SJF.class,
            SRTF.class
    };
    private static final String[] SCHEDULE_ALGORITHM_TOOLTIP = {
        "\n선입선출\n",
        "\n에이징\n",
        "\n멀티레벨피드백큐\n",
        "\n멀티레벨큐\n",
        "\n라운드로빈\n",
        "\n짧은 잡 먼저\n",
        "\n남은 짧은 시간 먼저\n"
    };
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
    }

    public String getSelectedName(){
        return selected.getSimpleName();
    }
    public Class<? extends Scheduler> getSelected() {
        return selected;
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
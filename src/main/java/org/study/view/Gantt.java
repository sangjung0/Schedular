package org.study.view;

import org.study.GanttData;
import org.study.SProcess;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * JScrollPane을 확장하는 Gantt Chart를 표현하는 클래스
 */
public class Gantt extends JScrollPane {

    private static final int FRAME_PADDING = 5;
    private static final Color CHART_COLOR = Color.RED;

    private final JPanel chart;
    private final ArrayList<GanttData> ganttData;

    /**
     * Gantt 객체 생성
     */
    Gantt(ArrayList<GanttData> ganttData){
        chart = new JPanel();
        this.ganttData = ganttData;
        setBorder(new EmptyBorder(FRAME_PADDING,FRAME_PADDING,FRAME_PADDING,FRAME_PADDING));
        setViewportView(chart);
        chart.setLayout(new GridBagLayout());
    }

    /**
     * 간트 차트 리랜더링
     */
    public void reRandGantt(){
        chart.removeAll();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.BOTH;

        int length = ganttData.size();
        int executionTime = 0;
        for(int i = 0; i < length; i++){
            GanttData dt = ganttData.get(i);
            SProcess process = dt.process();
            constraints.gridx = i;

            constraints.gridy = 0;
            constraints.ipady = 10;
            String str = "&nbsp;".repeat(dt.time());
            str = "<html>"+ str + process.getName() + str + "<br>" + str + dt.time();
            chart.add(new Chart(str,
                    0 == length -1 ? Chart.SOLO :
                        i == 0 ? Chart.FIRST :
                                i == length-1 ? Chart.LAST :
                                        Chart.MIDDLE
                    ), constraints);

            constraints.gridy = 1;
            chart.add(new JLabel(String.valueOf(executionTime)), constraints);
            executionTime += dt.time();
        }
        chart.revalidate();
        chart.repaint();
    }

    /**
     * 차트 칸 하나하나를 표현하는 JLabel을 확장하는 클래스
     */
    private static class Chart extends JLabel{
        public static final int FIRST = 0;
        public static final int MIDDLE = 1;
        public static final int LAST = 2;
        public static final int SOLO = 3; //흑흑

        Chart(String name, int type){
            super(name);
            switch (type){
                case FIRST: setBorder(new MatteBorder(2, 2, 2, 1, CHART_COLOR)); break;
                case LAST: setBorder(new MatteBorder(2, 1, 2, 2, CHART_COLOR)); break;
                case MIDDLE: setBorder(new MatteBorder(2, 1, 2, 1, CHART_COLOR)); break;
                default: setBorder(new MatteBorder(2,2,2,2,CHART_COLOR));
            }
        }
    }
}

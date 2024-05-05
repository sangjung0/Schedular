package org.study.scheduler;

import org.study.GanttData;
import org.study.SProcess;

import java.util.ArrayList;

public class Test implements Scheduler{

    @Override
    public void run(ArrayList<SProcess> processes, ArrayList<GanttData> ganttData) {
        ganttData.clear();

        for(SProcess p : processes){
            p.setWaitingTime(1);
            p.setExecutionTime(1);
            ganttData.add(new GanttData(p, p.getBurstTime()));
        }
    }
}

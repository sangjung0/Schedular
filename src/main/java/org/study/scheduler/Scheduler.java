package org.study.scheduler;

import org.study.GanttData;
import org.study.SProcess;

import java.util.ArrayList;

public interface Scheduler {
    public void run(ArrayList<SProcess> processes, ArrayList<GanttData> ganttData);
}

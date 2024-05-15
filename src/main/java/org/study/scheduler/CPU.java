package org.study.scheduler;

import org.study.GanttData;
import org.study.SProcess;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class CPU {

    protected final SProcess emptyProcess;
    protected final PriorityQueue<SProcess> jobQ;
    protected SProcess currentProcess;
    protected int cpuActivatedTime;
    private SProcess ganttProcess;
    private int processRunTime;
    private final Scheduler scheduler;

    public CPU(Scheduler scheduler){
        emptyProcess = new SProcess("", 0, 1, 0, 1, 0, 0, 0);
        jobQ = new PriorityQueue<SProcess>(new ArrivalTimeComparator());
        currentProcess = null;
        cpuActivatedTime = 0;
        ganttProcess = null;
        processRunTime = 0;
        this.scheduler = scheduler;
    }

    public void start(ArrayList<SProcess> processes, ArrayList<GanttData> ganttData) {
        GanttData g;
        int runTime;

        jobQ.addAll(processes);
        cpuActivatedTime = runTime = 0;
        while(!jobQ.isEmpty() || !scheduler.readyQIsEmpty() || currentProcess != null){
            schedule(runTime);
            if((g = getGanttData(runTime)) != null) ganttData.add(g);
            runTime = cpuCycle();
        }
        currentProcess = null;
        if((g = getGanttData(runTime)) != null) ganttData.add(g);

    }

    private void schedule(int runTime) {
        Iterator<SProcess> it = scheduler.iterableReadyQ();
        while(it.hasNext()){
            SProcess p = it.next();
            if(p != currentProcess)p.setWaitingTime(p.getWaitingTime() + runTime);
        }
        jobQToReadyQ();

        if(currentProcess != null){
            if (currentProcess.getBurstTime() <= currentProcess.getExecutionTime()){
                currentProcess.setTurnAroundTime(cpuActivatedTime - currentProcess.getArrivalTime()); // 턴어라운드 타임
            } else scheduler.addReadyQ(currentProcess);
        }

        if(scheduler.readyQIsEmpty()) currentProcess = null;
        else currentProcess = scheduler.pollReadyQ();
    }

    protected void jobQToReadyQ(){
        SProcess p;
        while(!jobQ.isEmpty() && jobQ.peek().getArrivalTime() <= cpuActivatedTime){
            scheduler.addReadyQ(p = jobQ.poll());
            p.setWaitingTime(cpuActivatedTime - p.getArrivalTime());
        }
    }

    private GanttData getGanttData(int runTime){
        if (ganttProcess == currentProcess){
            processRunTime += runTime;
            return null;
        }else if(ganttProcess == null){
            ganttProcess = currentProcess;
            processRunTime = 0;
            return null;
        }
        GanttData g;
        g = new GanttData(ganttProcess.copy(), processRunTime+runTime);
        ganttProcess = currentProcess; //간트 데이터
        processRunTime = 0;
        return g;
    }

    private int cpuCycle(){
        if(currentProcess == null){
            cpuActivatedTime += 1;
            return 1;
        }
        int runTime;

        runTime = scheduler.getRunTime(currentProcess);
        if (currentProcess.getResponseTime() == 0) currentProcess.setResponseTime(cpuActivatedTime - currentProcess.getArrivalTime()); //리스폰스 타임
        currentProcess.setExecutionTime(currentProcess.getExecutionTime() + runTime); // 실행 시간
        cpuActivatedTime += runTime;

        return runTime;
    }

    protected static class ArrivalTimeComparator implements Comparator<SProcess> {
        @Override
        public int compare(SProcess o1, SProcess o2) {
            return o1.getArrivalTime() - o2.getArrivalTime();
        }
    }
}

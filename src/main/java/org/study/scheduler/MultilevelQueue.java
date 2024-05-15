package org.study.scheduler;

import org.study.SProcess;

import java.util.ArrayList;
import java.util.Iterator;

public class MultilevelQueue extends Scheduler{
    private static final int TIME_QUANTUM = 2;
    private static final int CRITERION = 15;
    private static final int RR_TIME_RATIO = 8;
    private static final int FCFS_TIME_RATIO = 2;

    private FCFS fcfs;
    private RoundRobin roundRobin;
    private int executionTime;
    private boolean cntProcessIsForeground;
    private int cntExecutionTime;

    public MultilevelQueue(){
        fcfs = new FCFS();
        roundRobin = new RoundRobin();
        cntProcessIsForeground = true;
        cntExecutionTime = 0;
        executionTime = RR_TIME_RATIO;
    }

    @Override
    protected int getRunTime(SProcess currentProcess) {
        int time;
        cntExecutionTime += (time = cntProcessIsForeground ? roundRobin.getRunTime(currentProcess) : fcfs.getRunTime(currentProcess));
        return time;
    }

    @Override
    protected void addReadyQ(SProcess process) {
        if(process.getPriority() > CRITERION) roundRobin.addReadyQ(process);
        else fcfs.addReadyQ(process);
    }

    @Override
    protected boolean readyQIsEmpty() {
        return roundRobin.readyQIsEmpty() && fcfs.readyQIsEmpty();
    }

    @Override
    protected SProcess pollReadyQ() {
        if (cntProcessIsForeground){
            if(cntExecutionTime < executionTime && !roundRobin.readyQIsEmpty()) return roundRobin.pollReadyQ();
            cntProcessIsForeground = false;
            executionTime = FCFS_TIME_RATIO;
            cntExecutionTime = 0;
        }
        if(cntExecutionTime < executionTime && !fcfs.readyQIsEmpty()) return fcfs.pollReadyQ();
        cntProcessIsForeground = true;
        if(cntExecutionTime > executionTime) executionTime = executionTime * RR_TIME_RATIO/FCFS_TIME_RATIO;
        else executionTime = RR_TIME_RATIO;
        cntExecutionTime = 0;
        return roundRobin.pollReadyQ();
    }

    @Override
    protected SProcess peekReadyQ() {
        if (cntProcessIsForeground){
            if(cntExecutionTime < executionTime)return roundRobin.peekReadyQ();
            return fcfs.peekReadyQ();
        }
        if(cntExecutionTime < executionTime) return fcfs.peekReadyQ();
        return roundRobin.peekReadyQ();
    }

    @Override
    protected Iterator<SProcess> iterableReadyQ() {
        ArrayList<SProcess> temp = new ArrayList<SProcess>();
        Iterator<SProcess> iter = roundRobin.iterableReadyQ();

        while(iter.hasNext()) temp.add(iter.next());
        iter = fcfs.iterableReadyQ();
        while(iter.hasNext()) temp.add(iter.next());

        return temp.iterator();
    }
}

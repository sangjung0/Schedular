package org.study.scheduler;

import org.study.SProcess;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * MultilevelFeedbackQueue 스케줄러 구현
 */
public class MultilevelFeedbackQueue implements Scheduler {
    private static final int Q1_TIME_QUANTUM = 8;
    private static final int Q2_TIME_QUANTUM = 16;
    private static final int Q1 = 1;
    private static final int Q2 = 2;
    private static final int Q3 = 3;

    private final FCFS q3;
    private final RoundRobin q2;
    private final RoundRobin q1;
    private int cntProcess;

    public MultilevelFeedbackQueue(){
        q1 = new RoundRobin(Q1_TIME_QUANTUM);
        q2 = new RoundRobin(Q2_TIME_QUANTUM);
        q3 = new FCFS();
        cntProcess = Q1;
    }

    @Override
    public int getRunTime(SProcess currentProcess) {
        if(cntProcess == Q1) return Q1_TIME_QUANTUM;
        if(cntProcess == Q2) return Q2_TIME_QUANTUM;
        return q3.getRunTime(currentProcess);
    }

    @Override
    public void addReadyQ(SProcess process) {
        if(process.getExecutionTime() == 0) q1.addReadyQ(process);
        else if(process.getExecutionTime() == Q1_TIME_QUANTUM) q2.addReadyQ(process);
        else q3.addReadyQ(process);
    }

    @Override
    public boolean readyQIsEmpty() {
        return q1.readyQIsEmpty() && q2.readyQIsEmpty() && q3.readyQIsEmpty();
    }

    @Override
    public SProcess pollReadyQ() {
        if(!q1.readyQIsEmpty()){cntProcess=Q1; return q1.pollReadyQ();}
        if(!q2.readyQIsEmpty()){cntProcess=Q2; return q2.pollReadyQ();}
        cntProcess = Q3;
        return q3.pollReadyQ();
    }

    @Override
    public SProcess peekReadyQ() {
        if(!q1.readyQIsEmpty()) return q1.peekReadyQ();
        if(!q2.readyQIsEmpty()) return q2.peekReadyQ();
        return q3.peekReadyQ();
    }

    @Override
    public Iterator<SProcess> iterator() {
        ArrayList<SProcess> temp = new ArrayList<SProcess>();
        Iterator<SProcess> iter;

        iter = q1.iterator();
        while(iter.hasNext()) temp.add(iter.next());
        iter = q2.iterator();
        while(iter.hasNext()) temp.add(iter.next());
        iter = q3.iterator();
        while(iter.hasNext()) temp.add(iter.next());

        return temp.iterator();
    }
}

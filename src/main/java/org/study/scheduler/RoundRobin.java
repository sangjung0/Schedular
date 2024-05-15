package org.study.scheduler;

import org.study.SProcess;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class RoundRobin extends NonPreemptiveScheduler{

    private static final int TIME_QUANTUM = 2;

    private final Queue<SProcess> readyQ;
    private final int timeQuantum;

    public RoundRobin(){
        this(TIME_QUANTUM);
    }

    public RoundRobin(int timeQuantum){
        readyQ = new LinkedList<SProcess>();
        this.timeQuantum = timeQuantum;
    }

    @Override
    protected int getRunTime(SProcess currentProcess) {
        int remainTime;
        remainTime = currentProcess.getBurstTime() - currentProcess.getExecutionTime();
        return timeQuantum <= remainTime ? timeQuantum : remainTime;
    }

    @Override
    protected void addReadyQ(SProcess process) {
        readyQ.add(process);
    }

    @Override
    protected boolean readyQIsEmpty() {
        return readyQ.isEmpty();
    }

    @Override
    protected SProcess pollReadyQ() {
        return readyQ.poll();
    }

    @Override
    protected SProcess peekReadyQ() {
        return readyQ.peek();
    }

    @Override
    protected Iterator<SProcess> iterableReadyQ() {
        return readyQ.iterator();
    }
}

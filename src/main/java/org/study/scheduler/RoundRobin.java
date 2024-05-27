package org.study.scheduler;

import org.study.Constants;
import org.study.SProcess;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * RoundRobin 스케줄러 구현
 */
public class RoundRobin implements Scheduler {

    private static final int TIME_QUANTUM = Constants.SCHEDULER_ROUND_ROBIN_TIME_QUANTUM;

    private final Queue<SProcess> readyQ;
    private final int timeQuantum;

    public RoundRobin(){
        this(TIME_QUANTUM);
    }

    public RoundRobin(int timeQuantum){
        readyQ = new LinkedList<SProcess>();
        this.timeQuantum = timeQuantum;
    }

    protected int getTimeQuantum(){return timeQuantum;}

    @Override
    public int getRunTime(SProcess currentProcess) {
        int remainTime = currentProcess.getBurstTime() - currentProcess.getExecutionTime();
        return timeQuantum <= remainTime ? timeQuantum : remainTime;
    }

    @Override
    public void addReadyQ(SProcess process) {
        readyQ.add(process);
    }

    @Override
    public boolean readyQIsEmpty() {
        return readyQ.isEmpty();
    }

    @Override
    public SProcess pollReadyQ() {
        return readyQ.poll();
    }

    @Override
    public SProcess peekReadyQ() {
        return readyQ.peek();
    }

    @Override
    public Iterator<SProcess> iterator() {
        return readyQ.iterator();
    }
}

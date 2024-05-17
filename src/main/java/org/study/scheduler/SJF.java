package org.study.scheduler;

import org.study.SProcess;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * SJF 스케줄러 구현 클래스
 */
public class SJF implements Scheduler {

    private final PriorityQueue<SProcess> readyQ;

    public SJF(){
        readyQ = new PriorityQueue<SProcess>(new BurstTimeComparator());
    }

    @Override
    public int getRunTime(SProcess currentProcess) {
        return currentProcess.getBurstTime() - currentProcess.getExecutionTime();
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

    /**
     * Burst Time 을 기준으로 하는 Comparator
     */
    private static class BurstTimeComparator implements Comparator<SProcess> {
        @Override
        public int compare(SProcess o1, SProcess o2) {
            return o1.getBurstTime() - o2.getBurstTime();
        }
    }
}

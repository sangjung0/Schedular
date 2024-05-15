package org.study.scheduler;

import org.study.SProcess;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class SJF extends NonPreemptiveScheduler{

    private final PriorityQueue<SProcess> readyQ;

    public SJF(){
        readyQ = new PriorityQueue<SProcess>(new BurstTimeComparator());
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

    protected static class BurstTimeComparator implements Comparator<SProcess> {
        @Override
        public int compare(SProcess o1, SProcess o2) {
            return o1.getBurstTime() - o2.getBurstTime();
        }
    }
}
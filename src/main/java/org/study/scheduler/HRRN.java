package org.study.scheduler;

import org.study.SProcess;

import java.util.Iterator;
import java.util.LinkedList;

public class HRRN extends NonPreemptiveScheduler{

    private final LinkedList<SProcess> readyQ;

    public HRRN(){
        readyQ = new LinkedList<>();
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
        SProcess min;
        readyQ.remove((min = peekReadyQ()));
        return min;
    }

    @Override
    protected SProcess peekReadyQ() {
        SProcess min;
        int time, temp;

        min = readyQ.getFirst();
        time = ((temp=min.getBurstTime() - min.getExecutionTime())+min.getWaitingTime())/temp;
        for(SProcess p: readyQ){
            if (time > (temp = ((temp=min.getBurstTime() - min.getExecutionTime())+min.getWaitingTime())/temp)){
                time = temp;
                min = p;
            }
        }
        return min;
    }

    @Override
    protected Iterator<SProcess> iterableReadyQ() {
        return readyQ.iterator();
    }
}

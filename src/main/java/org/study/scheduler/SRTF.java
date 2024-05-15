package org.study.scheduler;

import org.study.SProcess;

import java.util.Iterator;
import java.util.LinkedList;

public class SRTF extends Scheduler {

    private static final int TIME_QUANTUM = 1;
    private final LinkedList<SProcess> readyQ;

    public SRTF(){
        readyQ = new LinkedList<SProcess>();
    }

    @Override
    protected int getRunTime(SProcess currentProcess) {
        int remainTime;
        remainTime = currentProcess.getBurstTime() - currentProcess.getExecutionTime();
        return TIME_QUANTUM <= remainTime ? TIME_QUANTUM : remainTime;
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
        time = min.getBurstTime() - min.getExecutionTime();
        for(SProcess p: readyQ){
            if (time > (temp = p.getBurstTime() - p.getExecutionTime())){
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

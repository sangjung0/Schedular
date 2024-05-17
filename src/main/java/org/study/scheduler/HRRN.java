package org.study.scheduler;

import org.study.SProcess;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * HRRN 스케줄러 구현
 */
public class HRRN implements Scheduler {

    private final LinkedList<SProcess> readyQ;

    public HRRN(){
        readyQ = new LinkedList<>();
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
        SProcess min;
        readyQ.remove((min = peekReadyQ()));
        return min;
    }

    @Override
    public SProcess peekReadyQ() {
        // 프로세스를 확인할 때마다 우선순위가 바뀌므로 항상 계산
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
    public Iterator<SProcess> iterator() {
        return readyQ.iterator();
    }
}

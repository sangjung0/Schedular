package org.study.scheduler;

import org.study.SProcess;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * SRTF 스케줄러 구현
 */
public class SRTF implements Scheduler {

    private static final int TIME_QUANTUM = 1;
    private final LinkedList<SProcess> readyQ;

    public SRTF(){
        readyQ = new LinkedList<SProcess>();
    }

    @Override
    public int getRunTime(SProcess currentProcess) {
        int remainTime;
        remainTime = currentProcess.getBurstTime() - currentProcess.getExecutionTime();
        return TIME_QUANTUM <= remainTime ? TIME_QUANTUM : remainTime;
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
    public Iterator<SProcess> iterator() {
        return readyQ.iterator();
    }
}

package org.study.scheduler;

import org.study.Constants;
import org.study.SProcess;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * MultilevelQueue 스케줄러 구현
 */
public class MultilevelQueue implements Scheduler{
    private static final int CRITERION = Constants.SCHEDULER_MULTILEVEL_QUEUE_CRITERION;
    private static final int RR_TIME_RATIO = Constants.SCHEDULER_MULTILEVEL_QUEUE_RR_TIME_RATIO;
    private static final int FCFS_TIME_RATIO = Constants.SCHEDULER_MULTILEVEL_QUEUE_FCFS_TIME_RATIO;

    // Foreground = roundRobin, Background = fcfs
    private final FCFS fcfs;
    private final RoundRobin roundRobin;
    private int executionTime; // 실행 해야 할 시간
    private int cntExecutionTime; // 현재 프로세스가 실행 한 시간
    private boolean cntProcessIsForeground; // 마지막으로 poll된 프로세스가 ForeGround 인지의 여부

    public MultilevelQueue(){
        fcfs = new FCFS();
        roundRobin = new RoundRobin();
        cntProcessIsForeground = true;
        cntExecutionTime = 0;
        executionTime = RR_TIME_RATIO;
    }

    @Override
    public int getRunTime(SProcess currentProcess) {
        int time;
        cntExecutionTime += (time = cntProcessIsForeground ? roundRobin.getRunTime(currentProcess) : fcfs.getRunTime(currentProcess));
        return time;
    }

    @Override
    public void addReadyQ(SProcess process) {
        if(process.getPriority() > CRITERION) roundRobin.addReadyQ(process);
        else fcfs.addReadyQ(process);
    }

    @Override
    public boolean readyQIsEmpty() {
        return roundRobin.readyQIsEmpty() && fcfs.readyQIsEmpty();
    }

    @Override
    public SProcess pollReadyQ() {
        if (cntProcessIsForeground){
            if(cntExecutionTime < executionTime && !roundRobin.readyQIsEmpty()) return roundRobin.pollReadyQ();
            cntProcessIsForeground = false;
            executionTime = FCFS_TIME_RATIO;
            cntExecutionTime = 0;
        }
        if(cntExecutionTime < executionTime && !fcfs.readyQIsEmpty()) return fcfs.pollReadyQ();
        cntProcessIsForeground = true;
        /*
        만약 FCFS 스케줄러를 통해 실행된 프로세스가 FCFS_TIME_RATIO 값 이상으로 실행됐다면, R&R 스케줄러가 그만큼 더 많은 CPU 타임을 가질 수 있게 함.
         */
        if(cntExecutionTime > executionTime) executionTime = cntExecutionTime * RR_TIME_RATIO/FCFS_TIME_RATIO;
        else executionTime = RR_TIME_RATIO;
        cntExecutionTime = 0;
        if (roundRobin.readyQIsEmpty()) return pollReadyQ();
        return roundRobin.pollReadyQ();
    }

    @Override
    public SProcess peekReadyQ() {
        if (cntProcessIsForeground){
            if(cntExecutionTime < executionTime)return roundRobin.peekReadyQ();
            return fcfs.peekReadyQ();
        }
        if(cntExecutionTime < executionTime) return fcfs.peekReadyQ();
        return roundRobin.peekReadyQ();
    }

    @Override
    public Iterator<SProcess> iterator() {
        ArrayList<SProcess> temp = new ArrayList<SProcess>();
        Iterator<SProcess> iter;

        iter = roundRobin.iterator();
        while(iter.hasNext()) temp.add(iter.next());
        iter = fcfs.iterator();
        while(iter.hasNext()) temp.add(iter.next());

        return temp.iterator();
    }
}

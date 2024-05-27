package org.study.scheduler;

import org.study.Constants;
import org.study.GanttData;
import org.study.SProcess;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * 스케줄러 동작을 위한 Processor 모방 클래스
 * 객체 재사용을 권장하지 않음
 */
public class Processor {
    private static final String WAIT = Constants.PROCESS_WAIT;

    protected final SProcess emptyProcess;
    protected final PriorityQueue<SProcess> jobQ;
    private final Scheduler scheduler;
    protected SProcess currentProcess;
    protected int cpuActivatedTime;
    private SProcess ganttProcess;
    private int processRunTime;
    private int contextSwitchingCount;

    /**
     * Processor 생성. 전달 받은 스케줄러를 기반으로 동작
     * @param scheduler 스케줄러 클래스
     */
    public <T extends Scheduler> Processor(Class<T> scheduler) throws Exception {
        this.scheduler = scheduler.getDeclaredConstructor().newInstance();
        emptyProcess = new SProcess(WAIT, 0, 1, 0, 0, 0, 0, 0);
        jobQ = new PriorityQueue<SProcess>(new ArrivalTimeComparator());
        currentProcess = emptyProcess.copy();
        currentProcess.setExecutionTime(1);
        cpuActivatedTime = 0;
        ganttProcess = currentProcess;
        processRunTime = 0;
        contextSwitchingCount = 0;
    }

    /**
     * processes 데이터 기반으로 스케줄러 구동. 결과 값은 processes 객체와 ganttData에 저장. 문맥 교환 횟수는 반환
     * @param processes 스케줄링 할 프로세스들
     * @param ganttData 결과 값을 받을 객체
     * @return 문맥 교환 횟수 반환
     */
    public int start(ArrayList<SProcess> processes, ArrayList<GanttData> ganttData) {
        GanttData g;
        int runTime;

        jobQ.addAll(processes);
        while(!jobQ.isEmpty() || !scheduler.readyQIsEmpty() || !currentProcess.equals(emptyProcess)){
            runTime = schedule();
            if((g = getGanttData(runTime)) != null) ganttData.add(g);
            cpuCycle(runTime);
        }

        return contextSwitchingCount;
    }

    /**
     * 스케줄 함수 프로세스를
     *  - job Q에서 ready Q로 이동
     *  - 현재 프로세스 종료 여부 판단
     *  - 프로세스 선택
     *  - waiting time 조정
     *  - 문맥 교환 횟수 측정
     * @return 프로세스 실행 시간
     */
    private int schedule() {
        SProcess p;
        int runTime;

        // jobQ에서 readyQ로 이동
        while(!jobQ.isEmpty() && jobQ.peek().getArrivalTime() <= cpuActivatedTime){
            scheduler.addReadyQ(p = jobQ.poll());
            p.setWaitingTime(cpuActivatedTime - p.getArrivalTime());
        }

        // 현재 프로세스 종료 여부 판단
        if (currentProcess.getBurstTime() <= currentProcess.getExecutionTime()){
            currentProcess.setTurnaroundTime(cpuActivatedTime - currentProcess.getArrivalTime()); // 턴어라운드 타임
        } else scheduler.addReadyQ(currentProcess);

        // 프로세스 선택
        if(scheduler.readyQIsEmpty()) currentProcess = emptyProcess.copy();
        else {
            p = scheduler.pollReadyQ();
            // 문맥 교환 횟수 증가
            if(!currentProcess.equals(emptyProcess) && currentProcess != p) contextSwitchingCount++;
            currentProcess = p;
        }

        runTime = scheduler.getRunTime(currentProcess);

        // waiting 타임 추가
        Iterator<SProcess> it = scheduler.iterator();
        while(it.hasNext()){
            p = it.next();
            p.setWaitingTime(p.getWaitingTime() + runTime);
        }

        return runTime;
    }

    /**
     * 프로세스 정보와 실행 시간을 받아서 간트 데이터 생성
     * @param runTime 프로세스 실행 시간
     * @return 간트차트 데이터
     */
    private GanttData getGanttData(int runTime){
        if (ganttProcess.equals(currentProcess)){
            processRunTime += runTime;
            return null;
        }else if(processRunTime == 0){
            ganttProcess = currentProcess;
            processRunTime = runTime;
            return null;
        }
        GanttData g = new GanttData(ganttProcess.copy(), processRunTime);
        ganttProcess = currentProcess; //간트 데이터
        processRunTime = runTime;
        return g;
    }

    /**
     * run 역할을 함. 프로세스를 실행하고, 실행 시간과 response time 을 기록
     * @param runTime 프로세스 실행 시간
     */
    private void cpuCycle(int runTime){
        if (currentProcess.getResponseTime() < 0) currentProcess.setResponseTime(cpuActivatedTime - currentProcess.getArrivalTime()); //리스폰스 타임
        currentProcess.setExecutionTime(currentProcess.getExecutionTime() + runTime); // 실행 시간
        cpuActivatedTime += runTime;
    }

    /**
     * 도착 시간을 기준으로 하는 Comparator
     */
    protected static class ArrivalTimeComparator implements Comparator<SProcess> {
        @Override
        public int compare(SProcess o1, SProcess o2) {
            return o1.getArrivalTime() - o2.getArrivalTime();
        }
    }
}

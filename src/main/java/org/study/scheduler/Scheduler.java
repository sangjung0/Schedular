package org.study.scheduler;

import org.study.SProcess;

import java.util.Iterator;

/**
 * 스케줄러 인터페이스
 * 구현체가 readyQ를 관리하는 형태로 만들어지길 기대함
 * 객체 재사용을 권장하지 않음
 */
public interface Scheduler {

    /**
     * 프로세스를 전달 받아서 해당 프로세스에 할당된 시간을 반환
     * @param currentProcess 프로세스
     * @return 프로세스 할당 시간 ( time >= 0 )
     */
    int getRunTime(SProcess currentProcess);

    /**
     * Ready Q에 프로세스 추가
     * @param process 프로세스
     */
    void addReadyQ(SProcess process);

    /**
     * Ready Q가 비어있는지 여부 반환
     * @return True or False
     */
    boolean readyQIsEmpty();

    /**
     * Ready Q에 있는 프로세스 중 우선순위(스케줄러가 추구하는 기준)가 가장 높은 프로세스를 Ready Q에 삭제 후 반환
     * @return SProcess
     */
    SProcess pollReadyQ();

    /**
     * Ready Q에 있는 프로세스 중 우선순위(스케줄러가 추구하는 기준)가 가장 높은 프로세스를 Ready Q에서 삭제하지 않고 반환
     * @return SProcess
     */
    SProcess peekReadyQ();

    /**
     * Ready Q에 있는 프로세스 Iterator 반환
     * @return Iterator
     */
    Iterator<SProcess> iterator();

}

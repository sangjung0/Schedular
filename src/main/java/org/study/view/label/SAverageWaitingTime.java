package org.study.view.label;

import org.study.Constants;

/**
 * SLabel을 확장한 SAverageWaitingTime
 * 평균 대기시간 표시 라벨
 */
public class SAverageWaitingTime extends SLabel{
    private static final String NAME = Constants.LABEL_AVERAGE_WAITING_TIME;

    /**
     * 버튼 초기화
     */
    public SAverageWaitingTime() {
        super(NAME);
    }
}

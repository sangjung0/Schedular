package org.study.view.label;

import org.study.Constants;

/**
 * SLabel을 확장한 SAverageWaitingTime
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

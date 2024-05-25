package org.study.view.label;

import org.study.Constants;

/**
 * SLabel 을 확장한 STotalExecutionTime
 * 총 실행 시간을 표시 함
 */
public class STotalExecutionTime extends SLabel{
    private static final String NAME = Constants.LABEL_TOTAL_EXECUTION_TIME;

    /**
     * 버튼 초기화
     */
    public STotalExecutionTime() {
        super(NAME);
    }
}

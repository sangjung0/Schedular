package org.study.view.label;

import org.study.Constants;

/**
 * SLabel을 확장한 SContextSwitchCount
 * 문맥교환 데이터 표시 라벨
 */
public class SContextSwitchCount extends SLabel{
    private static final String NAME = Constants.LABEL_CONTEXT_SWITCHING_COUNT;

    /**
     * 버튼 초기화
     */
    public SContextSwitchCount() {
        super(NAME);
    }
}

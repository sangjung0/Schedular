package org.study.view.button;

import org.study.Constants;

/**
 * SButton을 확장하는 SRun 버튼
 */
public class SClear extends SButton{

    private static final String NAME = Constants.BUTTON_CHART_CLEAR;

    public SClear(){
        this(()->{});
    }

    /**
     * SClear 클릭 될때 callback 함수 실행
     * @param callback 이벤트 callback
     */
    public SClear(Callback callback) {
        super(NAME, callback);
    }
}

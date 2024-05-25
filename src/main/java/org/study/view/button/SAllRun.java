package org.study.view.button;

import org.study.Constants;

/**
 * SButton을 확장하는 SAllRun
 */
public class SAllRun extends SButton{
    private static final String NAME = Constants.BUTTON_ALL_RUN;

    public SAllRun() { this(()->{});}

    /**
     * SAllRun 클릭 될때 callback 함수 실행
     * @param callback 이벤트 callback
     */
    public SAllRun(Callback callback){
        super(NAME, callback);
    }
}

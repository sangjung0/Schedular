package org.study.view.button;

import org.study.Constants;

/**
 * SButton을 확장하는 SRandom
 */
public class SRandom extends SButton{
    private static final String NAME = Constants.BUTTON_RANDOM;

    public SRandom() { this(()->{});}

    /**
     * SAllRun 클릭 될때 callback 함수 실행
     * @param callback 이벤트 callback
     */
    public SRandom(Callback callback){
        super(NAME, callback);
    }
}

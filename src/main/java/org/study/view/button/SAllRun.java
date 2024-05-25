package org.study.view.button;

/**
 * SButton을 확장하는 SAllRun
 */
public class SAllRun extends SButton{
    private static final String NAME = "All Run";

    public SAllRun() { this(()->{});}

    /**
     * SAllRun 클릭 될때 callback 함수 실행
     * @param callback 이벤트 callback
     */
    public SAllRun(Callback callback){
        super(NAME, callback);
    }
}

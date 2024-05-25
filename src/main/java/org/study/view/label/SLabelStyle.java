package org.study.view.label;

import javax.swing.*;

public abstract class SLabelStyle extends JLabel {
    public SLabelStyle(String name){
        super(name);

        // 스타일
        setFont(getFont().deriveFont(15.0F));
    }

    public SLabelStyle(){
        this("");
    }
}

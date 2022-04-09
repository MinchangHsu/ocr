package com.caster.ocr.imageFilter.imp;

/**
 * 排除 RGB 其中一個值 符合條件即排除
 * 建構子必須傳入:
 *      1. 模組 (R、G、B) 要判斷哪個值 default R
 *      2. 判斷式 (>、<、==、<=、>=)  default >
 *      3. 數值
 *
 * Created by Caster on 2019/2/13.
 */
public class ExcludeRGBSinglePoleValue extends AbstractFilter {

    int max = 0;
    int index = 0;
    String judge ;

    public ExcludeRGBSinglePoleValue(String module ,String judge ,int max) {
        switch (module){
            case "R":
                index = 0;
                break;
            case "G":
                index = 1;
                break;
            case "B":
                index = 2;
                break;
            default:
                index = 0;
                break;
        }

        this.judge = judge;
        this.max = max;
    }

    @Override
    public boolean cleanRule(int x, int y) {

        int rgb = originRGBInfoMap.get(x + "," + y)[index];

        switch (judge){
            case "==":
                return rgb == max ;
            case ">":
                return rgb > max ;
            case ">=":
                return rgb >= max ;
            case "<":
                return rgb < max ;
            case "<=":
                return rgb <= max ;
            default:
                return rgb > max ;
        }
    }
}

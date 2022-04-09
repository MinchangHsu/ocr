package com.caster.ocr.imageFilter.imp;

/**
 * 比較 RGB 其中兩個值得差異
 * 排除符合條件的噪點
 *
 *
 * Created by Caster on 2019/2/13.
 */
public class ExcludeRGBBothThingJudge extends AbstractFilter {

    int max = 0;
    String judge ;

    public ExcludeRGBBothThingJudge(String judge ,int max) {

        this.judge = judge;
        this.max = max;
    }

    @Override
    public boolean cleanRule(int x, int y) {

        int r = originRGBInfoMap.get(x + "," + y)[0];
        int g = originRGBInfoMap.get(x + "," + y)[1];
        int b = originRGBInfoMap.get(x + "," + y)[2];

        switch (judge){
            case "==":
                if (r == max && g == max) {
                    return true;
                } else if (r == max && b == max) {
                    return true;
                } else if (b == max && g == max) {
                    return true;
                }else{
                    return false;
                }
            case ">":
                if (r > max && g > max) {
                    return true;
                } else if (r > max && b > max) {
                    return true;
                } else if (b > max && g > max) {
                    return true;
                }else{
                    return false;
                }
            case ">=":
                if (r >= max && g >= max) {
                    return true;
                } else if (r >= max && b >= max) {
                    return true;
                } else if (b >= max && g >= max) {
                    return true;
                }else{
                    return false;
                }
            case "<":
                if (r < max && g < max) {
                    return true;
                } else if (r < max && b < max) {
                    return true;
                } else if (b < max && g < max) {
                    return true;
                }else{
                    return false;
                }
            case "<=":
                if (r <= max && g <= max) {
                    return true;
                } else if (r <= max && b <= max) {
                    return true;
                } else if (b <= max && g <= max) {
                    return true;
                }else{
                    return false;
                }
            default:
                if (r > max && g > max) {
                    return true;
                } else if (r > max && b > max) {
                    return true;
                } else if (b > max && g > max) {
                    return true;
                }else{
                    return false;
                }
        }

    }
}

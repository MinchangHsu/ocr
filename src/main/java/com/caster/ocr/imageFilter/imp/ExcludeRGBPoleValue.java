package com.caster.ocr.imageFilter.imp;

/**
 * 配置 RGB 各顏色共同極值
 * 建構子必須傳入
 *      1.min RGB 最小值
 *      2.max RGB 最大值
 *
 *
 * Created by Caster on 2019/2/13.
 */
public class ExcludeRGBPoleValue extends AbstractFilter {

    int min = 0;
    int max = 0;

    public ExcludeRGBPoleValue(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean cleanRule(int x, int y) {

        int r = originRGBInfoMap.get(x + "," + y)[0];
        int g = originRGBInfoMap.get(x + "," + y)[1];
        int b = originRGBInfoMap.get(x + "," + y)[2];

        if (r > max && b > max && g > max) {
            return true;
        } else if (r < min && b < min && g < min) {
            return true;
        } else {
            return false;
        }
    }
}

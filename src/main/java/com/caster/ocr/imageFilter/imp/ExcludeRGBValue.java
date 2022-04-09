package com.caster.ocr.imageFilter.imp;

import java.util.List;

/**
 * 定義一個 RGB 數值 list
 * 針對 RGB 其中一個值 符合 就排除噪點
 * 建構子必須傳入 數值清單
 *
 * Created by Caster on 2019/2/13.
 */
public class ExcludeRGBValue extends AbstractFilter {

    List<Integer> excludeRGBList;

    public ExcludeRGBValue(List<Integer> excludeRGBList) {
        this.excludeRGBList = excludeRGBList;
    }

    @Override
    public boolean cleanRule(int x, int y) {

        int r = originRGBInfoMap.get(x + "," + y)[0];
        int g = originRGBInfoMap.get(x + "," + y)[1];
        int b = originRGBInfoMap.get(x + "," + y)[2];

        if (excludeRGBList.contains(r) || excludeRGBList.contains(g) || excludeRGBList.contains(b)) {
            return true;
        } else {
            return false;
        }
    }
}

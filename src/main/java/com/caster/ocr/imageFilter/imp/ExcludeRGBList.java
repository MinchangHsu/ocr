package com.caster.ocr.imageFilter.imp;

import java.util.List;

/**
 * 定義一個 RGB 數值 list
 * 如果該點RGB其中之一符合 清單內的數值 即刪除
 * 建構子必須傳入 數值清單
 *
 * Created by Caster on 2019/2/11.
 */
public class ExcludeRGBList extends AbstractFilter {
    List<Integer> excludeRGBList;

    public ExcludeRGBList(List<Integer> excludeRGBList) {
        this.excludeRGBList = excludeRGBList;
    }

    public boolean cleanRule(int x, int y) {

        int r = originRGBInfoMap.get(x + "," + y)[0];
        int g = originRGBInfoMap.get(x + "," + y)[1];
        int b = originRGBInfoMap.get(x + "," + y)[2];

        if (excludeRGBList.contains(r) && excludeRGBList.contains(g) && excludeRGBList.contains(b)) {
            return true;
        } else {
            return false;
        }
    }
}

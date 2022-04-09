package com.caster.ocr.imageFilter.imp;

/**
 * 去除驗證碼邊框 以下條件符合就會刪除
 * x = 0  & y = 0 & x = (w-1) & y = (h-1)
 *
 * Created by Caster on 2019/2/11.
 */
public class RemoveFrame extends AbstractFilter {

    int minX = 0;
    int maxX = 0;
    int minY = 0;
    int maxY = 0;

    public RemoveFrame(int minX, int maxX, int minY, int maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    @Override
    public boolean cleanRule(int x, int y) {

        if (y <= minY) {
            return true;
        } else if (y >= maxY) {
            return true;
        } else if (x <= minX) {
            return true;
        } else if (x >= maxX) {
            return true;
        } else {
            return false;
        }
    }
}

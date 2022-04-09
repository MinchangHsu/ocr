package com.caster.ocr.imageFilter.imp;

/**
 * 主要 調整圖片亮度 藉由調整 RGB 數值增加 調亮圖片判斷過亮的數值 為噪點
 * 白色(255,255,255)  黑色(0,0,0)
 * 排除白色的顏色
 * 建構子必須傳入
 *      1.模組編號(module)
 *      2.比對數值(max)
 *      3.調整倍率(addition) (選填)
 *      4.額外附加數值(add)  (選填)
 *
 *
 * Created by Caster on 2019/2/14.
 */
public class IncreaseImageBright extends AbstractFilter {

    int module; // 模組編號
    int max; // 比對數值
    double addition; // 調亮倍率  Ex.1.1
    int add; // 提高數值

    public IncreaseImageBright(int module, int max, double addition, int add) {
        this.module = module;
        this.max = max;
        this.addition = addition;
        this.add = add;
    }

    public IncreaseImageBright(int module, int max) {
        this.module = module;
        this.max = max;
        this.addition = 1;
        this.add = 0;
    }

    @Override
    public boolean cleanRule(int x, int y) {

        int r = (int) (originRGBInfoMap.get(x + "," + y)[0] * addition) + add;
        int g = (int) (originRGBInfoMap.get(x + "," + y)[1] * addition) + add;
        int b = (int) (originRGBInfoMap.get(x + "," + y)[2] * addition) + add;

        if ((module == 1 || module == 0) && r >= max) {
            return true;
        } else if ((module == 2 || module == 0) && g >= max) {
            return true;
        } else if ((module == 3 || module == 0) && b >= max) {
            return true;
        } else {
            return false;
        }
    }
}

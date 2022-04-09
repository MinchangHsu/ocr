package com.caster.ocr.imageFilter;


import com.caster.ocr.policy.ImagePolicy;

import java.util.Map;

/**
 * 主要定义过滤器共用的方法
 * 定义规则方法
 *
 * Created by Caster on 2019/2/11.
 */
public interface IImageFilter {
    int[] whiteColor = {255, 255, 255};
    int[] blackColor = {0, 0, 0};

    /**
     * 配置圖片大小
     * @param height
     * @param width
     */
    void setImageSize(int height, int width);

    /**
     * 驗證碼圖片過濾規則
     * @param tUtil
     * @return
     */
    Map<String, int[]> imageFilterRule(ImagePolicy tUtil);
}

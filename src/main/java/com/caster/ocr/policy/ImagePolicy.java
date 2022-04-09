package com.caster.ocr.policy;


import com.caster.ocr.policy.shapeSurround.AbsShapeProcess;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 處理驗證圖片策略
 *
 * TesseractUtil (interface)  <- Imp  AbsTesseractUtilx (abstract) 顶层定义必须共用方法
 * 如需增加共用方法可以到最下层 (extend)  TesseractUtilxImp 再由最下层去实做。
 *
 * Created by Caster on 2019/2/1.
 */
public interface ImagePolicy {

    /**
     * 將圖片轉換成Map<String, int[]>
     *     String = 坐標
     *     int[] = {r,g,b}
     * @param shapeProcess
     * @return
     */
    Map<String, int[]> translateRGBToMap(AbsShapeProcess shapeProcess);

    /**
     * 依照座標設定RGB
     */
    int[] setRGBToMap(Map<String, int[]> pointsRGBInfoMap, int j, int i);

    /**
     * 刪除驗證碼圖片
     */
    void deleteVerifyImageFile(List<File> list);

    /**
     * 測試用 打印 RGB 圖片 各點內容
     */
    void printSystemLog(boolean isGood);

    /**
     * 測試用 打印 RGB 圖片 各點內容
     */
    void printSystemLog(boolean isGood, int r);

    /**
     * reflash originRGBMap 更新圖片map
     */
    void resetOriginRGBMap(Map<String, int[]> pointsRGBInfoMap);

    /**
     * 輸出圖片
     */
    File printPicture(Map<String, int[]> printImageMap) throws IOException;

    /**
     * 輸出最終圖片
     */
    File printFinalPicture(Map<String, int[]> printImageMap, String fileName) throws IOException;

    /**
     * 確認是否為測試階段
     */
    boolean checkIsTest();

    /**
     * 設置測試階段
     */
    void setIsTest(boolean isTest);

    /**
     * 配置圖片大小
     */
    void setImageSize(int height, int width);

    /**
     * 設置驗證碼圖片Map
     */
    void setOriginRGBMap(Map<String, int[]> originRGBInfoMap);

    /**
     * 取得驗證碼圖片最初Map
     */
    Map<String, int[]> getOriginRGBMap();
}

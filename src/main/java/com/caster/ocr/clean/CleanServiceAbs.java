package com.caster.ocr.clean;


import com.caster.ocr.policy.ImagePolicy;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.caster.ocr.utils.Contents.imageFolder;

/**
 * 主要在定義去噪流程架構
 * 主流程、副流程 相關流程架構共用方法
 *
 * Created by Caster on 2019/2/12.
 */
public abstract class CleanServiceAbs {

    protected static final Logger log = LoggerFactory.getLogger("clean");
    protected int h = 0;  // image Height
    protected int w = 0;  // image Width
    protected boolean isTest = false;  // check is Development step
    protected boolean printOCRpic = false;  // output OCR result picture
    protected Map<String, int[]> originRGBInfoMap = new ConcurrentHashMap<>(); // origin verify image RGB map
    protected ImagePolicy policyImpl;

    /**
     * 執行圖片去噪主架構
     */
    public String executeOCRMainProcess(InputStream verifyImageInputStream, int verifyCodeLength) throws InterruptedException {
        log.info(" OCR認證碼使用【tess4j】");
        boolean stop = true;
        String verifiedCode = "";
        int ocrCount = 0;
        do {
            File verifyImageFile = fetchVerifyImage(verifyImageInputStream);
            verifiedCode = minorProcess(verifyImageFile);
            if (verifiedCode != null && verifiedCode.length() == verifyCodeLength && getContainsCode(getValidCode(), verifiedCode, verifyCodeLength)) {
                stop = false;
            }
            log.info(" OCR認證碼【tess4j】 = 【" + verifiedCode + "】");
            ocrCount++;
            if (ocrCount >= 1) { // Short.MAX_VALUE
                stop = false;
            }
        } while (stop);
        log.info(" OCR認證碼次數【tess4j】 = 【" + ocrCount + "】");
        return verifiedCode;
    }

    /**
     * 獲取驗證圖片
     */
    public File fetchVerifyImage(InputStream verifyImageInputStream) {
        File verifyImageFile = null;
        try {
            verifyImageFile = new File(imageFolder, System.currentTimeMillis() + "-bi1" + ".png");
            FileUtils.copyInputStreamToFile(verifyImageInputStream, verifyImageFile);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("getTmp : ", e);
        }
        return verifyImageFile;
    }

    /**
     * 執行圖片去噪副流程
     */
    public String minorProcess(File verifyImageFile) {
        List<File> deleteFileList = new ArrayList<>();
        try {

            //初始化圖片
            init(verifyImageFile);

            // 圖片去噪
            File verifyImageCleanFile = cleanVerifyImage(verifyImageFile);

            // 加入刪除圖片清單
            if (!isTest) {
                deleteFileList.add(verifyImageFile);
                deleteFileList.add(verifyImageCleanFile);
            }

            // 執行 OCR 辨識
            String verifiedCode = executeOCR(verifyImageCleanFile).trim().replace(" ", "");
            log.debug("checkCode =>" + verifiedCode + "  size=" + verifiedCode.length());

            if(printOCRpic)
                policyImpl.printFinalPicture(originRGBInfoMap, verifiedCode);

            // 刪除圖片
            policyImpl.deleteVerifyImageFile(deleteFileList);
            return verifiedCode;
        } catch (Exception e) {
            log.error("getTmp : ", e);
        }
        return "";
    }

    /**
     * 圖片去噪前，初始化圖片。
     */
    public void init(File sfile) throws IOException {
        setPolicyImpl();
        originRGBInfoMap.clear();
        BufferedImage bufferedImage = ImageIO.read(sfile);
        h = bufferedImage.getHeight();
        w = bufferedImage.getWidth();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int argb = bufferedImage.getRGB(x, y);
                int r = (int) (((argb >> 16) & 0xFF));
                int g = (int) (((argb >> 8) & 0xFF));
                int b = (int) (((argb >> 0) & 0xFF));

                int rgbIntArray[] = {r, g, b};
                originRGBInfoMap.put(x + "," + y, rgbIntArray);
            }
        }
        policyImpl.setOriginRGBMap(originRGBInfoMap);
        policyImpl.setImageSize(h, w);
    }

    /**
     * 檢核 OCR 辨識結果 是否符合預期內容
     */
    public boolean getContainsCode(String codeKey, String code, int verifyCodeLength) {
        boolean status = true;
        for (int i = 0; i < verifyCodeLength; i++) {
            if (!(codeKey.contains(code.charAt(i) + ""))) {
                status = false;
                break;
            }
        }
        return status;
    }

    /**
     * 將去噪完圖片送給 OCR 進行辨識 並回傳辨識結果
     */
    public String executeOCR(File verifyImageCleanFile) throws TesseractException {
        ITesseract instance = new Tesseract();
        instance.setLanguage(getOCRLanguage());
        instance.setDatapath("./tessdata");
        String result = instance.doOCR(verifyImageCleanFile);
        return result;
    }

    /**
     * OCR 辨識出來 檢核字元串
     * @return
     */
    public String getValidCode() {
        return "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    }

    /**
     * 設定OCR辨識樣本數據庫
     */
    public String getOCRLanguage() {
        return "eng";
    }

    /**
     * 專門處理圖片去噪
     */
    public abstract File cleanVerifyImage(File sfile) throws Exception;

    /**
     * 設定 Tessraact Util 公用類別
     */
    public abstract void setPolicyImpl();

}

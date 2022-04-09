//package com.caster.ocr.service.impl;
//
//import com.caster.ocr.imageFilter.IImageFilter;
//import com.caster.ocr.imageFilter.imp.*;
//import com.caster.ocr.service.CleanServiceAbs;
//import com.caster.ocr.tessractUtil.imp.TesseractUtil1Imp;
//import com.sun.imageio.plugins.gif.GIFImageReader;
//import com.sun.imageio.plugins.gif.GIFImageReaderSpi;
//import com.sun.imageio.plugins.gif.GIFImageWriter;
//import com.sun.imageio.plugins.gif.GIFImageWriterSpi;
//import org.apache.commons.io.FileUtils;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.protocol.HttpContext;
//import javax.imageio.spi.ImageReaderSpi;
//import javax.imageio.spi.ImageWriterSpi;
//import javax.imageio.stream.FileImageInputStream;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.LinkedList;
//import java.util.Queue;
//
///**
// * Created by Caster on 2019/2/13.
// */
//public class TesseractClean extends CleanServiceAbs {
//    @Override
//    public File cleanVerifyImage(File sfile) throws Exception {
//
//        //開發測試請使用 true
//        isTest = true;
//
//        //設定是否為開發模式
//        tUtil.setIsTest(isTest);
//
//        // 過濾器引入
//        Queue<IImageFilter> filterQueue = new LinkedList<>();
//
//        filterQueue.add(new ExcludeCountCollectionRGBList(1, 5));  // 留下最多颜色
//        filterQueue.add(new CrossJudge(2, 255));  // 左右
//        filterQueue.add(new CrossJudge(3, 255));  // 上下
//
//
//
//        while (!filterQueue.isEmpty()) {
//            IImageFilter imageFilter = filterQueue.poll();
//            imageFilter.setImageSize(h, w);
//            originRGBInfoMap = imageFilter.imageFilterRule(tUtil);
//            tUtil.resetOriginRGBMap(originRGBInfoMap);
//            if (isTest)
//                tUtil.printPicture(originRGBInfoMap);
//        }
//        return tUtil.printPicture(originRGBInfoMap);
//    }
//
//    @Override
//    public void setTessraactUtilClass() {
//        tUtil = new TesseractUtil1Imp();
//    }
//
//    public static void main(String[] args) throws InterruptedException {
//
//        TesseractClean util = new TesseractClean();
//
//        String urlCodeTarget = "http://rtmer.021jysc.com:8082/system/gifCode";
//        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
//        CloseableHttpClient client = httpClientBuilder.build();
//        String code = util.executeOCRMainProcess(client, urlCodeTarget, null, 4);
//
//        log.debug("最终辩示:" + code);
//    }
//
//    /**
//     * 圖片去噪前，初始化圖片。
//     */
//    @Override
//    public void init(File sfile) throws IOException {
//        setTessraactUtilClass();
//        originRGBInfoMap.clear();
//        tUtil.setIsTest(false);
//
//
//        FileImageInputStream in = new FileImageInputStream(sfile);
//
//        //GIFImageReader reader = null;
//        ImageReaderSpi readerSpi = new GIFImageReaderSpi();
//
//        //readerSpi.canDecodeInput(in);
//        GIFImageReader gifReader = (GIFImageReader) readerSpi.createReaderInstance();
//        gifReader.setInput(in);
//        int num = gifReader.getNumImages(true);
//        gifReader.getNumImages(true);
//
//        System.out.println("GIF 照片張數:"+num);
//        ImageWriterSpi writerSpi = new GIFImageWriterSpi();
//        GIFImageWriter writer = (GIFImageWriter) writerSpi.createWriterInstance();
//
//        // 重疊 所有照片 合併成一張
//        for (int i = 0; i < num; i++) {
//            BufferedImage bufferedImage = gifReader.read(i); // 取得第幾張照片
//            h = bufferedImage.getHeight();
//            w = bufferedImage.getWidth();
//            for (int y = 0; y < h; y++) {
//                for (int x = 0; x < w; x++) {
//                    int argb = bufferedImage.getRGB(x, y);
//                    int r = (int) (((argb >> 16) & 0xFF));
//                    int g = (int) (((argb >> 8) & 0xFF));
//                    int b = (int) (((argb >> 0) & 0xFF));
//
//                    int rgbIntArray[] = {r, g, b};
//                    int white[] = {255,255,255};
//                    if(r != 255 && g != 255 && b != 255){ // 這邊主要是為了，因為圖片顏色會變淡，只抓取顏色較深的像素
//                        if(originRGBInfoMap.containsKey(x + "," + y)){
//                            int oriIntArray[] = originRGBInfoMap.get(x + "," + y);
//                            int oriCount = oriIntArray[0] + oriIntArray[1] + oriIntArray[2];
//                            int nowCount = r + g + b ;
//
//                            if(nowCount > oriCount){
//                                originRGBInfoMap.put(x + "," + y, oriIntArray);
//                            }else{
//                                originRGBInfoMap.put(x + "," + y, rgbIntArray);
//                            }
//
//                        }else{
//                            originRGBInfoMap.put(x + "," + y, rgbIntArray);
//                        }
//                    }else {
//                        if(originRGBInfoMap.containsKey(x + "," + y)){
//                            originRGBInfoMap.put(x + "," + y, originRGBInfoMap.get(x + "," + y));
//                        }else{
//                            originRGBInfoMap.put(x + "," + y, white);
//                        }
//                    }
//                }
//            }
//        }
//        tUtil.setOriginRGBMap(originRGBInfoMap);
//        tUtil.setImageSize(h, w);
//    }
//
//    @Override
//    public File fetchVerifyImage(CloseableHttpClient client, String verifyImageUrl, HttpContext context) {
//        File verifyImageFile = null;
//        try {
//            HttpGet httpGet = new HttpGet(verifyImageUrl);
//            HttpResponse verifyImageRes = client.execute(httpGet, context);
//            HttpEntity verifyImageEntity = verifyImageRes.getEntity();
//            InputStream verifyImageInputStream = verifyImageEntity.getContent();
//            verifyImageFile = new File(imageFolder, System.currentTimeMillis() + "-bi1" + ".gif");
//            FileUtils.copyInputStreamToFile(verifyImageInputStream, verifyImageFile);
//
//        } catch (Exception e) {
//            log.error("getTmp : ", e);
//        }
//        return verifyImageFile;
//    }
//}

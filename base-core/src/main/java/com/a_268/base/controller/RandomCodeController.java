package com.a_268.base.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * 生成随机验证码图片
 * @author s.li
 * @create 2016-12-13-15:31
 */
@Controller
public class RandomCodeController extends  BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String RAND_CODE="COMMON_RAND_CODE";

    @RequestMapping("/ran/random")
    public void genericRandomCode(HttpServletRequest request,
                                  HttpServletResponse response,
                                  @RequestParam(value="codeName",required=true) String codeName)
            throws IOException {
        response.setHeader("Cache-Control", "private,no-cache,no-store");
        response.setContentType("image/png");
        int width = 85, height = 28;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        // 获取图形上下文
        Graphics2D g = image.createGraphics();//(getGraphics();
        g.setComposite(AlphaComposite.getInstance(3, 1.0F));
        // 生成随机类
        Random random = new Random();
        // 设定背景色
        g.setColor(new Color(249, 247, 243));//getRandColor(214, 243, 255
        g.fillRect(0, 0, width, height);
        // 设定字体
        g.setFont(new Font("Microsoft YaHei", Font.ROMAN_BASELINE, 20));
        // 取随机产生的认证码(4位数字)
        String sRand = "";
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(44, 85, 110));// 字体颜色
            g.drawString(rand, 13 * i + 16, 23);
        }
        // 将认证码存入SESSION
        redisCache.set(RAND_CODE+"_"+codeName, sRand,5*60);//默认缓存5分钟
        // 图象生效
        g.dispose();
        ServletOutputStream responseOutputStream = response.getOutputStream(); // 输出图象到页面
        ImageIO.write(image, "png", responseOutputStream);
        // 以下关闭输入流！ responseOutputStream.flush();
        responseOutputStream.close();
    }
}

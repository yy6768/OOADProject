package com.ant.util;

import java.awt.*;

/**
 * 工具类
 * 负责加载图片
 *
 */
public class ImageUtil {
    /**
     * 类加载器获得当前路径，对相对路径下的图片进行加载
     * @param path
     * @return
     */
    public static Image getImage(String path) {
        return Toolkit.getDefaultToolkit().getImage(ImageUtil.class.getClassLoader().getResource(path));
    }

}

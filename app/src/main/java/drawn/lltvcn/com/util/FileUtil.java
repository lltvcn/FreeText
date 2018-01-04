package drawn.lltvcn.com.util;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;

import drawn.lltvcn.com.textdemo.MainApplication;

/**
 * Created by zhaolei on 2017/10/17.
 */

public class FileUtil {
    private static String ROOT_PATH = "FontDemo";
    private static String FONT_PATH = "fonts";
    private static String IMG_PATH = "imgs";
    private static String DATA_PATH = "datas";
//    private static HashMap<String,String> imgMD5S;

    static {
//        imgMD5S = new HashMap<>();
        File file = new File(getFontDir());
        if(!file.exists()){
            file.mkdirs();
        }
        file = new File(getImgDir());
        if(!file.exists()){
            file.mkdirs();
        }
//        File[] files = file.listFiles();
//        if(files!=null){
//            for (int i = 0; i < files.length; i++) {
//                imgMD5S.put(files[i].getName(),getFileMD5(files[i]));
//            }
//        }
        file = new File(getDataDir());
        if(!file.exists()){
            file.mkdirs();
        }
    }

//    public static String getMd5ByName(String name){
//        return imgMD5S.get(name);
//    }

//    public static String getNameByMd5(String md5){
//        for (HashMap.Entry<String,String> entry:imgMD5S.entrySet()) {
//            if (entry.getValue().equals(md5))
//                return entry.getKey();
//        }
//        return null;
//    }

    public static String getFontDir(){
        return getRootPath()+FONT_PATH;
    }

    private static String getRootPath(){
        return getRootPath(ROOT_PATH,false,false);
    }

    public static String[] getFontNames(){
        File file = new File(getFontDir());
        return file.list();
    }

    public static String getFontPath(String name){
        return getFontDir()+File.separator+name;
    }

    public static String getDataDirByName(String name){
        return getDataDir()+File.separator+name;
    }

    public static String getDataDir(){
        return getRootPath()+DATA_PATH;
    }

    public static String getImgDir(){
        return getRootPath()+IMG_PATH;
    }

    public static String[] getImgNames(){
        File file = new File(getImgDir());
        return file.list();
    }

    public static String getImagePathByName(String name){
        return getImgDir()+File.separator+name;
    }

    public static String[] getImageFullPaths(){
        File file = new File(getImgDir());
        File[] files = file.listFiles();
        if(files!=null){
            String[] paths = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                paths[i] = files[i].getAbsolutePath();
            }
            return paths;
        }
        return null;
    }

    public static boolean checkSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    public static String getRootPath(String name, boolean hasMedia, boolean forceNoSDCard) {
        String path = null;
        if (!forceNoSDCard && checkSDCard()) {
            path = Environment.getExternalStorageDirectory().toString()
                    + File.separator
                    + name
                    + File.separator;

        } else {

            File dataDir = MainApplication.getInstance().getFilesDir();
            if (dataDir != null) {
                path = dataDir + File.separator
                        + name
                        + File.separator;
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdirs();
                    file.setExecutable(true, false);
                    file.setReadable(true, false);
                    file.setWritable(true, false);
                }
            } else {
                path = Environment.getDataDirectory().toString() + File.separator
                        + name
                        + File.separator;
            }


        }

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        if (!file.exists()) { //解决一部分手机将eMMC存储挂载到 /mnt/external_sd 、/mnt/sdcard2 等节点
            String tmpPath = createRootDirInDevMount();
            if (tmpPath != null) {
                path = tmpPath + File.separator + name
                        + File.separator;
            }

            file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }

        }
//		if(!hasMedia)
//		createNoMediaFile(path);

        return path;
    }

    public static boolean deleteFile(String filepath) {
        if (filepath == null || "".equals(filepath)) {
            return false;
        }
        File file = new File(filepath);
        if (file.exists() && file.isFile())
            return file.delete();
        return false;
    }

    public static void deleteDir(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDir(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    /**
     * 复制文件
     *
     * @param from
     * @param to
     */
    public static void copyFile(File from, File to) {
        if (null == from || !from.exists()) {
            return;
        }
        if (null == to) {
            return;
        }
        FileInputStream is = null;
        FileOutputStream os = null;
        try {
            is = new FileInputStream(from);
            if (!to.exists()) {
                to.createNewFile();
            }
            os = new FileOutputStream(to);
//			copyFileFast(is, os);
            copyFileUseNormal(from, to);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeIO(is, os);
        }
    }

    /****
     * 使用普通方式进行文件拷贝
     ***/

    public static void copyFileUseNormal(File srcFile, File destFile) throws IOException {
        try {
            copyFile(srcFile, destFile, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void copyFile(File srcFile, File destFile,
                                 boolean preserveFileDate) throws IOException, FileNotFoundException {
        if (srcFile == null)
            throw new NullPointerException("Source must not be null");
        if (destFile == null)
            throw new NullPointerException("Destination must not be null");
        if (!srcFile.exists())
            throw new FileNotFoundException("Source '" + srcFile
                    + "' does not exist");
        if (srcFile.isDirectory())
            throw new IOException("Source '" + srcFile
                    + "' exists but is a directory");
        if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath()))
            throw new IOException("Source '" + srcFile + "' and destination '"
                    + destFile + "' are the same");
        if (destFile.getParentFile() != null
                && !destFile.getParentFile().exists()
                && !destFile.getParentFile().mkdirs())
            throw new IOException("Destination '" + destFile
                    + "' directory cannot be created");
        if (destFile.exists() && !destFile.canWrite()) {
            throw new IOException("Destination '" + destFile
                    + "' exists but is read-only");
        } else {
            doCopyFile(srcFile, destFile, preserveFileDate);
            return;
        }
    }

    private static void closeQuietly(OutputStream output) {
        try {
            if (output != null)
                output.close();
        } catch (IOException ioe) {
        }
    }

    private static void closeQuietly(InputStream input) {
        try {
            if (input != null)
                input.close();
        } catch (IOException ioe) {
        }
    }


    private static void doCopyFile(File srcFile, File destFile,
                                   boolean preserveFileDate) throws IOException {
        if (destFile.exists() && destFile.isDirectory())
            throw new IOException("Destination '" + destFile
                    + "' exists but is a directory");
        FileInputStream input = new FileInputStream(srcFile);
        try {
            FileOutputStream output = new FileOutputStream(destFile);
            try {
                copy(input, output);
            } finally {
                closeQuietly(output);
            }
        } finally {
            closeQuietly(input);
        }
        if (srcFile.length() != destFile.length())
            throw new IOException("Failed to copy full contents from '"
                    + srcFile + "' to '" + destFile + "'");
        if (preserveFileDate)
            destFile.setLastModified(srcFile.lastModified());
    }

    private static int copy(InputStream input, OutputStream output)
            throws IOException {
        byte buffer[] = new byte[4096];
        int count = 0;
        for (int n = 0; -1 != (n = input.read(buffer)); ) {
            output.write(buffer, 0, n);
            count += n;
        }

        return count;
    }

    private static String createRootDirInDevMount() {
        String path = null;
        ArrayList<String> dirs = getDevMountList();
        if (dirs != null) {
            for (int i = 0; i < dirs.size(); i++) {
                String tmpPath = dirs.get(i);
                if ("/mnt/sdcard2".equals(tmpPath)
                        || "/mnt/external_sd".equals(tmpPath)) {
                    path = tmpPath;
                    break;
                }

            }
        }

        return path;
    }

    /**
     * 遍历 "system/etc/vold.fstab” 文件，获取全部的Android的挂载点信息
     *
     * @return
     */
    public static ArrayList<String> getDevMountList() {
        String[] toSearch = readFile("/etc/vold.fstab").split(" ");
        ArrayList<String> out = new ArrayList<String>();
        for (int i = 0; i < toSearch.length; i++) {
            if (toSearch[i].contains("dev_mount")) {
                if (new File(toSearch[i + 2]).exists()) {
                    out.add(toSearch[i + 2]);
                }
            }
        }
        return out;
    }

    /**
     * 从文件中读取文本
     *
     * @param filePath
     * @return
     */
    public static String readFile(String filePath) {
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
        } catch (Exception e) {
            throw new RuntimeException("jjjj");
        }
        return inputStream2String(is);
    }

    /**
     * 输入流转字符串
     *
     * @param is
     * @return 一个流中的字符串
     */
    public static String inputStream2String(InputStream is) {
        if (null == is) {
            return null;
        }
        StringBuilder resultSb = null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            resultSb = new StringBuilder();
            String len;
            while (null != (len = br.readLine())) {
                resultSb.append(len);
            }
        } catch (Exception ex) {
        } finally {
            closeIO(is);
        }
        return null == resultSb ? null : resultSb.toString();
    }

    /**
     * 关闭流
     *
     * @param closeables
     */
    public static void closeIO(Closeable... closeables) {
        if (null == closeables || closeables.length <= 0) {
            return;
        }
        for (Closeable cb : closeables) {
            try {
                if (null == cb) {
                    continue;
                }
                cb.close();
            } catch (IOException e) {
                throw new RuntimeException("");
            }
        }
    }



    /**
     * 获取单个文件的MD5值！

     * @param file
     * @return
     */

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

//    public static String getImgPathByMd5(String md5){
//        return getImgDir()+File.separator+getNameByMd5(md5);
//    }


}

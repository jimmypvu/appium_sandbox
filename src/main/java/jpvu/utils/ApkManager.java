package jpvu.utils;

public class ApkManager {
    private static final String API_DEMOS_APK_PATH = System.getProperty("user.dir") + "\\src\\test\\resources\\apks\\ApiDemos-debug.apk";
    private static final String GENERAL_STORE_APK_PATH = System.getProperty("user.dir") + "\\src\\test\\resources\\apks\\General-Store.apk";

    public static String setApkPath() {
        if(ConfigReader.getConfig("app").equals("generalstore")){
            return GENERAL_STORE_APK_PATH;
        }else{
            return API_DEMOS_APK_PATH;
        }
    }
}

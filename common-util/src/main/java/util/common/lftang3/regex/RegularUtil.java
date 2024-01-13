package util.common.lftang3.regex;

import java.util.regex.Pattern;

/**
 * @author ：lftang3
 * @date ：Created in 2022/7/6 14:12
 * @description：正则工具类
 * @modified By：
 * @version: 1.0$
 */
public class RegularUtil {

    private RegularUtil(){}

    //所有标签
    public static final Pattern ALL_LABEL = Pattern.compile("<[^<>]+>");

    //匹配所有标签，除去img标签
    public static final Pattern LABEL_FILTER_IMG = Pattern.compile("<[^<>|(img)]+>");

    //匹配所有标签之后是span标签的标签
    public static final Pattern LABEL_AFTER = Pattern.compile("<[^<>]+>(?=(<span[^>]>))");

    //匹配所有标签之前是span标签的标签
    public static final Pattern LABEL_BEFORE = Pattern.compile("(?<=(<span[^>]>))<[^<>]+>");

    //匹配所有标签之前不是span标签的标签
    public static final Pattern LABEL_BEFORE_NOT = Pattern.compile("(?<!(<span[^>]>))<[^<>]+>");

    //匹配所有标签之后不是span标签的标签
    public static final Pattern LABEL_AFTER_NOT = Pattern.compile("<[^<>]+>(?!(<span[^>]*>))");

    //span标签
    public static final Pattern SPAN_LABEL = Pattern.compile("<span[^>]*>");

    //整数
    public static final Pattern ALL_INTEGER = Pattern.compile("^-?[1-9]\\d*$");
    //正整数
    public static final Pattern POSITIVE_INTEGER = Pattern.compile("^[1-9]\\d*$");
    //负整数
    public static final Pattern NEGATIVE_INTEGER = Pattern.compile("^-[1-9]\\d*$");
    //数字
    public static final Pattern NUMBER = Pattern.compile("^([+\\-]?)\\d*\\.?\\d+$");
    //正数（+0）
    public static final Pattern POSITIVE_NUMBER = Pattern.compile("^[1-9]\\d*$|0");
    //负数（+0）
    public static final Pattern NEGATIVE_NUMBER = Pattern.compile("^-[1-9]\\d*$|0");
    //浮点数
    public static final Pattern FLOAT_NUMBER = Pattern.compile("^([+\\-]?)\\d*\\.\\d+$");
    //正浮点数
    public static final Pattern POSITIVE_FLOAT_NUMBER = Pattern.compile("^[1-9]\\d*\\.\\d*$|^0\\.\\d*[1-9]\\d*$");
    //负浮点数
    public static final Pattern NEGATIVE_FLOAT_NUMBER = Pattern.compile("^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$");

    //邮箱地址
    public static final Pattern MAIL_BOX = Pattern.compile("^\\w+([.\\-]\\w)*@[A-Za-z0-9]+([.\\-][A-Za-z0-9])*\\.[A-Za-z0-9]+$");

    //url
    public static final Pattern URL = Pattern.compile("^http[s]?://[\\w\\-]+\\.+[\\w\\-]+[\\w\\-./?%&=]*$");

    //只有中文字符
    public static final Pattern CHINESE = Pattern.compile("^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$");

    //ascii
    public static final Pattern ASCII = Pattern.compile("^[\\x00-\\xFF]+$");

    //邮政编码
    public static final Pattern ZIP_CODE = Pattern.compile("^\\d{6}$");

    //国内手机号
    public static final Pattern MOBILE = Pattern.compile("^(13|15|16|17|18)[0-9]{9}$");
    //电话号码的函数(包括验证国内区号,国际区号,分机号)
    public static final Pattern TEL_MOBILE = Pattern.compile("^(([0+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$");

    //ipv4
    public static final Pattern IPV4 = Pattern.compile("^((2(5[0-5]|[0-4]\\d)|1\\d{2}|[1-9]?\\d)\\.){3}(2(5[0-5]|[0-4]\\d)|1\\d{2}|[1-9]?\\d)$");

    //非空字符
    public static final Pattern NOT_EMPTY = Pattern.compile("^\\S+$");
    //匹配所有空格
    public static final Pattern SPACE = Pattern.compile("(&nbsp;)+|\\s+|[\\s\\p{Zs}]+");

    //图片
    public static final Pattern IMG = Pattern.compile("(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga|JPG|BMP|GIF|ICO|PCX|JPEG|TIF|PNG|RAW|TGA)$");
    //音频
    public static final Pattern AUDIO = Pattern.compile("(.*)\\.(mp3|wma|mid|midi|wav|vqf|MP3|WMA|MID|MIDI|WAV|VQF)$");
    //视频
    public static final Pattern VIDEO = Pattern.compile("\\.(rm|3gp|mp4|rmvb|avi|wmv|flv|vob|exe|mkv|swf|RM|3GP|MP4|RMVB|AVI|WMV|FLV|VOB|EXE|MKV|SWF)$");

    //日期（2022-4-22、2022/4/22）
    public static final Pattern  DATE = Pattern.compile("^\\d{4}([\\-/.])\\d{1,2}\\1\\d{1,2}$");
    //日期时间
    public static final Pattern  DATETIME = Pattern.compile("^\\d{4}([\\-/.])\\d{1,2}\\1\\d{1,2}(\\s\\d{2}:)?(\\d{2}:)?(\\d{2})?$");


    //字母开头的数字，26个英文字母或者下划线
    public static final Pattern USERNAME = Pattern.compile("^[A-Za-z]\\w{5,}$");
    //字母数字下划线
    public static final Pattern CHARACTER_NUMBER = Pattern.compile("^\\w+$");
    //第二代身份证
    public static final Pattern ID_CARD = Pattern.compile("^[1-9]([0-9]{14}|[0-9]{17})$");

}

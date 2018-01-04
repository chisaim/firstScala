package entity;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converter {

    public Converter() {
    }

    public void main() {
        String cn = "怀念外婆屋后的柚子树";
        System.out.println(cnToUnicode(cn));
        // 字符串 : \u5f00\u59cb\u4efb\u52a1 ，由于 \ 在java里是转义字符，要写出下面这种形式
        String unicode = "\\u5f00\\u59cb\\u4efb\\u52a1";
        System.out.println(unicodeToCn(unicode));
    }

    public String unicodeToCn(String unicode) {
        /** 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格*/
        String[] strs = unicode.split("\\\\u");
        String returnStr = "";
        // 由于unicode字符串以 \ u 开头，因此分割出的第一个字符是""。
        for (int i = 1; i < strs.length; i++) {
            returnStr += (char) Integer.valueOf(strs[i], 16).intValue();
        }
        return returnStr;
    }

    public static String cnToUnicode(String cn) {
        char[] chars = cn.toCharArray();
        String returnStr = "";
        for (int i = 0; i < chars.length; i++) {
            returnStr += "\\u" + Integer.toString(chars[i], 16);
        }
        return returnStr;
    }

    public static String sampleConverter() {
        String str = "(代开|可开|转开|本企业有|现有)&(增值|增直|税|票|禾兑)&(电|TEL|询|联|详情)".replace("(", "").replace(")", "");

        String[] strs = str.split("&");

        ArrayList list = new ArrayList();
        for (int i = 0; i < strs.length; i++) {
            System.out.println(strs[i]);
            list.add(strs[i]);

        }
        System.out.println(list);
        return "";
    }


    static String context = "万圣节狂欢节，义乌万达星期六专柜陪你玩转狂欢折扣季，还需等双十一嘛！不需要！星期六集团秋冬巡回巨献199起，还可以购两双减100，3双";

    public static void main(String[] args) {
        String regex = "(万圣节)&(狂欢节)".replace("(","").replace(")","");
//        String regex = "(万圣节)&(狂欢节)&(义乌)&(万达)&(专柜)";
//        String regex = "(独一无二)&(大福利)".replace("(","").replace(")","");
//        System.out.println(regex);
        Pattern pattern = Pattern.compile(regex);
        Matcher isMatch = pattern.matcher(context);
        System.out.println(isMatch.find());


    }


}

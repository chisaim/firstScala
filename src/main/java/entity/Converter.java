package entity;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converter {

    public Converter() {
    }

    public void main() {
        String cn = "���������ݺ��������";
        System.out.println(cnToUnicode(cn));
        // �ַ��� : \u5f00\u59cb\u4efb\u52a1 ������ \ ��java����ת���ַ���Ҫд������������ʽ
        String unicode = "\\u5f00\\u59cb\\u4efb\\u52a1";
        System.out.println(unicodeToCn(unicode));
    }

    public String unicodeToCn(String unicode) {
        /** �� \ u �ָ��Ϊjavaע��Ҳ��ʶ��unicode������м����һ���ո�*/
        String[] strs = unicode.split("\\\\u");
        String returnStr = "";
        // ����unicode�ַ����� \ u ��ͷ����˷ָ���ĵ�һ���ַ���""��
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
        String str = "(����|�ɿ�|ת��|����ҵ��|����)&(��ֵ|��ֱ|˰|Ʊ|�̶�)&(��|TEL|ѯ|��|����)".replace("(", "").replace(")", "");

        String[] strs = str.split("&");

        ArrayList list = new ArrayList();
        for (int i = 0; i < strs.length; i++) {
            System.out.println(strs[i]);
            list.add(strs[i]);

        }
        System.out.println(list);
        return "";
    }


    static String context = "��ʥ�ڿ񻶽ڣ��������������ר��������ת���ۿۼ��������˫ʮһ�����Ҫ�������������ﶬѲ�ؾ���199�𣬻����Թ���˫��100��3˫";

    public static void main(String[] args) {
        String regex = "(��ʥ��)&(�񻶽�)".replace("(","").replace(")","");
//        String regex = "(��ʥ��)&(�񻶽�)&(����)&(���)&(ר��)";
//        String regex = "(��һ�޶�)&(����)".replace("(","").replace(")","");
//        System.out.println(regex);
        Pattern pattern = Pattern.compile(regex);
        Matcher isMatch = pattern.matcher(context);
        System.out.println(isMatch.find());


    }


}

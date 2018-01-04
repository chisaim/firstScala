package entity;
import java.util.List;

public class App
{
    public static void main( String[] args )
    {
//        String str = "(�Ա�|ȫ��|����)&(��|Ӯ|����)&(��|��)";
        String str = "(��|��|��|��)&(��|��|֧��|��)&(��|����|ѯ|�ͷ�|��ʵ)&��";
        List<String> list = zuhe(str);
        for(String string:list){
            System.out.println("("+string+")");
        }
    }

    private static List<String> zuhe(String str) {
        str = str.replace("(", "");
        str = str.replace(")", "");
        String[] strArray = str.split("&");
        int num = 1;
        for(int m=0;m<strArray.length;m++){
            num*=(strArray[m].split("\\|").length);
        }
        String[] result = new String[num];
        for(int n=0;n<num;n++){
            result[n] = "";
        }
        int temp = 1;
        for(int m=0;m<strArray.length;m++){
            String[] array = strArray[m].split("\\|");
            for(int n=0;n<num;n++){
                if(!result[n].equals("")){
                    result[n]+="&";
                }
                result[n] += array[(n%(num/temp))/(num/temp/array.length)];
            }
            temp *= array.length;
        }
        return java.util.Arrays.asList(result);
    }
}
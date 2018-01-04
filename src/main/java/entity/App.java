package entity;
import java.util.List;

public class App
{
    public static void main( String[] args )
    {
//        String str = "(淘宝|全场|超市)&(折|赢|包邮)&(满|享)";
        String str = "(户|卡|帐|账)&(费|扣|支出|划)&(电|疑问|询|客服|核实)&行";
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
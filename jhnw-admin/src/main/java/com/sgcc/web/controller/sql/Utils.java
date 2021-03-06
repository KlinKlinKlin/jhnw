package com.sgcc.web.controller.sql;

import com.sgcc.sql.service.IProblemScanLogicService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 天幕顽主
 * @E-mail: WeiYaNing97@163.com
 * @date 2021年12月17日 10:06
 */
public class Utils {

    @Autowired
    private IProblemScanLogicService problemScanLogicService;

    /**
     * @method: 修整字符串
     * @Param: [resultString] 交换机返回信息
     * @E-mail: WeiYaNing97@163.com
     *
     * 去除多余 "\r\n" 连续空格
     */
    public static String trimString(String resultString){
        resultString = resultString.replace("\r\n\r\n"," "+"\r\n"+" ");
        resultString = resultString.replace(" \r\n \r\n"," "+"\r\n"+" ");
        resultString = resultString.replace("\r\n"," "+"\r\n"+" ");
        resultString = resultString.replace("\r\n\r\n"," "+"\r\n"+" ");
        resultString = resultString.replace(" \r\n \r\n"," "+"\r\n"+" ");
        resultString = resultString.replace("\r\n"," "+"\r\n"+" ");
        resultString = repaceWhiteSapce(resultString);
        resultString = resultString.trim();
        return resultString;
    }

    /**
     * @method: 多个连续空格 改为 多个单空格
     * @Param: [original]
     * @return: java.lang.String
     * @Author: 天幕顽主
     * @E-mail: WeiYaNing97@163.com
     */
    public static String repaceWhiteSapce(String original){
        StringBuilder sb = new StringBuilder();
        boolean isFirstSpace = false;//标记是否是第一个空格
        // original = original.trim();//如果考虑开头和结尾有空格的情形
        char c;
        for(int i = 0; i < original.length(); i++){
            c = original.charAt(i);
            if(c == ' ' || c == '\t')//遇到空格字符时,先判断是不是第一个空格字符
            {
                if(!isFirstSpace)
                {
                    sb.append(c);
                    isFirstSpace = true;
                }
            }
            else{//遇到非空格字符时
                sb.append(c);
                isFirstSpace = false;
            }
        }
        return sb.toString();
    }

    /**
    * @method: 判断是否为错误命令 或是否执行成功
    * @Param: [str] 交换机返回信息
    * @return: boolean  判断命令是否错误 错误为false 正确为true
    * @E-mail: WeiYaNing97@163.com
    */
    public static boolean judgmentError(String str){
        List<String> list = new ArrayList<>();
        list.add("% Unrecognized command");
        list.add("% Ambiguous command");
        list.add("% Incomplete command");
        list.add("不是内部或外部命令");
        list.add("不是可运行的程序或批处理文件");
        for (String string:list){
            if (str.indexOf(string) != -1){
                return false;
            }
        }
        return true;
    }

    /**
     * @method: 比较版本号
     * @Param: [remove_content, compare, content] //交换机版本号  比较方法   数据库版本号
     * 如果 交换机版本号  比较方法   数据库版本号 则返回 true
     * @return: boolean
     * @Author: 天幕顽主
     * @E-mail: WeiYaNing97@163.com
     */
    public static boolean compareVersion(String remove_content,String compare,String content){
        boolean compare_size = false;
        switch (compare){
            case ">":
                compare_size = compareVersionNumber(remove_content, content);
                return compare_size;
            case "<":
                compare_size = compareVersionNumber(content, remove_content);
                return compare_size;
            case "=":
                compare_size = remove_content.equals(content);
                return compare_size;
            case ">=":
                compare_size = compareVersionNumber(remove_content, content) || remove_content.equals(content);
                return compare_size;
            case "<=":
                compare_size = compareVersionNumber(content, remove_content) || remove_content.equals(content);
                return compare_size;
            case "!=":
                compare_size = !(remove_content.equals(content));
                return compare_size;
        }
        return compare_size;
    }

    /**
     * @method: 比较系统版本号大小
     * 如果 str1 > str2 返回 true
     * 如果 str1 < str2 返回 false
     * @Param: [str1, str2]
     * @return: boolean
     * @Author: 天幕顽主
     * @E-mail: WeiYaNing97@163.com
     */
    public static boolean compareVersionNumber(String str1,String str2){
        String[] split1 = str1.split("\\.");
        String[] split2 = str2.split("\\.");
        int j;
        if (split1.length < split2.length){
            j=split1.length;
        }else if (split2.length < split1.length){
            j=split2.length;
        }else{
            j=split1.length;
        }
        for (int i=0;i<j;i++){
            int i1 = Integer.valueOf(split1[i]).intValue();
            int i2 = Integer.valueOf(split2[i]).intValue();
            if (i1>i2){
                return true;
            }else if (i1<i2){
                return false;
            }
        }

        if (split1.length < split2.length){
            return false;
        }else if (split2.length < split1.length){
            return true;
        }

        return false;
    }


    /**
     * @method: 取词
     *         //取词方法
     * @Param: [action, returnString, matchContent, integer, length]
     * 提取方法 ：取词 取版本  返回信息的一行 提取关键字 位置 长度WLs
     * @return: java.lang.String
     * @Author: 天幕顽主
     * @E-mail: WeiYaNing97@163.com
     */
    public static String wordSelection(String action,String returnString,String matchContent,Integer integer,String length){
        switch (action){
            case "取版本":
                return Global.firmwareVersion;
            case "取词":
                integer = integer - 1 ;
                String substring = length.substring(length.length() - 1, length.length());
                int word_length = Integer.valueOf(length.substring(0, length.length() - 1)).intValue();
                String return_string = "";
                switch (substring){
                    case "w":
                    case "W":
                    case "s":
                    case "S":
                        String get_word="";
                        get_word="";
                        if (returnString.indexOf(" "+matchContent+" ")!=-1){
                            String[] split_String = returnString.split(" "+matchContent+" ");
                            String[] split_w = split_String[1].split(" ");
                            if (split_w.length<integer.intValue()+word_length){
                                return null;
                            }
                            int number = integer.intValue();
                            for (int num = 0;num<word_length;num++){
                                get_word += split_w[number]+" ";
                                number++;
                                return_string += get_word.trim();
                            }
                        }
                        if (return_string.length()>0){
                            return return_string;
                        }else {
                            return null;
                        }
                    case "l":
                    case "L":
                        String takeLetters = "" ;
                        int nol=0;
                        takeLetters = "";
                        int string_position=returnString.indexOf(matchContent);
                        if (string_position!=-1){
                            int word_position = string_position+matchContent.length()+integer.intValue();
                            if (word_position+word_length>returnString.length()){
                                takeLetters = "";
                            }else {
                                takeLetters = returnString.substring(word_position, word_position + word_length);
                            }
                            return_string +=nol +":"+takeLetters.trim()+",";
                        }
                }
                if (return_string.length()>0){
                    return return_string.substring(0,return_string.length()-1);
                }else {
                    return null;
                }
        }
        return null;
    }

    /**
     * @method: 匹配
     * @Param: [matchType, returnString, matchString]
     * @return: boolean
     * @Author: 天幕顽主
     * @E-mail: WeiYaNing97@163.com
     */
    public static boolean matchAnalysis(String matchType,String returnString,String matchString){
        switch(matchType){
            case "精确匹配" :
                int indexPosition = returnString.indexOf(matchString);
                if (indexPosition!=-1){//模糊匹配
                    String frontPosition = " ";
                    String rearPosition =" ";
                    if (indexPosition>1||indexPosition==1){
                        frontPosition = returnString.charAt(indexPosition-1)+"";
                    }
                    if (indexPosition+matchString.length()<returnString.length()){
                        rearPosition = returnString.charAt(indexPosition+matchString.length())+"";
                    }

                    if (frontPosition.equals(" ") && rearPosition.equals(" ")){
                        return true;
                    }else {
                        return false;
                    }
                }else {
                    return false;
                }
            case "模糊匹配" :
                if (returnString.indexOf(matchString)!=-1){
                    return true;
                }else {
                    return false;
                }
            case "不存在" :
                if (returnString.indexOf(matchString)!=-1){
                    return false;
                }else {
                    return true;
                }
            default :
                return false;
        }
    }

    public static String getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间

        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记

        Date date = new Date();// 获取当前时间
        return sdf.format(date);// 输出已经格式化的现在时间(24小时制)
    }

}
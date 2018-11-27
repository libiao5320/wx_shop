package cc.royao.commons.auth;


import java.util.Random;



/**
 * 产生优惠劵号工具类
 * @version V1.0
 * @author wangwei
 * @date: 2015-7-29 下午3:21:37
 */

public class SerialNum {
 
	/** 
     * 产生优惠劵号 
     * @return 随机10数
     */  
    public static String generateNumber() {  
        String no="";  
        int[] defaultNums = new int[12];  
        for (int i = 0; i < defaultNums.length; i++) {  
            defaultNums[i] = i;  
        }  
  
        Random random = new Random();  
        int[] nums = new int[LENGTH];  
        int canBeUsed = 10;  
        for (int i = 0; i < nums.length; i++) {  

            int index = random.nextInt(canBeUsed);  
            nums[i] = defaultNums[index];  
            //将已用过的数字扔到备选数组最后，并减小可选区域  
            swap(index, canBeUsed - 1, defaultNums);  
            canBeUsed--;  
        }  
        if (nums.length>0) {  
            for (int i = 0; i < nums.length; i++) {  
                no+=nums[i];  
            }  
        }  
  
        return no;  
    }  
    private static final int LENGTH = 10;  
  
    private static void swap(int i, int j, int[] nums) {  
        int temp = nums[i];  
        nums[i] = nums[j];  
        nums[j] = temp;  
    }  
      
    public static String generateNumber2() {  
        String no="";  
        int num[]=new int[10];  
        int c=0;  
        for (int i = 0; i < 10; i++) {  
            num[i] = new Random().nextInt(10);  
            c = num[i];  
            for (int j = 0; j < i; j++) {  
                if (num[j] == c) {  
                    i--;  
                    break;  
                }  
            }  
        }  
        if (num.length>0) {  
            for (int i = 0; i < num.length; i++) {  
                no+=num[i];  
            }  
        }  
        return no;  
    }  
  

	}




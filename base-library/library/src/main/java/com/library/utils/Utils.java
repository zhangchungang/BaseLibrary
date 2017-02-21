package com.library.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;

/**
 * Created by Administrator on 2017/2/6.
 */

public class Utils {
    /**
     * 判断手机号码是否合理
     *
     * @param phoneNums
     */
    public static boolean judgePhoneNums(String phoneNums) {
        if (isMatchLength(phoneNums, 11)
                && isMobileNO(phoneNums)) {
            return true;
        }
        return false;
    }
    /**
     * 判断一个字符串的位数
     * @param str
     * @param length
     * @return
     */
    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobileNums) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }
    /**
     * 用getIdentifier()获取资源Id
     * int resID = getResources().getIdentifier("imageName", "drawable", "com.test.image");
     * Drawable image = getResources().getDrawable(resID);
     * @param context
     * @param name
     * @return
     */
    public static int getIdentifier(Context context,String name){
        return context.getResources().getIdentifier(name,"ids",context.getPackageName());
    }
    /**
     * 文字大小的px
     * @param context
     * @param value
     * @return
     */
    public static int Int2px(Context context, float value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, value,context.getResources().getDisplayMetrics());
    }

    /**
     * 宽高尺寸的dp
     * @param context
     * @param value
     * @return
     */
    public static int Int2dp(Context context, float value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,context.getResources().getDisplayMetrics());
    }
}

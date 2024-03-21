package org.ecommerceplatform.utils;

import org.springframework.stereotype.Component;
@Component
public class DateUtil {

        public static String getCurrentDateStr() {
            // TODO Auto-generated method stub
            String number = Math.floor(((Math.random()*1000000+1000001)))+"";
            String num=number.substring(0,number.indexOf("."));
            return num;
        }

}

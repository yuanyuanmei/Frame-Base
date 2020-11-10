package com.ljcx.api;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Test2 {

        public static void main(String[] args) {

            printUrls();
        }

        /**
         * 输出Url
         */
        public static void printUrls(){

            //过期时间
            String expirationTime = "2019-11-22 23:59:59";
            //Unix时间戳
            Long unixTime = getUnixTime(expirationTime);

            //bizid
            String bizId = "88888";
            //随机码 随机码自己随意填写
            String randomStr = "549b399e00";
            //推流防盗链
            String key = "d429dfcbdb604e8145e0d2e7acefd152";
            String streamId = bizId+"_"+randomStr;
            //时间戳16进制
            String txTime = Integer.toHexString(unixTime.intValue()).toUpperCase();

            //获取md5 txSecret
            String txSecret = getMd5(key+streamId+txTime);

            //视频推送url
            String pushUrl = "rtmp://"+bizId+".livepush.myqcloud.com/live/"+streamId+"?bizid="+bizId+"&txSecret="+txSecret+"&txTime="+txTime;

            //视频播放url rtmp
            String playUrlRtmp = "rtmp://"+bizId+".liveplay.myqcloud.com/live/"+streamId;

            //视频播放url flv
            String playUrlFlv = "http://"+bizId+".liveplay.myqcloud.com/live/"+streamId+".flv";

            //视频播放url hls
            String playUrlHls = "http://"+bizId+".liveplay.myqcloud.com/live/"+streamId+".m3u8";

            System.out.println("pushUrl="+pushUrl);
            System.out.println("playUrlRtmp="+playUrlRtmp);
            System.out.println("playUrlFlv="+playUrlFlv);
            System.out.println("playUrlHls="+playUrlHls);
        }
        /**
         * 获取unix时间戳
         * @return
         * @throws Exception
         */
        public static Long getUnixTime (String dateStr) {

            try {

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                long epoch = df.parse(dateStr).getTime();

                return epoch/1000;

            } catch (ParseException e) {
                e.printStackTrace();
            }

            return 0L;
        }

        /**
         * 获取md5字符串
         * @param str
         * @return
         */
        public static String getMd5(String str) {

            MessageDigest md5 = null;
            try {

                md5 = MessageDigest.getInstance("MD5");

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            byte[] bs = md5.digest(str.getBytes());
            StringBuilder sb = new StringBuilder(40);
            for(byte x:bs) {
                if((x & 0xff)>>4 == 0) {
                    sb.append("0").append(Integer.toHexString(x & 0xff));
                } else {
                    sb.append(Integer.toHexString(x & 0xff));
                }
            }
            return sb.toString();
        }

}


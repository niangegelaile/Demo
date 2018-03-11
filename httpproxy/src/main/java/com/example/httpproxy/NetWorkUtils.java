package com.example.httpproxy;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * NetWork Utils
 * <ul>
 * <strong>Attentions</strong>
 * <li>You should add <strong>android.permission.ACCESS_NETWORK_STATE</strong> in manifest, to get network status.</li>
 * </ul>
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-11-03
 */
public class NetWorkUtils {

    public static final String NETWORK_TYPE_WIFI       = "wifi";
    public static final String NETWORK_TYPE_3G         = "eg";
    public static final String NETWORK_TYPE_2G         = "2g";
    public static final String NETWORK_TYPE_WAP        = "wap";
    public static final String NETWORK_TYPE_UNKNOWN    = "unknown";
    public static final String NETWORK_TYPE_DISCONNECT = "disconnect";

    /**
     * Get network type
     * 
     * @param context
     * @return
     */
    public static boolean getNetWorkEnable(Context context) {
        if(context==null){
            return false;
        }
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;
        String type = NETWORK_TYPE_DISCONNECT;
        if (manager == null || (networkInfo = manager.getActiveNetworkInfo()) == null) {
            return false;
        }

        if (networkInfo.isConnected()) {
            String typeName = networkInfo.getTypeName();
            if ("WIFI".equalsIgnoreCase(typeName)) {
//                type = NETWORK_TYPE_WIFI;
                return true;
            } else if ("MOBILE".equalsIgnoreCase(typeName)) {
//                String proxyHost = android.net.Proxy.getDefaultHost();
//                type = TextUtils.isEmpty(proxyHost) ? (isFastMobileNetwork(context) ? NETWORK_TYPE_3G : NETWORK_TYPE_2G)
//                        : NETWORK_TYPE_WAP;
                return true;
            } else {
//                type = NETWORK_TYPE_UNKNOWN;
                return false;
            }
        }
        return false;
    }

    /**
     * Whether is fast mobile network
     * 
     * @param context
     * @return
     */
    private static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager == null) {
            return false;
        }

        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false;
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false;
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false;
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true;
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true;
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false;
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true;
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true;
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true;
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true;
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true;
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false;
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }
}

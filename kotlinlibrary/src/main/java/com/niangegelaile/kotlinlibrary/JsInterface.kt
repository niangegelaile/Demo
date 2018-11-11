package com.niangegelaile.kotlinlibrary

import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast

import org.json.JSONObject

import java.lang.ref.WeakReference
import java.util.HashMap

/**
 * webview 注入原生方法
 */
class JsInterface(webView: WebView) {
    private val webViewWeakReference: WeakReference<WebView>
    val info: String
        @JavascriptInterface
        get() {
            val map = HashMap<String, Any>()
            map["id"] = 22
            map["tel"] = "13750523051"
            return JSONObject(map).toString()
        }

    init {
        this.webViewWeakReference = WeakReference(webView)
    }

    @JavascriptInterface
    fun javaMethod(p: String) {
        Log.d(TAG, "JSHook.JavaMethod() called! + $p")
        Toast.makeText(webViewWeakReference.get()!!.getContext(), "js调用了JsHook :javaMethod", Toast.LENGTH_SHORT).show()
    }

    @JavascriptInterface
    fun showAndroid() {
        val info = "来自手机内的内容！！！"
        webViewWeakReference.get()!!.loadUrl("javascript:show('$info')")
    }

    companion object {
        private val TAG = "JsInterface"
    }
}

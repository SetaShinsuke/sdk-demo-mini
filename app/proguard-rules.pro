# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\SETA\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# ========== facehub_sdk ==========
#-libraryjars libs/facehubSDKLib-release.aar
-keep class com.azusasoft.facehubcloudsdk.** {*;}
-dontwarn org.apache.log4j.**
#-keep class de.mindpipe.android.logging.log4j.** {*;}
#-keep class org.apache.log4j.** {*;}
# ================================


# ========== Async-Http ==========
#-libraryjars libs/android-async-http:1.4.9.jar
# ================================

# ========== EventBus ============
#-libraryjars libs/eventbus-2.4.0.jar
#-keepclassmembers class ** {
#    public void onEvent*(**);
#}

# ========== support-v4 ==========
#-libraryjars libs/android-support-v4.jar
#-dontwarn android.support.v4.**
#-keep class android.support.v4.** {*;}
#-keep interface android.support.v4.app.** {*;}
# ================================


# ========== image_loader ==========
#-libraryjars libs/universal-image-loader-1.8.4-with-sources.jar  #imageLoader的jar包不要混淆
#-keep class com.nostra13.universalimageloader.** { *; }              #imageLoader包下所有类及类里面的内容不要混淆
# ==================================

#-libraryjars libs/android-logging-log4j-1.0.3.jar
#-libraryjars libs/log4j-1.2.15.jar
#-keep class * org.apache.log4j.** {*;}

# 不被混淆的
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.preference.Preference
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.annotation.**
-keep public class * extends android.support.v7.*
#自定义控件不要混淆
-keep public class * extends android.view.View {*;}

#adapter也不能混淆
-keep public class * extends android.widget.Adapter {*;}


-dontwarn android.webkit.WebView
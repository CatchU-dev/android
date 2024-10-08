# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keep class com.isaacsufyan.catchu.domain.model.** { *; }
#-keep class com.isaacsufyan.catchu.domain.enum.** { *; }

# Keep the Gson annotations
-keepattributes Signature,RuntimeVisibleAnnotations

# Keep Gson classes
-keep class com.google.gson.** { *; }


# Preserve Retrofit and its CallAdapterFactory
-keep class retrofit2.adapter.completablefuture.** { *; }
-keep class retrofit2.** { *; }
-keepclassmembers class retrofit2.** { *; }

# Preserve generic type information for classes using CompletableFuture
-keep class java.util.concurrent.CompletableFuture { *; }
-keepattributes Signature

# Keep generic information for the methods in Retrofit's CallAdapterFactory
-keepclassmembers class * {
    public static retrofit2.CompletableFutureCallAdapterFactory create(...);
}

# Prevent warnings from Retrofit classes
-dontwarn retrofit2.**


今天做的是接着昨天的AIDLService继续往下做，解决昨天的问题所在，让程序可以跑起来
今天碰到的第一个错误：<interface declaration>, <parcelable declaration>, AidlTokenType.import or AidlTokenType.package exp
解决办法：找到Android Studio -> Settings -> Editor -> Language Injections 。找到AIDL那项删除即可。
第二个错误：android.content.ActivityNotFoundException: Unable to find explicit activity class
原因是StartActivity 改为startService
第三个错误：java.lang.NullPointerException：路径设置错误
创建AIDL文件后不可以修改里面的文件，否则就会报错。	
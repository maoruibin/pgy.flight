package com.moji.daypack.data.model;

import java.util.List;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 3/16/16
 * Time: 11:07 PM
 * Desc: App
 */
/*
{
    "id": "564d8604e75e2d71e5000008",
    "user_id": "55cdb07ff2fc42680d00000c",
    "type": "android",
    "name": "牙医经理人",
    "short": "bailu",
    "bundle_id": "com.Bailu.DentistManager",
    "genre_id": 0,
    "is_opened": true,
    "web_template": "default",
    "custom_market_url": "",
    "has_combo": false,
    "created_at": 1447921156,
    "icon_url": "http://firicon.fir.im/ad11104bab7047cc40760c2bbcfcb3e154071cd8?t=1458042185.912362",
    "master_release": {
      "version": "2.6.5",
      "build": "16031502",
      "release_type": "inhouse",
      "distribution_name": "",
      "supported_platform": null,
      "created_at": 1458030884
    }
  }
*/
public class AppPgy extends BaseBean{

    /**
     * code : 0
     * message :
     * data : {"list":[{"appKey":"4b04794582cd7e91bb9b6526bf4a25ab","appType":"1","appFileSize":"33046776","appName":"墨迹天气","appVersion":"6.0.4","appVersionNo":"5006000400","appBuildVersion":"70","appIdentifier":"com.moji.MojiWeatherNew","appIcon":"b6c9a3d1a889d4cdd4768356a4e6e39b","appDescription":"","appUpdateDescription":"1. @杨阳\n修复15天24小时切换时的性能问题\n优化数据加载中时的滑动性能","appScreenshots":"","appCreated":"2016-08-24 22:16:09"},{"appKey":"82f3fbb5001ab4b39049536b79a03218","appType":"2","appFileSize":"15541930","appName":"Android 6.0","appVersion":"6.0002.02","appVersionNo":"6000202","appBuildVersion":"36","appIdentifier":"com.moji.mjweather","appIcon":"ba81bdb0a40c596e701f961a16951d37","appDescription":"全球4.7亿人的都在用的天气APP，支持3000国内城市及70万海外城市及地区天气查询。提供15天天气预报，更有实时空气质量&空气质量预报，让您随时掌握呼吸的每一寸空气！特殊天气提前发送预警信息，生活更便利！\n【天气功能】\n*逐时预报：提供24小时的逐小时温度、天气变化趋势，全天温度变化尽在掌握。\n*15天天气预报：预报时间更长，风力预报，更早知晓未来天气。\n*短时预报：分钟级，公里级天气预报，量身预报雨雪，一站式打车服务享便利。\n*空气质量实时报告，包含PM2.5、PM10等六项污染物，还有全国空气质量排行，查看你的城市空气排名。\n*空气指数预报，预报未来5天的AQI级别、雾霾情况。\n在微信里也能随时查看天气和空气指数啦！\n欢迎搜索关注我们微信公众号：墨迹天气服务号\n微信号：mojiweatherchina","appUpdateDescription":"1、修复首页广告位位置 @段迪\n2、适配华为荣耀8通知栏 @段迪\n3、修复24小时隐藏图标不清晰 @段迪\n4、BaseMoudle数组资源文件增加繁体 @张徐峰\n5、增加晴天时首页短时曲线背板 @段迪\n6、修改桌面插件灰度产生的空指针问题 @张徐峰\n7、修改预报数据过少，插件显示问题 @张徐峰\n8、修改推送到达埋点，解决多进程冲突 @张徐峰\n9、华为反馈横竖屏问题 @张乙龙\n10、增加晴天时首页短时曲线背板 @段迪\n【已知问题】\n无","appScreenshots":"f66b9679e65fb1fdb527841b6d08de61,7db4d5634a5163af6e3f7a2891665811,a9a2c627d7bc2ae6fa1974fcec2b135b,99780c93a72a392a9c30b8565dbd5580,7905ff27ce8b7353f52533c39ccc217a,5e6cc92fc47fe263fb7cf02f3f121464","appCreated":"2016-08-24 22:04:02"},{"appKey":"6883a26e1c93b66914a5c3b3ae1d7a1c","appType":"2","appFileSize":"287891","appName":"日拱一卒","appVersion":"1.0.7","appVersionNo":"1","appBuildVersion":"3","appIdentifier":"com.moji.daypack","appIcon":"afbc80a06a12590c64b5c5d694401dbf","appDescription":"墨迹天气每日构建应用。","appUpdateDescription":"修改下载的方式@宋凯","appScreenshots":"","appCreated":"2016-08-17 17:15:53"},{"appKey":"1d74c8b036220ac3be09560ffe85f454","appType":"1","appFileSize":"376867","appName":"日拱一卒","appVersion":"1.0.8","appVersionNo":"1001000803","appBuildVersion":"2","appIdentifier":"com.moji.dailybuild","appIcon":"fad61ecfed2c04fd684e57f8455bc06b","appDescription":"墨迹天气每日构建应用。","appUpdateDescription":"增加推送功能。","appScreenshots":"","appCreated":"2016-08-16 22:01:06"},{"appKey":"d55808c28c01068c1a7058c841076127","appType":"2","appFileSize":"20630040","appName":"墨迹Alpha版","appVersion":"6.0000.01","appVersionNo":"1","appBuildVersion":"24","appIdentifier":"com.moji.mjweather.alpha","appIcon":"f413ad578a36e053703dfb6bdd3f2db7","appDescription":"","appUpdateDescription":"1:短时页面分享功能。@Owner：宋志刚 \n2、修复登陆成功后偶尔跳转时常的问题。@Owner：毛瑞斌\n3、修复 AQI 页面的交互细节问题以及AQI 全国排名顺序的问题。@Owner：毛瑞斌\n3、空白区域点击下滑。@Owner：佟磊\n\n【已知问题】\n1、Alpha版本的分享暂时无法使用。","appScreenshots":"","appCreated":"2016-06-23 23:56:13"},{"appKey":"b667babf7708e8055990ca58bb57ec6c","appType":"1","appFileSize":"29854078","appName":"墨迹天气","appVersion":"5.88","appVersionNo":"5005080899","appBuildVersion":"13","appIdentifier":"com.moji.MojiWeatherTest","appIcon":"8d9c0006927edde0eb0f08b2000d097c","appDescription":"","appUpdateDescription":"1. 修复Widget-\u201d现在\u201c中时间和空指信息重叠的问题。 @张瀚元\n2. 修复墨圈-未登录时话题详情页话题红人榜里仍能关注成功的问题。 @尤国飚\n3. 修复Feeds评论推送不能打开文章问题。 @姚普欣\n4. 修正15天详情页面宽度，固定显示Yesterday。 @吴比\n\n【已知问题】\n1. 滴滴SDK定位不准，肯能需要更新SDK版本。 @张瀚元","appScreenshots":"","appCreated":"2016-06-15 23:32:46"}]}
     */


    public DataBean data;

    public static class DataBean {
        /**
         * appKey : 4b04794582cd7e91bb9b6526bf4a25ab
         * appType : 1
         * appFileSize : 33046776
         * appName : 墨迹天气
         * appVersion : 6.0.4
         * appVersionNo : 5006000400
         * appBuildVersion : 70
         * appIdentifier : com.moji.MojiWeatherNew
         * appIcon : b6c9a3d1a889d4cdd4768356a4e6e39b
         * appDescription :
         * appUpdateDescription : 1. @杨阳
         修复15天24小时切换时的性能问题
         优化数据加载中时的滑动性能
         * appScreenshots :
         * appCreated : 2016-08-24 22:16:09
         */

        public List<AppEntity> list;
    }
}

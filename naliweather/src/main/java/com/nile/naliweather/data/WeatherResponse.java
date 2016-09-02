package com.nile.naliweather.data;

import java.io.Serializable;

/**
 * Created by taotao on 16/9/1.
 */
public class WeatherResponse implements Serializable {
    private static final long serialVersionUID = 4901139040991047373L;
    /**
     * reason : successed!
     * result : {"data":{"realtime":{"city_code":"101020100","city_name":"上海","date":"2016-08-31","time":"19:00:00","week":3,"moon":"七月廿九","dataUptime":1472642231,"weather":{"temperature":"30","humidity":"37","info":"晴","img":"0"},"wind":{"direct":"北风","power":"0级","offset":null,"windspeed":null}},"life":{"date":"2016-8-31","info":{"chuanyi":["炎热","天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。"],"ganmao":["少发","各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。"],"kongtiao":["部分时间开启","您将感到些燥热，建议您在适当的时候开启制冷空调来降低温度，以免中暑。"],"wuran":["较差","气象条件较不利于空气污染物稀释、扩散和清除，请适当减少室外活动时间。"],"xiche":["较适宜","较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"],"yundong":["较适宜","天气较好，户外运动请注意防晒，推荐您在室内进行低强度运动。"],"ziwaixian":["中等","属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。"]}},"weather":[{"date":"2016-08-31","info":{"day":["0","晴","34","西南风","微风","05:29"],"night":["1","多云","25","西南风","微风","18:19"]},"week":"三","nongli":"七月廿九"},{"date":"2016-09-01","info":{"dawn":["1","多云","25","西南风","微风","18:19"],"day":["1","多云","34","南风","微风","05:30"],"night":["1","多云","26","南风","微风","18:17"]},"week":"四","nongli":"八月初一"},{"date":"2016-09-02","info":{"dawn":["1","多云","26","南风","微风","18:17"],"day":["1","多云","35","南风","微风","05:30"],"night":["1","多云","26","东南风","微风","18:16"]},"week":"五","nongli":"八月初二"},{"date":"2016-09-03","info":{"dawn":["1","多云","26","东南风","微风","18:16"],"day":["1","多云","34","北风","微风","05:31"],"night":["1","多云","26","北风","微风","18:15"]},"week":"六","nongli":"八月初三"},{"date":"2016-09-04","info":{"dawn":["1","多云","26","北风","微风","18:15"],"day":["3","阵雨","33","东北风","微风","05:32"],"night":["3","阵雨","27","东北风","微风","18:14"]},"week":"日","nongli":"八月初四"}],"pm25":{"key":"Shanghai","show_desc":0,"pm25":{"curPm":"79","pm25":"36","pm10":"73","level":2,"quality":"良","des":"可以接受的，除极少数对某种污染物特别敏感的人以外，对公众健康没有危害。"},"dateTime":"2016年08月31日19时","cityName":"上海"},"jingqu":"","date":"","isForeign":"0"}}
     * error_code : 0
     */

    private String reason;
    /**
     * data : {"realtime":{"city_code":"101020100","city_name":"上海","date":"2016-08-31","time":"19:00:00","week":3,"moon":"七月廿九","dataUptime":1472642231,"weather":{"temperature":"30","humidity":"37","info":"晴","img":"0"},"wind":{"direct":"北风","power":"0级","offset":null,"windspeed":null}},"life":{"date":"2016-8-31","info":{"chuanyi":["炎热","天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。"],"ganmao":["少发","各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。"],"kongtiao":["部分时间开启","您将感到些燥热，建议您在适当的时候开启制冷空调来降低温度，以免中暑。"],"wuran":["较差","气象条件较不利于空气污染物稀释、扩散和清除，请适当减少室外活动时间。"],"xiche":["较适宜","较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"],"yundong":["较适宜","天气较好，户外运动请注意防晒，推荐您在室内进行低强度运动。"],"ziwaixian":["中等","属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。"]}},"weather":[{"date":"2016-08-31","info":{"day":["0","晴","34","西南风","微风","05:29"],"night":["1","多云","25","西南风","微风","18:19"]},"week":"三","nongli":"七月廿九"},{"date":"2016-09-01","info":{"dawn":["1","多云","25","西南风","微风","18:19"],"day":["1","多云","34","南风","微风","05:30"],"night":["1","多云","26","南风","微风","18:17"]},"week":"四","nongli":"八月初一"},{"date":"2016-09-02","info":{"dawn":["1","多云","26","南风","微风","18:17"],"day":["1","多云","35","南风","微风","05:30"],"night":["1","多云","26","东南风","微风","18:16"]},"week":"五","nongli":"八月初二"},{"date":"2016-09-03","info":{"dawn":["1","多云","26","东南风","微风","18:16"],"day":["1","多云","34","北风","微风","05:31"],"night":["1","多云","26","北风","微风","18:15"]},"week":"六","nongli":"八月初三"},{"date":"2016-09-04","info":{"dawn":["1","多云","26","北风","微风","18:15"],"day":["3","阵雨","33","东北风","微风","05:32"],"night":["3","阵雨","27","东北风","微风","18:14"]},"week":"日","nongli":"八月初四"}],"pm25":{"key":"Shanghai","show_desc":0,"pm25":{"curPm":"79","pm25":"36","pm10":"73","level":2,"quality":"良","des":"可以接受的，除极少数对某种污染物特别敏感的人以外，对公众健康没有危害。"},"dateTime":"2016年08月31日19时","cityName":"上海"},"jingqu":"","date":"","isForeign":"0"}
     */

    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
}

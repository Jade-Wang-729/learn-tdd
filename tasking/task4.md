4. 向存在的用户发送通知消息
***发送通知信息：向notification表里添加entity（用户id，status，content）
***信息不为空
### 1. given 存在的用户名 有效信息内容 when 发信息 then 发送成功
* Repository：given 存在的用户名 有效信息内容 when 发信息 then 插入数据成功
* Service：given 存在的用户名 有效信息内容 when 发信息 then 返回成功
* Controller： given 存在的用户名 有效信息内容 when 发信息 then 返回成功，httpStatus:200
* ### 1. given 不存在的用户名 有效信息内容 when 发信息 then 发送成功
* Repository：given 不存在的用户名 有效信息内容 when 发信息 then 插入数据失败
* Service：given 不存在的用户名 有效信息内容 when 发信息 then 返回error（用户不存在）
* Controller： given 不存在的用户名 有效信息内容 when 发信息 then 返回失败，httpStatus:400，error（用户不存在）
* ### 1. given 存在的用户名 无效信息内容 when 发信息 then 发送成功
* Service：given 存在的用户名 无效信息内容 when 发信息 then 返回error（信息为空）
* Controller： given 存在的用户名 无效信息内容 when 发信息 then 返回失败，httpStatus:400，error（信息为空）

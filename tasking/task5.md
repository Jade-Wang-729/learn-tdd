5. 更新通知消息状态（approve，reject）
***更新通知信息：依据id和content，修改status
***先选择状态
### 1. given 存在的id，存储的content， when 更新 then 更新成功
* Repository：given 存在的id，存储的content when 更新 then 修改数据成功
* Service：given 存在的id，存储的content when 更新 then 返回成功
* Controller： given 存在的id，存储的content when 更新 then 返回成功，httpStatus:200
* ### 1. given 不存在的id，存储的content when 发信息 then 更新失败
* Repository：given 不存在的id，存储的content when 更新 then 插入数据失败
* Service：given 不存在的id，存储的content when 更新 then 返回error（用户不存在）
* Controller： given 不存在的用户名 有效信息内容 when 更新 then 返回失败，httpStatus:400，error（用户不存在）
* ### 1. given 存在的id 无效信息内容 when 发信息 then 更新失败
* Service：given 存在的id 无效信息内容 when 更新 then 返回error（信息不存在）
* Controller： given 存在的id 无效信息内容 when 更新 then 返回失败，httpStatus:400，error（信息不存在）

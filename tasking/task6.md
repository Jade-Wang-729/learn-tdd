6. 根据用户id查询通知消息
***0或多条消息
### 1. given 存在的id，有3条存储的content， when 查询 then 查询成功
* Repository：given 存在的id when 查询 then 返回3条数据
* Service：given 存在的id when 查询 then 返回成功
* Controller： given 存在的id when 查询 then 返回3条数据，httpStatus:200
### 2. given 存在的id，有1条存储的content， when 查询 then 查询成功
* Repository：given 存在的id when 查询 then 返回1条数据
* Service：given 存在的id when 查询 then 返回成功
* Controller： given 存在的id when 查询 then 返回1条数据，httpStatus:200* 
### 3. given 存在的id，有0条存储的content， when 查询 then 查询成功
* Repository：given 存在的id when 查询 then 返回3条数据
* Service：given 存在的id when 查询 then 返回成功
* Controller： given 存在的id when 查询 then 返回3条数据，httpStatus:200*
### 4. given 不存在的id， when 查询 then 查询失败
* Repository：given 不存在的id，when 查询 then 查询失败
* Service：given 不存在的id，when 查询 then 返回error（id不存在）
* Controller： given 不存在的id ，when 查询 then 返回失败，httpStatus:400，error（id不存在）


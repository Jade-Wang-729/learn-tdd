3. 用户可以修改密码。用户名和密码输入正确，两次新密码相等
### 1. given 有效的用户名和有效的密码 两次正确的新密码 when 改密码 then 修改成功
* Repository：given 有效的用户名和有效的密码 when 查询用户数据 then 返回查询结果
* Repository：given 两次正确的新密码 when 修改数据 then 成功修改数据库
* Service：given 有效的用户名和有效的密码 两次正确的新密码 when 改密码 then 
* Controller： given 有效的用户名和有效密码 两次正确的新密码 when 改密码 then 返回成功，httpStatus:200
### 2. given 无效的用户名或无效的密码 when 改密码 then 修改失败
* Repository：given 无效的用户名或无效的密码 when 查询用户数据 then 返回空查询列
* Service：given 无效的用户名或无效的密码 when 改密码 then 返回失败
* Controller： given 无效的用户名或无效的密码 when 改密码 then 返回失败，httpStatus:400
### 3. given 有效的用户名和有效的密码 两次不一致的新密码 when 改密码 then 修改失败
* Repository：given 有效的用户名和有效的密码 两次不一致的新密码 when 查询用户数据 then 查询成功
* Service：given 有效的用户名和有效的密码 两次不一致的新密码 when 改密码 then 返回失败
* Controller： given 有效的用户名和有效的密码 两次不一致的新密码 when 改密码 then 返回失败，httpStatus:400


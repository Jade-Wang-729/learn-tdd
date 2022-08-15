2. 用户使用正确的用户名和密码可以重新登录，用户名或密码错误提示用"用户名或密码错误"
### 1. given 存在的用户名和对应的密码 when 登陆 then 登陆成功
* Repository：given 存在于数据库的用户名和对应密码 when 登陆 then 可以查询用户数据
* Service：given 存在于数据库的用户名和对应密码 when 登陆 then 登陆成功
* Controller： given 有效的用户名和有效密码 when 登陆 then 返回成功，httpStatus:200
*
### 2. given 无效的用户名和密码 when 登陆 then 登陆失败
* Repository：given 无效的用户名和密码 when 登陆 then 可以查询出用户数据
* Service：given 无效的用户名和密码 when 登陆 then 抛出异常，异常信息<用户名或密码错误>
* Controller： given 无效的用户名和密码 when 登陆 then 返回<用户名或密码错误>，httpStatus:400
*
### 3. given 有效的用户名和错误密码 when 登陆 then 登陆失败
* Repository：given 有效的用户名和错误密码 when 登陆 then 可以查询出用户数据
* Service：given 有效的用户名和错误密码 when 登陆 then 抛出异常，异常信息<用户名或密码错误>
* Controller： given 有效的用户名和错误密码 when 登陆 then 返回<用户名或密码错误>，httpStatus:400

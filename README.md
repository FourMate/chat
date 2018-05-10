# chat
聊天应用，前后端分离，包含两个项目，一个前端项目、一个后端项目

## 后台会对非static开头的请求做过滤，该请求应该传递sessionId和token，后台会根据则两个值判断该用户是否已经登录。

## 前端提供了几个方法：
#### nighty.Login.isLogin(serverBaseUrl, messageMethod)，messageMethod为可选参数，该方法会请求后台判断是否已经登录
#### nighty.Login.logout(serverBaseUrl, loginPage, messageMethod)，messageMethod为可选参数
#### nighty.Login.getSessionId()
#### nighty.Login.getToken()

## 说明
#### 1.serverBaseUrl为后台API的基本URL，如http://localhost:8080/nighty-app
#### 2.loginPage为登录会需要跳转的页面路径
#### 3.messageMethod为弹出提示信息的方法，如果不传，则为alert

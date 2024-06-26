## 其他说明

###  1 数据库说明

1. 项目使用MySQL8

2. 请使用DB.sql创建数据库。

### 2 Server部署和运行



#### 1 必须使用Tomcat10以及以上版本

	1. 由于Tomcat9不支持Servlet5.0规范，故推荐使用Tomcat10或者11
	1. 使用maven安装依赖
	1. 运行端口请指定为30836
	1. 请设置应用程序上下文（web根路径）为  / ，如图：

![image-20240626121437802](https://ftp.icefox.site/blog/images/202406261214209.png)

5. *设置你的数据库用户名和密码，默认两者均为root，路径如下

``` txt
Server/src/main/java/site/icefox/xtstore/Utils/DbConnectionUtil.java
```

### 3 Web部署和运行

#### 1  安装 pnpm

``` bash
npm i pnpm -g
```

#### 2 安装依赖

``` bash
pnpm install
```

#### 3 运行

``` bash
pnpm run dev
```


首先需要新建一个项目叫`config-server`,在intelliJ IDEA中 `new
 project ---> Spring-Initializr`

![new project ](http://upload-images.jianshu.io/upload_images/2039222-d6df70681d6849a8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![--> 选择 Spring-Initializr](http://upload-images.jianshu.io/upload_images/2039222-879836e77aaa8056.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![--> 选择 Spring-Initializr --> next](http://upload-images.jianshu.io/upload_images/2039222-2a93fc40e04287e1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![--> 编辑Group栏目, Artifact(项目名字)，Type选择Gradle  Project--> next](http://upload-images.jianshu.io/upload_images/2039222-f76e5861c9dd855c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![添加需要的依赖actuator 和 cloud server](http://upload-images.jianshu.io/upload_images/2039222-fddb44547bab4e13.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![选择的依赖在右边显示👉---> next ](http://upload-images.jianshu.io/upload_images/2039222-d69b4968d8c92f4c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![输入项目的名字 --> Finish](http://upload-images.jianshu.io/upload_images/2039222-81dc55a2bb17fa16.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![完成之后如果会出现这个窗口的话，就把第一个和倒数第二个勾选上、或者倒数第三个](http://upload-images.jianshu.io/upload_images/2039222-4958c58ecb6d257d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

现在需要将一个配置信息项目放在github上：
https://github.com/qmm-g-Excellent/Configs

  在resource目录下的yml文件里写上如下内容：
  
```
server:
  port: 1234  //该config server 运行的端口号
spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/qmm-g-Excellent/Configs.git
          search-paths:
            - "station*"
```
上面需要在第一行写上"---"，然后再写配置内容。

![请求application name 为s1rates 的内容](http://upload-images.jianshu.io/upload_images/2039222-0bd568df4ef907df.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![用json格式获取内容](http://upload-images.jianshu.io/upload_images/2039222-4d8f6d2dcc273230.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![获取dev下的内容](http://upload-images.jianshu.io/upload_images/2039222-48efafe5b0276830.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![获取json格式的内容](http://upload-images.jianshu.io/upload_images/2039222-f1abc2ba990b01a8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
 

![以一个不存在的application name: s3rates,去获取它的内容](http://upload-images.jianshu.io/upload_images/2039222-66e8c12e1ff364d0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
结果就是只会获取application.properties的内容。

**总结：**
* 根据以上例子，使用非json格式获取内容的结果来看，可以知道，如果获取default下的内容时，它是会去取拿根目录下的`application.properties`的内容，再会拿`station1`下面的`s1rates.properties`的内容；
* 如果获取dev下的内容时，它是会去取拿根目录下的`application.properties`的内容，再会拿`station1`下面的`s1rates.properties`的内容，还会去拿指定的`s1rates-dev.properties`下的内容。然后把它们放到一起返回。
8 以json格式获取内容时，是把所有的内容做整合，先拿最外面默认的`application.properties`的内容，然后去拿`s1rates.properties`的内容，再去拿指定的 ` s1rate-de.properties`中的没有的属性会保留，重复的属性会被覆盖。最后把整合后的结果返回来。



*自己想一想：*
#####1.这是神马？
 >这是一个提供公共配置的server。  
 * 它有自己的配置文件，其中写的配置内容包括：服务器所需要的公共配置信息，比如：服务端口，数据库链接信息等；  
 * 还有一些放在远程仓库的配置信息，其内容可以是业务上的一些属性，将其写成配置信息。
（选择将这些配置信息放在远程仓库，而不是放在服务中的某个配置文件中，是因为如果放在server的配置文件中，如果需要更改这些配置信息，那么server就需要重新打包、run；而写在远程仓库，在server的配置中引入链接等配置去读取远程库中的配置内容，即使内容改变了，server在每次去获取远程库中的内容时，也会读取到最新的配置信息，不需要重新run server。）


#####2.为什么要有这个？
* 可以提供给其他server需要的公共配置信息，及服务调用时的调用信息

#####3.用这个有很么好处？
* 提供公共统一的配置信息

out of scope：
服务发现的好处：
* 提供业务中需要的配置信息
* 它是一种服务发现的实现方案，这里记录了分布式系统中所有服务的信息，开发者或者其他服务可以据此找到这些服务。
* 配置简单，无需了解其他服务的体系架构，就能找到并使用该服务

*也可以在start.spring.io上面生成一个项目。*

#####业务服务器

新建一个普通的gradle项目：

![建一个普通的gradle项目](http://upload-images.jianshu.io/upload_images/2039222-0deaca6aa6c56b9f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![填写包名 和 项目名 ](http://upload-images.jianshu.io/upload_images/2039222-198f82b92a0af9dc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

新建`/java/ com.dailmer.microservice/ConfigclientApplication.java`：
```
  package com.dailmer.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConfigClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigClientApplication.class, args);
	}
}

```

新建`/java/ com.dailmer.microservice/RateController.java` 

```java
package com.dailmer.microservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateController {

    @Value("${rate}")
    String rate;

    @Value("${lanecount}")
    String lanecount;

    @Value("${tollstart}")
    String tollStart;

    @Value("${environment}")
    String env;

    @RequestMapping(value = "/rate", method = RequestMethod.GET)
//    @GetMapping("/rate")
    public String getRate() {
        return String.format("rate is %s, lanecount is %s, tollStart is %s", rate, lanecount, tollStart);
    }
}
```


新建`resource/application.properties`：
```java
spring.application.name=s1rates    //  这里就是写上config-server中的application name
//management.security.enabled=false
```
新建`resource/bootstrap.properties`：

```java
spring.profiles.active=qa; //这里表示config-server中的profile 类型
spring.cloud.config.uri=http://localhost:8888  // config-server的访问地址
//spring.cloud.config.username=qmm
//spring.cloud.config.password=123
```

如果上面的两个文件中注释的部分的作用是，如果config-server不希望本服务就直接获取config-server的内容，而是需要通过用户名密码来访问获取内容：
那么就需要做以下几个步骤：
* config-server:
 1. 添加依赖 spring-boot-starter-security
    ![](http://upload-images.jianshu.io/upload_images/2039222-9f41e24b6adcfdcb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
 
 2. 在application.properties 配置文件中添加配置信息：
 ```
 security:
   basic:
     enabled: true
   user:
     name: qmm
     password: 123
 ```
  设置用户名和密码
 
* microservice：
`application.properties中添加配置内容：`
```
management.security.enabled=false
```
`bootstrap.properties中添加配置内容：`
```
spring.cloud.config.username=qmm
spring.cloud.config.password=123
```

 然后microservice就需要通过配置的用户名和密码来访问config-server中的配置内容。
 ![⚠️：选择Type：Basic Auth ](http://upload-images.jianshu.io/upload_images/2039222-e5b43bcfdf8459ff.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#####有个问题：如果修改来config-server中配置的放在github上的配置项目内容，而在postman中 请求microservice中的api 访问config-server的配置内容时，  
就会依然只能拿到原来的内容，不会拿到更改后的最新内容，这时候就需要在microservice的rateController.java中添加refreshScope注解：
![image.png](http://upload-images.jianshu.io/upload_images/2039222-a12c2d1f950baf04.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
* 那么就需要先请求：`http:localhost:8080/refresh` ,8080是`microservice`的端口,该请求表示刷新config-server加载的配置内容。
* 再请求`microservice`中写的api：`http:localhst:8080/rate`
这样`microservice`才会拿到最新的配置内容。

*也可以通过start.spring.io来生成项目，注意添加需要的依赖。*

![image.png](http://upload-images.jianshu.io/upload_images/2039222-c6b4d7f8cbeeb7ec.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
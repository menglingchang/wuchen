# wuchen
用到的技术点：
1、基于Netty实现rpc框架
2、使用Zookeeper实现服务列表注册
3、涉及Java序列化、反序列化技术
4、多线程与并发编程技术
5、自定义注解、反射技术
架构：
menglc-netty-dubbo-server ---  dubbo核心的服务
netty-dubbo-common  ---工具类
member-service-producer-api – 会员服务接口
member-service-producer-impl –会员服务接口实现
order-service-consumer-api–订单消费者接口
order-service-consumer-impl –订单消费者接口实现

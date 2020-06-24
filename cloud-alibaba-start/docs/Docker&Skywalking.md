# Skywalking install with Docker & Elasticsearch

## Env

| Name          | Version                 |
| ------------- | ----------------------- |
| Skywalking    | 8.0.1                   |
| Elasticsearch | 6.8.1                   |
| OS            | Mac10.15、Ubuntu18.04.4 |
|               |                         |

## Links

- `skywalking`官方下载地址：[https://skywalking.apache.org/downloads/](https://skywalking.apache.org/downloads/)
- `skywalking`官方源码仓库：[https://github.com/apache/skywalking](https://github.com/apache/skywalking)
- `elasticsearch`官方Docker镜像仓库：[https://hub.docker.com/_/elasticsearch](https://hub.docker.com/_/elasticsearch)

## Docker install Elasticsearch

`SkyWalking`对搜集到的数据有多种存储方案，官方推荐`Elasticsearch`。而对于分布式项目，通常需要多台服务器部署大量的分布式项目，必不可少需要Docker，我们首先看下SkyWalking官方源码中有没有针对ElasticSeatch的docker配置。

在[https://github.com/apache/skywalking/tree/master/docker](https://github.com/apache/skywalking/tree/master/docker) 可以看到官方提供了`docker-compose.yml`，但需要注意，此`docker-compose.yml`中不仅包含了对`elasticsearch`的配置还有`oap`等配置，经过测试发现官方这个配置在实际`docker run`中是有问题的，因此我们仅需关注Elasticsearch的配置。

1. 编写`docker-compose.yml`（放置到虚拟机Ubuntu中）

```yaml
version: '3.3'
services:
  elasticsearch:
    image: docker.elastic.co/elasticsearch/elasticsearch:6.8.1
    container_name: elasticsearch
    restart: always
    ports:
      - 9200:9200
    environment:
      discovery.type: single-node
    ulimits:
      memlock:
        soft: -1
        hard: -1
```

**注意：** `ES_TAG`参数在`.env`中定义，可看到官方推荐的`elasticsearch6.x`版本。

![image-20200624222615945](http://cdn.tycoding.cn/20200624222616.png)

2. 运行`docker-compose`，拉取镜像并启动容器

```shell
docker-compose up -d
```

![image-20200624223255367](http://cdn.tycoding.cn/20200624223259.png)

3. 测试Elasticsearch是否启动成功

在本地浏览器访问`ip:9200`。如果不知道虚拟机的IP地址，可通过`ipconfig`命令查看。访问出现如下JSON数据证明启动成功。（注意访问的是HTTP地址，如果访问成了HTTPS，也会导致连接不上）

![image-20200624223328726](http://cdn.tycoding.cn/20200624223328.png)

**Tips**

我在测试中会出现`docker ps`容器命名启动成功，但是在本地通过`ip:9200`始终连接不上。这里我有几个建议：

- 不要使用最新的Ubuntu系统（其他系统也是
- 重启虚拟机（不知道是不是VMware的挂起功能的影响

## SkyWalking服务端安装

1. 去SkyWalking官网下载最新版本的skywalking：[https://skywalking.apache.org/downloads/](https://skywalking.apache.org/downloads/)

![image-20200624223536591](http://cdn.tycoding.cn/20200624223536.png)

目前最新版本是`8.0.1`，注意如果使用的Elasticsearch7.x就选择`Binary Distribution for ElasticSearch 7`，因为我们前面提到了`skywalking`官方推荐的`elasticsearch6.8.1`所以选择`Binary Distribution`下载：

![image-20200624223936303](http://cdn.tycoding.cn/20200624223936.png)

2. 修改`application.yml`

首先找到`config/application.yml`，在84行找到关于`storage`的配置：

![image-20200624224226028](http://cdn.tycoding.cn/20200624224226.png)

首先修改`selector`参数，因为`storage`节点下有`elasticsearch`、`elasticsearch7`等节点，而我们前面提到了使用的`elasticsearch6.x`，所以修改为：

```yaml
selector: elasticsearch
```

然后修改`elasticsearch.nameSpace`参数，这个参数指配置的`elasticsearch`容器名称，请去前面回顾`docker-compose.yml`中的配置，我们定义的容器名称是：`elasticsearch`，所以修改为：

```yaml
  elasticsearch:
    nameSpace: ${SW_NAMESPACE:"elasticsearch"}
```

最后修改`elasticsearch.clusterNodes`，这个参数指`elasticsearch`服务的连接IP地址，默认是9200端口，如果你是在本地（Docker）部署的Elasticsearch，不需要修改这个参数，如果是部署在虚拟机上（比如我），那么修改为（上面演示的访问虚拟机Elasticsearch的地址）：

```yaml
  elasticsearch:
    nameSpace: ${SW_NAMESPACE:"elasticsearch"}
    clusterNodes: ${SW_STORAGE_ES_CLUSTER_NODES:172.16.60.129:9200}
```

上面的`172.16.60.129`是我的虚拟机IP地址。最后修改后的结果：

![image-20200624225806720](http://cdn.tycoding.cn/20200624225806.png)

启动`apache-skywalking-apm-bin/bin/startup.sh`：

![image-20200624225430352](http://cdn.tycoding.cn/20200624225430.png)

在浏览器上访问：`localhost:8080`，出现以下页面证明SkyWalking服务端安装成功。

**Tips**

此过程最重要的是修改`application.yml`文件，而主要修改`storage`节点的前三行参数。

- `selector`：选择的存储方案。（很多教程中直接就是说注释下面的`h2`配置就行了，但实际中仅注释并没指定`elasticsearch`存储方案。不如主动选择`elasticsearch`，也不必再注释`h2`节点了）
- `nameSpace`： Elasticsearch容器的名称，可以通过`docker container ls`查看，或者查看`docker-compose.yml`配置文件
- `clusterNodes`：指定Elasticsearch服务访问的IP地址，本地就是localhost，远程就是远程IP，端口默认9200

上面三个配置一个都不能少，否则会出现各种报错。如果出现报错就查看`logs`下的日志文件


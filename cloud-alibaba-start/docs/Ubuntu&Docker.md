# Ubuntu install Docker 

## Test Env

| Name          | Version |
| ------------- | ------- |
| Ubuntu server | 18.04.4 |

## Ubuntu install

下载`ubuntu server`：

- 官方地址：[https://ubuntu.com/server](https://ubuntu.com/server)
- 历史版本：[http://ftp.sjtu.edu.cn/ubuntu-cd/](http://ftp.sjtu.edu.cn/ubuntu-cd/) （速度快）

**Tips**

建议大家使用低版本开发。

## Docker install

1. 卸载旧版本

```shell
$ sudo apt-get install \
    linux-image-extra-$(uname -r) \
    linux-image-extra-virtual
```

2. 安装一些必要工具

```shell
sudo apt-get update
sudo apt-get -y install apt-transport-https ca-certificates curl software-properties-common
```

3. 安装GPG证书

```shell
curl -fsSL http://mirrors.aliyun.com/docker-ce/linux/ubuntu/gpg | sudo apt-key add -
```

4. 写入软件源信息

```shell
sudo add-apt-repository "deb [arch=amd64] http://mirrors.aliyun.com/docker-ce/linux/ubuntu $(lsb_release -cs) stable"
```

5. 更新并安装Docker CE

```shell
sudo apt-get -y update
sudo apt-get -y install docker-ce
```

6. 启动Docker CE

```shell
$ sudo service docker start
```

7. 建立docker用户组

```shell
$ sudo groupadd docker
```

8. 将当前用户加入docker组

```shell
$ sudo usermod -aG docker $USER
```

9. 配置Docker镜像加速器

```shell
$ vi /etc/docker/daemon.json

{
  "registry-mirrors": [
    "https://registry.docker-cn.com"
  ]
}
```

10. 重启Docker服务

```shell
$ sudo systemctl daemon-reload
$ sudo systemctl restart docker
```

11. 查看docker安装是否正常

```shell
$ docker info
```

出现一大堆docker的配置信息则证明安装正常。

## Docker Compose install

官方安装教程：[https://docs.docker.com/compose/install/](https://docs.docker.com/compose/install/)

在其中找到最新的release版本安装。以下使用`1.26.0`版本：

```shell
sudo curl -L "https://github.com/docker/compose/releases/download/1.26.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

sudo chmod +x /usr/local/bin/docker-compose
```

## Docker Shell

| Shell                           | Tips                           |
| ------------------------------- | ------------------------------ |
| `docker info`                   | 查看当前`docker`信息           |
| `docker ps`                     | 查看当前启动的容器             |
| `docker images`                 | 查看当前`docker`中所有镜像列表 |
| `docker image stop [id]`        | 删除指定`id`的镜像             |
| `docker container ls`           | 查看当前`docker`中所有容器     |
| `docker container stop [id]`    | 停止正在运行的指定`id`容器     |
| `docker container restart [id]` | 重启指定`id`容器               |
| `docker container start [id]`   | 运行指定`id`容器               |
|                                 |                                |



## Ubuntu Shell

**防火墙`ufw`命令：**

```shell
tycoding@tycoding:~$ ufw -h
ERROR: Invalid syntax

Usage: ufw COMMAND

Commands:
 enable                          enables the firewall
 disable                         disables the firewall
 default ARG                     set default policy
 logging LEVEL                   set logging to LEVEL
 allow ARGS                      add allow rule
 deny ARGS                       add deny rule
 reject ARGS                     add reject rule
 limit ARGS                      add limit rule
 delete RULE|NUM                 delete RULE
 insert NUM RULE                 insert RULE at NUM
 route RULE                      add route RULE
 route delete RULE|NUM           delete route RULE
 route insert NUM RULE           insert route RULE at NUM
 reload                          reload firewall
 reset                           reset firewall
 status                          show firewall status
 status numbered                 show firewall status as numbered list of RULES
 status verbose                  show verbose firewall status
 show ARG                        show firewall report
 version                         display version information

Application profile commands:
 app list                        list application profiles
 app info PROFILE                show information on PROFILE
 app update PROFILE              update PROFILE
 app default ARG                 set default application policy
```



## Tips

在Ubuntu虚拟机上操作`docker`中可能会出现一些问题：

>  **在`docker`上启动了容器，但在本地连接不上指定的ip端口**

栗子：本人在`docker`上部署的`elastcisearch`启动后，在本地浏览器上死活无法无法连接`9200`端口。

可以尝试关闭防火墙；或重启虚拟机（之前是直接将虚拟机挂起的）



## Docs

- [https://www.funtl.com/zh/docs-docker/](https://www.funtl.com/zh/docs-docker/) docker教程
- [https://hub.docker.com/](https://hub.docker.com/) docekr仓库
- [https://docs.docker.com/compose/install/](https://docs.docker.com/compose/install/) docker compose安装
- [https://www.cnblogs.com/milton/p/10138998.html](https://www.cnblogs.com/milton/p/10138998.html) 防火墙管理
# InstanceService

实例管理

## 接口列表

### /instance/deploy
目前支持部署Deployment（测试中）

后续建议部署过程与Jenkins集成，使用KubeSphere的API感觉不是特别方便，使用Jenkins直接接入github获取代码和部署配置文件。
### /instance/delete
目前支持删除Deployment（测试中）
### /instance/resourceHistory
查询特定POD的资源使用日志，包括CPU，MEM，NET（已完成）
### /instance/status
查看某一namespace下的容器状态信息（已完成）


## application.yaml配置说明
KubeSphereConfig:

url: KubeSphere地址

username: KubeSphere账号

password: KubeSphere账号的密码

这是属性配置可以在部署配置中通过环境变量修改

## 关于deployment.yaml
其中例如namespace等设置为变量，可以在CI/CD的过程中动态修改。

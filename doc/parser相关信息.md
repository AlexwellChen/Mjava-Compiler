## Mjava parser相关信息

本次实验使用递归下降算法完成，在token序列调用上使用了实验一的lexier获取token而没有采用从文件中读取。

代码结构主要分为两个部分：

* 工具函数
  * syntaxError
  * match
  * pushBackToken
  * getNextToken
  * matchType
* 递归下降函数


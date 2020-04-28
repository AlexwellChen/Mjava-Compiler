在DFA的图中省略了一些比较细节的部分在此予以说明

1. 对于&&的判断：

   读入第一个&后如果接下来没有读到&就报错，在if当中嵌入了一个if用于后续判断。

2. 对于 . 的判断：

   处于状态2（字符）：

   ​	如果点后面跟随的是空白符或字母：

   		* str.length 

   ​	那么将空白或字母回退到输入流中，输出当前token为POINT

   对于System.out.println的处理方法：

   ​	读入点时，判断buffer中的字符串是否与"System."或”System.out.“相等，若相等则继续读入。结束后判断是否属于关键字System.out.println，如果不是System.out.println则以一般标识符输出。

   处于状态1（数字）：

   * 222.33	（浮点数，不合法）
   * 222.+      （符号，不合法）在这里会输出222.为illegal_token，+被识别为PLUS，illegal_token错误将会在语法分析阶段被处理。

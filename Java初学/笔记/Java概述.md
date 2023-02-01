# Java概述

## 什么是程序

程序是为了模拟现实世界，解决现实问题而使用计算机语言编写的一系列有序的指令集合
![1](https://i.loli.net/2020/08/10/bC8UxXGiLwJlktc.jpg)

## Java的简介与特点

Java：由Sun Microsystems公司于1995年5月推出的Java程序设计语言和Java平台的总称。

Java语言是一种可以撰写跨平台应用软件的面向对象的程序设计语言，由当时任职太阳微系统的詹姆斯·高斯林（James Gosling）等人于1990年代初开发，它最初被命名为Oak。

语言特点

面向对象（贴近人类思维模式，模拟现实世界，解决现实问题）
简单性（自动内存管理机制、不易造成内存溢出）
跨平台（操作系统、服务器、数据库）

## 计算机的执行机制

编译执行：
	将源文件编译成平台相关的机器码文件，一次编译，多次执行。
	执行效率高，不可跨平台。
![2](https://i.loli.net/2020/08/10/kxwUGdljPneQBHR.jpg)

解释执行：
	将源文件交给不同的平台独有的解释器
	执行效率低，可以跨平台
![3](https://i.loli.net/2020/08/10/TR2KLiv1wMBFXuE.png)


## Java的运行机制

先编译，再解释
	将源文件编译成字节码文件（平台中立文件.class）,再将字节码文件进行解释执行。
	java的设计理念：Write Once Run Anywhere
![4](https://i.loli.net/2020/08/10/vfyqxAzOS2th3QH.png)

## Java的名词解释

JVM（Java Virtual Machine）虚拟机：
使用软件在不同系统中，模拟相同的环境

JRE（Java Runtime Environment）运行环境：
包含JVM和解释器，完整的Java运行环境

JDK（Java Development Kit）开发环境：
包含JRE+类库+开发工具包（编译器+调试工具）

## 常用DOS命令操作

Windos+R  快捷呼出运行窗口
输入cmd打DOS命令窗口

更换盘符										d:
查看当前目录下的文件及文件夹	dir
进入文件夹									cd 文件夹名
返回上一级目录							 cd..
清空屏幕										cls
删除文件										del 文件名
删除文件夹									ed 文件夹名
退出											   exit

[命令提示符 - 搜狗百科](https://baike.sogou.com/v582162.htm?fromTitle=命令提示符)

## 第一个应用程序

```java
public class hello
{
	public static void main(String[] args)
	{
		System.out.println("Hello word!你好啊！");
	}
}
```

## 类的阐述

同一个源文件中可以定义多个类
编译后，每个类都会生成独立的.class文件
一个类中，只能有一个主方法，每个类都可以有自己的主方法
public修饰的类称为公开类，要求类名必须与文件名称完全相同，包括大小写
一个源文件中，只能有一个公开类

## Package（包）

作用：类似于文件夹，用于管理字节码（.class）文件
语法：package 包名;
位置：必须写在源文件的第一行

## 代码注释

单行注释
`// 单行注释`

多行注释
`/* 多行注释 */`

文档注释（生成外部文档：javadoc）

文档注释提供将程序信息嵌入程序的功能。
开发者可以使用javadoc工具将信息取出，然后转换为HTML文件。

```java
/**
 * 
 * @author XXX
 * 2020年7月19日 下午5:47:26
 * TODO 说明:123456
 */
```

[javadoc-百度百科](https://baike.baidu.com/item/javadoc/4640765?fr=aladdin)
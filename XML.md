# 1.1 XML简介

XML 用来传输和存储数据。
HTML 用来显示数据。

**什么是 XML？**

- XML 指可扩展标记语言（EXtensible Markup Language）。
- XML 是一种很像HTML的标记语言。
- XML 的设计宗旨是传输数据，而不是显示数据。
- XML 标签没有被预定义。您需要自行定义标签。
- XML 被设计为具有自我描述性。
- XML 是 W3C 的推荐标准。

**XML 和 HTML 之间的差异**

XML 不是 HTML 的替代。

XML 和 HTML 为不同的目的而设计：

- XML 被设计用来传输和存储数据，其焦点是数据的内容。
- HTML 被设计用来显示数据，其焦点是数据的外观。

HTML 旨在显示信息，而 XML 旨在传输信息。

# 1.2 XML树结构

**一个 XML 文档实例**
XML 文档使用简单的具有自我描述性的语法：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<note>
    <to>Tove</to>
    <from>Jani</from>
    <heading>Reminder</heading>
    <body>Don't forget me this weekend!</body>
</note>
```
第一行是 XML 声明。它定义 XML 的版本（1.0）和所使用的编码（UTF-8 : 万国码, 可显示各种语言）。
下一行描述文档的**根元素**（像在说："本文档是一个便签"）：
`<note>`
接下来 4 行描述根的 4 个**子元素**（to, from, heading 以及 body）：
```xml
<to>Tove</to>
<from>Jani</from>
<heading>Reminder</heading>
<body>Don't forget me this weekend!</body>
```
最后一行定义根元素的结尾：
`</note>`

**XML 文档形成一种树结构**

XML 文档必须包含**根元素**。该元素是所有其他元素的父元素。

XML 文档中的元素形成了一棵文档树。这棵树从根部开始，所有的元素都可以有子元素。

父、子以及同胞等术语用于描述元素之间的关系。

父元素拥有子元素。相同层级上的子元素成为同胞（兄弟或姐妹）。

所有的元素都可以有文本内容和属性（类似 HTML 中）。

# 1.3 XML语法

- XML文档必须有一个根元素
- XML元素都必须有一个关闭标签
- XML标签对大小写敏感
- XML元素必须被正确的嵌套
- XML属性值必须加引号
- XML中空格会保留
- XML 中的注释`<!-- 这是一条注释 -->`
- XML 声明文件的可选部分，如果存在需要放在文档的第一行`<?xml version="1.0" encoding="utf-8"?>`
- XML的实体引用

|  代码  |  符号  |  英文  |
| ---- | ---- | ---- |
|&lt;	|<	|less than|
|&gt;	|>	|greater than|
|&amp;	|&	|ampersand|
|&apos;	|'	|apostrophe|
|&quot;	|"	|quotation mark|

# 1.4 XML元素

**什么是 XML 元素？**

XML 元素指的是从（且包括）开始标签直到（且包括）结束标签的部分。

一个元素可以包含：

- 其他元素
- 文本
- 属性
- 或混合以上所有...

**XML 命名规则**

- 名称可以包含字母、数字以及其他的字符
- 名称不能以数字或者标点符号开始
- 名称不能以字母 xml（或者 XML、Xml 等等）开始
- 名称不能包含空格

可使用任何名称，没有保留的字词。

**XML 元素是可扩展的**

# 1.5 XML 属性

**XML 属性必须加引号**

属性值必须被引号包围，不过单引号和双引号均可使用。
比如一个人的性别，person 元素可以这样写：
`<person sex="female">`
或者这样也可以：
`<person sex='female'>`
如果属性值本身包含双引号，您可以使用单引号，就像这个实例：
`<gangster name='George "Shotgun" Ziegler'>`
或者您可以使用字符实体：
`<gangster name="George &quot;Shotgun&quot; Ziegler">`

# 1.6 XML验证

拥有正确语法的XML被称为“形式良好”的XML。
通过DTD验证的XML是“合法”的XML。

**验证XML文档**

既有正确的语法也能通过DTD验证是“合法”的XML。

DOCTYPE声明是对外部DTD文件的引用
```xml
<?xml version="1.0" encoding="ISO-8859-1"?>
<!DOCTYPE note SYSTEM "Note.dtd">
<note>
<to>Tove</to>
<from>Jani</from>
<heading>Reminder</heading>
<body>Don't forget me this weekend!</body>
</note>
```

**XML DTD**

DTD的目的是定义XML文档的结构。它使用多个合法的元素来定义文档结构：
```dtd
<!DOCTYPE note
[
<!ELEMENT note (to,from,heading,body)>
<!ELEMENT to (#PCDATA)>
<!ELEMENT from (#PCDATA)>
<!ELEMENT heading (#PCDATA)>
<!ELEMENT body (#PCDATA)>
]>
```
# 第一章 Android项目结构

在使用Android Studio创建第一个项目时，默认创建一个名称为app的Module（一个Module就是一个Android应用）

#### manifests节点
manifests节点用于显示Android应用程序的配置文件。

通常情况下，每个Android应用程序必须包含一个AndroidManifest.xml文件，位于manifests节点下，它是整个Android应用的全局描述文件。在该文件内，需要标明应用的名称、使用图标、Activity和Service等信息，否则程序不能正常启动。

**注意：在Android程序中，每一个Activity都需要在AndroidManifest.xml文件中有一个对应的`<activity>`标记。**

例：

```xml
<?xml version="1.0" encoding="utf-8"?>
<!--manifest，根节点，描述了package中所有内容-->
<!--xmlns:android，属性：命名空间的声明，表示Android中的各种标准属性能在该XML文件中使用-->
<!--package，属性：应用程序包的声明-->
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.application01">
    <!--application，包含package中application级别组件声明的根节点-->
    <!--android:icon，属性：应用程序图标-->
    <!--android:label，属性：应用程序标签（应用程序指定的名称）-->
    <!--android:theme，属性：应用程序主题-->
    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <!--activity，与用户交互的主要工具，用户打开的初始页面-->
        <activity android:name=".MainActivity">
            <!--intent-filter，配置Intent过滤器-->
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

#### java节点

java节点用于显示包含了Android程序所有包及源文件（.java）

例：

```java
package com.example.application01;//指定包
//导入
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
/**
 * Android Studio创建的MainActivity类默认继承AppCompatActivity类
 * 并在该类中重写了onCreate方法
 * 在onCreate方法中通过setContentView(R.layout.activity_main);设置当前Activity要显示的布局文件
 * */
public class MainActivity extends AppCompatActivity {
    //该方法在创建Activity时被回调，用于对该Activity执行初始化
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * R.layout.activity_main获取layout目录中的activity_main.xml布局文件
         * 在Android程序中，每个资源都会在R.java文件中生成一个索引，通过索引调用程序中的资源文件
         * R.java文件是只读文件，开发人员不能对其进行修改
         * */
        setContentView(R.layout.activity_main);
    }
}
```

#### res节点

res节点用于显示保存在res目录下的资源文件，当res目录中的文件发生变化时，R.java文件会自动修改

**注意：9-Patch图片（背景图片，可自适应屏幕）和Shape资源文件只能放在drawable子目录中；需要适应屏幕分辨率的图片推荐放在mipmap子目录，可以提高性能，占用内存更少**

res目录中的子包：

- drawable子目录

  存储图片资源

- layout子目录

  存储布局文件

- mipmap子目录

  存储项目中应用的启动图标（5种分辨率的启动图标文件）

- values子目录

  存储应用中使用的颜色colors、尺寸dimens、字符串strings、样式styles资源文件

#### Gradle Scripts

保存Gradle构建和属性文件

# 第二章 用户界面设计基础

## 2.1 UI设计相关概念

### 2.1.1 View

View，视图，View类是所有的UI组件，View类位于android.view包中，View类的子类一般位于android.widget包中

在Android中，View类及其子类的相关属性，既可以在XML布局文件中设置，也可以通过成员方法在Java代码中设置。

### 2.1.2 ViewGroup

ViewGroup，容器，ViewGroup类继承自View类，是View类的扩展，是一个抽象类，是容纳其他组件的容器。

ViewGroup类控制其子组件分布时，依赖于两个内部类。

- ViewGroup.LayoutParams类

  封装了布局的位置、宽、高等信息。

  支持android:layout_height和android:layout_width两个XML属性，
  属性值可以使用精确的数值，也可以使用FILL_PARENT（与父容器相同）、MATCH_PARENT（与父容器相同，需API8以上版本）、WRAP_CONTENT（包裹其自身的内容）

- ViewGroup.MarginLayoutParams类

  用于控制其子组件的外边距

  支持的常用属性：
  
  - android:layout_marginBottom：设置底外边距
  - androut:layout_marginTop：设置顶外边距
  - android:layout_marginStart：设置左外边距（Android4.2新增）
  - android:layout_marginEnd：设置右外边距（Android4.2新增）
  - android:layout_marginLeft：设置左外边距
  - android:layout_marginRight：设置右外边距

## 2.2 控制UI界面

### 2.2.1 使用XML布局文件控制UI界面（推荐）

1. 在Android应用的res/layout目录下编写XML布局文件

   ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <!--xmlns:android，属性：命名空间的声明，表示Android中的各种标准属性能在该XML文件中使用-->
   <!--xmlns:tools，属性，指定布局的默认工具-->
   <!--android:layout_width="match_parent"，属性：布局的宽-->
   <!--android:layout_height="match_parent"，属性：布局的高-->
   <!--    "match_parent"——与父容器相同-->
   <!--    "wrap_content"——自适应内容-->
   <!--android:background，属性：背景图片-->
   <!--FrameLayout，帧布局-->
   <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
       xmlns:tools="http://schemas.android.com/tools"
       android:layout_width="match_parent"
       android:layout_height="match_parent"
       android:background="@mipmap/background01"
       tools:context=".MainActivity">
       <!--TextView，元素：文本框组件，用来显示文本-->
       <!--android:text，属性：文本框组件显示的文本-->
       <TextView
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:layout_gravity="center"
           android:textColor="#000000"
           android:textSize="30sp"
           android:text="@string/text" /></FrameLayout>
   ```

2. 在Activity中使用以下Java代码显示XML文件中布局的内容

   ```java
   /**
   * R.layout.activity_main获取layout目录中的activity_main.xml布局文件
   * 在Android程序中，每个资源都会在R.java文件中生成一个索引，通过索引调用程序中的资源文件
   * R.java文件是只读文件，开发人员不能对其进行修改
   * */
   setContentView(R.layout.activity_main);
   ```

### 2.2.2 在Java代码中控制UI界面

1. 创建布局管理器，设置布局管理器的属性

2. 创建具体的组件，设置组件的布局和属性

3. 将创建的组件添加到布局管理器中

   例：

   ```java
   package com.example.application01;//指定包
   //导入
   
   import androidx.appcompat.app.AlertDialog;
   import androidx.appcompat.app.AppCompatActivity;
   
   import android.content.DialogInterface;
   import android.graphics.Color;
   import android.os.Bundle;
   import android.util.Log;
   import android.util.TypedValue;
   import android.view.Gravity;
   import android.view.View;
   import android.view.ViewGroup;
   import android.widget.FrameLayout;
   import android.widget.TextView;
   
   /**
    * Android Studio创建的MainActivity类默认继承AppCompatActivity类
    * 并在该类中重写了onCreate方法
    * 在onCreate方法中通过setContentView(R.layout.activity_main);设置当前Activity要显示的布局文件
    */
   public class MainActivity extends AppCompatActivity {
       //该方法在创建Activity时被回调，用于对该Activity执行初始化
       @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           /**
            * R.layout.activity_main获取layout目录中的activity_main.xml布局文件
            * 在Android程序中，每个资源都会在R.java文件中生成一个索引，通过索引调用程序中的资源文件
            * R.java文件是只读文件，开发人员不能对其进行修改
            * */
   //        setContentView(R.layout.activity_main);//使用XML布局文件
           //创建布局管理器，设置布局管理器的属性
           FrameLayout frameLayout = new FrameLayout(this);//创建帧布局管理器
           frameLayout.setBackgroundResource(R.mipmap.background01);//设置背景
           setContentView(frameLayout);    //设置在Activity中显示frameLayout
           //创建具体的组件，设置组件的布局和属性
           TextView text = new TextView(this);//实例化
           text.setText("开始");//设置文本
           text.setTextColor(Color.rgb(0, 0, 0));//设置字体颜色
           text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);//设置字体大小
           //保存布局参数的对象
           FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
           params.gravity = Gravity.CENTER;//设置居中显示
           text.setLayoutParams(params);//设置布局参数
           //实现单击文本框时，弹出询问对话框
           text.setOnClickListener(new View.OnClickListener() {//添加单击事件监听器
               @Override
               public void onClick(View v) {
                   //设置对话框
                   new AlertDialog.Builder(MainActivity.this)
                           //设置标题
                           .setTitle("对话框提示")
                           //设置对话框显示内容
                           .setMessage("欢迎你的到来，请问是否进入？")
                           //是，按钮，添加单击事件
                           .setPositiveButton("是", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   Log.i("标题1", "进入");//输出消息日志
                               }
                           })
                           //否，按钮，添加单击事件
                           .setNegativeButton("否", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   Log.i("标题2", "退出");//输出消息日志
                                   finish();//结束
                               }
                           })
                           //显示对话框
                           .show();
               }
           });
           //将文本框组件添加到布局管理器
           frameLayout.addView(text);
       }
   }
   ```

### 2.2.3 使用XML和Java代码混合控制UI界面

例子：QQ相册

1. 修改res\layout下的布局文件activity_main.xml文件

   ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <!--xmlns:android，属性：命名空间的声明，表示Android中的各种标准属性能在该XML文件中使用-->
   <!--xmlns:tools，属性，指定布局的默认工具-->
   <!--android:layout_width="match_parent"，属性：布局的宽-->
   <!--android:layout_height="match_parent"，属性：布局的高-->
   <!--    "match_parent"——与父容器相同-->
   <!--    "wrap_content"——自适应内容-->
   <!--android:background，属性：背景图片-->
   <!--GridLayout，网格布局-->
   <!--android:orientation，属性：排列方式——horizontal水平、vertical垂直-->
   <!--android:rowCount，属性：行-->
   <!--android:columnCount，属性：列-->
   <GridLayout xmlns:android="http://schemas.android.com/apk/res/android"
       xmlns:tools="http://schemas.android.com/tools"
       android:id="@+id/layout"
       android:layout_width="match_parent"
       android:layout_height="match_parent"
       android:columnCount="4"
       android:orientation="horizontal"
       android:rowCount="3"
       tools:context=".MainActivity"></GridLayout>
   ```

2. 在MainActivity中，声明img和imagePath两个成员变量

   img是一个ImageView类型的一维数组，用于保存ImageView组件

   imagePath是一个int类型的一维数组，用于保存要访问的图片资源

   ```java
       //img是一个ImageView类型的一维数组，用于保存ImageView组件
       //imagePath是一个int类型的一维数组，用于保存要访问的图片资源
       private ImageView[] img = new ImageView[12];
       private int[] imagePath = new int[]{
               R.mipmap.bj01, R.mipmap.bj02, R.mipmap.bj03, R.mipmap.bj04,
               R.mipmap.bj05, R.mipmap.bj06, R.mipmap.bj07, R.mipmap.bj08,
               R.mipmap.bj09, R.mipmap.bj10, R.mipmap.bj11, R.mipmap.bj12,
       };
   ```

3. 在MainActivity的onCreate()方法中，先获取XML布局文件中的网格布局管理器，然后用for循环创建显示图片的ImageView组件，并将其添加到布局管理器

   ```java
   package com.example.application01;//指定包
   //导入
   
   import androidx.appcompat.app.AppCompatActivity;
   
   import android.os.Bundle;
   import android.view.ViewGroup;
   import android.widget.GridLayout;
   import android.widget.ImageView;
   
   
   /**
    * Android Studio创建的MainActivity类默认继承AppCompatActivity类
    * 并在该类中重写了onCreate方法
    * 在onCreate方法中通过setContentView(R.layout.activity_main);设置当前Activity要显示的布局文件
    */
   public class MainActivity extends AppCompatActivity {
       //img是一个ImageView类型的一维数组，用于保存ImageView组件
       //imagePath是一个int类型的一维数组，用于保存要访问的图片资源
       private ImageView[] img = new ImageView[12];
       private int[] imagePath = new int[]{
               R.mipmap.bj01, R.mipmap.bj02, R.mipmap.bj03, R.mipmap.bj04,
               R.mipmap.bj05, R.mipmap.bj06, R.mipmap.bj07, R.mipmap.bj08,
               R.mipmap.bj09, R.mipmap.bj10, R.mipmap.bj11, R.mipmap.bj12,
       };
   
       //该方法在创建Activity时被回调，用于对该Activity执行初始化
       @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           /**
            * R.layout.activity_main获取layout目录中的activity_main.xml布局文件
            * 在Android程序中，每个资源都会在R.java文件中生成一个索引，通过索引调用程序中的资源文件
            * R.java文件是只读文件，开发人员不能对其进行修改
            * */
           setContentView(R.layout.activity_main);//使用XML布局文件
           //获取XML文件中定义的网格布局管理器
           GridLayout layout = (GridLayout) findViewById(R.id.layout);
           for (int i = 0; i < imagePath.length; i++) {
               img[i] = new ImageView(MainActivity.this);
               img[i].setImageResource(imagePath[i]);
               img[i].setPadding(10, 10, 10, 10);
               //设置图片宽高
               ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(200, 100);
               //设置布局参数
               img[i].setLayoutParams(params);
               //添加到布局管理器
               layout.addView(img[i]);
           }
       }
   }
   ```

### 2.2.4 开发自定义的View

例子：跟随手指移动的图片

1. 修改res\layout下的布局文件activity_main.xml文件

   ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <!--xmlns:android，属性：命名空间的声明，表示Android中的各种标准属性能在该XML文件中使用-->
   <!--xmlns:tools，属性，指定布局的默认工具-->
   <!--android:layout_width="match_parent"，属性：布局的宽-->
   <!--android:layout_height="match_parent"，属性：布局的高-->
   <!--    "match_parent"——与父容器相同-->
   <!--    "wrap_content"——自适应内容-->
   <!--android:background，属性：背景图片-->
   <!--FrameLayout，帧布局-->
   <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
       xmlns:tools="http://schemas.android.com/tools"
       android:id="@+id/mylayout"
       android:layout_width="match_parent"
       android:layout_height="match_parent"
       android:background="@mipmap/background01"
       tools:context=".MainActivity"></FrameLayout>
   ```

2. 在com.example.xxxx包中新建一个MyView的Java类，该类继承自android.view.View类，重写带一个参数Context的构造方法和onDraw()方法

   ```java
   package com.example.application01;
   
   import android.content.Context;
   import android.graphics.Bitmap;
   import android.graphics.BitmapFactory;
   import android.graphics.Canvas;
   import android.graphics.Paint;
   import android.view.View;
   
   public class MyView extends View {
       //显示位置的XY坐标
       public float bitmapX, bitmapY;
   
       public MyView(Context context) {
           super(context);
           //默认位置
           bitmapX = 300;
           bitmapY = 100;
       }
   
       @Override
       protected void onDraw(Canvas canvas) {
           super.onDraw(canvas);
           //创建并实例化Paint对象（画笔）
           Paint paint = new Paint();
           //根据图片生成位图对象
           Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.stars);
           //绘制图片
           canvas.drawBitmap(bitmap, bitmapX, bitmapY, paint);
           //判断图片是否回收
           if (bitmap.isRecycled()) {
               bitmap.recycle();//强制回收
           }
       }
   }
   ```

3. 在主活动的onCreate()方法中，先获取帧布局管理器，并实例化对象stars，然后用for循环创建显示图片的ImageView组件，并将其添加到布局管理器

   ```java
   package com.example.application01;//指定包
   //导入
   
   import androidx.appcompat.app.AppCompatActivity;
   
   import android.os.Bundle;
   import android.view.MotionEvent;
   import android.view.View;
   import android.widget.FrameLayout;
   
   
   /**
    * Android Studio创建的MainActivity类默认继承AppCompatActivity类
    * 并在该类中重写了onCreate方法
    * 在onCreate方法中通过setContentView(R.layout.activity_main);设置当前Activity要显示的布局文件
    */
   public class MainActivity extends AppCompatActivity {
       //该方法在创建Activity时被回调，用于对该Activity执行初始化
       @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           /**
            * R.layout.activity_main获取layout目录中的activity_main.xml布局文件
            * 在Android程序中，每个资源都会在R.java文件中生成一个索引，通过索引调用程序中的资源文件
            * R.java文件是只读文件，开发人员不能对其进行修改
            * */
           setContentView(R.layout.activity_main);//使用XML布局文件
           //获取XML文件中定义的帧布局管理器
           FrameLayout layout = findViewById(R.id.mylayout);
           //创建并实例化
           final MyView stars = new MyView(this);
           //添加触摸事件监听
           stars.setOnTouchListener(new View.OnTouchListener() {
               @Override
               public boolean onTouch(View v, MotionEvent event) {
                   //设置显示的XY坐标
                   stars.bitmapX = event.getX();
                   stars.bitmapY = event.getY();
                   //重绘组件
                   stars.invalidate();
                   return true;
               }
           });
           //添加到布局管理器
           layout.addView(stars);
       }
   }
   ```

## 2.3 布局管理器

### 2.3.1 相对布局管理器RelativeLayout

相对布局管理器，通过相对定位的方式让组件出现在布局的任何位置。

```xml
<?xml version="1.0" encoding="utf-8"?>
<!--xmlns:android，属性：命名空间的声明，表示Android中的各种标准属性能在该XML文件中使用-->
<!--xmlns:tools，属性，指定布局的默认工具-->
<!--android:layout_width="match_parent"，属性：布局的宽-->
<!--android:layout_height="match_parent"，属性：布局的高-->
<!--    "match_parent"——与父容器相同-->
<!--    "wrap_content"——自适应内容-->
<!--android:background，属性：背景图片-->
<!--RelativeLayout，相对布局-->
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@mipmap/background01"
    tools:context=".MainActivity">
    <!--    居中显示的文本视图-->
    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:text="@string/text"
        android:textColor="#000000"
        android:textSize="18sp"
        android:textStyle="bold" />
    <!--    按钮button1：textView的下方，与textView左边界对齐-->
    <Button
        android:id="@+id/button1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@id/textView"
        android:layout_alignLeft="@id/textView"
        android:text="@string/button1" />
    <!--    按钮button2：textView的下方，button1的右侧-->
    <Button
        android:id="@+id/button2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@id/textView"
        android:layout_toRightOf="@id/button1"
        android:text="@string/button2" />
</RelativeLayout>
```

### 2.3.2 线性布局管理器LinearLayout

线性布局管理器，将放入其中的组件按照垂直或水平方向布局，即控制放入其中的组件纵向排列或横向排列。
（线性布局管理器中的组件不会换行）

纵向排列——垂直线性布局管理器，每一行只能放一个组件

横向排列——水平线性布局管理器，每一列只能放一个组件

```xml
<?xml version="1.0" encoding="utf-8"?><!--xmlns:android，属性：命名空间的声明，表示Android中的各种标准属性能在该XML文件中使用-->
<!--xmlns:tools，属性，指定布局的默认工具-->
<!--android:layout_width="match_parent"，属性：布局的宽-->
<!--android:layout_height="match_parent"，属性：布局的高-->
<!--    "match_parent"——与父容器相同-->
<!--    "wrap_content"——自适应内容-->
<!--android:background，属性：背景图片-->
<!--LinearLayout，线性布局-->
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@mipmap/background01"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/textView1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="20px" />

    <EditText
        android:id="@+id/editNumber"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#FFFFFF"
        android:drawableLeft="@mipmap/number"
        android:ems="10"
        android:hint="@string/Number"
        android:inputType="number"
        android:padding="10dp" />

    <EditText
        android:id="@+id/editPassword"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#FFFFFF"
        android:drawableLeft="@mipmap/password"
        android:ems="10"
        android:hint="@string/Password"
        android:inputType="textPassword"
        android:padding="10dp" />

    <TextView
        android:id="@+id/textView2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="1px" />

    <Button
        android:id="@+id/button1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#FFFFFF"
        android:text="登录" />

    <Button
        android:id="@+id/button2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#FFFFFF"
        android:text="注册" />


</LinearLayout>
```

### 2.3.3 帧布局管理器FrameLayout

在帧布局管理器中，每添加一个组件，都将创建一个空白区域，通常称为一帧，这些帧都会放置在屏幕的左上角，即帧布局是从屏幕的左上角（0，0）开始的。

多个组件层叠排序，后面的组件覆盖前面的组件。

```xml
<?xml version="1.0" encoding="utf-8"?><!--xmlns:android，属性：命名空间的声明，表示Android中的各种标准属性能在该XML文件中使用-->
<!--xmlns:tools，属性，指定布局的默认工具-->
<!--android:layout_width="match_parent"，属性：布局的宽-->
<!--android:layout_height="match_parent"，属性：布局的高-->
<!--    "match_parent"——与父容器相同-->
<!--    "wrap_content"——自适应内容-->
<!--android:background，属性：背景图片-->
<!--FrameLayout，帧布局-->
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@mipmap/background01"
    android:foreground="@mipmap/stars"
    android:foregroundGravity="center"
    tools:context=".MainActivity">

    <TextView
        android:layout_width="250dp"
        android:layout_height="250dp"
        android:layout_centerInParent="true"
        android:layout_gravity="center"
        android:background="#503592C4"
        android:text="1" />

    <TextView
        android:layout_width="200dp"
        android:layout_height="200dp"
        android:layout_centerInParent="true"
        android:layout_gravity="center"
        android:background="#803592C4"
        android:text="2" />

    <TextView
        android:layout_width="150dp"
        android:layout_height="150dp"
        android:layout_centerInParent="true"
        android:layout_gravity="center"
        android:background="#FF3592C4"
        android:text="3" />

</FrameLayout>
```

### 2.3.4 表格布局管理器TableLayout

表格布局管理器以行、列的形式来管理放入其中的UI组件。

在表格布局管理器中，可添加多个TableRow，每个TableRow占用一行。

TableRow也是容器，在其中可添加其他组件，每添加一个组件，表格就增加一列。

列可以被隐藏、拉伸、收缩。

```xml
<?xml version="1.0" encoding="utf-8"?><!--xmlns:android，属性：命名空间的声明，表示Android中的各种标准属性能在该XML文件中使用-->
<!--xmlns:tools，属性，指定布局的默认工具-->
<!--android:layout_width="match_parent"，属性：布局的宽-->
<!--android:layout_height="match_parent"，属性：布局的高-->
<!--    "match_parent"——与父容器相同-->
<!--    "wrap_content"——自适应内容-->
<!--android:background，属性：背景图片-->
<!--TableLayout，表格布局-->
<TableLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@mipmap/background01"
    android:stretchColumns="0,3"
    tools:context=".MainActivity">
    <!--第一行-->
    <TableRow
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:paddingTop="100dp">

        <TextView />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerInParent="true"
            android:layout_gravity="center"
            android:background="#FFFFFF"
            android:text="账  号："
            android:textSize="18sp" />

        <EditText
            android:id="@+id/editNumber"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="#FFFFFF"
            android:ems="10"
            android:hint="@string/Number"
            android:inputType="number" />

        <TextView />
    </TableRow>
    <!--第二行-->
    <TableRow
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:paddingTop="10dp">

        <TextView />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerInParent="true"
            android:layout_gravity="center"
            android:background="#FFFFFF"
            android:text="密  码："
            android:textSize="18sp" />

        <EditText
            android:id="@+id/editPassword"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="#FFFFFF"
            android:ems="10"
            android:hint="@string/Password"
            android:inputType="textPassword" />

        <TextView />
    </TableRow>
    <!--第三行-->
    <TableRow
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:paddingTop="10dp">

        <TextView />

        <Button
            android:id="@+id/button1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:background="#9999FF"
            android:text="注册" />

        <Button
            android:id="@+id/button2"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:background="#9999FF"
            android:text="登录" />

        <TextView />
    </TableRow>
</TableLayout>
```

### 2.3.5 网格布局管理器GridLayout

在网格布局管理器中，屏幕被虚拟的细线划分为行、列、单元格。

每个单元格放置一个组件，组件可以跨行也可以跨列

```xml
<?xml version="1.0" encoding="utf-8"?><!--xmlns:android，属性：命名空间的声明，表示Android中的各种标准属性能在该XML文件中使用-->
<!--xmlns:tools，属性，指定布局的默认工具-->
<!--android:layout_width="match_parent"，属性：布局的宽-->
<!--android:layout_height="match_parent"，属性：布局的高-->
<!--    "match_parent"——与父容器相同-->
<!--    "wrap_content"——自适应内容-->
<!--android:background，属性：背景图片-->
<!--GridLayout，网格布局-->
<GridLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@mipmap/background01"
    android:columnCount="6"
    tools:context=".MainActivity">

    <ImageView
        android:id="@+id/imageView1"
        android:layout_row="0"
        android:layout_column="5"
        app:srcCompat="@mipmap/nv" />

    <ImageView
        android:id="@+id/imageView2"
        android:layout_row="0"
        android:layout_column="1"
        android:layout_columnSpan="4"
        android:layout_gravity="end"
        android:layout_marginTop="10dp"
        android:layout_marginRight="5dp"
        android:layout_marginBottom="20dp"
        app:srcCompat="@mipmap/nv1" />

    <ImageView
        android:id="@+id/imageView3"
        android:layout_row="1"
        android:layout_column="0"
        app:srcCompat="@mipmap/nan" />

    <ImageView
        android:id="@+id/imageView4"
        android:layout_row="1"
        android:layout_column="1"
        android:layout_marginLeft="5dp"
        android:layout_marginTop="10dp"
        android:layout_marginBottom="20dp"
        app:srcCompat="@mipmap/nan1" />

    <ImageView
        android:id="@+id/imageView5"
        android:layout_row="2"
        android:layout_column="5"
        app:srcCompat="@mipmap/nv" />

    <ImageView
        android:id="@+id/imageView6"
        android:layout_row="2"
        android:layout_column="1"
        android:layout_columnSpan="4"
        android:layout_gravity="end"
        android:layout_marginTop="10dp"
        android:layout_marginRight="5dp"
        android:layout_marginBottom="20dp"
        app:srcCompat="@mipmap/nv2" />
</GridLayout>
```

### 2.3.6 约束布局ConstraintLayout


### 2.3.7 布局管理器的嵌套

- 根布局管理器必须包含xmlns属性

- 在一个布局文件中，最多只能有一个根布局管理器，如果需要有多个，还需要使用一个根布局管理器将它们括起来

- 不能嵌套太深，如果嵌套太深，则会影响性能，主要体现在降低页面的加载速度方面

  ```xml
  <?xml version="1.0" encoding="utf-8"?><!--xmlns:android，属性：命名空间的声明，表示Android中的各种标准属性能在该XML文件中使用-->
  <!--xmlns:tools，属性，指定布局的默认工具-->
  <!--android:layout_width="match_parent"，属性：布局的宽-->
  <!--android:layout_height="match_parent"，属性：布局的高-->
  <!--    "match_parent"——与父容器相同-->
  <!--    "wrap_content"——自适应内容-->
  <!--android:background，属性：背景图片-->
  <!--布局的嵌套-->
  <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto"
      xmlns:tools="http://schemas.android.com/tools"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      android:background="@mipmap/background01"
      android:orientation="vertical"
      tools:context=".MainActivity">
  
      <RelativeLayout
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          android:layout_margin="10dp"
          android:background="#FFF17E">
  
          <ImageView
              android:id="@+id/imageView"
              android:layout_width="wrap_content"
              android:layout_height="wrap_content"
              android:layout_alignParentLeft="true"
              app:srcCompat="@mipmap/nv" />
  
          <TextView
              android:id="@+id/textView1"
              android:layout_width="wrap_content"
              android:layout_height="wrap_content"
              android:layout_marginTop="10dp"
              android:layout_toRightOf="@id/imageView"
              android:text="雪花"
              android:textColor="#000000"
              android:textSize="20sp"
              android:textStyle="bold" />
  
          <TextView
              android:id="@+id/textView2"
              android:layout_width="wrap_content"
              android:layout_height="wrap_content"
              android:layout_below="@id/textView1"
              android:layout_marginTop="5dp"
              android:layout_marginBottom="5dp"
              android:layout_toRightOf="@id/imageView"
              android:minLines="5"
              android:text="根布局管理器必须包含xmlns属性
              在一个布局文件中，最多只能有一个根布局管理器，如果需要有多个，还需要使用一个根布局管理器将它们括起来
              不能嵌套太深，如果嵌套太深，则会影响性能，主要体现在降低页面的加载速度方面"
              android:textColor="#000000" />
  
          <TextView
              android:id="@+id/textView3"
              android:layout_width="wrap_content"
              android:layout_height="wrap_content"
              android:layout_below="@id/textView2"
              android:layout_marginTop="3dp"
              android:layout_toRightOf="@id/imageView"
              android:text="昨天"
              android:textColor="#000000" />
      </RelativeLayout>
  </LinearLayout>
  ```

# 第三章 基本UI组件

## 3.1 文本类组件

### 3.1.1 文本框

文本框，用于显示文本的组件

### 3.1.2 编辑框

编辑框，用于编辑文本的组件

## 3.2 按钮类组件

### 3.2.1 普通按钮

### 3.2.2 图片按钮

### 3.2.3 单选按钮

### 3.2.4 复选框

## 3.3 日期时间类组件

### 3.3.1 日期选择器

### 3.3.2 时间选择器

### 3.3.3 计时器

# 第四章 高级UI组件

## 4.1 进度条类组件

### 4.1.1 进度条

### 4.1.2 拖动条

### 4.1.3 星级评分条

## 4.2 图像类组件

### 4.2.1 图像视图

### 4.2.2 图像切换器

### 4.2.3 网格视图

## 4.3 列表类组件

### 4.3.1 下拉列表框

### 4.3.2 列表视图

## 4.4 通用组件

### 4.4.1 滚动视图

### 4.4.2 选项卡
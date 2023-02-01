#include<iostream>
#include<string>
#include<iomanip>
#include<fstream>
#include<windows.h>
#include<graphics.h>//图形库
#include<mmsystem.h>//音乐
#include<time.h>//strftime( c , 20 , " %H : %M " , &time );
#include<conio.h>//ch[i]=getch();
using namespace std;
#pragma comment (lib,"winmm.lib")

const int MAX=20;
const int INF=2323;
ifstream inFile;
ofstream outFile;

//图顶点存储的信息
class GraphVex
{
	friend class Graph;
	string name;//城市名称
};

//图边存储的信息
class GraphArc
{
	friend class Graph;
	//飞机
	int plane_num;//编号
	tm plane_start_time;//出发时间
	tm plane_reach_time;//抵达时间
	int plane_money;//花费
	int plane_time;//花费时间
	int plane_change;//中转数
	//列车
	int train_num;//编号
	tm train_start_time;//出发时间
	tm train_reach_time;//抵达时间
	int train_money;//花费
	int train_time;//花费时间
	int train_change;//中转数
};

class Graph
{
private:
	int vexnum;
	int p_arcnum;
	int t_arcnum;
	GraphVex vex[MAX];		//顶点
	GraphArc arc[MAX][MAX];	//矩阵
public:
	Graph();
	void Init();			//初始化（文件、键盘）
private:
	//界面
	void Main_interface();	//主界面
	void Show_traffic();	//全国交通
	void Counsel();			//咨询界面
	void Administrator();	//管理员界面
	void Administrator(char ch[10]);	//管理员进入界面
	void City_Face();	//城市信息
	void Plane_Face();	//飞机航班
	void Train_Face();	//列车时刻
	//功能1——城市——添加、删除、显示
	void City_addition();
	void City_remove();
	void City_show();
	//功能2——飞机航班——添加、删除、显示
	void Plane_addition();
	void Plane_remove();
	void Plane_show();
	//功能3——列车时刻——添加、删除、显示
	void Train_addition();
	void Train_remove();
	void Train_show();

	void Print_c();		//城市显示
	void Print_p();		//飞机显示
	void Print_t();		//城市显示
	void Save_c();		//保存城市
	void File();		//文件导入
	void Create_city();		//创建城市文件
	void Create_plane();	//创建飞机文件
	void Create_train();	//创建列车文件

	void C_money_plane();	//最少费用——飞机
	void C_money_train();	//最少费用——列车
	void C_time_plane();	//最少时间——飞机
	void C_time_train();	//最少时间——列车
	void C_change_plane();	//最少中转——飞机
	void C_change_train();	//最少中转——列车

	void Find_pass(int i,int j);//查找并记录结点
	int Find_cityname(string name);//查找城市名称（判断是否存在该城市）
	tm Time_cin();//输入时间
	int Time_diff(tm start,tm reach);//时间差
	string Time_conversion(tm time);//时间格式转换
	int PD_min();//判断分
	int PD_hour();//判断时
	string PD_cityname();//判断输入城市名称
	int PD_max(int max);//判断极限(0-max)
	int PD_int();//判断是否输入整型数字
	char PD_flag();//判断是否继续输入
};

int z=0;
int Path[MAX][MAX];
int Pathcount[MAX];
GraphArc temp[MAX][MAX];

//改变颜色
void SetColor(unsigned short ForeColor,unsigned short BackGroundColor)
{
	HANDLE hCon=GetStdHandle(STD_OUTPUT_HANDLE);
	SetConsoleTextAttribute(hCon,(ForeColor%17)|(BackGroundColor%16*16));
}
//线
void PrintLine()
{
	cout<<"------------------------------------------------------------------------------------------------------------------------\n";
}
//构造函数初始化
Graph::Graph()
{
	int vexnum = 0;
	int p_arcnum = 0;
	int t_arcnum = 0;
	//矩阵全部初始化为INF
	for(int i=0; i<MAX; i++)
		for(int j=0; j<MAX; j++)
			arc[i][j].plane_num=arc[i][j].train_num=
			arc[i][j].plane_start_time.tm_hour=arc[i][j].train_start_time.tm_hour=
			arc[i][j].plane_reach_time.tm_min=arc[i][j].train_reach_time.tm_min=
			arc[i][j].plane_money=arc[i][j].train_money=
			arc[i][j].plane_time=arc[i][j].train_time=
			arc[i][j].plane_change=arc[i][j].train_change=INF;
}
//初始化
void Graph::Init()
{
	system("cls");
	initgraph(980,500);//创建一个宽640,长480的窗口
	setbkcolor(WHITE);//设置当前绘图背景色
	cleardevice();//清屏

	settextcolor(BLACK);//设置当前文字颜色
	settextstyle(20, 0, "宋体");//设置当前字体样式
	setbkmode(TRANSPARENT);//设置背景混合模式
	outtextxy(410, 15, "请选择初始化方式：");
	outtextxy(410, 200, "注意：文件不可为空！");

	setfillcolor(LIGHTBLUE);//设置当前填充颜色
	solidrectangle(440, 55, 560, 90);	//画填充矩形(无边框)
	solidrectangle(440, 105, 560, 140);	//画填充矩形(无边框)
	solidrectangle(440, 155, 560, 190);	//画填充矩形(无边框)

	settextcolor(WHITE);//设置当前文字颜色
	settextstyle(20, 0, "楷体");//设置当前字体样式
	setbkmode(TRANSPARENT);//设置背景混合模式
	outtextxy(450, 60, "1 文件");
	outtextxy(450, 110, "2 键盘");
	outtextxy(450, 160, "返回主界面");

	MOUSEMSG m;
	while (1)//一直获取鼠标信息
	{
		m = GetMouseMsg();
		//1 文件
		if(m.x>=440 && m.x<=560 && m.y>=55 && m.y<=90)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-5,55-5,560+5,90+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
			{
				closegraph();//关闭图形环境
				File();//文件输入
				cout<<"文件输入完成，即将返回主界面"<<endl;
				system("pause");
				Main_interface();
			}
		}
		//2 键盘
		else if(m.x>=440 && m.x<=560 && m.y>=105 && m.y<=140)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-5,105-5,560+5,140+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
			{
				closegraph();//关闭图形环境
				Create_city();	//创建城市文件
				Create_plane();	//创建飞机文件
				Create_train();	//创建列车文件
				cout<<"键盘输入完成，即将返回主界面"<<endl;
				system("pause");
				Main_interface();
			}
		}
		//返回主界面
		else if(m.x>=440 && m.x<=560 && m.y>=155 && m.y<=190)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-5,155-5,560+5,190+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
				Main_interface();
		}
		//鼠标没有点在上面
		else
		{
			setlinecolor(WHITE);
			rectangle(440-5,55-5,560+5,90+5);
			rectangle(440-5,105-5,560+5,140+5);
			rectangle(440-5,155-5,560+5,190+5);
			rectangle(435-5,205-5,565+5,240+5);
		}
	}
	closegraph();//关闭图形环境
}
//主界面
void Graph::Main_interface()
{
	initgraph(980,500);//创建一个宽640,长480的窗口
	setbkcolor(WHITE);//设置当前绘图背景色
	cleardevice();//清屏

	setlinecolor(RGB(225,128,0));	// 设置当前线条颜色
	setfillcolor(RGB(255,255,128));	// 设置当前填充颜色
	fillrectangle(400, 10, 600, 40);// 画填充矩形(有边框)
	settextcolor(BLACK);//设置当前文字颜色
	settextstyle(20, 0, "宋体");//设置当前字体样式
	setbkmode(TRANSPARENT);//设置背景混合模式
	outtextxy(475, 15, "主界面");
	settextstyle(15, 0, "宋体");//设置当前字体样式
	outtextxy(600, 450, "本系统由谢晓艳制作完成");


	setfillcolor(LIGHTBLUE);//设置当前填充颜色
	solidrectangle(440, 55, 560, 90);	//画填充矩形(无边框)
	solidrectangle(440, 105, 560, 140);	//画填充矩形(无边框)
	solidrectangle(440, 155, 560, 190);	//画填充矩形(无边框)
	solidrectangle(440, 205, 560, 240);	//画填充矩形(无边框)

	settextcolor(WHITE);//设置当前文字颜色
	settextstyle(20, 0, "楷体");//设置当前字体样式
	setbkmode(TRANSPARENT);//设置背景混合模式
	outtextxy(450, 60, "1 显示交通");
	outtextxy(450, 110, "2 交通咨询");
	outtextxy(450, 160, "3 信息管理");
	outtextxy(450, 210, "0 退出系统");

	MOUSEMSG m;
	while (1)//一直获取鼠标信息
	{
		m = GetMouseMsg();
		//1 显示交通
		if(m.x>=440 && m.x<=560 && m.y>=55 && m.y<=90)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-5,55-5,560+5,90+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
			{
				closegraph();//关闭图形环境
				Show_traffic();//显示交通
			}
		}
		//2 交通咨询
		else if(m.x>=440 && m.x<=560 && m.y>=105 && m.y<=140)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-5,105-5,560+5,140+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
			{
				closegraph();//关闭图形环境
				Counsel();//交通咨询
			}
		}
		//3 信息管理
		else if(m.x>=440 && m.x<=560 && m.y>=155 && m.y<=190)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-5,155-5,560+5,190+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
			{
				closegraph();//关闭图形环境
				char ch[10];
				Administrator(ch);//信息管理
			}
		}
		//0 退出系统
		else if(m.x>=440 && m.x<=560 && m.y>=205 && m.y<=240)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-5,205-5,560+5,240+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
			{
				cleardevice();//清屏
				settextcolor(BLACK);//设置当前文字颜色
				outtextxy(340, 250, "~~~     感谢使用本系统!     ~~~");
				Sleep(1000);
				exit(0);
			}
		}
		//鼠标没有点在上面
		else
		{
			setlinecolor(WHITE);
			rectangle(440-5,55-5,560+5,90+5);
			rectangle(440-5,105-5,560+5,140+5);
			rectangle(440-5,155-5,560+5,190+5);
			rectangle(440-5,205-5,560+5,240+5);
		}
	}
	closegraph();//关闭图形环境
}
//全国交通		
void Graph::Show_traffic()
{
	system("cls");//清屏
	cout<<"                                              **********城市信息**********\n";
	Print_c();//显示城市
	system("pause");
	cout<<"                                              **********飞机航班表**********\n";
	Print_p();//显示飞机
	system("pause");
	cout<<"                                              **********列车时刻图**********\n";
	Print_t();//显示列车
	system("pause");
	Main_interface();
}
//咨询界面	
void Graph::Counsel()
{	
	initgraph(980,500);//创建一个宽640,长480的窗口
	setbkcolor(WHITE);//设置当前绘图背景色
	cleardevice();//清屏

	setlinecolor(RGB(225,128,0));	// 设置当前线条颜色
	setfillcolor(RGB(255,255,128));	// 设置当前填充颜色
	fillrectangle(400, 10, 600, 40);// 画填充矩形(有边框)
	settextcolor(BLACK);//设置当前文字颜色
	settextstyle(20, 0, "宋体");//设置当前字体样式
	setbkmode(TRANSPARENT);//设置背景混合模式
	outtextxy(465, 15, "交通咨询");

	//最佳选择
	setfillcolor(LIGHTBLUE);//设置当前填充颜色
	solidrectangle(440, 55, 560, 90);	//画填充矩形(无边框)
	solidrectangle(440, 105, 560, 140);	//画填充矩形(无边框)
	solidrectangle(440, 155, 560, 190);	//画填充矩形(无边框)
	solidrectangle(440, 205, 560, 240);	//画填充矩形(无边框)
	settextcolor(WHITE);//设置当前文字颜色
	settextstyle(20, 0, "楷体");//设置当前字体样式
	setbkmode(TRANSPARENT);//设置背景混合模式
	outtextxy(450, 60, "1 最少费用");
	outtextxy(450, 110, "2 最少时间");
	outtextxy(450, 160, "3 最少中转");
	outtextxy(450, 210, "返回主界面");

	//交通方式
	setfillcolor(RGB(255,128,128));//设置当前填充颜色
	solidrectangle(440+200, 55-20, 560+175, 90-25);		//画填充矩形(无边框)
	solidrectangle(440+200, 105-20, 560+175, 140-25);	//画填充矩形(无边框)
	solidrectangle(440-175, 105-20, 560-200, 140-25);	//画填充矩形(无边框)
	solidrectangle(440-175, 155-20, 560-200, 190-25);	//画填充矩形(无边框)
	solidrectangle(440+200, 155-20, 560+175, 190-25);	//画填充矩形(无边框)
	solidrectangle(440+200, 205-20, 560+175, 240-25);	//画填充矩形(无边框)
	settextcolor(WHITE);//设置当前文字颜色
	settextstyle(15, 0, "楷体");//设置当前字体样式
	setbkmode(TRANSPARENT);//设置背景混合模式
	outtextxy(450+200, 60-20, "1 = 飞机");
	outtextxy(450+200, 110-20, "2 = 航班");
	outtextxy(450-175, 110-20, "1 = 飞机");
	outtextxy(450-175, 160-20, "2 = 航班");
	outtextxy(450+200, 160-20, "1 = 飞机");
	outtextxy(450+200, 210-20, "2 = 航班");

	//连线
	line(440+200, 72.5-20, 560, 72.5);//画一条线
	line(440+200, 122.5-20, 560, 72.5);//画一条线
	line(560-200, 122.5-20, 440, 122.5);//画一条线
	line(560-200, 172.5-20, 440, 122.5);//画一条线
	line(440+200, 172.5-20, 560, 172.5);//画一条线
	line(440+200, 222.5-20, 560, 172.5);//画一条线

	MOUSEMSG m;
	while (1)//一直获取鼠标信息
	{
		m = GetMouseMsg();
		//1 最少费用——飞机
		if(m.x>=440+200 && m.x<=560+175 && m.y>=55-20 && m.y<=90-25)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440+200-5,55-20-5,560+175+5,90-25+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
			{
				closegraph();//关闭图形环境
				C_money_plane();//最少费用——飞机
				Counsel();
			}
		}
		//1 最少费用——列车
		else if(m.x>=440+200 && m.x<=560+175 && m.y>=105-20 && m.y<=140-25)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440+200-5,105-20-5,560+175+5,140-25+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
			{
				closegraph();//关闭图形环境
				C_money_train();//最少费用——列车
				Counsel();
			}
		}
		//2 最少时间——飞机
		else if(m.x>=440-175 && m.x<=560-200 && m.y>=105-20 && m.y<=140-25)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-175-5,105-20-5,560-200+5,140-25+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
			{
				closegraph();//关闭图形环境
				C_time_plane();//最少时间——飞机
				Counsel();
			}
		}
		//2 最少时间——列车
		else if(m.x>=440-175 && m.x<=560-200 && m.y>=155-20 && m.y<=190-25)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-175-5,155-20-5,560-200+5,190-25+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
			{
				closegraph();//关闭图形环境
				C_time_train();//最少时间——列车
				Counsel();
			}
		}
		//3 最少中转——飞机
		else if(m.x>=440+200 && m.x<=560+175 && m.y>=155-20 && m.y<=190-25)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440+200-5,155-20-5,560+175+5,190-25+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
			{
				closegraph();//关闭图形环境
				C_change_plane();//最少中转——飞机
				Counsel();
			}
		}
		//3 最少中转——列车
		else if(m.x>=440+200 && m.x<=560+175 && m.y>=205-20 && m.y<=240-25)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440+200-5,205-20-5,560+175+5,240-25+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
			{
				closegraph();//关闭图形环境
				C_change_train();//最少中转——列车
				Counsel();
			}
		}
		//返回主界面
		else if(m.x>=440 && m.x<=560 && m.y>=205 && m.y<=240)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-5,205-5,560+5,240+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
				Main_interface();
		}
		//鼠标没有点在上面
		else
		{
			setlinecolor(WHITE);
			rectangle(440+200-5,55-20-5,560+175+5,90-25+5);
			rectangle(440+200-5,105-20-5,560+175+5,140-25+5);
			rectangle(440-175-5,105-20-5,560-200+5,140-25+5);
			rectangle(440-175-5,155-20-5,560-200+5,190-25+5);
			rectangle(440+200-5,155-20-5,560+175+5,190-25+5);
			rectangle(440+200-5,205-20-5,560+175+5,240-25+5);
			rectangle(440-5,205-5,560+5,240+5);
		}
	}
	closegraph();//关闭图形环境
}
//管理员进入界面
void Graph::Administrator(char ch[10])
{
	system("cls");//清屏
	cout<<"\n\n\n\n\n\n\n\n                            密码：123456\n";
	cout<<"                            请输入管理员密码：";
	for(int i=0; i<10; i++)
	{
		ch[i]=getch();//输入密码
		if(ch[i]=='\r') break;//按回车键结束输入
		cout<<"*";//用*代替输入的密码
	}
	ch[i]='\0';//添加字符串结束符
	string str=ch;
	if(str == "123456")//判断密码是否正确
		Administrator();//正确进入管理员界面
	else
	{
		system("cls");
		SetColor(4,15);
		cout<<"\n\n\n\n\n\n\n\n                            输入密码错误！\a";
		SetColor(0,15);
		Sleep(1000);//错误则提示，然后返回主界面
		Main_interface();
	}
}
//管理员界面
void Graph::Administrator()
{
	initgraph(980,500);//创建一个宽640,长480的窗口
	setbkcolor(WHITE);//设置当前绘图背景色
	cleardevice();//清屏

	setlinecolor(RGB(225,128,0));	// 设置当前线条颜色
	setfillcolor(RGB(255,255,128));	// 设置当前填充颜色
	fillrectangle(400, 10, 600, 40);// 画填充矩形(有边框)
	settextcolor(BLACK);//设置当前文字颜色
	settextstyle(20, 0, "宋体");//设置当前字体样式
	setbkmode(TRANSPARENT);//设置背景混合模式
	outtextxy(465, 15, "信息管理");


	setfillcolor(LIGHTBLUE);//设置当前填充颜色
	solidrectangle(440, 55, 560, 90);	//画填充矩形(无边框)
	solidrectangle(440, 105, 560, 140);	//画填充矩形(无边框)
	solidrectangle(440, 155, 560, 190);	//画填充矩形(无边框)
	solidrectangle(440, 205, 560, 240);	//画填充矩形(无边框)
	solidrectangle(440, 255, 560, 290);	//画填充矩形(无边框)

	settextcolor(WHITE);//设置当前文字颜色
	settextstyle(20, 0, "楷体");//设置当前字体样式
	setbkmode(TRANSPARENT);//设置背景混合模式
	outtextxy(450, 60, "1 初始化");
	outtextxy(450, 110, "2 城市信息");
	outtextxy(450, 160, "3 飞机航班");
	outtextxy(450, 210, "4 列车时刻");
	outtextxy(450, 260, "返回主界面");

	MOUSEMSG m;
	while (1)//一直获取鼠标信息
	{
		m = GetMouseMsg();
		//1 初始化
		if(m.x>=440 && m.x<=560 && m.y>=55 && m.y<=90)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-5,55-5,560+5,90+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
			{
				closegraph();//关闭图形环境
				Init();//初始化
			}
		}
		//2 城市信息
		else if(m.x>=440 && m.x<=560 && m.y>=105 && m.y<=140)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-5,105-5,560+5,140+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
			{
				closegraph();//关闭图形环境
				City_Face();//城市信息
			}
		}
		//3 飞机航班
		else if(m.x>=440 && m.x<=560 && m.y>=155 && m.y<=190)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-5,155-5,560+5,190+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
			{
				closegraph();//关闭图形环境
				Plane_Face();//飞机航班
			}
		}
		//4 列车时刻
		else if(m.x>=440 && m.x<=560 && m.y>=205 && m.y<=240)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-5,205-5,560+5,240+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
			{
				closegraph();//关闭图形环境
				Train_Face();//列车时刻
			}
		}
		//返回主界面
		else if(m.x>=440 && m.x<=560 && m.y>=255 && m.y<=290)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-5,255-5,560+5,290+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
				Main_interface();
		}
		//鼠标没有点在上面
		else
		{
			setlinecolor(WHITE);
			rectangle(440-5,55-5,560+5,90+5);
			rectangle(440-5,105-5,560+5,140+5);
			rectangle(440-5,155-5,560+5,190+5);
			rectangle(440-5,205-5,560+5,240+5);
			rectangle(440-5,255-5,560+5,290+5);
		}
	}
	closegraph();//关闭图形环境
}
//城市信息
void Graph::City_Face()	
{	
	initgraph(980,500);//创建一个宽640,长480的窗口
	setbkcolor(WHITE);//设置当前绘图背景色
	cleardevice();//清屏

	setlinecolor(RGB(225,128,0));	// 设置当前线条颜色
	setfillcolor(RGB(255,255,128));	// 设置当前填充颜色
	fillrectangle(400, 10, 600, 40);// 画填充矩形(有边框)
	settextcolor(BLACK);//设置当前文字颜色
	settextstyle(20, 0, "宋体");//设置当前字体样式
	setbkmode(TRANSPARENT);//设置背景混合模式
	outtextxy(465, 15, "城市信息");


	setfillcolor(LIGHTBLUE);//设置当前填充颜色
	solidrectangle(440, 55, 560, 90);	//画填充矩形(无边框)
	solidrectangle(440, 105, 560, 140);	//画填充矩形(无边框)
	solidrectangle(440, 155, 560, 190);	//画填充矩形(无边框)
	solidrectangle(440, 205, 560, 240);	//画填充矩形(无边框)
	solidrectangle(435, 255, 565, 290);	//画填充矩形(无边框)
	solidrectangle(435, 305, 565, 340);	//画填充矩形(无边框)

	settextcolor(WHITE);//设置当前文字颜色
	settextstyle(20, 0, "楷体");//设置当前字体样式
	setbkmode(TRANSPARENT);//设置背景混合模式
	outtextxy(450, 60, "1 添加城市");
	outtextxy(450, 110, "2 删除城市");
	outtextxy(450, 160, "3 显示城市");
	outtextxy(450, 210, "4 保存");
	outtextxy(450, 260, "返回主界面");
	outtextxy(440, 310, "返回上一界面");

	MOUSEMSG m;
	while (1)//一直获取鼠标信息
	{
		m = GetMouseMsg();
		//1 添加城市
		if(m.x>=440 && m.x<=560 && m.y>=55 && m.y<=90)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-5,55-5,560+5,90+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
			{
				closegraph();//关闭图形环境
				City_addition();//添加城市
			}
		}
		//2 删除城市
		else if(m.x>=440 && m.x<=560 && m.y>=105 && m.y<=140)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-5,105-5,560+5,140+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
			{
				closegraph();//关闭图形环境
				City_remove();//删除城市
			}
		}
		//3 显示城市
		else if(m.x>=440 && m.x<=560 && m.y>=155 && m.y<=190)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-5,155-5,560+5,190+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
			{
				closegraph();//关闭图形环境
				City_show();//显示城市
			}
		}
		//4 保存
		else if(m.x>=440 && m.x<=560 && m.y>=205 && m.y<=240)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-5,205-5,560+5,240+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
			{
				closegraph();//关闭图形环境
				char flag;
				system("cls");
				Print_c();//显示城市
				cout<<"确认保存城市信息按y|Y，否则放弃保存：";
				flag=PD_flag();
				if(flag=='y'||flag=='Y')Save_c();//保存信息
				else City_Face();
			}
		}
		//返回主界面
		else if(m.x>=440 && m.x<=560 && m.y>=255 && m.y<=290)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-5,255-5,560+5,290+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
				Main_interface();
		}
		//返回上一界面
		else if(m.x>=435 && m.x<=565 && m.y>=305 && m.y<=340)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(435-5,305-5,565+5,340+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
				Administrator();
		}
		//鼠标没有点在上面
		else
		{
			setlinecolor(WHITE);
			rectangle(440-5,55-5,560+5,90+5);
			rectangle(440-5,105-5,560+5,140+5);
			rectangle(440-5,155-5,560+5,190+5);
			rectangle(440-5,205-5,560+5,240+5);
			rectangle(435-5,255-5,565+5,290+5);
			rectangle(435-5,305-5,565+5,340+5);
		}
	}
	closegraph();//关闭图形环境
}
//飞机航班
void Graph::Plane_Face()		
{
	initgraph(980,500);//创建一个宽640,长480的窗口
	setbkcolor(WHITE);//设置当前绘图背景色
	cleardevice();//清屏

	setlinecolor(RGB(225,128,0));	// 设置当前线条颜色
	setfillcolor(RGB(255,255,128));	// 设置当前填充颜色
	fillrectangle(400, 10, 600, 40);// 画填充矩形(有边框)
	settextcolor(BLACK);//设置当前文字颜色
	settextstyle(20, 0, "宋体");//设置当前字体样式
	setbkmode(TRANSPARENT);//设置背景混合模式
	outtextxy(465, 15, "飞机航班");


	setfillcolor(LIGHTBLUE);//设置当前填充颜色
	solidrectangle(440, 55, 560, 90);	//画填充矩形(无边框)
	solidrectangle(440, 105, 560, 140);	//画填充矩形(无边框)
	solidrectangle(440, 155, 560, 190);	//画填充矩形(无边框)
	solidrectangle(440, 205, 560, 240);	//画填充矩形(无边框)
	solidrectangle(435, 255, 565, 290);	//画填充矩形(无边框)

	settextcolor(WHITE);//设置当前文字颜色
	settextstyle(20, 0, "楷体");//设置当前字体样式
	setbkmode(TRANSPARENT);//设置背景混合模式
	outtextxy(450, 60, "1 添加飞机");
	outtextxy(450, 110, "2 删除飞机");
	outtextxy(450, 160, "3 显示飞机");
	outtextxy(450, 210, "返回主界面");
	outtextxy(440, 260, "返回上一界面");

	MOUSEMSG m;
	while (1)//一直获取鼠标信息
	{
		m = GetMouseMsg();
		//1 添加飞机
		if(m.x>=440 && m.x<=560 && m.y>=55 && m.y<=90)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-5,55-5,560+5,90+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
			{
				closegraph();//关闭图形环境
				Plane_addition();//添加飞机
			}
		}
		//2 删除飞机
		else if(m.x>=440 && m.x<=560 && m.y>=105 && m.y<=140)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-5,105-5,560+5,140+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
			{
				closegraph();//关闭图形环境
				Plane_remove();//删除飞机
			}
		}
		//3 显示飞机
		else if(m.x>=440 && m.x<=560 && m.y>=155 && m.y<=190)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-5,155-5,560+5,190+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
			{
				closegraph();//关闭图形环境
				Plane_show();//显示飞机
			}
		}
		//返回主界面
		else if(m.x>=440 && m.x<=560 && m.y>=205 && m.y<=240)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-5,205-5,560+5,240+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
				Main_interface();
		}
		//返回上一界面
		else if(m.x>=435 && m.x<=565 && m.y>=255 && m.y<=290)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(435-5,255-5,565+5,290+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
				Administrator();
		}
		//鼠标没有点在上面
		else
		{
			setlinecolor(WHITE);
			rectangle(440-5,55-5,560+5,90+5);
			rectangle(440-5,105-5,560+5,140+5);
			rectangle(440-5,155-5,560+5,190+5);
			rectangle(440-5,205-5,560+5,240+5);
			rectangle(435-5,255-5,565+5,290+5);
		}
	}
	closegraph();//关闭图形环境
}
//列车时刻
void Graph::Train_Face()		
{	
	initgraph(980,500);//创建一个宽640,长480的窗口
	setbkcolor(WHITE);//设置当前绘图背景色
	cleardevice();//清屏

	setlinecolor(RGB(225,128,0));	// 设置当前线条颜色
	setfillcolor(RGB(255,255,128));	// 设置当前填充颜色
	fillrectangle(400, 10, 600, 40);// 画填充矩形(有边框)
	settextcolor(BLACK);//设置当前文字颜色
	settextstyle(20, 0, "宋体");//设置当前字体样式
	setbkmode(TRANSPARENT);//设置背景混合模式
	outtextxy(465, 15, "列车时刻 ");


	setfillcolor(LIGHTBLUE);//设置当前填充颜色
	solidrectangle(440, 55, 560, 90);	//画填充矩形(无边框)
	solidrectangle(440, 105, 560, 140);	//画填充矩形(无边框)
	solidrectangle(440, 155, 560, 190);	//画填充矩形(无边框)
	solidrectangle(440, 205, 560, 240);	//画填充矩形(无边框)
	solidrectangle(435, 255, 565, 290);	//画填充矩形(无边框)

	settextcolor(WHITE);//设置当前文字颜色
	settextstyle(20, 0, "楷体");//设置当前字体样式
	setbkmode(TRANSPARENT);//设置背景混合模式
	outtextxy(450, 60, "1 添加列车");
	outtextxy(450, 110, "2 删除列车");
	outtextxy(450, 160, "3 显示列车");
	outtextxy(450, 210, "返回主界面");
	outtextxy(440, 260, "返回上一界面");

	MOUSEMSG m;
	while (1)//一直获取鼠标信息
	{
		m = GetMouseMsg();
		//1 添加列车
		if(m.x>=440 && m.x<=560 && m.y>=55 && m.y<=90)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-5,55-5,560+5,90+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
			{
				closegraph();//关闭图形环境
				Train_addition();//添加列车
			}
		}
		//2 删除列车
		else if(m.x>=440 && m.x<=560 && m.y>=105 && m.y<=140)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-5,105-5,560+5,140+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
			{
				closegraph();//关闭图形环境
				Train_remove();//删除列车
			}
		}
		//3 显示列车
		else if(m.x>=440 && m.x<=560 && m.y>=155 && m.y<=190)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-5,155-5,560+5,190+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
			{
				closegraph();//关闭图形环境
				Train_show();//显示列车
			}
		}
		//返回主界面
		else if(m.x>=440 && m.x<=560 && m.y>=205 && m.y<=240)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(440-5,205-5,560+5,240+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
				Main_interface();
		}
		//返回上一界面
		else if(m.x>=440 && m.x<=560 && m.y>=255 && m.y<=290)
		{
			setlinecolor(RED);//设置当前线条颜色
			rectangle(435-5,255-5,565+5,290+5);
			if(m.uMsg == WM_LBUTTONDOWN)//鼠标左键按下
				Administrator();
		}
		//鼠标没有点在上面
		else
		{
			setlinecolor(WHITE);
			rectangle(440-5,55-5,560+5,90+5);
			rectangle(440-5,105-5,560+5,140+5);
			rectangle(440-5,155-5,560+5,190+5);
			rectangle(440-5,205-5,560+5,240+5);
			rectangle(435-5,255-5,565+5,290+5);
		}
	}
	closegraph();//关闭图形环境
}
//功能1——城市——添加、删除、显示
void Graph::City_addition()
{
	char flag='y';//判断是否继续添加
	while(flag=='Y'||flag=='y')
	{
		system("cls");//清屏
		cout<<"                      ***********添加城市信息*************\n"; 
		Print_c();//显示城市
		cout<<"          请输入城市名称：";
		vex[vexnum].name = PD_cityname();
		if(Find_cityname(vex[vexnum].name)!=-1)
		{
			SetColor(13,15);
			cout<<"该城市已存在！"<<endl;
			SetColor(0,15);
			system("pause");
			City_addition();
		}
		vexnum++;
		PrintLine();
		cout<<"          添加城市信息完成"<<endl;
		cout<<"          如果继续添加请按y|Y:";
		flag=PD_flag();
	}
	system("pause");
	City_Face();
}
void Graph::City_remove()
{
	int i,j;
	char flag='y';
	while(flag=='Y'||flag=='y')
	{
		system("cls");//清屏
		cout<<"                      ***********删除城市信息*************\n"; 
		Print_c();//显示城市
		cout<<"          请输入需要删除的城市名称：";
		string name = PD_cityname();
		int n = Find_cityname(name);//城市名称的下标
		if(n==-1)
		{
			SetColor(13,15);
			cout<<"该城市不存在！"<<endl;
			SetColor(0,15);
			system("pause");
			City_remove();
		}
		for(i=n; i<vexnum; i++)
			for(j=0; j<vexnum; j++)
			{
				arc[i][j].plane_num=arc[i+1][j].plane_num;
				arc[i][j].plane_start_time=arc[i+1][j].plane_start_time;
				arc[i][j].plane_reach_time=arc[i+1][j].plane_reach_time;
				arc[i][j].plane_money=arc[i+1][j].plane_money;
				arc[i][j].plane_time=arc[i+1][j].plane_time;
				arc[i][j].plane_change=arc[i+1][j].plane_change;
				arc[i][j].train_num=arc[i+1][j].train_num;
				arc[i][j].train_start_time=arc[i+1][j].train_start_time;
				arc[i][j].train_reach_time=arc[i+1][j].train_reach_time;
				arc[i][j].train_money=arc[i+1][j].train_money;
				arc[i][j].train_time=arc[i+1][j].train_time;
				arc[i][j].train_change=arc[i+1][j].train_change;
			}
		for(i=n; i<vexnum; i++)
			for(j=0; j<vexnum; j++)
			{
				arc[i][j].plane_num=arc[i][j+1].plane_num;
				arc[i][j].plane_start_time=arc[i][j+1].plane_start_time;
				arc[i][j].plane_reach_time=arc[i][j+1].plane_reach_time;
				arc[i][j].plane_money=arc[i][j+1].plane_money;
				arc[i][j].plane_time=arc[i][j+1].plane_time;
				arc[i][j].plane_change=arc[i][j+1].plane_change;
				arc[i][j].train_num=arc[i][j+1].train_num;
				arc[i][j].train_start_time=arc[i][j+1].train_start_time;
				arc[i][j].train_reach_time=arc[i][j+1].train_reach_time;
				arc[i][j].train_money=arc[i][j+1].train_money;
				arc[i][j].train_time=arc[i][j+1].train_time;
				arc[i][j].train_change=arc[i][j+1].train_change;
			}
		for(i=n; i<vexnum; i++)
			vex[i].name=vex[i+1].name;
		vexnum--;
		PrintLine();
		cout<<"          删除城市信息完成"<<endl;
		cout<<"          如果继续删除请按y|Y:";
		flag=PD_flag();
	}
	system("pause");
	City_Face();
}
void Graph::City_show()
{		
	system("cls");//清屏
	cout<<"                      ***********显示城市信息*************\n"; 
	Print_c();//显示城市
	cout<<"显示城市信息完成"<<endl;
	system("pause");
	City_Face();
}

//功能2——飞机航班——添加、修改、删除、显示
void Graph::Plane_addition()
{
	char flag='y';
	while(flag=='Y'||flag=='y')
	{
		system("cls");//清屏
		cout<<"                      ***********添加飞机航班*************\n"; 
		Print_c();//显示城市
		cout<<"请输入出发城市名称：";
		string start_name=PD_cityname();
		int s=Find_cityname(start_name);
		cout<<"请输入抵达城市名称：";
		string reach_name=PD_cityname();
		int r=Find_cityname(reach_name);
		if (s<0 || r<0 || s==r)
		{
			SetColor(13,15);
			cout<<"输入错误！\n\a"<<endl;
			SetColor(0,15);
			system("pause");
			Plane_addition();
		}
		cout<<"请输入出发时间：\n";
		arc[s][r].plane_start_time = Time_cin();//输入时间
		cout<<"请输入抵达时间：\n";
		arc[s][r].plane_reach_time = Time_cin();//输入时间
		cout<<"请输入飞机航班编号：";
		arc[s][r].plane_num = PD_int();//编号
		cout<<"请输入飞机航班费用：";
		arc[s][r].plane_money = PD_int();//费用
		arc[s][r].plane_time = Time_diff(arc[s][r].plane_start_time , arc[s][r].plane_reach_time);//时间差
		arc[s][r].plane_change=1;
		p_arcnum++;
		PrintLine();
		cout<<"          添加飞机航班完成"<<endl;
		cout<<"          如果继续录入请按y|Y:";
		flag=PD_flag();
	}
	system("pause");
	Plane_Face();
}
void Graph::Plane_remove()
{
	int i,j;
	char flag='y';
	while(flag=='Y'||flag=='y')
	{
		system("cls");//清屏
		cout<<"                      ***********删除航班信息*************\n"; 
		Print_p();
		cout<<"          请输入需要删除的航班序号：";
		int num=PD_int();
		PrintLine();
		for(i=0; i<MAX; i++)
			for(j=0; j<MAX; j++)
				if(arc[i][j].plane_num == num)
				{
					if(arc[i][j].plane_money!=0 && arc[i][j].plane_money!=INF)
					{
						cout<<setiosflags(ios::left)
							<<setw(20)<<arc[i][j].plane_num
							<<setw(20)<<vex[i].name
							<<setw(20)<<vex[j].name
							<<setw(15)<<Time_conversion(arc[i][j].plane_start_time)
							<<setw(15)<<Time_conversion(arc[i][j].plane_reach_time)
							<<setw(15)<<arc[i][j].plane_money
							<<setw(15)<<arc[i][j].plane_time<<endl;
						PrintLine();
					}
				}
		cout<<"          确定删除请按y|Y：";
		char fun;
		fun=PD_flag();
		if(fun=='Y' || fun=='y')
		{
			for(i=0; i<vexnum; i++)
				for(j=0; j<vexnum; j++)
					if(arc[i][j].plane_num == num)
					{
						arc[i][j].plane_num=INF;
						arc[i][j].plane_start_time.tm_hour=INF;
						arc[i][j].plane_start_time.tm_min=INF;
						arc[i][j].plane_reach_time.tm_hour=INF;
						arc[i][j].plane_reach_time.tm_min=INF;
						arc[i][j].plane_money=INF;
						arc[i][j].plane_time=INF;
						arc[i][j].plane_change=INF;
						flag='a';
						cout<<"          删除飞机航班完成"<<endl;
					}
					if(flag!='a')
					{
						cout<<" 没有此航班 !请重新输入 !"<<endl;
					}
			cout<<"          如果继续删除请按y|Y:";
			flag=PD_flag();
		}
		else break;
	}
	system("pause");
	Plane_Face();
}
void Graph::Plane_show()
{
	system("cls");
	cout<<"							 飞机航班表\n";
	Print_p();
	system("pause");
	Plane_Face();
}

//功能3——列车时刻——添加、修改、删除、显示
void Graph::Train_addition()
{
	char flag='y';
	while(flag=='Y'||flag=='y')
	{
		system("cls");//清屏
		cout<<"                      ***********添加列车车次*************\n"; 
		Print_c();//显示城市
		cout<<"请输入出发城市名称：";
		string start_name=PD_cityname();
		int s=Find_cityname(start_name);
		cout<<"请输入抵达城市名称：";
		string reach_name=PD_cityname();
		int r=Find_cityname(reach_name);
		if (s<0 || r<0 || s==r)
		{
			SetColor(13,15);
			cout<<"输入错误！\n\a"<<endl;
			SetColor(0,15);
			system("pause");
			Train_addition();
		}
		cout<<"请输入出发时间：\n";
		arc[s][r].train_start_time = Time_cin();//输入时间
		cout<<"请输入抵达时间：\n";
		arc[s][r].train_reach_time = Time_cin();//输入时间
		cout<<"请输入列车车次编号：";
		arc[s][r].train_num = PD_int();//编号
		cout<<"请输入列车车次费用：";
		arc[s][r].train_money = PD_int();//费用
		arc[s][r].train_time = Time_diff(arc[s][r].train_start_time , arc[s][r].train_reach_time);//时间差
		arc[s][r].train_change=1;
		p_arcnum++;
		PrintLine();
		cout<<"          添加列车车次完成"<<endl;
		cout<<"          如果继续录入请按y|Y:";
		flag=PD_flag();
	}
	system("pause");
	Train_Face();
}
void Graph::Train_remove()
{
	int i,j;
	char flag='y';
	while(flag=='Y'||flag=='y')
	{
		system("cls");//清屏
		cout<<"                      ***********删除车次信息*************\n"; 
		Print_t();
		cout<<"          请输入需要删除的车次序号：";
		int num=PD_int();
		PrintLine();
		for(i=0; i<MAX; i++)
			for(j=0; j<MAX; j++)
				if(arc[i][j].train_num == num)
				{
					if(arc[i][j].train_money!=0 && arc[i][j].train_money!=INF)
					{
						cout<<setiosflags(ios::left)
							<<setw(20)<<arc[i][j].train_num
							<<setw(20)<<vex[i].name
							<<setw(20)<<vex[j].name
							<<setw(15)<<Time_conversion(arc[i][j].train_start_time)
							<<setw(15)<<Time_conversion(arc[i][j].train_reach_time)
							<<setw(15)<<arc[i][j].train_money
							<<setw(15)<<arc[i][j].train_time<<endl;
						PrintLine();
					}
				}
		cout<<"          确定删除请按y|Y：";
		char fun;
		fun=PD_flag();
		if(fun=='Y' || fun=='y')
		{
			for(i=0; i<vexnum; i++)
				for(j=0; j<vexnum; j++)
					if(arc[i][j].train_num == num)
					{
						arc[i][j].train_num=INF;
						arc[i][j].train_start_time.tm_hour=INF;
						arc[i][j].train_start_time.tm_min=INF;
						arc[i][j].train_reach_time.tm_hour=INF;
						arc[i][j].train_reach_time.tm_min=INF;
						arc[i][j].train_money=INF;
						arc[i][j].train_time=INF;
						arc[i][j].train_change=INF;
						flag='a';
						cout<<"          删除列车车次完成"<<endl;
					}
					if(flag!='a')
					{
						cout<<" 没有此车次 !请重新输入 !"<<endl;
					}
			cout<<"          如果继续删除请按y|Y:";
			flag=PD_flag();
		}
		else break;
	}
	system("pause");
	Train_Face();
}
void Graph::Train_show()
{
	system("cls");
	cout<<"							 列车时刻表\n";
	Print_t();
	system("pause");
	Train_Face();
}
//城市显示
void Graph::Print_c()
{
	if(vexnum==0)
	{
		cout<<" 无城市 !"<<endl;
		cout<<" 按任意键返回主界面。。。"<<endl;
		system("pause");
		Main_interface();
	}
	cout<<"城市数："<<vexnum<<endl;
	//输出城市
	for(int i=0; i<vexnum; i++)
	{
		SetColor(0,3);
		cout<<setiosflags(ios::left)<<i<<"_"<<setw(10)<<vex[i].name;
		SetColor(0,15);
	}
	cout<<endl;
	PrintLine();//画线
}
//飞机显示
void Graph::Print_p()
{
	int i,j;
	cout<<"          现有航班数："<<p_arcnum<<endl;
	SetColor(0,3);
	cout<<setiosflags(ios::left)<<setw(20)<<"编号"<<setw(20)<<"出发城市"<<setw(20)<<"抵达城市"<<setw(15)<<"出发时间"<<setw(15)<<"抵达时间"<<setw(15)<<"票价"<<setw(15)<<"时间"<<endl;
	SetColor(0,15);
	for(i=0; i<MAX; i++)
		for(j=0; j<MAX; j++)
			if(arc[i][j].plane_money!=0 && arc[i][j].plane_money!=INF)
			{
				cout<<setiosflags(ios::left)
					<<setw(20)<<arc[i][j].plane_num
					<<setw(20)<<vex[i].name
					<<setw(20)<<vex[j].name
					<<setw(15)<<Time_conversion(arc[i][j].plane_start_time)
					<<setw(15)<<Time_conversion(arc[i][j].plane_reach_time)
					<<setw(15)<<arc[i][j].plane_money
					<<setw(15)<<arc[i][j].plane_time<<endl;
				PrintLine();
			}
}
//列车显示
void Graph::Print_t()
{
	int i,j;
	cout<<"          现有车次数："<<t_arcnum<<endl;
	SetColor(0,3);
	cout<<setiosflags(ios::left)<<setw(20)<<"编号"<<setw(20)<<"出发城市"<<setw(20)<<"抵达城市"<<setw(15)<<"出发时间"<<setw(15)<<"抵达时间"<<setw(15)<<"票价"<<setw(15)<<"时间"<<endl;
	SetColor(0,15);
	for(i=0; i<MAX; i++)
		for(j=0; j<MAX; j++)
			if(arc[i][j].train_money!=0 && arc[i][j].train_money!=INF)
			{
				cout<<setiosflags(ios::left)
					<<setw(20)<<arc[i][j].train_num
					<<setw(20)<<vex[i].name
					<<setw(20)<<vex[j].name
					<<setw(15)<<Time_conversion(arc[i][j].train_start_time)
					<<setw(15)<<Time_conversion(arc[i][j].train_reach_time)
					<<setw(15)<<arc[i][j].train_money
					<<setw(15)<<arc[i][j].train_time<<endl;
				PrintLine();
			}
}
//保存城市信息
void Graph::Save_c()
{
//城市信息
	outFile.open("城市信息new.txt",ios::out | ios::in | ios::app);//打开文件用于读和写
	if(!outFile)
	{
		cout<<"城市信息.txt打开文件失败。\n";
		Sleep(1000);
		Init();
	}
	outFile<<vexnum<<endl;
	for(int i=0; i<vexnum; i++)
		outFile<<vex[i].name<<endl;
	outFile.close();//关闭文件
	remove("城市信息.txt");//删除文件
	rename("城市信息new.txt","城市信息.txt");//文件改名
	cout<<"          保存城市信息完成"<<endl;
}
//文件导入
void Graph::File()
{
	//城市信息
	inFile.open("城市信息.txt",ios::in);
	if(!inFile)
	{
		cout<<"城市信息.txt打开文件失败。\n";
		Sleep(1000);
		Init();
	}
	inFile.seekg(0,ios::end);   
	long size1 = inFile.tellg();      
	cout<< "城市信息.txt文件大小为："<<size1<<" 字节"<<endl;
	Sleep(1000);
	if(size1 <= 0)Init();

	inFile.seekg(ios::beg);//重新定位文件位置指针 查找方向为ios::beg（从流的开头开始定位）
	inFile>>vexnum;
	for(int i=0; i<vexnum; i++)
	{
		inFile>>vex[i].name;
	}
	inFile.close();
	//飞机信息
	inFile.open("飞机信息.txt",ios::in);//打开文件用于读
	if(!inFile)
	{
		cout<<"飞机信息.txt打开文件失败。\n";
		Sleep(1000);
		Init();
	}
	inFile.seekg(0,ios::end);   
	long size2 = inFile.tellg();      
	cout<< "飞机信息.txt文件大小为："<<size2<<" 字节"<<endl;
	Sleep(1000);
	if(size2 <= 0)Init();

	inFile.seekg(ios::beg);//重新定位文件位置指针 查找方向为ios::beg（从流的开头开始定位）
	inFile>>p_arcnum;
	int n=p_arcnum;
	while(n--)
	{
		int sp,rp;
		inFile>>sp;
		inFile>>rp;
		inFile>>arc[sp][rp].plane_start_time.tm_hour;
		inFile>>arc[sp][rp].plane_start_time.tm_min;
		inFile>>arc[sp][rp].plane_reach_time.tm_hour;
		inFile>>arc[sp][rp].plane_reach_time.tm_min;
		inFile>>arc[sp][rp].plane_num;
		inFile>>arc[sp][rp].plane_money;
		inFile>>arc[sp][rp].plane_time;
		arc[sp][rp].plane_change=1;
	}	
	inFile.close();
	//列车信息
	inFile.open("列车信息.txt",ios::in);//打开文件用于读
	if(!inFile)
	{
		cout<<"列车信息.txt打开文件失败。\n";
		Sleep(1000);
		Init();
	}
	inFile.seekg(0,ios::end);   
	long size3 = inFile.tellg();      
	cout<< "列车信息.txt文件大小为："<<size3<<" 字节"<<endl;
	Sleep(1000);
	if(size3 <= 0)Init();

	inFile.seekg(ios::beg);//重新定位文件位置指针 查找方向为ios::beg（从流的开头开始定位）
	inFile>>t_arcnum;
	int m=t_arcnum;
	while(m--)
	{
		int st,rt;
		inFile>>st;
		inFile>>rt;
		inFile>>arc[st][rt].train_start_time.tm_hour;
		inFile>>arc[st][rt].train_start_time.tm_min;
		inFile>>arc[st][rt].train_reach_time.tm_hour;
		inFile>>arc[st][rt].train_reach_time.tm_min;
		inFile>>arc[st][rt].train_num;
		inFile>>arc[st][rt].train_money;
		inFile>>arc[st][rt].train_time;
		arc[st][rt].train_change=1;
	}	
	inFile.close();
}
//创建城市文件
void Graph::Create_city()
{
	system("cls");//清屏
	outFile.open("城市信息.txt",ios::out);
	if(!outFile)
	{//打开文件失败重新进行初始化
		cout<<"城市信息.txt打开文件失败。\n";
		Sleep(1000);
		Init();
	}
	cout<<"《《《创建城市信息文件》》》\n";
	cout<<"请输入城市个数：";
	vexnum = PD_max(MAX);//输入顶点数（不超过MAX）
	outFile<<vexnum<<endl;
	for(int i=0; i<vexnum; i++)
	{
		cout<<"第"<<i+1<<"个：";
		vex[i].name=PD_cityname();
		outFile<<vex[i].name<<endl;
	}
	outFile.close();
	cout<<"          创建城市文件完成"<<endl;
	Sleep(1000);
}
//创建飞机文件
void Graph::Create_plane()
{
	int i;
	system("cls");
	outFile.open("飞机信息.txt",ios::out);
	if(!outFile)
	{
		cout<<"飞机信息.txt打开文件失败。\n";
		Sleep(1000);
		Init();
	}
	//输入航班数量
	cout<<"《《《创建飞机航班信息文件》》》\n";
	cout<<"城市数："<<vexnum<<endl;
	cout<<"请输入航班数量：";
	int m = vexnum*(vexnum - 1);
	p_arcnum = PD_max(m);//输入边数（不超过MAX*(MAX-1)）
	outFile<<p_arcnum<<endl;
	//输入相关信息
	for(i=0; i<p_arcnum; i++)
	{
		system("cls");
		cout<<"《《《创建飞机航班信息文件》》》\n";
		Print_c();//显示城市
		cout<<"请依次输入相关信息：\n";
		cout<<"第"<<i+1<<"个：\n";
		cout<<"请输入出发城市名称：";
		string start_name=PD_cityname();
		int s=Find_cityname(start_name);
		cout<<"请输入抵达城市名称：";
		string reach_name=PD_cityname();
		int r=Find_cityname(reach_name);
		if (s<0 || r<0 || s==r)
		{
			SetColor(13,15);
			cout<<"输入错误！\n\a"<<endl;
			SetColor(0,15);
			system("pause");
			Init();
		}
		cout<<"请输入出发时间：\n";
		arc[s][r].plane_start_time = Time_cin();//输入时间
		cout<<"请输入抵达时间：\n";
		arc[s][r].plane_reach_time = Time_cin();//输入时间
		cout<<"请输入飞机航班编号：";
		arc[s][r].plane_num = PD_int();
		cout<<"请输入飞机航班费用：";
		arc[s][r].plane_money = PD_int();//费用
		arc[s][r].plane_time = Time_diff(arc[s][r].plane_start_time , arc[s][r].plane_reach_time);//时间差
		arc[s][r].plane_change=1;
		outFile<<setiosflags(ios::left)
			<<setw(20)<<s
			<<setw(20)<<r
			<<setw(20)<<arc[s][r].plane_start_time.tm_hour
			<<setw(20)<<arc[s][r].plane_start_time.tm_min
			<<setw(20)<<arc[s][r].plane_reach_time.tm_hour
			<<setw(20)<<arc[s][r].plane_reach_time.tm_min
			<<setw(20)<<arc[s][r].plane_num
			<<setw(20)<<arc[s][r].plane_money
			<<setw(20)<<arc[s][r].plane_time<<endl;
	}
	outFile.close();
	cout<<"          创建飞机文件完成"<<endl;
	Sleep(1000);
}
//创建列车文件
void Graph::Create_train()
{	
	int i;
	system("cls");
	outFile.open("列车信息.txt",ios::out);
	if(!outFile)
	{
		cout<<"列车信息.txt打开文件失败。\n";
		Sleep(1000);
		Init();
	}
	//输入车次数量
	cout<<"《《《创建列车车次信息文件》》》\n";
	cout<<"城市数："<<vexnum<<endl;
	cout<<"请输入车次数量：";
	int m = vexnum*(vexnum - 1);
	t_arcnum = PD_max(m);//输入边数（不超过MAX*(MAX-1)）
	outFile<<t_arcnum<<endl;
	//输入相关信息
	for(i=0; i<t_arcnum; i++)
	{
		system("cls");
		cout<<"《《《创建列车车次信息文件》》》\n";
		Print_c();//显示城市
		cout<<"请依次输入相关信息：\n";
		cout<<"第"<<i+1<<"个：\n";
		cout<<"请输入出发城市名称：";
		string start_name=PD_cityname();
		int s=Find_cityname(start_name);
		cout<<"请输入抵达城市名称：";
		string reach_name=PD_cityname();
		int r=Find_cityname(reach_name);
		if (s<0 || r<0 || s==r)
		{
			SetColor(13,15);
			cout<<"输入错误！\n\a"<<endl;
			SetColor(0,15);
			system("pause");
			Init();
		}
		cout<<"请输入出发时间：\n";
		arc[s][r].train_start_time = Time_cin();//输入时间
		cout<<"请输入抵达时间：\n";
		arc[s][r].train_reach_time = Time_cin();//输入时间
		cout<<"请输入列车车次编号：";
		arc[s][r].train_num = PD_int();
		cout<<"请输入列车车次费用：";
		arc[s][r].train_money = PD_int();//费用
		arc[s][r].train_time = Time_diff(arc[s][r].train_start_time , arc[s][r].train_reach_time);//时间差
		arc[s][r].train_change=1;
		outFile<<setiosflags(ios::left)
			<<setw(20)<<s
			<<setw(20)<<r
			<<setw(20)<<arc[s][r].train_start_time.tm_hour
			<<setw(20)<<arc[s][r].train_start_time.tm_min
			<<setw(20)<<arc[s][r].train_reach_time.tm_hour
			<<setw(20)<<arc[s][r].train_reach_time.tm_min
			<<setw(20)<<arc[s][r].train_num
			<<setw(20)<<arc[s][r].train_money
			<<setw(20)<<arc[s][r].train_time<<endl;
	}
	outFile.close();
	cout<<"          创建列车文件完成"<<endl;
	Sleep(1000);
}
//最少费用——飞机
void Graph::C_money_plane()
{
	system("cls");//清屏
	cout<<"           最少费用——飞机\n";
	int i,j,k;
	Print_c();//显示城市
	cout<<"请输入出发城市名称：";
	string start_name=PD_cityname();
	int s=Find_cityname(start_name);
	cout<<"请输入抵达城市名称：";
	string reach_name=PD_cityname();
	int r=Find_cityname(reach_name);
	if (s<0 || r<0 || s==r)
	{
		SetColor(13,15);
		cout<<"输入错误！\n\a"<<endl;
		SetColor(0,15);
		system("pause");
		Counsel();
	}
	for(i=0;i<vexnum;i++)
		for(j=0;j<vexnum;j++)
		{
			temp[i][j]=arc[i][j];
			Path[i][j]=-1;
		}
	for(k=0; k<vexnum; k++)
		for(i=0; i<vexnum; i++)
			for(j=0; j<vexnum; j++)
			{
				if(temp[i][k].plane_money+temp[k][j].plane_money < temp[i][j].plane_money)
				{
					temp[i][j].plane_money = temp[i][k].plane_money+temp[k][j].plane_money;
					Path[i][j]=k;
				}
			}
	cout<<endl;
	cout<<vex[s].name<<" 到 "<<vex[r].name<<" 的飞机花费最少的路线为 :\n";
	SetColor(0,3);
	cout<<setiosflags(ios::left)<<setw(20)<<"航班"<<setw(20)<<"出发城市"<<setw(20)<<"抵达城市"<<setw(15)<<"出发时间"<<setw(15)<<"抵达时间"<<setw(15)<<"票价"<<endl;
	SetColor(0,15);
	Pathcount[0]=s;
	z=1;
	Find_pass(s,r);
	Pathcount[z]=r;
	if(arc[Pathcount[0]][Pathcount[1]].plane_money == INF)
	{
		cout<<" 没有此路线 !"<<endl;
		Sleep(1000);
		Counsel();
	}
	for(i=0;i<z;i++)
	{
		string start_time = Time_conversion(arc[Pathcount[i]][Pathcount[i+1]].plane_start_time);
		string reach_time = Time_conversion(arc[Pathcount[i]][Pathcount[i+1]].plane_reach_time);
		cout<<setiosflags(ios::left)
			<<setw(20)<<arc[Pathcount[i]][Pathcount[i+1]].plane_num
			<<setw(20)<<vex[Pathcount[i]].name
			<<setw(20)<<vex[Pathcount[i+1]].name
			<<setw(15)<<start_time
			<<setw(15)<<reach_time
			<<setw(15)<<temp[Pathcount[i]][Pathcount[i+1]].plane_money<<endl;
	}
	cout<<" 最少花费为 :"<<temp[s][r].plane_money<<" 元"<<endl;
	cout<<endl;
	system("pause");
}
//最少费用——列车
void Graph::C_money_train()
{	
	system("cls");//清屏
	cout<<"           最少费用——列车\n";
	int i,j,k;
	Print_c();//显示城市
	cout<<"请输入出发城市名称：";
	string start_name=PD_cityname();
	int s=Find_cityname(start_name);
	cout<<"请输入抵达城市名称：";
	string reach_name=PD_cityname();
	int r=Find_cityname(reach_name);
	if (s<0 || r<0 || s==r)
	{
		SetColor(13,15);
		cout<<"输入错误！\n\a"<<endl;
		SetColor(0,15);
		system("pause");
		Counsel();
	}
	for(i=0;i<vexnum;i++)
		for(j=0;j<vexnum;j++)
		{
			temp[i][j]=arc[i][j];
			Path[i][j]=-1;
		}
	for(k=0; k<vexnum; k++)
		for(i=0; i<vexnum; i++)
			for(j=0; j<vexnum; j++)
			{
				if(temp[i][k].train_money+temp[k][j].train_money < temp[i][j].train_money)
				{
					temp[i][j].train_money = temp[i][k].train_money+temp[k][j].train_money;
					Path[i][j]=k;
				}
			}
	cout<<endl;
	cout<<vex[s].name<<" 到 "<<vex[r].name<<" 的列车花费最少的路线为 :\n";
	SetColor(0,3);
	cout<<setiosflags(ios::left)<<setw(20)<<"车次"<<setw(20)<<"出发城市"<<setw(20)<<"抵达城市"<<setw(15)<<"出发时间"<<setw(15)<<"抵达时间"<<setw(15)<<"票价"<<endl;
	SetColor(0,15);
	Pathcount[0]=s;
	z=1;
	Find_pass(s,r);
	Pathcount[z]=r;
	if(arc[Pathcount[0]][Pathcount[1]].train_money == INF)
	{
		cout<<" 没有此路线 !"<<endl;
		Sleep(1000);
		Counsel();
	}
	for(i=0;i<z;i++)
	{
		string start_time = Time_conversion(arc[Pathcount[i]][Pathcount[i+1]].train_start_time);
		string reach_time = Time_conversion(arc[Pathcount[i]][Pathcount[i+1]].train_reach_time);
		cout<<setiosflags(ios::left)
			<<setw(20)<<arc[Pathcount[i]][Pathcount[i+1]].train_num
			<<setw(20)<<vex[Pathcount[i]].name
			<<setw(20)<<vex[Pathcount[i+1]].name
			<<setw(15)<<start_time
			<<setw(15)<<reach_time
			<<setw(15)<<temp[Pathcount[i]][Pathcount[i+1]].train_money<<endl;
	}
	cout<<" 最少花费为 :"<<temp[s][r].train_money<<" 元"<<endl;
	cout<<endl;
	system("pause");
}
//最少时间——飞机
void Graph::C_time_plane()
{	
	system("cls");//清屏
	cout<<"           最少时间——飞机\n";
	int i,j,k;
	Print_c();//显示城市
	cout<<"请输入出发城市名称：";
	string start_name=PD_cityname();
	int s=Find_cityname(start_name);
	cout<<"请输入抵达城市名称：";
	string reach_name=PD_cityname();
	int r=Find_cityname(reach_name);
	if (s<0 || r<0 || s==r)
	{
		SetColor(13,15);
		cout<<"输入错误！\n\a"<<endl;
		SetColor(0,15);
		system("pause");
		Counsel();
	}
	for(i=0;i<vexnum;i++)
		for(j=0;j<vexnum;j++)
		{
			temp[i][j]=arc[i][j];
			Path[i][j]=-1;
		}
	for(k=0; k<vexnum; k++)
		for(i=0; i<vexnum; i++)
			for(j=0; j<vexnum; j++)
			{
				if(temp[i][k].plane_time+temp[k][j].plane_time < temp[i][j].plane_time)
				{
					temp[i][j].plane_time = temp[i][k].plane_time+temp[k][j].plane_time;
					Path[i][j]=k;
				}
			}
	cout<<endl;
	cout<<vex[s].name<<" 到 "<<vex[r].name<<" 的飞机时间最少的路线为 :\n";
	SetColor(0,3);
	cout<<setiosflags(ios::left)<<setw(20)<<"航班"<<setw(20)<<"出发城市"<<setw(20)<<"抵达城市"<<setw(15)<<"出发时间"<<setw(15)<<"抵达时间"<<setw(15)<<"时间"<<endl;
	SetColor(0,15);
	Pathcount[0]=s;
	z=1;
	Find_pass(s,r);
	Pathcount[z]=r;
	if(arc[Pathcount[0]][Pathcount[1]].plane_time == INF)
	{
		cout<<" 没有此路线 !"<<endl;
		Sleep(1000);
		Counsel();
	}
	for(i=0;i<z;i++)
	{
		string start_time = Time_conversion(arc[Pathcount[i]][Pathcount[i+1]].plane_start_time);
		string reach_time = Time_conversion(arc[Pathcount[i]][Pathcount[i+1]].plane_reach_time);
		cout<<setiosflags(ios::left)
			<<setw(20)<<arc[Pathcount[i]][Pathcount[i+1]].plane_num
			<<setw(20)<<vex[Pathcount[i]].name
			<<setw(20)<<vex[Pathcount[i+1]].name
			<<setw(15)<<start_time
			<<setw(15)<<reach_time
			<<setw(15)<<temp[Pathcount[i]][Pathcount[i+1]].plane_time<<endl;
	}
	cout<<" 最少时间为 :"<<temp[s][r].plane_time<<" 元"<<endl;
	cout<<endl;
	system("pause");
}
//最少时间——列车
void Graph::C_time_train()
{	
	system("cls");//清屏
	cout<<"           最少时间——列车\n";
	int i,j,k;
	Print_c();//显示城市
	cout<<"请输入出发城市名称：";
	string start_name=PD_cityname();
	int s=Find_cityname(start_name);
	cout<<"请输入抵达城市名称：";
	string reach_name=PD_cityname();
	int r=Find_cityname(reach_name);
	if (s<0 || r<0 || s==r)
	{
		SetColor(13,15);
		cout<<"输入错误！\n\a"<<endl;
		SetColor(0,15);
		system("pause");
		Counsel();
	}
	for(i=0;i<vexnum;i++)
		for(j=0;j<vexnum;j++)
		{
			temp[i][j]=arc[i][j];
			Path[i][j]=-1;
		}
	for(k=0; k<vexnum; k++)
		for(i=0; i<vexnum; i++)
			for(j=0; j<vexnum; j++)
			{
				if(temp[i][k].train_time+temp[k][j].train_time < temp[i][j].train_time)
				{
					temp[i][j].train_time = temp[i][k].train_time+temp[k][j].train_time;
					Path[i][j]=k;
				}
			}
	cout<<endl;
	cout<<vex[s].name<<" 到 "<<vex[r].name<<" 的列车时间最少的路线为 :\n";
	SetColor(0,3);
	cout<<setiosflags(ios::left)<<setw(20)<<"车次"<<setw(20)<<"出发城市"<<setw(20)<<"抵达城市"<<setw(15)<<"出发时间"<<setw(15)<<"抵达时间"<<setw(15)<<"时间"<<endl;
	SetColor(0,15);
	Pathcount[0]=s;
	z=1;
	Find_pass(s,r);
	Pathcount[z]=r;
	if(arc[Pathcount[0]][Pathcount[1]].train_time == INF)
	{
		cout<<" 没有此路线 !"<<endl;
		Sleep(1000);
		Counsel();
	}
	for(i=0;i<z;i++)
	{
		string start_time = Time_conversion(arc[Pathcount[i]][Pathcount[i+1]].train_start_time);
		string reach_time = Time_conversion(arc[Pathcount[i]][Pathcount[i+1]].train_reach_time);
		cout<<setiosflags(ios::left)
			<<setw(20)<<arc[Pathcount[i]][Pathcount[i+1]].train_num
			<<setw(20)<<vex[Pathcount[i]].name
			<<setw(20)<<vex[Pathcount[i+1]].name
			<<setw(15)<<start_time
			<<setw(15)<<reach_time
			<<setw(15)<<temp[Pathcount[i]][Pathcount[i+1]].train_time<<endl;
	}
	cout<<" 最少时间为 :"<<temp[s][r].train_time<<" 元"<<endl;
	cout<<endl;
	system("pause");
}
//最少中转——飞机
void Graph::C_change_plane()
{	
	system("cls");//清屏
	cout<<"           最少中转——飞机\n";
	int i,j,k;
	Print_c();//显示城市
	cout<<"请输入出发城市名称：";
	string start_name=PD_cityname();
	int s=Find_cityname(start_name);
	cout<<"请输入抵达城市名称：";
	string reach_name=PD_cityname();
	int r=Find_cityname(reach_name);
	if (s<0 || r<0 || s==r)
	{
		SetColor(13,15);
		cout<<"输入错误！\n\a"<<endl;
		SetColor(0,15);
		system("pause");
		Counsel();
	}
	for(i=0;i<vexnum;i++)
		for(j=0;j<vexnum;j++)
		{
			temp[i][j]=arc[i][j];
			Path[i][j]=-1;
		}
	for(k=0; k<vexnum; k++)
		for(i=0; i<vexnum; i++)
			for(j=0; j<vexnum; j++)
			{
				if(temp[i][k].plane_change+temp[k][j].plane_change < temp[i][j].plane_change)
				{
					temp[i][j].plane_change = temp[i][k].plane_change+temp[k][j].plane_change;
					Path[i][j]=k;
				}
			}
	cout<<endl;
	cout<<vex[s].name<<" 到 "<<vex[r].name<<" 的飞机中转最少的路线为 :\n";
	SetColor(0,3);
	cout<<setiosflags(ios::left)<<setw(20)<<"航班"<<setw(20)<<"出发城市"<<setw(20)<<"抵达城市"<<setw(15)<<"出发时间"<<setw(15)<<"抵达时间"<<endl;
	SetColor(0,15);
	Pathcount[0]=s;
	z=1;
	Find_pass(s,r);
	Pathcount[z]=r;
	if(arc[Pathcount[0]][Pathcount[1]].plane_money == INF)
	{
		cout<<" 没有此路线 !"<<endl;
		Sleep(1000);
		Counsel();
	}
	for(i=0;i<z;i++)
	{
		string start_time = Time_conversion(arc[Pathcount[i]][Pathcount[i+1]].plane_start_time);
		string reach_time = Time_conversion(arc[Pathcount[i]][Pathcount[i+1]].plane_reach_time);
		cout<<setiosflags(ios::left)
			<<setw(20)<<arc[Pathcount[i]][Pathcount[i+1]].plane_num
			<<setw(20)<<vex[Pathcount[i]].name
			<<setw(20)<<vex[Pathcount[i+1]].name
			<<setw(15)<<start_time
			<<setw(15)<<reach_time<<endl;
	}
	cout<<" 最少中转为 :"<<temp[s][r].plane_change-1<<" 次"<<endl;
	cout<<endl;
	system("pause");
}
//最少中转——列车
void Graph::C_change_train()
{		
	system("cls");//清屏
	cout<<"           最少中转——列车\n";
	int i,j,k;
	Print_c();//显示城市
	cout<<"请输入出发城市名称：";
	string start_name=PD_cityname();
	int s=Find_cityname(start_name);
	cout<<"请输入抵达城市名称：";
	string reach_name=PD_cityname();
	int r=Find_cityname(reach_name);
	if (s<0 || r<0 || s==r)
	{
		SetColor(13,15);
		cout<<"输入错误！\n\a"<<endl;
		SetColor(0,15);
		system("pause");
		Counsel();
	}
	for(i=0;i<vexnum;i++)
		for(j=0;j<vexnum;j++)
		{
			temp[i][j]=arc[i][j];
			Path[i][j]=-1;
		}
	for(k=0; k<vexnum; k++)
		for(i=0; i<vexnum; i++)
			for(j=0; j<vexnum; j++)
			{
				if(temp[i][k].train_change+temp[k][j].train_change < temp[i][j].train_change)
				{
					temp[i][j].train_change = temp[i][k].train_change+temp[k][j].train_change;
					Path[i][j]=k;
				}
			}
	cout<<endl;
	cout<<vex[s].name<<" 到 "<<vex[r].name<<" 的列车中转最少的路线为 :\n";
	SetColor(0,3);
	cout<<setiosflags(ios::left)<<setw(20)<<"车次"<<setw(20)<<"出发城市"<<setw(20)<<"抵达城市"<<setw(15)<<"出发时间"<<setw(15)<<"抵达时间"<<endl;
	SetColor(0,15);
	Pathcount[0]=s;
	z=1;
	Find_pass(s,r);
	Pathcount[z]=r;
	if(arc[Pathcount[0]][Pathcount[1]].train_money == INF)
	{
		cout<<" 没有此路线 !"<<endl;
		Sleep(1000);
		Counsel();
	}
	for(i=0;i<z;i++)
	{
		string start_time = Time_conversion(arc[Pathcount[i]][Pathcount[i+1]].train_start_time);
		string reach_time = Time_conversion(arc[Pathcount[i]][Pathcount[i+1]].train_reach_time);
		cout<<setiosflags(ios::left)
			<<setw(20)<<arc[Pathcount[i]][Pathcount[i+1]].train_num
			<<setw(20)<<vex[Pathcount[i]].name
			<<setw(20)<<vex[Pathcount[i+1]].name
			<<setw(15)<<start_time
			<<setw(15)<<reach_time<<endl;
	}
	cout<<" 最少中转为 :"<<temp[s][r].train_change-1<<" 次"<<endl;
	cout<<endl;
	system("pause");
}
//查找并记录节点
void Graph::Find_pass(int i,int j)
{ 
	if(Path[i][j]!=-1)
	{
		Find_pass(i,Path[i][j]);
		Pathcount[z]=Path[i][j];
		z++;
		Find_pass(Path[i][j],j);
	}
}
//查找城市名称（判断是否存在该城市）
int Graph::Find_cityname(string name)
{
	for(int i=0; i<vexnum; i++)
	{
		if(vex[i].name == name)
			return i;
	}
	return -1;
}
//输入时间
tm Graph::Time_cin()
{
	tm time = {0};
	int hour,min;
	hour = PD_hour();
	min = PD_min();
	time.tm_hour=hour;
	time.tm_min=min;
	return time;
}
//时间差
int Graph::Time_diff(tm start,tm reach)
{
	int m1,m2,m;
	m1=start.tm_hour*60+start.tm_min;
	m2=reach.tm_hour*60+reach.tm_min;
	if(m2<m1)
	{
		m2=m2+60*24;
		m=m2-m1;
	}
	else m=m2-m1;
	return m;
}
//时间格式转换
string Graph::Time_conversion(tm time)
{
	char c[20];
	strftime( c , 20 , " %H : %M " , &time );
	string str_time=c;
	return str_time;
}
//判断分
int Graph::PD_min()
{
	int ju,end;
	int min;
	cout<<"分：";
	ju=1;
	while(ju)
	{
		ju=0;
		cin>>min;
		end=getchar();//判断输入结束符是否为回车
		if(end!=10)
		{
			ju=1;
			SetColor(4,15);
			cout<<" 错误（请输入数字）\a\n";
			SetColor(0,15);
			cout<<" 重新输入：";
			cin.clear();//恢复输入状态
			cin.sync();//清空缓冲区
		}
		else if(min<0 || min>60)
		{
			ju=1;
			SetColor(4,15);
			cout<<" 错误（范围为0—60）\a\n";
			SetColor(0,15);
			cout<<" 请重新输入：";
			cin.clear();//恢复输入状态
			cin.sync();//清空缓冲区
		}
	}
	return min;
}
//判断时
int Graph::PD_hour()
{
	int ju,end;
	int hour;
	cout<<"时：";
	ju=1;
	while(ju)
	{
		ju=0;
		cin>>hour;
		end=getchar();//判断输入结束符是否为回车
		if(end!=10)
		{
			ju=1;
			SetColor(4,15);
			cout<<" 错误（请输入数字）\a\n";
			SetColor(0,15);
			cout<<" 重新输入：";
			cin.clear();//恢复输入状态
			cin.sync();//清空缓冲区
		}
		else if(hour<1 || hour>24)
		{
			ju=1;
			SetColor(4,15);
			cout<<" 错误（范围为1—24）\a\n";
			SetColor(0,15);
			cout<<" 请重新输入：";
			cin.clear();//恢复输入状态
			cin.sync();//清空缓冲区
		}
	}
	return hour;
}
//判断输入城市名称
string Graph::PD_cityname()
{
	int ju=1,end,i,j;
	string cityname;
	while(ju)
	{
		ju=0;
		cin>>cityname;
		end=getchar();//判断输入结束符是否为回车
		if(end!=10)
		{
			ju=1;
			SetColor(4,15);
			cout<<" 错误\a\n";
			SetColor(0,15);
			cout<<"重新输入城市名称：";		
			cin.clear();//恢复输入状态
			cin.sync();//清空缓冲区
		}
		else
		{
			for(i=0,j=1;i<=60;i=i+2,j=j+2)
			//将汉字转化为两个ASCII值
			{
				int a=cityname[i];
				int b=cityname[j];
				if(a<-91||a>0)
			//如果第一个ASCII值不是负值或者小于-91则不是汉字。
				{
					ju=1;
					SetColor(4,15);
					cout<<" 错误（请输入汉字）\a\n";
					SetColor(0,15);
					cout<<"重新输入城市名称：";
					cin.clear();//恢复输入状态
					cin.sync();//清空缓冲区
					break;
				}
			}
			if(cityname.size()>12)//限制输入的汉字数量
			{
				ju=1;
				SetColor(4,15);
				cout<<"错误（请不要超过六个字）\a\n";
				SetColor(0,15);
				cout<<" 重新输入城市名称：";
				cin.clear();//恢复输入状态
				cin.sync();//清空缓冲区
				continue;
			}
		}
	}
	return cityname;
}
//判断极限(0-max)
int Graph::PD_max(int max)
{
	int ju=1,end;
	int i;
	while(ju)
	{
		ju=0;
		cin>>i;
		end=getchar();//判断输入结束符是否为回车
		if(end!=10 || i<0 || i>max)
		{
			ju=1;
			SetColor(4,15);
			cout<<"输入错误！（范围：0-"<<max<<"）\a\n";
			SetColor(0,15);
			cout<<"请重新输入：";
			cin.clear();//恢复输入状态
			cin.sync();//清空缓冲区
		}
	}
	return i;
}
//判断是否输入整型数字
int Graph::PD_int()
{
	int ju=1,end;
	int in;
	while(ju)
	{
		ju=0;
		cin>>in;
		end=getchar();//判断输入结束符是否为回车
		if(end!=10)
		{
			ju=1;
			SetColor(4,15);
			cout<<"输入错误！请重新输入：";
			SetColor(0,15);
			cin.clear();//恢复输入状态
			cin.sync();//清空缓冲区
		}
		if(in<0||in>99999999)
		{
			ju=1;
			SetColor(4,15);
			cout<<" 错误（范围为0—99999999）\a\n";
			SetColor(0,15);
			cout<<" 请重新输入：";
			cin.clear();//恢复输入状态
			cin.sync();//清空缓冲区
		}
	}
	return in;
}
//判断是否继续输入
char Graph::PD_flag()
{
	int ju=1,end;
	char flag;
	while(ju)
	{
		ju=0;
		cin>>flag;
		end=getchar();
		if(end!=10)
		{
			ju=1;
			SetColor(4,15);
			cout<<"输入错误！请重新输入：";
			SetColor(0,15);
			cin.clear();//恢复输入状态
			cin.sync();//清空缓冲区
		}
		else if(flag!='Y'||flag!='y')
			break;
	}
	return flag;
}
int main()
{
	//音乐
	mciSendString("open 钢琴曲-大鱼.mp3 alias music",0,0,0);
	mciSendString("play music repeat",0,0,0);

	initgraph(980,500);//创建一个宽640,长480的窗口
	setbkcolor(WHITE);//设置当前绘图背景色
	cleardevice();//清屏

	settextcolor(BLACK);//设置当前文字颜色
	setbkmode(TRANSPARENT);//设置背景混合模式
	settextstyle(20, 0, "楷体");//设置当前字体样式

	outtextxy(340, 250, "~~~     欢迎进入交通咨询系统!     ~~~");
	Sleep(1000);
	cleardevice();//清屏
	closegraph();//关闭图形环境

	Graph base;
	system("color F0");
	base.Init();

	system("pause");
	return 0;
}

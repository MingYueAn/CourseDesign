#include<iostream>
#include<string>
#include<iomanip>
#include<fstream>
#include<windows.h>
#include<graphics.h>//ͼ�ο�
#include<mmsystem.h>//����
#include<time.h>//strftime( c , 20 , " %H : %M " , &time );
#include<conio.h>//ch[i]=getch();
using namespace std;
#pragma comment (lib,"winmm.lib")

const int MAX=20;
const int INF=2323;
ifstream inFile;
ofstream outFile;

//ͼ����洢����Ϣ
class GraphVex
{
	friend class Graph;
	string name;//��������
};

//ͼ�ߴ洢����Ϣ
class GraphArc
{
	friend class Graph;
	//�ɻ�
	int plane_num;//���
	tm plane_start_time;//����ʱ��
	tm plane_reach_time;//�ִ�ʱ��
	int plane_money;//����
	int plane_time;//����ʱ��
	int plane_change;//��ת��
	//�г�
	int train_num;//���
	tm train_start_time;//����ʱ��
	tm train_reach_time;//�ִ�ʱ��
	int train_money;//����
	int train_time;//����ʱ��
	int train_change;//��ת��
};

class Graph
{
private:
	int vexnum;
	int p_arcnum;
	int t_arcnum;
	GraphVex vex[MAX];		//����
	GraphArc arc[MAX][MAX];	//����
public:
	Graph();
	void Init();			//��ʼ�����ļ������̣�
private:
	//����
	void Main_interface();	//������
	void Show_traffic();	//ȫ����ͨ
	void Counsel();			//��ѯ����
	void Administrator();	//����Ա����
	void Administrator(char ch[10]);	//����Ա�������
	void City_Face();	//������Ϣ
	void Plane_Face();	//�ɻ�����
	void Train_Face();	//�г�ʱ��
	//����1�������С�����ӡ�ɾ������ʾ
	void City_addition();
	void City_remove();
	void City_show();
	//����2�����ɻ����ࡪ����ӡ�ɾ������ʾ
	void Plane_addition();
	void Plane_remove();
	void Plane_show();
	//����3�����г�ʱ�̡�����ӡ�ɾ������ʾ
	void Train_addition();
	void Train_remove();
	void Train_show();

	void Print_c();		//������ʾ
	void Print_p();		//�ɻ���ʾ
	void Print_t();		//������ʾ
	void Save_c();		//�������
	void File();		//�ļ�����
	void Create_city();		//���������ļ�
	void Create_plane();	//�����ɻ��ļ�
	void Create_train();	//�����г��ļ�

	void C_money_plane();	//���ٷ��á����ɻ�
	void C_money_train();	//���ٷ��á����г�
	void C_time_plane();	//����ʱ�䡪���ɻ�
	void C_time_train();	//����ʱ�䡪���г�
	void C_change_plane();	//������ת�����ɻ�
	void C_change_train();	//������ת�����г�

	void Find_pass(int i,int j);//���Ҳ���¼���
	int Find_cityname(string name);//���ҳ������ƣ��ж��Ƿ���ڸó��У�
	tm Time_cin();//����ʱ��
	int Time_diff(tm start,tm reach);//ʱ���
	string Time_conversion(tm time);//ʱ���ʽת��
	int PD_min();//�жϷ�
	int PD_hour();//�ж�ʱ
	string PD_cityname();//�ж������������
	int PD_max(int max);//�жϼ���(0-max)
	int PD_int();//�ж��Ƿ�������������
	char PD_flag();//�ж��Ƿ��������
};

int z=0;
int Path[MAX][MAX];
int Pathcount[MAX];
GraphArc temp[MAX][MAX];

//�ı���ɫ
void SetColor(unsigned short ForeColor,unsigned short BackGroundColor)
{
	HANDLE hCon=GetStdHandle(STD_OUTPUT_HANDLE);
	SetConsoleTextAttribute(hCon,(ForeColor%17)|(BackGroundColor%16*16));
}
//��
void PrintLine()
{
	cout<<"------------------------------------------------------------------------------------------------------------------------\n";
}
//���캯����ʼ��
Graph::Graph()
{
	int vexnum = 0;
	int p_arcnum = 0;
	int t_arcnum = 0;
	//����ȫ����ʼ��ΪINF
	for(int i=0; i<MAX; i++)
		for(int j=0; j<MAX; j++)
			arc[i][j].plane_num=arc[i][j].train_num=
			arc[i][j].plane_start_time.tm_hour=arc[i][j].train_start_time.tm_hour=
			arc[i][j].plane_reach_time.tm_min=arc[i][j].train_reach_time.tm_min=
			arc[i][j].plane_money=arc[i][j].train_money=
			arc[i][j].plane_time=arc[i][j].train_time=
			arc[i][j].plane_change=arc[i][j].train_change=INF;
}
//��ʼ��
void Graph::Init()
{
	system("cls");
	initgraph(980,500);//����һ����640,��480�Ĵ���
	setbkcolor(WHITE);//���õ�ǰ��ͼ����ɫ
	cleardevice();//����

	settextcolor(BLACK);//���õ�ǰ������ɫ
	settextstyle(20, 0, "����");//���õ�ǰ������ʽ
	setbkmode(TRANSPARENT);//���ñ������ģʽ
	outtextxy(410, 15, "��ѡ���ʼ����ʽ��");
	outtextxy(410, 200, "ע�⣺�ļ�����Ϊ�գ�");

	setfillcolor(LIGHTBLUE);//���õ�ǰ�����ɫ
	solidrectangle(440, 55, 560, 90);	//��������(�ޱ߿�)
	solidrectangle(440, 105, 560, 140);	//��������(�ޱ߿�)
	solidrectangle(440, 155, 560, 190);	//��������(�ޱ߿�)

	settextcolor(WHITE);//���õ�ǰ������ɫ
	settextstyle(20, 0, "����");//���õ�ǰ������ʽ
	setbkmode(TRANSPARENT);//���ñ������ģʽ
	outtextxy(450, 60, "1 �ļ�");
	outtextxy(450, 110, "2 ����");
	outtextxy(450, 160, "����������");

	MOUSEMSG m;
	while (1)//һֱ��ȡ�����Ϣ
	{
		m = GetMouseMsg();
		//1 �ļ�
		if(m.x>=440 && m.x<=560 && m.y>=55 && m.y<=90)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-5,55-5,560+5,90+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
			{
				closegraph();//�ر�ͼ�λ���
				File();//�ļ�����
				cout<<"�ļ�������ɣ���������������"<<endl;
				system("pause");
				Main_interface();
			}
		}
		//2 ����
		else if(m.x>=440 && m.x<=560 && m.y>=105 && m.y<=140)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-5,105-5,560+5,140+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
			{
				closegraph();//�ر�ͼ�λ���
				Create_city();	//���������ļ�
				Create_plane();	//�����ɻ��ļ�
				Create_train();	//�����г��ļ�
				cout<<"����������ɣ���������������"<<endl;
				system("pause");
				Main_interface();
			}
		}
		//����������
		else if(m.x>=440 && m.x<=560 && m.y>=155 && m.y<=190)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-5,155-5,560+5,190+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
				Main_interface();
		}
		//���û�е�������
		else
		{
			setlinecolor(WHITE);
			rectangle(440-5,55-5,560+5,90+5);
			rectangle(440-5,105-5,560+5,140+5);
			rectangle(440-5,155-5,560+5,190+5);
			rectangle(435-5,205-5,565+5,240+5);
		}
	}
	closegraph();//�ر�ͼ�λ���
}
//������
void Graph::Main_interface()
{
	initgraph(980,500);//����һ����640,��480�Ĵ���
	setbkcolor(WHITE);//���õ�ǰ��ͼ����ɫ
	cleardevice();//����

	setlinecolor(RGB(225,128,0));	// ���õ�ǰ������ɫ
	setfillcolor(RGB(255,255,128));	// ���õ�ǰ�����ɫ
	fillrectangle(400, 10, 600, 40);// ��������(�б߿�)
	settextcolor(BLACK);//���õ�ǰ������ɫ
	settextstyle(20, 0, "����");//���õ�ǰ������ʽ
	setbkmode(TRANSPARENT);//���ñ������ģʽ
	outtextxy(475, 15, "������");
	settextstyle(15, 0, "����");//���õ�ǰ������ʽ
	outtextxy(600, 450, "��ϵͳ��л�����������");


	setfillcolor(LIGHTBLUE);//���õ�ǰ�����ɫ
	solidrectangle(440, 55, 560, 90);	//��������(�ޱ߿�)
	solidrectangle(440, 105, 560, 140);	//��������(�ޱ߿�)
	solidrectangle(440, 155, 560, 190);	//��������(�ޱ߿�)
	solidrectangle(440, 205, 560, 240);	//��������(�ޱ߿�)

	settextcolor(WHITE);//���õ�ǰ������ɫ
	settextstyle(20, 0, "����");//���õ�ǰ������ʽ
	setbkmode(TRANSPARENT);//���ñ������ģʽ
	outtextxy(450, 60, "1 ��ʾ��ͨ");
	outtextxy(450, 110, "2 ��ͨ��ѯ");
	outtextxy(450, 160, "3 ��Ϣ����");
	outtextxy(450, 210, "0 �˳�ϵͳ");

	MOUSEMSG m;
	while (1)//һֱ��ȡ�����Ϣ
	{
		m = GetMouseMsg();
		//1 ��ʾ��ͨ
		if(m.x>=440 && m.x<=560 && m.y>=55 && m.y<=90)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-5,55-5,560+5,90+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
			{
				closegraph();//�ر�ͼ�λ���
				Show_traffic();//��ʾ��ͨ
			}
		}
		//2 ��ͨ��ѯ
		else if(m.x>=440 && m.x<=560 && m.y>=105 && m.y<=140)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-5,105-5,560+5,140+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
			{
				closegraph();//�ر�ͼ�λ���
				Counsel();//��ͨ��ѯ
			}
		}
		//3 ��Ϣ����
		else if(m.x>=440 && m.x<=560 && m.y>=155 && m.y<=190)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-5,155-5,560+5,190+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
			{
				closegraph();//�ر�ͼ�λ���
				char ch[10];
				Administrator(ch);//��Ϣ����
			}
		}
		//0 �˳�ϵͳ
		else if(m.x>=440 && m.x<=560 && m.y>=205 && m.y<=240)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-5,205-5,560+5,240+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
			{
				cleardevice();//����
				settextcolor(BLACK);//���õ�ǰ������ɫ
				outtextxy(340, 250, "~~~     ��лʹ�ñ�ϵͳ!     ~~~");
				Sleep(1000);
				exit(0);
			}
		}
		//���û�е�������
		else
		{
			setlinecolor(WHITE);
			rectangle(440-5,55-5,560+5,90+5);
			rectangle(440-5,105-5,560+5,140+5);
			rectangle(440-5,155-5,560+5,190+5);
			rectangle(440-5,205-5,560+5,240+5);
		}
	}
	closegraph();//�ر�ͼ�λ���
}
//ȫ����ͨ		
void Graph::Show_traffic()
{
	system("cls");//����
	cout<<"                                              **********������Ϣ**********\n";
	Print_c();//��ʾ����
	system("pause");
	cout<<"                                              **********�ɻ������**********\n";
	Print_p();//��ʾ�ɻ�
	system("pause");
	cout<<"                                              **********�г�ʱ��ͼ**********\n";
	Print_t();//��ʾ�г�
	system("pause");
	Main_interface();
}
//��ѯ����	
void Graph::Counsel()
{	
	initgraph(980,500);//����һ����640,��480�Ĵ���
	setbkcolor(WHITE);//���õ�ǰ��ͼ����ɫ
	cleardevice();//����

	setlinecolor(RGB(225,128,0));	// ���õ�ǰ������ɫ
	setfillcolor(RGB(255,255,128));	// ���õ�ǰ�����ɫ
	fillrectangle(400, 10, 600, 40);// ��������(�б߿�)
	settextcolor(BLACK);//���õ�ǰ������ɫ
	settextstyle(20, 0, "����");//���õ�ǰ������ʽ
	setbkmode(TRANSPARENT);//���ñ������ģʽ
	outtextxy(465, 15, "��ͨ��ѯ");

	//���ѡ��
	setfillcolor(LIGHTBLUE);//���õ�ǰ�����ɫ
	solidrectangle(440, 55, 560, 90);	//��������(�ޱ߿�)
	solidrectangle(440, 105, 560, 140);	//��������(�ޱ߿�)
	solidrectangle(440, 155, 560, 190);	//��������(�ޱ߿�)
	solidrectangle(440, 205, 560, 240);	//��������(�ޱ߿�)
	settextcolor(WHITE);//���õ�ǰ������ɫ
	settextstyle(20, 0, "����");//���õ�ǰ������ʽ
	setbkmode(TRANSPARENT);//���ñ������ģʽ
	outtextxy(450, 60, "1 ���ٷ���");
	outtextxy(450, 110, "2 ����ʱ��");
	outtextxy(450, 160, "3 ������ת");
	outtextxy(450, 210, "����������");

	//��ͨ��ʽ
	setfillcolor(RGB(255,128,128));//���õ�ǰ�����ɫ
	solidrectangle(440+200, 55-20, 560+175, 90-25);		//��������(�ޱ߿�)
	solidrectangle(440+200, 105-20, 560+175, 140-25);	//��������(�ޱ߿�)
	solidrectangle(440-175, 105-20, 560-200, 140-25);	//��������(�ޱ߿�)
	solidrectangle(440-175, 155-20, 560-200, 190-25);	//��������(�ޱ߿�)
	solidrectangle(440+200, 155-20, 560+175, 190-25);	//��������(�ޱ߿�)
	solidrectangle(440+200, 205-20, 560+175, 240-25);	//��������(�ޱ߿�)
	settextcolor(WHITE);//���õ�ǰ������ɫ
	settextstyle(15, 0, "����");//���õ�ǰ������ʽ
	setbkmode(TRANSPARENT);//���ñ������ģʽ
	outtextxy(450+200, 60-20, "1 = �ɻ�");
	outtextxy(450+200, 110-20, "2 = ����");
	outtextxy(450-175, 110-20, "1 = �ɻ�");
	outtextxy(450-175, 160-20, "2 = ����");
	outtextxy(450+200, 160-20, "1 = �ɻ�");
	outtextxy(450+200, 210-20, "2 = ����");

	//����
	line(440+200, 72.5-20, 560, 72.5);//��һ����
	line(440+200, 122.5-20, 560, 72.5);//��һ����
	line(560-200, 122.5-20, 440, 122.5);//��һ����
	line(560-200, 172.5-20, 440, 122.5);//��һ����
	line(440+200, 172.5-20, 560, 172.5);//��һ����
	line(440+200, 222.5-20, 560, 172.5);//��һ����

	MOUSEMSG m;
	while (1)//һֱ��ȡ�����Ϣ
	{
		m = GetMouseMsg();
		//1 ���ٷ��á����ɻ�
		if(m.x>=440+200 && m.x<=560+175 && m.y>=55-20 && m.y<=90-25)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440+200-5,55-20-5,560+175+5,90-25+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
			{
				closegraph();//�ر�ͼ�λ���
				C_money_plane();//���ٷ��á����ɻ�
				Counsel();
			}
		}
		//1 ���ٷ��á����г�
		else if(m.x>=440+200 && m.x<=560+175 && m.y>=105-20 && m.y<=140-25)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440+200-5,105-20-5,560+175+5,140-25+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
			{
				closegraph();//�ر�ͼ�λ���
				C_money_train();//���ٷ��á����г�
				Counsel();
			}
		}
		//2 ����ʱ�䡪���ɻ�
		else if(m.x>=440-175 && m.x<=560-200 && m.y>=105-20 && m.y<=140-25)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-175-5,105-20-5,560-200+5,140-25+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
			{
				closegraph();//�ر�ͼ�λ���
				C_time_plane();//����ʱ�䡪���ɻ�
				Counsel();
			}
		}
		//2 ����ʱ�䡪���г�
		else if(m.x>=440-175 && m.x<=560-200 && m.y>=155-20 && m.y<=190-25)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-175-5,155-20-5,560-200+5,190-25+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
			{
				closegraph();//�ر�ͼ�λ���
				C_time_train();//����ʱ�䡪���г�
				Counsel();
			}
		}
		//3 ������ת�����ɻ�
		else if(m.x>=440+200 && m.x<=560+175 && m.y>=155-20 && m.y<=190-25)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440+200-5,155-20-5,560+175+5,190-25+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
			{
				closegraph();//�ر�ͼ�λ���
				C_change_plane();//������ת�����ɻ�
				Counsel();
			}
		}
		//3 ������ת�����г�
		else if(m.x>=440+200 && m.x<=560+175 && m.y>=205-20 && m.y<=240-25)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440+200-5,205-20-5,560+175+5,240-25+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
			{
				closegraph();//�ر�ͼ�λ���
				C_change_train();//������ת�����г�
				Counsel();
			}
		}
		//����������
		else if(m.x>=440 && m.x<=560 && m.y>=205 && m.y<=240)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-5,205-5,560+5,240+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
				Main_interface();
		}
		//���û�е�������
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
	closegraph();//�ر�ͼ�λ���
}
//����Ա�������
void Graph::Administrator(char ch[10])
{
	system("cls");//����
	cout<<"\n\n\n\n\n\n\n\n                            ���룺123456\n";
	cout<<"                            ���������Ա���룺";
	for(int i=0; i<10; i++)
	{
		ch[i]=getch();//��������
		if(ch[i]=='\r') break;//���س�����������
		cout<<"*";//��*�������������
	}
	ch[i]='\0';//����ַ���������
	string str=ch;
	if(str == "123456")//�ж������Ƿ���ȷ
		Administrator();//��ȷ�������Ա����
	else
	{
		system("cls");
		SetColor(4,15);
		cout<<"\n\n\n\n\n\n\n\n                            �����������\a";
		SetColor(0,15);
		Sleep(1000);//��������ʾ��Ȼ�󷵻�������
		Main_interface();
	}
}
//����Ա����
void Graph::Administrator()
{
	initgraph(980,500);//����һ����640,��480�Ĵ���
	setbkcolor(WHITE);//���õ�ǰ��ͼ����ɫ
	cleardevice();//����

	setlinecolor(RGB(225,128,0));	// ���õ�ǰ������ɫ
	setfillcolor(RGB(255,255,128));	// ���õ�ǰ�����ɫ
	fillrectangle(400, 10, 600, 40);// ��������(�б߿�)
	settextcolor(BLACK);//���õ�ǰ������ɫ
	settextstyle(20, 0, "����");//���õ�ǰ������ʽ
	setbkmode(TRANSPARENT);//���ñ������ģʽ
	outtextxy(465, 15, "��Ϣ����");


	setfillcolor(LIGHTBLUE);//���õ�ǰ�����ɫ
	solidrectangle(440, 55, 560, 90);	//��������(�ޱ߿�)
	solidrectangle(440, 105, 560, 140);	//��������(�ޱ߿�)
	solidrectangle(440, 155, 560, 190);	//��������(�ޱ߿�)
	solidrectangle(440, 205, 560, 240);	//��������(�ޱ߿�)
	solidrectangle(440, 255, 560, 290);	//��������(�ޱ߿�)

	settextcolor(WHITE);//���õ�ǰ������ɫ
	settextstyle(20, 0, "����");//���õ�ǰ������ʽ
	setbkmode(TRANSPARENT);//���ñ������ģʽ
	outtextxy(450, 60, "1 ��ʼ��");
	outtextxy(450, 110, "2 ������Ϣ");
	outtextxy(450, 160, "3 �ɻ�����");
	outtextxy(450, 210, "4 �г�ʱ��");
	outtextxy(450, 260, "����������");

	MOUSEMSG m;
	while (1)//һֱ��ȡ�����Ϣ
	{
		m = GetMouseMsg();
		//1 ��ʼ��
		if(m.x>=440 && m.x<=560 && m.y>=55 && m.y<=90)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-5,55-5,560+5,90+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
			{
				closegraph();//�ر�ͼ�λ���
				Init();//��ʼ��
			}
		}
		//2 ������Ϣ
		else if(m.x>=440 && m.x<=560 && m.y>=105 && m.y<=140)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-5,105-5,560+5,140+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
			{
				closegraph();//�ر�ͼ�λ���
				City_Face();//������Ϣ
			}
		}
		//3 �ɻ�����
		else if(m.x>=440 && m.x<=560 && m.y>=155 && m.y<=190)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-5,155-5,560+5,190+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
			{
				closegraph();//�ر�ͼ�λ���
				Plane_Face();//�ɻ�����
			}
		}
		//4 �г�ʱ��
		else if(m.x>=440 && m.x<=560 && m.y>=205 && m.y<=240)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-5,205-5,560+5,240+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
			{
				closegraph();//�ر�ͼ�λ���
				Train_Face();//�г�ʱ��
			}
		}
		//����������
		else if(m.x>=440 && m.x<=560 && m.y>=255 && m.y<=290)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-5,255-5,560+5,290+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
				Main_interface();
		}
		//���û�е�������
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
	closegraph();//�ر�ͼ�λ���
}
//������Ϣ
void Graph::City_Face()	
{	
	initgraph(980,500);//����һ����640,��480�Ĵ���
	setbkcolor(WHITE);//���õ�ǰ��ͼ����ɫ
	cleardevice();//����

	setlinecolor(RGB(225,128,0));	// ���õ�ǰ������ɫ
	setfillcolor(RGB(255,255,128));	// ���õ�ǰ�����ɫ
	fillrectangle(400, 10, 600, 40);// ��������(�б߿�)
	settextcolor(BLACK);//���õ�ǰ������ɫ
	settextstyle(20, 0, "����");//���õ�ǰ������ʽ
	setbkmode(TRANSPARENT);//���ñ������ģʽ
	outtextxy(465, 15, "������Ϣ");


	setfillcolor(LIGHTBLUE);//���õ�ǰ�����ɫ
	solidrectangle(440, 55, 560, 90);	//��������(�ޱ߿�)
	solidrectangle(440, 105, 560, 140);	//��������(�ޱ߿�)
	solidrectangle(440, 155, 560, 190);	//��������(�ޱ߿�)
	solidrectangle(440, 205, 560, 240);	//��������(�ޱ߿�)
	solidrectangle(435, 255, 565, 290);	//��������(�ޱ߿�)
	solidrectangle(435, 305, 565, 340);	//��������(�ޱ߿�)

	settextcolor(WHITE);//���õ�ǰ������ɫ
	settextstyle(20, 0, "����");//���õ�ǰ������ʽ
	setbkmode(TRANSPARENT);//���ñ������ģʽ
	outtextxy(450, 60, "1 ��ӳ���");
	outtextxy(450, 110, "2 ɾ������");
	outtextxy(450, 160, "3 ��ʾ����");
	outtextxy(450, 210, "4 ����");
	outtextxy(450, 260, "����������");
	outtextxy(440, 310, "������һ����");

	MOUSEMSG m;
	while (1)//һֱ��ȡ�����Ϣ
	{
		m = GetMouseMsg();
		//1 ��ӳ���
		if(m.x>=440 && m.x<=560 && m.y>=55 && m.y<=90)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-5,55-5,560+5,90+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
			{
				closegraph();//�ر�ͼ�λ���
				City_addition();//��ӳ���
			}
		}
		//2 ɾ������
		else if(m.x>=440 && m.x<=560 && m.y>=105 && m.y<=140)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-5,105-5,560+5,140+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
			{
				closegraph();//�ر�ͼ�λ���
				City_remove();//ɾ������
			}
		}
		//3 ��ʾ����
		else if(m.x>=440 && m.x<=560 && m.y>=155 && m.y<=190)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-5,155-5,560+5,190+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
			{
				closegraph();//�ر�ͼ�λ���
				City_show();//��ʾ����
			}
		}
		//4 ����
		else if(m.x>=440 && m.x<=560 && m.y>=205 && m.y<=240)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-5,205-5,560+5,240+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
			{
				closegraph();//�ر�ͼ�λ���
				char flag;
				system("cls");
				Print_c();//��ʾ����
				cout<<"ȷ�ϱ��������Ϣ��y|Y������������棺";
				flag=PD_flag();
				if(flag=='y'||flag=='Y')Save_c();//������Ϣ
				else City_Face();
			}
		}
		//����������
		else if(m.x>=440 && m.x<=560 && m.y>=255 && m.y<=290)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-5,255-5,560+5,290+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
				Main_interface();
		}
		//������һ����
		else if(m.x>=435 && m.x<=565 && m.y>=305 && m.y<=340)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(435-5,305-5,565+5,340+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
				Administrator();
		}
		//���û�е�������
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
	closegraph();//�ر�ͼ�λ���
}
//�ɻ�����
void Graph::Plane_Face()		
{
	initgraph(980,500);//����һ����640,��480�Ĵ���
	setbkcolor(WHITE);//���õ�ǰ��ͼ����ɫ
	cleardevice();//����

	setlinecolor(RGB(225,128,0));	// ���õ�ǰ������ɫ
	setfillcolor(RGB(255,255,128));	// ���õ�ǰ�����ɫ
	fillrectangle(400, 10, 600, 40);// ��������(�б߿�)
	settextcolor(BLACK);//���õ�ǰ������ɫ
	settextstyle(20, 0, "����");//���õ�ǰ������ʽ
	setbkmode(TRANSPARENT);//���ñ������ģʽ
	outtextxy(465, 15, "�ɻ�����");


	setfillcolor(LIGHTBLUE);//���õ�ǰ�����ɫ
	solidrectangle(440, 55, 560, 90);	//��������(�ޱ߿�)
	solidrectangle(440, 105, 560, 140);	//��������(�ޱ߿�)
	solidrectangle(440, 155, 560, 190);	//��������(�ޱ߿�)
	solidrectangle(440, 205, 560, 240);	//��������(�ޱ߿�)
	solidrectangle(435, 255, 565, 290);	//��������(�ޱ߿�)

	settextcolor(WHITE);//���õ�ǰ������ɫ
	settextstyle(20, 0, "����");//���õ�ǰ������ʽ
	setbkmode(TRANSPARENT);//���ñ������ģʽ
	outtextxy(450, 60, "1 ��ӷɻ�");
	outtextxy(450, 110, "2 ɾ���ɻ�");
	outtextxy(450, 160, "3 ��ʾ�ɻ�");
	outtextxy(450, 210, "����������");
	outtextxy(440, 260, "������һ����");

	MOUSEMSG m;
	while (1)//һֱ��ȡ�����Ϣ
	{
		m = GetMouseMsg();
		//1 ��ӷɻ�
		if(m.x>=440 && m.x<=560 && m.y>=55 && m.y<=90)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-5,55-5,560+5,90+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
			{
				closegraph();//�ر�ͼ�λ���
				Plane_addition();//��ӷɻ�
			}
		}
		//2 ɾ���ɻ�
		else if(m.x>=440 && m.x<=560 && m.y>=105 && m.y<=140)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-5,105-5,560+5,140+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
			{
				closegraph();//�ر�ͼ�λ���
				Plane_remove();//ɾ���ɻ�
			}
		}
		//3 ��ʾ�ɻ�
		else if(m.x>=440 && m.x<=560 && m.y>=155 && m.y<=190)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-5,155-5,560+5,190+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
			{
				closegraph();//�ر�ͼ�λ���
				Plane_show();//��ʾ�ɻ�
			}
		}
		//����������
		else if(m.x>=440 && m.x<=560 && m.y>=205 && m.y<=240)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-5,205-5,560+5,240+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
				Main_interface();
		}
		//������һ����
		else if(m.x>=435 && m.x<=565 && m.y>=255 && m.y<=290)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(435-5,255-5,565+5,290+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
				Administrator();
		}
		//���û�е�������
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
	closegraph();//�ر�ͼ�λ���
}
//�г�ʱ��
void Graph::Train_Face()		
{	
	initgraph(980,500);//����һ����640,��480�Ĵ���
	setbkcolor(WHITE);//���õ�ǰ��ͼ����ɫ
	cleardevice();//����

	setlinecolor(RGB(225,128,0));	// ���õ�ǰ������ɫ
	setfillcolor(RGB(255,255,128));	// ���õ�ǰ�����ɫ
	fillrectangle(400, 10, 600, 40);// ��������(�б߿�)
	settextcolor(BLACK);//���õ�ǰ������ɫ
	settextstyle(20, 0, "����");//���õ�ǰ������ʽ
	setbkmode(TRANSPARENT);//���ñ������ģʽ
	outtextxy(465, 15, "�г�ʱ�� ");


	setfillcolor(LIGHTBLUE);//���õ�ǰ�����ɫ
	solidrectangle(440, 55, 560, 90);	//��������(�ޱ߿�)
	solidrectangle(440, 105, 560, 140);	//��������(�ޱ߿�)
	solidrectangle(440, 155, 560, 190);	//��������(�ޱ߿�)
	solidrectangle(440, 205, 560, 240);	//��������(�ޱ߿�)
	solidrectangle(435, 255, 565, 290);	//��������(�ޱ߿�)

	settextcolor(WHITE);//���õ�ǰ������ɫ
	settextstyle(20, 0, "����");//���õ�ǰ������ʽ
	setbkmode(TRANSPARENT);//���ñ������ģʽ
	outtextxy(450, 60, "1 ����г�");
	outtextxy(450, 110, "2 ɾ���г�");
	outtextxy(450, 160, "3 ��ʾ�г�");
	outtextxy(450, 210, "����������");
	outtextxy(440, 260, "������һ����");

	MOUSEMSG m;
	while (1)//һֱ��ȡ�����Ϣ
	{
		m = GetMouseMsg();
		//1 ����г�
		if(m.x>=440 && m.x<=560 && m.y>=55 && m.y<=90)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-5,55-5,560+5,90+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
			{
				closegraph();//�ر�ͼ�λ���
				Train_addition();//����г�
			}
		}
		//2 ɾ���г�
		else if(m.x>=440 && m.x<=560 && m.y>=105 && m.y<=140)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-5,105-5,560+5,140+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
			{
				closegraph();//�ر�ͼ�λ���
				Train_remove();//ɾ���г�
			}
		}
		//3 ��ʾ�г�
		else if(m.x>=440 && m.x<=560 && m.y>=155 && m.y<=190)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-5,155-5,560+5,190+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
			{
				closegraph();//�ر�ͼ�λ���
				Train_show();//��ʾ�г�
			}
		}
		//����������
		else if(m.x>=440 && m.x<=560 && m.y>=205 && m.y<=240)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(440-5,205-5,560+5,240+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
				Main_interface();
		}
		//������һ����
		else if(m.x>=440 && m.x<=560 && m.y>=255 && m.y<=290)
		{
			setlinecolor(RED);//���õ�ǰ������ɫ
			rectangle(435-5,255-5,565+5,290+5);
			if(m.uMsg == WM_LBUTTONDOWN)//����������
				Administrator();
		}
		//���û�е�������
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
	closegraph();//�ر�ͼ�λ���
}
//����1�������С�����ӡ�ɾ������ʾ
void Graph::City_addition()
{
	char flag='y';//�ж��Ƿ�������
	while(flag=='Y'||flag=='y')
	{
		system("cls");//����
		cout<<"                      ***********��ӳ�����Ϣ*************\n"; 
		Print_c();//��ʾ����
		cout<<"          ������������ƣ�";
		vex[vexnum].name = PD_cityname();
		if(Find_cityname(vex[vexnum].name)!=-1)
		{
			SetColor(13,15);
			cout<<"�ó����Ѵ��ڣ�"<<endl;
			SetColor(0,15);
			system("pause");
			City_addition();
		}
		vexnum++;
		PrintLine();
		cout<<"          ��ӳ�����Ϣ���"<<endl;
		cout<<"          �����������밴y|Y:";
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
		system("cls");//����
		cout<<"                      ***********ɾ��������Ϣ*************\n"; 
		Print_c();//��ʾ����
		cout<<"          ��������Ҫɾ���ĳ������ƣ�";
		string name = PD_cityname();
		int n = Find_cityname(name);//�������Ƶ��±�
		if(n==-1)
		{
			SetColor(13,15);
			cout<<"�ó��в����ڣ�"<<endl;
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
		cout<<"          ɾ��������Ϣ���"<<endl;
		cout<<"          �������ɾ���밴y|Y:";
		flag=PD_flag();
	}
	system("pause");
	City_Face();
}
void Graph::City_show()
{		
	system("cls");//����
	cout<<"                      ***********��ʾ������Ϣ*************\n"; 
	Print_c();//��ʾ����
	cout<<"��ʾ������Ϣ���"<<endl;
	system("pause");
	City_Face();
}

//����2�����ɻ����ࡪ����ӡ��޸ġ�ɾ������ʾ
void Graph::Plane_addition()
{
	char flag='y';
	while(flag=='Y'||flag=='y')
	{
		system("cls");//����
		cout<<"                      ***********��ӷɻ�����*************\n"; 
		Print_c();//��ʾ����
		cout<<"����������������ƣ�";
		string start_name=PD_cityname();
		int s=Find_cityname(start_name);
		cout<<"������ִ�������ƣ�";
		string reach_name=PD_cityname();
		int r=Find_cityname(reach_name);
		if (s<0 || r<0 || s==r)
		{
			SetColor(13,15);
			cout<<"�������\n\a"<<endl;
			SetColor(0,15);
			system("pause");
			Plane_addition();
		}
		cout<<"���������ʱ�䣺\n";
		arc[s][r].plane_start_time = Time_cin();//����ʱ��
		cout<<"������ִ�ʱ�䣺\n";
		arc[s][r].plane_reach_time = Time_cin();//����ʱ��
		cout<<"������ɻ������ţ�";
		arc[s][r].plane_num = PD_int();//���
		cout<<"������ɻ�������ã�";
		arc[s][r].plane_money = PD_int();//����
		arc[s][r].plane_time = Time_diff(arc[s][r].plane_start_time , arc[s][r].plane_reach_time);//ʱ���
		arc[s][r].plane_change=1;
		p_arcnum++;
		PrintLine();
		cout<<"          ��ӷɻ��������"<<endl;
		cout<<"          �������¼���밴y|Y:";
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
		system("cls");//����
		cout<<"                      ***********ɾ��������Ϣ*************\n"; 
		Print_p();
		cout<<"          ��������Ҫɾ���ĺ�����ţ�";
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
		cout<<"          ȷ��ɾ���밴y|Y��";
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
						cout<<"          ɾ���ɻ��������"<<endl;
					}
					if(flag!='a')
					{
						cout<<" û�д˺��� !���������� !"<<endl;
					}
			cout<<"          �������ɾ���밴y|Y:";
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
	cout<<"							 �ɻ������\n";
	Print_p();
	system("pause");
	Plane_Face();
}

//����3�����г�ʱ�̡�����ӡ��޸ġ�ɾ������ʾ
void Graph::Train_addition()
{
	char flag='y';
	while(flag=='Y'||flag=='y')
	{
		system("cls");//����
		cout<<"                      ***********����г�����*************\n"; 
		Print_c();//��ʾ����
		cout<<"����������������ƣ�";
		string start_name=PD_cityname();
		int s=Find_cityname(start_name);
		cout<<"������ִ�������ƣ�";
		string reach_name=PD_cityname();
		int r=Find_cityname(reach_name);
		if (s<0 || r<0 || s==r)
		{
			SetColor(13,15);
			cout<<"�������\n\a"<<endl;
			SetColor(0,15);
			system("pause");
			Train_addition();
		}
		cout<<"���������ʱ�䣺\n";
		arc[s][r].train_start_time = Time_cin();//����ʱ��
		cout<<"������ִ�ʱ�䣺\n";
		arc[s][r].train_reach_time = Time_cin();//����ʱ��
		cout<<"�������г����α�ţ�";
		arc[s][r].train_num = PD_int();//���
		cout<<"�������г����η��ã�";
		arc[s][r].train_money = PD_int();//����
		arc[s][r].train_time = Time_diff(arc[s][r].train_start_time , arc[s][r].train_reach_time);//ʱ���
		arc[s][r].train_change=1;
		p_arcnum++;
		PrintLine();
		cout<<"          ����г��������"<<endl;
		cout<<"          �������¼���밴y|Y:";
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
		system("cls");//����
		cout<<"                      ***********ɾ��������Ϣ*************\n"; 
		Print_t();
		cout<<"          ��������Ҫɾ���ĳ�����ţ�";
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
		cout<<"          ȷ��ɾ���밴y|Y��";
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
						cout<<"          ɾ���г��������"<<endl;
					}
					if(flag!='a')
					{
						cout<<" û�д˳��� !���������� !"<<endl;
					}
			cout<<"          �������ɾ���밴y|Y:";
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
	cout<<"							 �г�ʱ�̱�\n";
	Print_t();
	system("pause");
	Train_Face();
}
//������ʾ
void Graph::Print_c()
{
	if(vexnum==0)
	{
		cout<<" �޳��� !"<<endl;
		cout<<" ����������������档����"<<endl;
		system("pause");
		Main_interface();
	}
	cout<<"��������"<<vexnum<<endl;
	//�������
	for(int i=0; i<vexnum; i++)
	{
		SetColor(0,3);
		cout<<setiosflags(ios::left)<<i<<"_"<<setw(10)<<vex[i].name;
		SetColor(0,15);
	}
	cout<<endl;
	PrintLine();//����
}
//�ɻ���ʾ
void Graph::Print_p()
{
	int i,j;
	cout<<"          ���к�������"<<p_arcnum<<endl;
	SetColor(0,3);
	cout<<setiosflags(ios::left)<<setw(20)<<"���"<<setw(20)<<"��������"<<setw(20)<<"�ִ����"<<setw(15)<<"����ʱ��"<<setw(15)<<"�ִ�ʱ��"<<setw(15)<<"Ʊ��"<<setw(15)<<"ʱ��"<<endl;
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
//�г���ʾ
void Graph::Print_t()
{
	int i,j;
	cout<<"          ���г�������"<<t_arcnum<<endl;
	SetColor(0,3);
	cout<<setiosflags(ios::left)<<setw(20)<<"���"<<setw(20)<<"��������"<<setw(20)<<"�ִ����"<<setw(15)<<"����ʱ��"<<setw(15)<<"�ִ�ʱ��"<<setw(15)<<"Ʊ��"<<setw(15)<<"ʱ��"<<endl;
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
//���������Ϣ
void Graph::Save_c()
{
//������Ϣ
	outFile.open("������Ϣnew.txt",ios::out | ios::in | ios::app);//���ļ����ڶ���д
	if(!outFile)
	{
		cout<<"������Ϣ.txt���ļ�ʧ�ܡ�\n";
		Sleep(1000);
		Init();
	}
	outFile<<vexnum<<endl;
	for(int i=0; i<vexnum; i++)
		outFile<<vex[i].name<<endl;
	outFile.close();//�ر��ļ�
	remove("������Ϣ.txt");//ɾ���ļ�
	rename("������Ϣnew.txt","������Ϣ.txt");//�ļ�����
	cout<<"          ���������Ϣ���"<<endl;
}
//�ļ�����
void Graph::File()
{
	//������Ϣ
	inFile.open("������Ϣ.txt",ios::in);
	if(!inFile)
	{
		cout<<"������Ϣ.txt���ļ�ʧ�ܡ�\n";
		Sleep(1000);
		Init();
	}
	inFile.seekg(0,ios::end);   
	long size1 = inFile.tellg();      
	cout<< "������Ϣ.txt�ļ���СΪ��"<<size1<<" �ֽ�"<<endl;
	Sleep(1000);
	if(size1 <= 0)Init();

	inFile.seekg(ios::beg);//���¶�λ�ļ�λ��ָ�� ���ҷ���Ϊios::beg�������Ŀ�ͷ��ʼ��λ��
	inFile>>vexnum;
	for(int i=0; i<vexnum; i++)
	{
		inFile>>vex[i].name;
	}
	inFile.close();
	//�ɻ���Ϣ
	inFile.open("�ɻ���Ϣ.txt",ios::in);//���ļ����ڶ�
	if(!inFile)
	{
		cout<<"�ɻ���Ϣ.txt���ļ�ʧ�ܡ�\n";
		Sleep(1000);
		Init();
	}
	inFile.seekg(0,ios::end);   
	long size2 = inFile.tellg();      
	cout<< "�ɻ���Ϣ.txt�ļ���СΪ��"<<size2<<" �ֽ�"<<endl;
	Sleep(1000);
	if(size2 <= 0)Init();

	inFile.seekg(ios::beg);//���¶�λ�ļ�λ��ָ�� ���ҷ���Ϊios::beg�������Ŀ�ͷ��ʼ��λ��
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
	//�г���Ϣ
	inFile.open("�г���Ϣ.txt",ios::in);//���ļ����ڶ�
	if(!inFile)
	{
		cout<<"�г���Ϣ.txt���ļ�ʧ�ܡ�\n";
		Sleep(1000);
		Init();
	}
	inFile.seekg(0,ios::end);   
	long size3 = inFile.tellg();      
	cout<< "�г���Ϣ.txt�ļ���СΪ��"<<size3<<" �ֽ�"<<endl;
	Sleep(1000);
	if(size3 <= 0)Init();

	inFile.seekg(ios::beg);//���¶�λ�ļ�λ��ָ�� ���ҷ���Ϊios::beg�������Ŀ�ͷ��ʼ��λ��
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
//���������ļ�
void Graph::Create_city()
{
	system("cls");//����
	outFile.open("������Ϣ.txt",ios::out);
	if(!outFile)
	{//���ļ�ʧ�����½��г�ʼ��
		cout<<"������Ϣ.txt���ļ�ʧ�ܡ�\n";
		Sleep(1000);
		Init();
	}
	cout<<"����������������Ϣ�ļ�������\n";
	cout<<"��������и�����";
	vexnum = PD_max(MAX);//���붥������������MAX��
	outFile<<vexnum<<endl;
	for(int i=0; i<vexnum; i++)
	{
		cout<<"��"<<i+1<<"����";
		vex[i].name=PD_cityname();
		outFile<<vex[i].name<<endl;
	}
	outFile.close();
	cout<<"          ���������ļ����"<<endl;
	Sleep(1000);
}
//�����ɻ��ļ�
void Graph::Create_plane()
{
	int i;
	system("cls");
	outFile.open("�ɻ���Ϣ.txt",ios::out);
	if(!outFile)
	{
		cout<<"�ɻ���Ϣ.txt���ļ�ʧ�ܡ�\n";
		Sleep(1000);
		Init();
	}
	//���뺽������
	cout<<"�����������ɻ�������Ϣ�ļ�������\n";
	cout<<"��������"<<vexnum<<endl;
	cout<<"�����뺽��������";
	int m = vexnum*(vexnum - 1);
	p_arcnum = PD_max(m);//���������������MAX*(MAX-1)��
	outFile<<p_arcnum<<endl;
	//���������Ϣ
	for(i=0; i<p_arcnum; i++)
	{
		system("cls");
		cout<<"�����������ɻ�������Ϣ�ļ�������\n";
		Print_c();//��ʾ����
		cout<<"���������������Ϣ��\n";
		cout<<"��"<<i+1<<"����\n";
		cout<<"����������������ƣ�";
		string start_name=PD_cityname();
		int s=Find_cityname(start_name);
		cout<<"������ִ�������ƣ�";
		string reach_name=PD_cityname();
		int r=Find_cityname(reach_name);
		if (s<0 || r<0 || s==r)
		{
			SetColor(13,15);
			cout<<"�������\n\a"<<endl;
			SetColor(0,15);
			system("pause");
			Init();
		}
		cout<<"���������ʱ�䣺\n";
		arc[s][r].plane_start_time = Time_cin();//����ʱ��
		cout<<"������ִ�ʱ�䣺\n";
		arc[s][r].plane_reach_time = Time_cin();//����ʱ��
		cout<<"������ɻ������ţ�";
		arc[s][r].plane_num = PD_int();
		cout<<"������ɻ�������ã�";
		arc[s][r].plane_money = PD_int();//����
		arc[s][r].plane_time = Time_diff(arc[s][r].plane_start_time , arc[s][r].plane_reach_time);//ʱ���
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
	cout<<"          �����ɻ��ļ����"<<endl;
	Sleep(1000);
}
//�����г��ļ�
void Graph::Create_train()
{	
	int i;
	system("cls");
	outFile.open("�г���Ϣ.txt",ios::out);
	if(!outFile)
	{
		cout<<"�г���Ϣ.txt���ļ�ʧ�ܡ�\n";
		Sleep(1000);
		Init();
	}
	//���복������
	cout<<"�����������г�������Ϣ�ļ�������\n";
	cout<<"��������"<<vexnum<<endl;
	cout<<"�����복��������";
	int m = vexnum*(vexnum - 1);
	t_arcnum = PD_max(m);//���������������MAX*(MAX-1)��
	outFile<<t_arcnum<<endl;
	//���������Ϣ
	for(i=0; i<t_arcnum; i++)
	{
		system("cls");
		cout<<"�����������г�������Ϣ�ļ�������\n";
		Print_c();//��ʾ����
		cout<<"���������������Ϣ��\n";
		cout<<"��"<<i+1<<"����\n";
		cout<<"����������������ƣ�";
		string start_name=PD_cityname();
		int s=Find_cityname(start_name);
		cout<<"������ִ�������ƣ�";
		string reach_name=PD_cityname();
		int r=Find_cityname(reach_name);
		if (s<0 || r<0 || s==r)
		{
			SetColor(13,15);
			cout<<"�������\n\a"<<endl;
			SetColor(0,15);
			system("pause");
			Init();
		}
		cout<<"���������ʱ�䣺\n";
		arc[s][r].train_start_time = Time_cin();//����ʱ��
		cout<<"������ִ�ʱ�䣺\n";
		arc[s][r].train_reach_time = Time_cin();//����ʱ��
		cout<<"�������г����α�ţ�";
		arc[s][r].train_num = PD_int();
		cout<<"�������г����η��ã�";
		arc[s][r].train_money = PD_int();//����
		arc[s][r].train_time = Time_diff(arc[s][r].train_start_time , arc[s][r].train_reach_time);//ʱ���
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
	cout<<"          �����г��ļ����"<<endl;
	Sleep(1000);
}
//���ٷ��á����ɻ�
void Graph::C_money_plane()
{
	system("cls");//����
	cout<<"           ���ٷ��á����ɻ�\n";
	int i,j,k;
	Print_c();//��ʾ����
	cout<<"����������������ƣ�";
	string start_name=PD_cityname();
	int s=Find_cityname(start_name);
	cout<<"������ִ�������ƣ�";
	string reach_name=PD_cityname();
	int r=Find_cityname(reach_name);
	if (s<0 || r<0 || s==r)
	{
		SetColor(13,15);
		cout<<"�������\n\a"<<endl;
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
	cout<<vex[s].name<<" �� "<<vex[r].name<<" �ķɻ��������ٵ�·��Ϊ :\n";
	SetColor(0,3);
	cout<<setiosflags(ios::left)<<setw(20)<<"����"<<setw(20)<<"��������"<<setw(20)<<"�ִ����"<<setw(15)<<"����ʱ��"<<setw(15)<<"�ִ�ʱ��"<<setw(15)<<"Ʊ��"<<endl;
	SetColor(0,15);
	Pathcount[0]=s;
	z=1;
	Find_pass(s,r);
	Pathcount[z]=r;
	if(arc[Pathcount[0]][Pathcount[1]].plane_money == INF)
	{
		cout<<" û�д�·�� !"<<endl;
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
	cout<<" ���ٻ���Ϊ :"<<temp[s][r].plane_money<<" Ԫ"<<endl;
	cout<<endl;
	system("pause");
}
//���ٷ��á����г�
void Graph::C_money_train()
{	
	system("cls");//����
	cout<<"           ���ٷ��á����г�\n";
	int i,j,k;
	Print_c();//��ʾ����
	cout<<"����������������ƣ�";
	string start_name=PD_cityname();
	int s=Find_cityname(start_name);
	cout<<"������ִ�������ƣ�";
	string reach_name=PD_cityname();
	int r=Find_cityname(reach_name);
	if (s<0 || r<0 || s==r)
	{
		SetColor(13,15);
		cout<<"�������\n\a"<<endl;
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
	cout<<vex[s].name<<" �� "<<vex[r].name<<" ���г��������ٵ�·��Ϊ :\n";
	SetColor(0,3);
	cout<<setiosflags(ios::left)<<setw(20)<<"����"<<setw(20)<<"��������"<<setw(20)<<"�ִ����"<<setw(15)<<"����ʱ��"<<setw(15)<<"�ִ�ʱ��"<<setw(15)<<"Ʊ��"<<endl;
	SetColor(0,15);
	Pathcount[0]=s;
	z=1;
	Find_pass(s,r);
	Pathcount[z]=r;
	if(arc[Pathcount[0]][Pathcount[1]].train_money == INF)
	{
		cout<<" û�д�·�� !"<<endl;
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
	cout<<" ���ٻ���Ϊ :"<<temp[s][r].train_money<<" Ԫ"<<endl;
	cout<<endl;
	system("pause");
}
//����ʱ�䡪���ɻ�
void Graph::C_time_plane()
{	
	system("cls");//����
	cout<<"           ����ʱ�䡪���ɻ�\n";
	int i,j,k;
	Print_c();//��ʾ����
	cout<<"����������������ƣ�";
	string start_name=PD_cityname();
	int s=Find_cityname(start_name);
	cout<<"������ִ�������ƣ�";
	string reach_name=PD_cityname();
	int r=Find_cityname(reach_name);
	if (s<0 || r<0 || s==r)
	{
		SetColor(13,15);
		cout<<"�������\n\a"<<endl;
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
	cout<<vex[s].name<<" �� "<<vex[r].name<<" �ķɻ�ʱ�����ٵ�·��Ϊ :\n";
	SetColor(0,3);
	cout<<setiosflags(ios::left)<<setw(20)<<"����"<<setw(20)<<"��������"<<setw(20)<<"�ִ����"<<setw(15)<<"����ʱ��"<<setw(15)<<"�ִ�ʱ��"<<setw(15)<<"ʱ��"<<endl;
	SetColor(0,15);
	Pathcount[0]=s;
	z=1;
	Find_pass(s,r);
	Pathcount[z]=r;
	if(arc[Pathcount[0]][Pathcount[1]].plane_time == INF)
	{
		cout<<" û�д�·�� !"<<endl;
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
	cout<<" ����ʱ��Ϊ :"<<temp[s][r].plane_time<<" Ԫ"<<endl;
	cout<<endl;
	system("pause");
}
//����ʱ�䡪���г�
void Graph::C_time_train()
{	
	system("cls");//����
	cout<<"           ����ʱ�䡪���г�\n";
	int i,j,k;
	Print_c();//��ʾ����
	cout<<"����������������ƣ�";
	string start_name=PD_cityname();
	int s=Find_cityname(start_name);
	cout<<"������ִ�������ƣ�";
	string reach_name=PD_cityname();
	int r=Find_cityname(reach_name);
	if (s<0 || r<0 || s==r)
	{
		SetColor(13,15);
		cout<<"�������\n\a"<<endl;
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
	cout<<vex[s].name<<" �� "<<vex[r].name<<" ���г�ʱ�����ٵ�·��Ϊ :\n";
	SetColor(0,3);
	cout<<setiosflags(ios::left)<<setw(20)<<"����"<<setw(20)<<"��������"<<setw(20)<<"�ִ����"<<setw(15)<<"����ʱ��"<<setw(15)<<"�ִ�ʱ��"<<setw(15)<<"ʱ��"<<endl;
	SetColor(0,15);
	Pathcount[0]=s;
	z=1;
	Find_pass(s,r);
	Pathcount[z]=r;
	if(arc[Pathcount[0]][Pathcount[1]].train_time == INF)
	{
		cout<<" û�д�·�� !"<<endl;
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
	cout<<" ����ʱ��Ϊ :"<<temp[s][r].train_time<<" Ԫ"<<endl;
	cout<<endl;
	system("pause");
}
//������ת�����ɻ�
void Graph::C_change_plane()
{	
	system("cls");//����
	cout<<"           ������ת�����ɻ�\n";
	int i,j,k;
	Print_c();//��ʾ����
	cout<<"����������������ƣ�";
	string start_name=PD_cityname();
	int s=Find_cityname(start_name);
	cout<<"������ִ�������ƣ�";
	string reach_name=PD_cityname();
	int r=Find_cityname(reach_name);
	if (s<0 || r<0 || s==r)
	{
		SetColor(13,15);
		cout<<"�������\n\a"<<endl;
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
	cout<<vex[s].name<<" �� "<<vex[r].name<<" �ķɻ���ת���ٵ�·��Ϊ :\n";
	SetColor(0,3);
	cout<<setiosflags(ios::left)<<setw(20)<<"����"<<setw(20)<<"��������"<<setw(20)<<"�ִ����"<<setw(15)<<"����ʱ��"<<setw(15)<<"�ִ�ʱ��"<<endl;
	SetColor(0,15);
	Pathcount[0]=s;
	z=1;
	Find_pass(s,r);
	Pathcount[z]=r;
	if(arc[Pathcount[0]][Pathcount[1]].plane_money == INF)
	{
		cout<<" û�д�·�� !"<<endl;
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
	cout<<" ������תΪ :"<<temp[s][r].plane_change-1<<" ��"<<endl;
	cout<<endl;
	system("pause");
}
//������ת�����г�
void Graph::C_change_train()
{		
	system("cls");//����
	cout<<"           ������ת�����г�\n";
	int i,j,k;
	Print_c();//��ʾ����
	cout<<"����������������ƣ�";
	string start_name=PD_cityname();
	int s=Find_cityname(start_name);
	cout<<"������ִ�������ƣ�";
	string reach_name=PD_cityname();
	int r=Find_cityname(reach_name);
	if (s<0 || r<0 || s==r)
	{
		SetColor(13,15);
		cout<<"�������\n\a"<<endl;
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
	cout<<vex[s].name<<" �� "<<vex[r].name<<" ���г���ת���ٵ�·��Ϊ :\n";
	SetColor(0,3);
	cout<<setiosflags(ios::left)<<setw(20)<<"����"<<setw(20)<<"��������"<<setw(20)<<"�ִ����"<<setw(15)<<"����ʱ��"<<setw(15)<<"�ִ�ʱ��"<<endl;
	SetColor(0,15);
	Pathcount[0]=s;
	z=1;
	Find_pass(s,r);
	Pathcount[z]=r;
	if(arc[Pathcount[0]][Pathcount[1]].train_money == INF)
	{
		cout<<" û�д�·�� !"<<endl;
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
	cout<<" ������תΪ :"<<temp[s][r].train_change-1<<" ��"<<endl;
	cout<<endl;
	system("pause");
}
//���Ҳ���¼�ڵ�
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
//���ҳ������ƣ��ж��Ƿ���ڸó��У�
int Graph::Find_cityname(string name)
{
	for(int i=0; i<vexnum; i++)
	{
		if(vex[i].name == name)
			return i;
	}
	return -1;
}
//����ʱ��
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
//ʱ���
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
//ʱ���ʽת��
string Graph::Time_conversion(tm time)
{
	char c[20];
	strftime( c , 20 , " %H : %M " , &time );
	string str_time=c;
	return str_time;
}
//�жϷ�
int Graph::PD_min()
{
	int ju,end;
	int min;
	cout<<"�֣�";
	ju=1;
	while(ju)
	{
		ju=0;
		cin>>min;
		end=getchar();//�ж�����������Ƿ�Ϊ�س�
		if(end!=10)
		{
			ju=1;
			SetColor(4,15);
			cout<<" �������������֣�\a\n";
			SetColor(0,15);
			cout<<" �������룺";
			cin.clear();//�ָ�����״̬
			cin.sync();//��ջ�����
		}
		else if(min<0 || min>60)
		{
			ju=1;
			SetColor(4,15);
			cout<<" ���󣨷�ΧΪ0��60��\a\n";
			SetColor(0,15);
			cout<<" ���������룺";
			cin.clear();//�ָ�����״̬
			cin.sync();//��ջ�����
		}
	}
	return min;
}
//�ж�ʱ
int Graph::PD_hour()
{
	int ju,end;
	int hour;
	cout<<"ʱ��";
	ju=1;
	while(ju)
	{
		ju=0;
		cin>>hour;
		end=getchar();//�ж�����������Ƿ�Ϊ�س�
		if(end!=10)
		{
			ju=1;
			SetColor(4,15);
			cout<<" �������������֣�\a\n";
			SetColor(0,15);
			cout<<" �������룺";
			cin.clear();//�ָ�����״̬
			cin.sync();//��ջ�����
		}
		else if(hour<1 || hour>24)
		{
			ju=1;
			SetColor(4,15);
			cout<<" ���󣨷�ΧΪ1��24��\a\n";
			SetColor(0,15);
			cout<<" ���������룺";
			cin.clear();//�ָ�����״̬
			cin.sync();//��ջ�����
		}
	}
	return hour;
}
//�ж������������
string Graph::PD_cityname()
{
	int ju=1,end,i,j;
	string cityname;
	while(ju)
	{
		ju=0;
		cin>>cityname;
		end=getchar();//�ж�����������Ƿ�Ϊ�س�
		if(end!=10)
		{
			ju=1;
			SetColor(4,15);
			cout<<" ����\a\n";
			SetColor(0,15);
			cout<<"��������������ƣ�";		
			cin.clear();//�ָ�����״̬
			cin.sync();//��ջ�����
		}
		else
		{
			for(i=0,j=1;i<=60;i=i+2,j=j+2)
			//������ת��Ϊ����ASCIIֵ
			{
				int a=cityname[i];
				int b=cityname[j];
				if(a<-91||a>0)
			//�����һ��ASCIIֵ���Ǹ�ֵ����С��-91���Ǻ��֡�
				{
					ju=1;
					SetColor(4,15);
					cout<<" ���������뺺�֣�\a\n";
					SetColor(0,15);
					cout<<"��������������ƣ�";
					cin.clear();//�ָ�����״̬
					cin.sync();//��ջ�����
					break;
				}
			}
			if(cityname.size()>12)//��������ĺ�������
			{
				ju=1;
				SetColor(4,15);
				cout<<"�����벻Ҫ���������֣�\a\n";
				SetColor(0,15);
				cout<<" ��������������ƣ�";
				cin.clear();//�ָ�����״̬
				cin.sync();//��ջ�����
				continue;
			}
		}
	}
	return cityname;
}
//�жϼ���(0-max)
int Graph::PD_max(int max)
{
	int ju=1,end;
	int i;
	while(ju)
	{
		ju=0;
		cin>>i;
		end=getchar();//�ж�����������Ƿ�Ϊ�س�
		if(end!=10 || i<0 || i>max)
		{
			ju=1;
			SetColor(4,15);
			cout<<"������󣡣���Χ��0-"<<max<<"��\a\n";
			SetColor(0,15);
			cout<<"���������룺";
			cin.clear();//�ָ�����״̬
			cin.sync();//��ջ�����
		}
	}
	return i;
}
//�ж��Ƿ�������������
int Graph::PD_int()
{
	int ju=1,end;
	int in;
	while(ju)
	{
		ju=0;
		cin>>in;
		end=getchar();//�ж�����������Ƿ�Ϊ�س�
		if(end!=10)
		{
			ju=1;
			SetColor(4,15);
			cout<<"����������������룺";
			SetColor(0,15);
			cin.clear();//�ָ�����״̬
			cin.sync();//��ջ�����
		}
		if(in<0||in>99999999)
		{
			ju=1;
			SetColor(4,15);
			cout<<" ���󣨷�ΧΪ0��99999999��\a\n";
			SetColor(0,15);
			cout<<" ���������룺";
			cin.clear();//�ָ�����״̬
			cin.sync();//��ջ�����
		}
	}
	return in;
}
//�ж��Ƿ��������
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
			cout<<"����������������룺";
			SetColor(0,15);
			cin.clear();//�ָ�����״̬
			cin.sync();//��ջ�����
		}
		else if(flag!='Y'||flag!='y')
			break;
	}
	return flag;
}
int main()
{
	//����
	mciSendString("open ������-����.mp3 alias music",0,0,0);
	mciSendString("play music repeat",0,0,0);

	initgraph(980,500);//����һ����640,��480�Ĵ���
	setbkcolor(WHITE);//���õ�ǰ��ͼ����ɫ
	cleardevice();//����

	settextcolor(BLACK);//���õ�ǰ������ɫ
	setbkmode(TRANSPARENT);//���ñ������ģʽ
	settextstyle(20, 0, "����");//���õ�ǰ������ʽ

	outtextxy(340, 250, "~~~     ��ӭ���뽻ͨ��ѯϵͳ!     ~~~");
	Sleep(1000);
	cleardevice();//����
	closegraph();//�ر�ͼ�λ���

	Graph base;
	system("color F0");
	base.Init();

	system("pause");
	return 0;
}

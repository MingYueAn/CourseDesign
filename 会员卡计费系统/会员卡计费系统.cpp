#include<iostream>
#include<string>
#include<iomanip>
#include<fstream>
#include<windows.h>
using namespace std;
//会员卡计费系统
typedef struct Member//会员信息 定义Member结构体类型--单链表结点类型
{
	char name[20];		//姓名
	char card[10];      //卡号
	char sex[4];        //性别
	int age;            //年龄
	double money;		//余额
	double addmoney;	//个人缴费总额
	double cost;		//个人消费总额
	char telephone[12];	//电话
	char gs[4];			//判断是否挂失
	char vip[6];		//判断是否为vip
	Member *next;//指向下一个结点的指针，即定义一个指针指向下一会员信息
}*M;//*Member

void printLine();	//线
void zhu_cai_dan();//主菜单
void Choose();		//菜单选择
//菜单选择中调用的函数声明
void deng_ji();   //成员登记
void xiou_gai();  //信息修改
void xu_fei();    //续费功能
void jie_suan();  //消费结算
void tui_ka();    //会员退卡
void tong_ji();   //统计功能
void bao_cun();   //保存信息
void lost_find_make();//挂失、解挂失、补办
void tui_chu();   //退出系统
//普通函数
void import();		//导入数据
M find(char*card);	//查找数据（卡号）
void tableHead();	//表头
void once(M p);		//显示一位会员的信息
void all(M p);		//显示全部会员信息
void px_addmoney();//1：按缴费总额排序
void px_cost();    //2：按累计消费总额排序
void tongji_back();//统计返回
//挂失、解挂失、补办界面调用的函数声明
void lostcard();//挂失
void findcard();//解挂失
void makecard();//补办
void pd_gs(M p);//判断是否挂失
//判断函数的声明
string pd_card(M p);		//卡号的判断
string pd_chinese(M p);		//汉字的判断
string pd_sex(M p);			//性别的判断
int pd_age(M p);			//年龄的判断
string pd_telephone(M p);	//电话号码的判断
double pd_double();	//判断是否输入数字（浮点型）
int pd_int();		//判断是否输入数字（整型）
char pd_fun();		//判断是否继续

M head=NULL,rear=NULL;//定义两个全局变量  head表示头指针  rear表示尾指针
fstream outFile,inFile;//定义两个全局变量  outFile写入文件  inFile读取文件

void SetColor(unsigned short ForeColor,unsigned short BackGroundColor)//改变颜色
{
	HANDLE hCon=GetStdHandle(STD_OUTPUT_HANDLE);
	SetConsoleTextAttribute(hCon,(ForeColor%17)|(BackGroundColor%16*16));
}

string pd_card(M p)//卡号判断
{
	int ju=1,end;
	while(ju)
	{
		ju=0;
		cin>>p->card;
		string st=p->card;//将卡号转换为string型
		end=getchar();//判断输入结束符是否为回车
		if(end!=10)
		{
			ju=1;
			SetColor(4,15);
			cout<<" 错误（请输入字母或数字，共九个）\a\n";
			SetColor(0,15);
			cout<<"重输卡号\r";
			cin.clear();//恢复输入状态
			cin.sync();//清空缓冲区
		}
		if(st.size()!=9)//限制输入九个
		{
			ju=1;
			SetColor(4,15);
			cout<<"错误（请输入字母或数字，共九个）\a\n";
			SetColor(0,15);
			cout<<" 重输卡号 \r";
			cin.clear();//恢复输入状态
			cin.sync();//清空缓冲区
			continue;
		}
		for(int i=0;i<12;i++)//对每一字符进行小数点判断
		{
			if((int)st[i]==46)//46为小数点
			{
				ju=1;
				SetColor(4,15);
				cout<<"错误（不可有小数点）\a\n";
				SetColor(0,15);
				cout<<" 重输卡号 \r";
				cin.clear();//恢复输入状态
				cin.sync();//清空缓冲区
				break;
			}
		}
	}
	return p->card;
}
string pd_chinese(M p)//汉字判断
{
	int ju=1,end,i,j;
	while(ju)
	{
		ju=0;
		cin>>p->name;
		string st=p->name;
		end=getchar();//判断输入结束符是否为回车
		if(end!=10)
		{
			ju=1;
			SetColor(4,15);
			cout<<" 错误\a\n";
			SetColor(0,15);
			cout<<"重新输入姓名：";		
			cin.clear();//恢复输入状态
			cin.sync();//清空缓冲区
		}
		else
		{
			for(i=0,j=1;i<=60;i=i+2,j=j+2)
			//将汉字转化为两个ASCII值
			{
				int a=st[i];
				int b=st[j];
				if(a<-91||a>0)
			//如果第一个ASCII值不是负值或者小于-91则不是汉字。
				{
					ju=1;
					SetColor(4,15);
					cout<<" 错误（请输入汉字）\a\n";
					SetColor(0,15);
					cout<<"重新输入姓名：";
					cin.clear();//恢复输入状态
					cin.sync();//清空缓冲区
					break;
				}
			}
			if(st.size()>12)//限制输入的汉字数量
			{
				ju=1;
				SetColor(4,15);
				cout<<"错误（请不要超过六个字）\a\n";
				SetColor(0,15);
				cout<<" 重新输入姓名：";
				cin.clear();//恢复输入状态
				cin.sync();//清空缓冲区
				continue;
			}
		}
	}
	return p->name;
}
string pd_sex(M p)//性别判断
{
	int ju=1,end;
	char* sex1="女";
	char* sex2="男";
	while(ju)
	{
		ju=0;
		cin>>p->sex;
		end=getchar();//判断输入结束符是否为回车
		if(end!=10)
		{
			ju=1;
			SetColor(4,15);
			cout<<"错误\a\n";
			SetColor(0,15);
			cout<<"重新输入性别：";
			cin.clear();//恢复输入状态
			cin.sync();//清空缓冲区
		}
		if(strcmp(sex1,p->sex)==0 || strcmp(sex2,p->sex)==0);
		else
		{
			ju=1;
			SetColor(4,15);
			cout<<"错误（请输入“男”或“女”）\a\n";
			SetColor(0,15);
			cout<<"重新输入性别：";
			cin.clear();//恢复输入状态
			cin.sync();//清空缓冲区
		}
	}
	return p->sex;
}
int pd_age(M p)//年龄判断
{
	int ju=1,end;
	while(ju)
	{
		ju=0;
		cin>>p->age;
		end=getchar();//判断输入结束符是否为回车
		if(end!=10)
		{
			ju=1;
			SetColor(4,15);
			cout<<" 错误（请输入数字）\a\n";
			SetColor(0,15);
			cout<<" 重新输入年龄：";
			cin.clear();//恢复输入状态
			cin.sync();//清空缓冲区
		}
		if(p->age<0 || p->age>150)//限制年龄范围
		{
			ju=1;
			SetColor(4,15);
			cout<<" 错误（年龄范围为0—150）\a\n";
			SetColor(0,15);
			cout<<"重新输入年龄：";
			cin.clear();//恢复输入状态
			cin.sync();//清空缓冲区
			continue;
		}
	}
	return p->age;
}
string pd_telephone(M p)//电话号码的判断。
{
	int ju=1,end;
	while(ju)
	{
		ju=0; 
		cin>>p->telephone;
		string st=p->telephone;
		end=getchar();//判断输入结束符是否为回车
		if(end!=10)
		{
			ju=1;
			SetColor(4,15);
			cout<<" 错误（请输入数字）\a\n";
			SetColor(0,15);
			cout<<" 重新输入电话：";
			cin.clear();//恢复输入状态
			cin.sync();//清空缓冲区
		}
		if(st.size()!=11)
		{
			ju=1;
			SetColor(4,15);
			cout<<"错误（请输入十一个数字）\a\n";
			SetColor(0,15);
			cout<<" 重新输入电话：";
			cin.clear();//恢复输入状态
			cin.sync();//清空缓冲区
			continue;
		}
		for(int i=0;i<11;i++)
		{
			if((int)st[i]<48||(int)st[i]>57)
			{
				ju=1;
				SetColor(4,15);
				cout<<"错误（请输入数字）\a\n";
				SetColor(0,15);
				cout<<" 重新输入电话 ：";
				cin.clear();//恢复输入状态
				cin.sync();//清空缓冲区
				break;
			}
		}
	}
	return p->telephone;
}
double pd_double()//判断是否输入数字（浮点型）
{
	int ju=1,end;
	double mon;
	while(ju)
	{
		ju=0;
		cin>>mon;
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
		if(mon<0||mon>100000)
		{
			ju=1;
			SetColor(4,15);
			cout<<" 错误（范围为0—100000）\a\n";
			SetColor(0,15);
			cout<<" 请重新输入：";
			cin.clear();//恢复输入状态
			cin.sync();//清空缓冲区
		}
	}
	return mon;
}
int pd_int()//判断是否输入数字（整型，用于输入选项）
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
			cout<<"输入错误！请您重新输入：";
			SetColor(0,15);
			cin.clear();//恢复输入状态
			cin.sync();//清空缓冲区
		}
	}
	return in;
}
char pd_fun()//判断y\Y
{
	int ju=1,end;
	char fun;
	while(ju)
	{
		ju=0;
		cin>>fun;
		end=getchar();//判断输入结束符是否为回车
		if(end!=10)
		{
			ju=1;
			SetColor(4,15);
			cout<<"输入错误！请您重新输入一个符号：";
			SetColor(0,15);
			cin.clear();//恢复输入状态
			cin.sync();//清空缓冲区
		}
		else if(fun!='y'||fun!='Y')
			break;
	}
	return fun;
}

//主函数
void main()
{
	system("color F0");
	import();//导入数据，生成单链表
	zhu_cai_dan();//主菜单函数调用
}

//线
void printLine()
{
	cout<<"------------------------------------------------------------------------------------------------------------------------\n";
}

//主菜单函数
void zhu_cai_dan()
{
	system("cls");//清屏
	cout<<"                                           **********会员卡计费系统**********\n";
	cout<<"                                                   1：成员登记\n";
	cout<<"                                                   2：信息修改\n";
	cout<<"                                                   3：续费功能\n";
	cout<<"                                                   4：消费结算\n";
	cout<<"                                                   5：成员退卡\n";
	cout<<"                                                   6：统计功能\n";
	cout<<"                                                   7：挂失、解挂失、补办\n";
	cout<<"                                                   8：保存信息\n";
	cout<<"                                                   0：退出系统\n";
	cout<<"                                ========================================================\n";
	Choose();//选择函数调用
}

//选择函数
void Choose()
{
	SetColor(13,15);
	cout<<"                                输入选项【0-8】：";
	SetColor(0,15);
	int choose;
	choose=pd_int();
	switch(choose)
	{
	    case 1:deng_ji();   break;//登记
	    case 2:xiou_gai();  break;//修改
	    case 3:xu_fei();    break;//续费
	    case 4:jie_suan();  break;//结算
	    case 5:tui_ka();    break;//退卡
	    case 6:tong_ji();   break;//统计
		case 7:lost_find_make();   break;//挂失、解挂失、补办
	    case 8:bao_cun();   break;//保存
	    case 0:tui_chu();   break;//退出
	    default:
			SetColor(4,15);
			cout<<"错误操作\a\n";
			SetColor(0,15);
			system("pause");
			zhu_cai_dan();
			break;
	}
}

//登记
void deng_ji()
{
	M p;//*M类型的指针变量p  指向一个新结点（指向一个新会员信息）
	char fun='y';//判断是否继续录入信息，即是否继续创建新结点
	while(fun=='y'||fun=='Y')
	{
		p=new Member;//创建一个新会员信息 动态分配内存空间
		system("cls");
		cout<<"                      ***********成员登记*************\n"; 
		cout<<"         请输入卡号：";pd_card(p);		//卡号
		M f=find(p->card);//判断卡号重复
		if(f)//如果f存在则有相同卡号
		{
			SetColor(4,15);
			cout<<"卡号重复\a\n";
			SetColor(0,15);
			Sleep(1000);
			zhu_cai_dan();
		}
		cout<<"         请输入姓名：";pd_chinese(p);	//姓名  
		cout<<"         请输入性别：";pd_sex(p);		//性别
		cout<<"         请输入年龄：";pd_age(p);		//年龄
		cout<<"         请输入金额：";
		double mon;
		mon=pd_double();
		p->money=mon;										//缴费
		cout<<"         请输入电话：";pd_telephone(p);	//电话

		p->cost=0.0;			//初始化消费为0
		p->addmoney=p->money;	//初始化缴费为p->money
		strcpy(p->gs,"否");		//初始化未挂失
		strcpy(p->vip,"普通");	//初始化普通成员

		p->next=NULL;//结点的next赋空值
		if(head==NULL)  //如果单链表为空，即头指针为空
			head=rear=p;//则使头指针和尾指针都指向p
		else
		{				  //如果单链表不为空
			rear->next=p; //将p接在尾指针的后面
			rear=p;       //更新尾指针---指向新的尾结点
		}
		SetColor(0,14);
		cout<<"创建完成！\n";
		SetColor(0,15);
		cout<<"如果继续录入请按y|Y:";
		fun=pd_fun();
	}
	system("pause");
	zhu_cai_dan();
}

//修改
void xiou_gai()
{
	system("cls");
	cout<<"                      ***********信息修改*************\n"; 
	char* card=new char;//分配空间
	cout<<"请输入要修改信息的卡号：";
	cin>>card;
	M p=find(card);//p为查找到信息的结点
	if(!p)
	{
		SetColor(4,15);
		cout<<"查无此信息。\a\n";	
		SetColor(0,15);
		system("pause");
		zhu_cai_dan();
	}
	else
	{
		pd_gs(p);
		cout<<"         1.  卡号： "<<p->card<<endl;
		cout<<"         2.  姓名： "<<p->name<<endl;
		cout<<"         3.  性别： "<<p->sex<<endl;	
		cout<<"         4.  年龄： "<<p->age<<endl;				
		cout<<"         5.  电话： "<<p->telephone<<endl;
		printLine();
		cout<<"请选择修改的项目【1-5】：";
		int choose;
		choose=pd_int();
		switch(choose)
		{
		case 1:	cout<<"    请输入卡号：";pd_card(p);		break;
		case 2:	cout<<"    请输入姓名：";pd_chinese(p);		break;     
		case 3: cout<<"    请输入性别：";pd_sex(p);			break;
		case 4: cout<<"    请输入年龄：";pd_age(p);			break;
		case 5: cout<<"    请输入电话：";pd_telephone(p);	break;
		default:
			SetColor(4,15);
			cout<<"错误操作\a\n";	
			SetColor(0,15);
			system("pause");
			xiou_gai();
		}
		printLine();
		once(p);
		SetColor(0,14);
		cout<<"修改完成！\n";
		SetColor(0,15);
		cout<<"继续修改请按y|Y：";
		char fun;
		fun=pd_fun();
		if(fun=='y'||fun=='Y')
		{
			system("pause");
			xiou_gai();
		}
		system("pause");
		zhu_cai_dan();
	}
	system("pause");
	zhu_cai_dan();	
}

//续费
void xu_fei()
{
	system("cls");
	printLine();
	char* card=new char;
	cout<<"     请输入要续费的卡号：";
	cin>>card;
	M p=find(card);//p为查找到信息的结点
	if(!p)
	{
		SetColor(4,15);
		cout<<"查无此信息。\a\n";
		SetColor(0,15);
		system("pause");
		zhu_cai_dan();
	}
	pd_gs(p);
	once(p);//显示该会员的信息
	printLine();
	cout<<"     请输入续费的金额：";
	double addmoney;
	addmoney=pd_double();
	cout<<"     确定续费请按y|Y：";
	char fun;
	fun=pd_fun();
	if(fun=='y'||fun=='Y')
	{

		p->money += addmoney;//余额=余额+续费
		p->addmoney += addmoney;//个人缴费总额=个人缴费总额+续费
		once(p);//显示该会员信息
		SetColor(0,14);
		cout<<"续费成功！\n";
		SetColor(0,15);
		cout<<"     继续续费请按y|Y：";
		char fun;
		fun=pd_fun();
		if(fun=='y'||fun=='Y')
		{ 
			xu_fei();  
		}
		system("pause");
		zhu_cai_dan();
	}
	else zhu_cai_dan();
}

//结算
void jie_suan()
{
	system("cls");
	printLine();
	char* card=new char;
	cout<<"     请输入要结算的卡号：";
	cin>>card;
	M p=find(card);//p为查找到信息的结点
	if(!p)
	{
		SetColor(4,15);
		cout<<"查无此信息。\a\n";
		SetColor(0,15);
		system("pause");
		zhu_cai_dan();
	}
	pd_gs(p);
	once(p);//显示该会员的信息
	printLine();	
	cout<<"     请输入花费的金额：";
	double cost;
	cost=pd_double();
	cout<<"     确定结算请按y|Y：";
	char fun;
	fun=pd_fun();
	if(fun=='y'||fun=='Y')
	{
		if(p->cost+cost >= 1000)
			strcpy(p->vip,"vip");//自动升级为vip
		if(strcmp(p->vip,"vip")==0)
		{
			system("cls");
			cout<<"                                             欢迎您的到来！\n";
			cout<<"                                            您将享受vip服务。\n";
			cout<<"本次消费九折优惠。\n";
			cost *= 0.9;
			cout<<"本次实际消费为"<< cost <<endl;
			if(p->money - cost <0)//判断余额是否充足
			{
				cout<<"余额不足，请及时充值。\n";
				system("pause");
				zhu_cai_dan();
			}
			else
			{
				p->money -= cost;//余额=余额-花费
				p->cost += cost;//个人消费总额
				once(p);//显示该会员信息
			}
		}
		else
		{
			if(p->money - cost <0)//判断余额是否充足
			{
				cout<<"余额不足，请及时充值。\n";
				system("pause");
				zhu_cai_dan();
			}
			else
			{
				p->money -= cost;//余额=余额-花费
				p->cost += cost;//个人消费总额
				once(p);//显示该会员信息
			}
		}
		cout<<"结算成功！\n";
		cout<<"     继续结算请按y|Y：";
		char fun;
		fun=pd_fun();
		if(fun=='y'||fun=='Y')
		{
			jie_suan();  
		}
		system("pause");
		zhu_cai_dan();
	}
	else zhu_cai_dan();
}

//退卡
void tui_ka()
{
	printLine();
	char* card=new char;
	cout<<"     请输入要删除信息的卡号：";
	cin>>card;
	M p=find(card);//p为查找到信息的结点
	if(!p)
	{
		SetColor(4,15);
		cout<<"查无此信息。\a\n";
		SetColor(0,15);
		system("pause");
		zhu_cai_dan();
	}
	pd_gs(p);//判断是否挂失，挂失则回到主菜单
	once(p);//显示该会员的信息
	cout<<"     确定删除请按y|Y：";
	char fun;
	cin>>fun;
	fun=pd_fun();
	if(fun=='y'||fun=='Y')
	{
		M f=head;
		if(f==p)//如果p为头指针，则头指针改为p的下一结点
			head=p->next;
		else
		{
			while(f->next!=p)//当f的下一结点不是p即需要查找信息的结点时，f指向下一结点
				f=f->next;
			f->next=p->next;//否则f跳过p指向p的下一结点
		}
		if(p==rear)//若p为尾指针，则使f指向尾指针
			f=rear;
		cout<<"删除成功！\n";
		system("pause");
		zhu_cai_dan();
	}
	else zhu_cai_dan();
}

//统计
void tong_ji()
{
	system("cls");
	M p=head;//定义指针p指向单链表的头结点
	cout<<"                                              **********统计功能**********\n";
	cout<<"                                              1：按个人累计缴费总额排序\n";
	cout<<"                                              2：按个人累计消费总额排序\n";
	cout<<"                                    ------------------------------------------------\n";
	SetColor(13,15);
	cout<<"                                    输入选项【1-2】：";
	SetColor(0,15);
	int choose;
	choose=pd_int();
	switch(choose)
	{
	    case 1:px_addmoney();   break;//按缴费总额排序
	    case 2:px_cost();		break;//按累计消费总额排序
	    default:
			SetColor(4,15);
			cout<<"错误操作\a\n";
			SetColor(0,15);
			system("pause");
			zhu_cai_dan();
			break;
	}
}

//保存
void bao_cun()
{
	inFile.open("会员信息new.txt",ios::out|ios::in|ios::binary|ios::app);
	if(!inFile)
	{
		cout<<"会员信息new.txt打开文件失败，保存文件失败。\n";
		system("pause");
		zhu_cai_dan();
	}
	M p=head;//定义指针p指向单链表的头结点
	tableHead();//表头
	while(p)//p不为空时
	{
		inFile.write((char*)p,sizeof(Member));
		//输出当前结点信息，即p指向的结点信息
		cout<<setiosflags(ios::left)
			<<setw(15)<< p->card
			<<setw(15)<< p->name 
			<<setw(10)<< p->sex 
			<<setw(10)<< p->age
			<<setw(15)<< p->money	//余额
			<<setw(15)<< p->cost	//个人累计消费 
			<<setw(15)<< p->telephone
			<<setw(15)<< p->gs 
			<<p->vip<<endl;
		printLine();
		p=p->next;//p指向单链表的下一个结点
	}
	inFile.close();//关闭
	remove("会员信息.txt");//删除文件
	rename("会员信息new.txt","会员信息.txt");//文件改名
	SetColor(0,14);
	cout<<"保存完成！\n";
	SetColor(0,15);
	system("pause");
	zhu_cai_dan();
}

//退出
void tui_chu()
{
	cout<<"确认所做的修改按y|Y，否则放弃所做的修改：";
	char fun;
	fun=pd_fun();
	if(fun=='y'||fun=='Y')
	{
		inFile.open("会员信息new.txt",ios::out|ios::in|ios::binary|ios::app);
		if(!inFile)
		{
			cout<<"会员信息new.txt打开文件失败，保存文件失败。\n";
			system("pause");
			zhu_cai_dan();
		}
		M p=head;//定义指针p指向单链表的头结点
		tableHead();//表头
		while(p)//p不为空时
		{
			inFile.write((char*)p,sizeof(Member));
			//输出当前结点信息，即p指向的结点信息
			cout<<setiosflags(ios::left)
			<<setw(15)<< p->card
			<<setw(15)<< p->name 
			<<setw(10)<< p->sex 
			<<setw(10)<< p->age
			<<setw(15)<< p->money	//余额
			<<setw(15)<< p->cost	//个人累计消费 
			<<setw(15)<< p->telephone
			<<setw(15)<< p->gs 
			<<p->vip<<endl;
			printLine();
			p=p->next;//p指向单链表的下一个结点
		}
		inFile.close();//关闭
		remove("会员信息.txt");//删除文件
		rename("会员信息new.txt","会员信息.txt");//文件改名
	}
	system("cls");
	for(int i=0;i<10;i++)
	{  cout<<"\n";  }
	SetColor(12,15);
	cout<<"                                         ~~~     感谢使用本系统!     ~~~\n";
	SetColor(0,15);
	Sleep(2000);
	exit(0);
}

//导入数据
void import() 
{
	outFile.open("会员信息.txt",ios::out|ios::in|ios::binary|ios::app);//打开文件用于读和写
	if(!outFile)
	{
		cout<<"会员信息.txt打开文件失败。\n";
		exit(0);//退出系统
	}
	M p;
	outFile.seekg(ios::beg);//打开要进行输入的文件outFile 重新定位文件位置指针 查找方向为ios::beg（从流的开头开始定位）
	while(!outFile.eof())//判断是否超过文件尾eof():end of file
	{
		p=new Member;//创建一个新会员信息 分配内存空间
		if(outFile.read((char *)p,sizeof(Member)))
		{
			p->next=NULL;//结点的next赋空值
			if(head==NULL)  //如果单链表为空，即头指针为空
				head=rear=p;//则使头指针和尾指针都指向p
			else
			{					 //如果单链表不为空
				rear->next=p;    //将p接在尾指针的后面
				rear=p;          //更新尾指针---指向新的尾结点
			}
		}
	}
	outFile.close();//关闭文件
	for(int i=0;i<10;i++)
	{  cout<<"\n";  }
	SetColor(12,15);
	cout<<"                                         ~~~     欢迎进入会员计费系统!     ~~~\n";
	SetColor(0,15);
	Sleep(1000);
}

//查找数据（卡号）
M find(char*card)
{
	M p=head;
	while(p&&(strcmp(p->card,card)!=0))//当p为空或输入的card与card不相同时跳出循环，否则指向下一结点
		p=p->next;//即当p不为空或输入的kh与card相同时，指向下一结点
	return p;
}

//显示一位会员的信息
void once(M p)
{
	//输出当前结点信息，即p指向的结点信息
	SetColor(0,11);
	cout<<setiosflags(ios::left)<<setw(15)<<"卡号"<<setw(15)<<"姓名"<<setw(10)<<"性别"<<setw(10)<<"年龄" <<setw(15)<<"余额" <<setw(15)<<"总消费"<<setw(15)<<"电话"<<setw(15)<<"是否挂失"<<setw(10)<<"vip"<<endl;
	SetColor(0,15);
	cout<<setiosflags(ios::left)<<setw(15)<<p->card<<setw(15)<<p->name<<setw(10)<<p->sex<<setw(10)<<p->age<<setw(15)<<p->money<<setw(15)<<p->cost<<setw(15)<<p->telephone<<setw(15)<<p->gs<<setw(10)<<p->vip<<endl;
}

//表头
void tableHead()
{
	cout<<"                                              **********会员信息**********\n";
	printLine();
	SetColor(0,3);
	cout<<setiosflags(ios::left)<<setw(15)<<"卡号"<<setw(15)<<"姓名"<<setw(10)<<"性别"<<setw(10)<<"年龄" <<setw(15)<<"余额" <<setw(15)<<"总消费"<<setw(15)<<"电话"<<setw(15)<<"是否挂失"<<setw(10)<<"vip"<<endl;
	SetColor(0,15);
	printLine();
}

//显示全部会员信息
void all(M p)
{
	tableHead();
	p=head;
	while(p)//p不为空时
	{
		//输出当前结点信息，即p指向的结点信息
		cout<<setiosflags(ios::left)
			<<setw(15)<< p->card
			<<setw(15)<< p->name 
			<<setw(10)<< p->sex 
			<<setw(10)<< p->age
			<<setw(15)<< p->money	//余额
			<<setw(15)<< p->cost	//个人累计消费 
			<<setw(15)<< p->telephone
			<<setw(15)<< p->gs 
			<<p->vip<<endl;
		printLine();
		p=p->next;//p指向单链表的下一个结点
	}
	cout<<"************************************************************************************************************************\n";
	double addmoney=0,cost=0;
	M a=head;
	while(a)
	{
		addmoney += a->addmoney;
		a=a->next;
	}
	M b=head;
	while(b)
	{
		cost += b->cost;
		b=b->next;
	}
	if(addmoney>1000000)
		cout<<"所有会员的缴费总额已超过1000000"<<endl;
		else if(cost>1000000)
			cout<<"所有会员的消费总额已超过1000000"<<endl;
		else
		{
			cout<<"所有会员的缴费总额："<<addmoney<<endl;
			cout<<"所有会员的消费总额："<<cost<<endl;
		}
}

//按每个会员的缴费总额进行排序
void px_addmoney()
{
	system("cls");
	char *ch = new char;
	int in;
	double dou;
	for(M p=head; p!=NULL; p=p->next)
	{
		for(M p1=p; p1!=NULL; p1=p1->next)
		{
			if(p->addmoney > p1->addmoney)
			{
				strcpy(ch,p->name);strcpy(p->name,p1->name);strcpy(p1->name,ch);
				strcpy(ch,p->card);strcpy(p->card,p1->card);strcpy(p1->card,ch);
				strcpy(ch,p->sex);strcpy(p->sex,p1->sex);strcpy(p1->sex,ch);
				in=p->age;p->age=p1->age;p1->age=in;
				dou=p->money;p->money=p1->money;p1->money=dou;
				dou=p->addmoney;p->addmoney=p1->addmoney;p1->addmoney=dou;
				dou=p->cost;p->cost=p1->cost;p1->cost=dou;
				strcpy(ch,p->telephone);strcpy(p->telephone,p1->telephone);strcpy(p1->telephone,ch);
			}
		}
	}
	cout<<"                                            **********缴费总额排序**********\n\n";
	M f=head;
	all(f);//显示所有成员信息
	tongji_back();//选择是否返回上一界面
}

//按个人累计消费总额进行排序
void px_cost()
{
	system("cls");
	char *ch = new char;
	int in;
	double dou;
	for(M p=head; p!=NULL; p=p->next)
	{
		for(M p1=p; p1!=NULL; p1=p1->next)
		{
			if(p->cost > p1->cost)
			{
				strcpy(ch,p->name);strcpy(p->name,p1->name);strcpy(p1->name,ch);
				strcpy(ch,p->card);strcpy(p->card,p1->card);strcpy(p1->card,ch);
				strcpy(ch,p->sex);strcpy(p->sex,p1->sex);strcpy(p1->sex,ch);
				in=p->age;p->age=p1->age;p1->age=in;
				dou=p->money;p->money=p1->money;p1->money=dou;
				dou=p->addmoney;p->addmoney=p1->addmoney;p1->addmoney=dou;
				dou=p->cost;p->cost=p1->cost;p1->cost=dou;
				strcpy(ch,p->telephone);strcpy(p->telephone,p1->telephone);strcpy(p1->telephone,ch);
			}
		}
	}
	cout<<"                                             **********消费总额排序**********\n\n";
	M f=head;
	all(f);//显示所有成员信息
	tongji_back();//选择是否返回上一界面
}


//统计返回
void tongji_back()
{
	printLine();
	cout<<"返回统计界面请按1"<<endl;
	cout<<"按其他数字返回主界面"<<endl;
	cout<<"请输入选择：";
	int choose;
	choose=pd_int();
	if(choose==1)tong_ji();
	else zhu_cai_dan();
}

//挂失、解挂失、补办
void lost_find_make()
{
	system("cls");
	M p=head;//定义指针p指向单链表的头结点
	cout<<"                                         **********挂失、解挂失、补办**********\n";
	cout<<"                                            1：挂失\n";
	cout<<"                                            2：解挂失\n";
	cout<<"                                            3：补办\n";
	cout<<"                                    ------------------------------------------------\n";
	SetColor(13,15);
	cout<<"                                    输入选项【1-3】：";
	SetColor(0,15);
	int choose;
	choose=pd_int();
	switch(choose)
	{
		case 1:lostcard();	break;//挂失
	    case 2:findcard();	break;//解挂失
		case 3:makecard();	break;//补办
	    default:
			SetColor(4,15);
			cout<<"错误操作\a\n";
			SetColor(0,15);
			system("pause");
			zhu_cai_dan();
			break;
	}
}

void lostcard()//挂失
{
	printLine();
	char* card=new char;
	cout<<"     请输入要挂失的卡号：";
	cin>>card;
	M p=find(card);//p为查找到信息的结点
	if(!p)
	{
		SetColor(4,15);
		cout<<"查无此信息。\a\n";
		SetColor(0,15);
		system("pause");
		zhu_cai_dan();
	}
	pd_gs(p);//判断是否挂失，挂失则回到主菜单
	once(p);//显示该会员的信息
	printLine();
	cout<<"     确定挂失请按y|Y：";
	char fun;
	fun=pd_fun();
	if(fun=='y'||fun=='Y')
	{
		strcpy(p->gs,"是");
		once(p);//显示该会员信息
		SetColor(0,14);
		cout<<"挂失成功！\n";
		SetColor(0,15);
		system("pause");
		zhu_cai_dan();
	}
	else zhu_cai_dan();
}
void findcard()//解挂失
{
	printLine();
	char* card=new char;
	cout<<"     请输入要解挂失的卡号：";
	cin>>card;
	M p=find(card);//p为查找到信息的结点
	if(!p)
	{
		SetColor(4,15);
		cout<<"查无此信息。\a\n";
		SetColor(0,15);
		system("pause");
		zhu_cai_dan();
	}
	once(p);//显示该会员的信息
	printLine();
	cout<<"     确定解挂失请按y|Y：";
	char fun;
	fun=pd_fun();
	if(fun=='y'||fun=='Y')
	{
		strcpy(p->gs,"否");
		once(p);//显示该会员信息
		SetColor(0,14);
		cout<<"解挂失成功！\n";
		SetColor(0,15);
		system("pause");
		zhu_cai_dan();
	}
	else zhu_cai_dan();
}
void  makecard()//补办
{
	printLine();
	char* card=new char;
	cout<<"     请输入要补办的卡号：";
	cin>>card;
	M p=find(card);//p为查找到信息的结点
	if(!p)
	{
		SetColor(4,15);
		cout<<"查无此信息。\a\n";
		SetColor(0,15);
		system("pause");
		zhu_cai_dan();
	}
	if(strcmp(p->gs,"否")==0)
	{
		SetColor(4,15);
		cout<<"此卡未挂失。\a\n";	
		SetColor(0,15);
		cout<<"请先挂失后再补办！\n";
		system("pause");
		zhu_cai_dan();
	}
	once(p);//显示该会员的信息
	printLine();
	cout<<"     确定补办请按y|Y：";
	char fun;
	fun=pd_fun();
	if(fun=='y'||fun=='Y')
	{
		cout<<"     请输入新卡号：";
		char cr[10];
		int ju=1,end;
		while(ju)
		{
			ju=0;
			cin>>cr;
			string st=cr;//将卡号转换为string型
			end=getchar();//判断输入结束符是否为回车
			if(end!=10)
			{
				ju=1;
				SetColor(4,15);
				cout<<" 错误（请输入字母或数字，共九个）\a\n";
				SetColor(0,15);
				cout<<"重新输入卡号：";
				cin.clear();//恢复输入状态
				cin.sync();//清空缓冲区
			}
			if(st.size()!=9)//限制输入九个
			{
				ju=1;
				SetColor(4,15);
				cout<<"错误（请输入字母或数字，共九个）\a\n";
				SetColor(0,15);
				cout<<" 重新输入卡号 ：";
				cin.clear();//恢复输入状态
				cin.sync();//清空缓冲区
				continue;
			}
			for(int i=0;i<10;i++)//对每一字符进行小数点判断
			{
				if((int)st[i]==46)//46为小数点
				{
					ju=1;
					SetColor(4,15);
					cout<<"错误（不可有小数点）\a\n";
					SetColor(0,15);
					cout<<" 重新输入卡号 ：";
					cin.clear();//恢复输入状态
					cin.sync();//清空缓冲区
					break;
				}
			}
		}
		M f = head;
		while(f)
		{
		  if(strcmp(cr,f->card)==0)
		  {
              cout<<"卡号重复!"<<endl;
			  system("pause");
			  makecard();
		  }
		  f = f->next;
		}
	    strcpy(p->card,cr);

		strcpy(p->gs,"否");
		once(p);//显示该会员信息
		SetColor(0,14);
		cout<<"补办成功！\n";
		SetColor(0,15);
		system("pause");
		zhu_cai_dan();
	}
	else zhu_cai_dan();
}
void  pd_gs(M p)
{
	if(strcmp(p->gs,"是")==0)
	{
		SetColor(4,15);
		cout<<"此卡已挂失。\a\n";	
		SetColor(0,15);
		system("pause");
		zhu_cai_dan();
	}
}


	

#include<iostream>
#include<string>
#include<iomanip>
#include<fstream>
#include<windows.h>
using namespace std;
//��Ա���Ʒ�ϵͳ
typedef struct Member//��Ա��Ϣ ����Member�ṹ������--������������
{
	char name[20];		//����
	char card[10];      //����
	char sex[4];        //�Ա�
	int age;            //����
	double money;		//���
	double addmoney;	//���˽ɷ��ܶ�
	double cost;		//���������ܶ�
	char telephone[12];	//�绰
	char gs[4];			//�ж��Ƿ��ʧ
	char vip[6];		//�ж��Ƿ�Ϊvip
	Member *next;//ָ����һ������ָ�룬������һ��ָ��ָ����һ��Ա��Ϣ
}*M;//*Member

void printLine();	//��
void zhu_cai_dan();//���˵�
void Choose();		//�˵�ѡ��
//�˵�ѡ���е��õĺ�������
void deng_ji();   //��Ա�Ǽ�
void xiou_gai();  //��Ϣ�޸�
void xu_fei();    //���ѹ���
void jie_suan();  //���ѽ���
void tui_ka();    //��Ա�˿�
void tong_ji();   //ͳ�ƹ���
void bao_cun();   //������Ϣ
void lost_find_make();//��ʧ�����ʧ������
void tui_chu();   //�˳�ϵͳ
//��ͨ����
void import();		//��������
M find(char*card);	//�������ݣ����ţ�
void tableHead();	//��ͷ
void once(M p);		//��ʾһλ��Ա����Ϣ
void all(M p);		//��ʾȫ����Ա��Ϣ
void px_addmoney();//1�����ɷ��ܶ�����
void px_cost();    //2�����ۼ������ܶ�����
void tongji_back();//ͳ�Ʒ���
//��ʧ�����ʧ�����������õĺ�������
void lostcard();//��ʧ
void findcard();//���ʧ
void makecard();//����
void pd_gs(M p);//�ж��Ƿ��ʧ
//�жϺ���������
string pd_card(M p);		//���ŵ��ж�
string pd_chinese(M p);		//���ֵ��ж�
string pd_sex(M p);			//�Ա���ж�
int pd_age(M p);			//������ж�
string pd_telephone(M p);	//�绰������ж�
double pd_double();	//�ж��Ƿ��������֣������ͣ�
int pd_int();		//�ж��Ƿ��������֣����ͣ�
char pd_fun();		//�ж��Ƿ����

M head=NULL,rear=NULL;//��������ȫ�ֱ���  head��ʾͷָ��  rear��ʾβָ��
fstream outFile,inFile;//��������ȫ�ֱ���  outFileд���ļ�  inFile��ȡ�ļ�

void SetColor(unsigned short ForeColor,unsigned short BackGroundColor)//�ı���ɫ
{
	HANDLE hCon=GetStdHandle(STD_OUTPUT_HANDLE);
	SetConsoleTextAttribute(hCon,(ForeColor%17)|(BackGroundColor%16*16));
}

string pd_card(M p)//�����ж�
{
	int ju=1,end;
	while(ju)
	{
		ju=0;
		cin>>p->card;
		string st=p->card;//������ת��Ϊstring��
		end=getchar();//�ж�����������Ƿ�Ϊ�س�
		if(end!=10)
		{
			ju=1;
			SetColor(4,15);
			cout<<" ������������ĸ�����֣����Ÿ���\a\n";
			SetColor(0,15);
			cout<<"���俨��\r";
			cin.clear();//�ָ�����״̬
			cin.sync();//��ջ�����
		}
		if(st.size()!=9)//��������Ÿ�
		{
			ju=1;
			SetColor(4,15);
			cout<<"������������ĸ�����֣����Ÿ���\a\n";
			SetColor(0,15);
			cout<<" ���俨�� \r";
			cin.clear();//�ָ�����״̬
			cin.sync();//��ջ�����
			continue;
		}
		for(int i=0;i<12;i++)//��ÿһ�ַ�����С�����ж�
		{
			if((int)st[i]==46)//46ΪС����
			{
				ju=1;
				SetColor(4,15);
				cout<<"���󣨲�����С���㣩\a\n";
				SetColor(0,15);
				cout<<" ���俨�� \r";
				cin.clear();//�ָ�����״̬
				cin.sync();//��ջ�����
				break;
			}
		}
	}
	return p->card;
}
string pd_chinese(M p)//�����ж�
{
	int ju=1,end,i,j;
	while(ju)
	{
		ju=0;
		cin>>p->name;
		string st=p->name;
		end=getchar();//�ж�����������Ƿ�Ϊ�س�
		if(end!=10)
		{
			ju=1;
			SetColor(4,15);
			cout<<" ����\a\n";
			SetColor(0,15);
			cout<<"��������������";		
			cin.clear();//�ָ�����״̬
			cin.sync();//��ջ�����
		}
		else
		{
			for(i=0,j=1;i<=60;i=i+2,j=j+2)
			//������ת��Ϊ����ASCIIֵ
			{
				int a=st[i];
				int b=st[j];
				if(a<-91||a>0)
			//�����һ��ASCIIֵ���Ǹ�ֵ����С��-91���Ǻ��֡�
				{
					ju=1;
					SetColor(4,15);
					cout<<" ���������뺺�֣�\a\n";
					SetColor(0,15);
					cout<<"��������������";
					cin.clear();//�ָ�����״̬
					cin.sync();//��ջ�����
					break;
				}
			}
			if(st.size()>12)//��������ĺ�������
			{
				ju=1;
				SetColor(4,15);
				cout<<"�����벻Ҫ���������֣�\a\n";
				SetColor(0,15);
				cout<<" ��������������";
				cin.clear();//�ָ�����״̬
				cin.sync();//��ջ�����
				continue;
			}
		}
	}
	return p->name;
}
string pd_sex(M p)//�Ա��ж�
{
	int ju=1,end;
	char* sex1="Ů";
	char* sex2="��";
	while(ju)
	{
		ju=0;
		cin>>p->sex;
		end=getchar();//�ж�����������Ƿ�Ϊ�س�
		if(end!=10)
		{
			ju=1;
			SetColor(4,15);
			cout<<"����\a\n";
			SetColor(0,15);
			cout<<"���������Ա�";
			cin.clear();//�ָ�����״̬
			cin.sync();//��ջ�����
		}
		if(strcmp(sex1,p->sex)==0 || strcmp(sex2,p->sex)==0);
		else
		{
			ju=1;
			SetColor(4,15);
			cout<<"���������롰�С���Ů����\a\n";
			SetColor(0,15);
			cout<<"���������Ա�";
			cin.clear();//�ָ�����״̬
			cin.sync();//��ջ�����
		}
	}
	return p->sex;
}
int pd_age(M p)//�����ж�
{
	int ju=1,end;
	while(ju)
	{
		ju=0;
		cin>>p->age;
		end=getchar();//�ж�����������Ƿ�Ϊ�س�
		if(end!=10)
		{
			ju=1;
			SetColor(4,15);
			cout<<" �������������֣�\a\n";
			SetColor(0,15);
			cout<<" �����������䣺";
			cin.clear();//�ָ�����״̬
			cin.sync();//��ջ�����
		}
		if(p->age<0 || p->age>150)//�������䷶Χ
		{
			ju=1;
			SetColor(4,15);
			cout<<" �������䷶ΧΪ0��150��\a\n";
			SetColor(0,15);
			cout<<"�����������䣺";
			cin.clear();//�ָ�����״̬
			cin.sync();//��ջ�����
			continue;
		}
	}
	return p->age;
}
string pd_telephone(M p)//�绰������жϡ�
{
	int ju=1,end;
	while(ju)
	{
		ju=0; 
		cin>>p->telephone;
		string st=p->telephone;
		end=getchar();//�ж�����������Ƿ�Ϊ�س�
		if(end!=10)
		{
			ju=1;
			SetColor(4,15);
			cout<<" �������������֣�\a\n";
			SetColor(0,15);
			cout<<" ��������绰��";
			cin.clear();//�ָ�����״̬
			cin.sync();//��ջ�����
		}
		if(st.size()!=11)
		{
			ju=1;
			SetColor(4,15);
			cout<<"����������ʮһ�����֣�\a\n";
			SetColor(0,15);
			cout<<" ��������绰��";
			cin.clear();//�ָ�����״̬
			cin.sync();//��ջ�����
			continue;
		}
		for(int i=0;i<11;i++)
		{
			if((int)st[i]<48||(int)st[i]>57)
			{
				ju=1;
				SetColor(4,15);
				cout<<"�������������֣�\a\n";
				SetColor(0,15);
				cout<<" ��������绰 ��";
				cin.clear();//�ָ�����״̬
				cin.sync();//��ջ�����
				break;
			}
		}
	}
	return p->telephone;
}
double pd_double()//�ж��Ƿ��������֣������ͣ�
{
	int ju=1,end;
	double mon;
	while(ju)
	{
		ju=0;
		cin>>mon;
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
		if(mon<0||mon>100000)
		{
			ju=1;
			SetColor(4,15);
			cout<<" ���󣨷�ΧΪ0��100000��\a\n";
			SetColor(0,15);
			cout<<" ���������룺";
			cin.clear();//�ָ�����״̬
			cin.sync();//��ջ�����
		}
	}
	return mon;
}
int pd_int()//�ж��Ƿ��������֣����ͣ���������ѡ�
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
			cout<<"������������������룺";
			SetColor(0,15);
			cin.clear();//�ָ�����״̬
			cin.sync();//��ջ�����
		}
	}
	return in;
}
char pd_fun()//�ж�y\Y
{
	int ju=1,end;
	char fun;
	while(ju)
	{
		ju=0;
		cin>>fun;
		end=getchar();//�ж�����������Ƿ�Ϊ�س�
		if(end!=10)
		{
			ju=1;
			SetColor(4,15);
			cout<<"�������������������һ�����ţ�";
			SetColor(0,15);
			cin.clear();//�ָ�����״̬
			cin.sync();//��ջ�����
		}
		else if(fun!='y'||fun!='Y')
			break;
	}
	return fun;
}

//������
void main()
{
	system("color F0");
	import();//�������ݣ����ɵ�����
	zhu_cai_dan();//���˵���������
}

//��
void printLine()
{
	cout<<"------------------------------------------------------------------------------------------------------------------------\n";
}

//���˵�����
void zhu_cai_dan()
{
	system("cls");//����
	cout<<"                                           **********��Ա���Ʒ�ϵͳ**********\n";
	cout<<"                                                   1����Ա�Ǽ�\n";
	cout<<"                                                   2����Ϣ�޸�\n";
	cout<<"                                                   3�����ѹ���\n";
	cout<<"                                                   4�����ѽ���\n";
	cout<<"                                                   5����Ա�˿�\n";
	cout<<"                                                   6��ͳ�ƹ���\n";
	cout<<"                                                   7����ʧ�����ʧ������\n";
	cout<<"                                                   8��������Ϣ\n";
	cout<<"                                                   0���˳�ϵͳ\n";
	cout<<"                                ========================================================\n";
	Choose();//ѡ��������
}

//ѡ����
void Choose()
{
	SetColor(13,15);
	cout<<"                                ����ѡ�0-8����";
	SetColor(0,15);
	int choose;
	choose=pd_int();
	switch(choose)
	{
	    case 1:deng_ji();   break;//�Ǽ�
	    case 2:xiou_gai();  break;//�޸�
	    case 3:xu_fei();    break;//����
	    case 4:jie_suan();  break;//����
	    case 5:tui_ka();    break;//�˿�
	    case 6:tong_ji();   break;//ͳ��
		case 7:lost_find_make();   break;//��ʧ�����ʧ������
	    case 8:bao_cun();   break;//����
	    case 0:tui_chu();   break;//�˳�
	    default:
			SetColor(4,15);
			cout<<"�������\a\n";
			SetColor(0,15);
			system("pause");
			zhu_cai_dan();
			break;
	}
}

//�Ǽ�
void deng_ji()
{
	M p;//*M���͵�ָ�����p  ָ��һ���½�㣨ָ��һ���»�Ա��Ϣ��
	char fun='y';//�ж��Ƿ����¼����Ϣ�����Ƿ���������½��
	while(fun=='y'||fun=='Y')
	{
		p=new Member;//����һ���»�Ա��Ϣ ��̬�����ڴ�ռ�
		system("cls");
		cout<<"                      ***********��Ա�Ǽ�*************\n"; 
		cout<<"         �����뿨�ţ�";pd_card(p);		//����
		M f=find(p->card);//�жϿ����ظ�
		if(f)//���f����������ͬ����
		{
			SetColor(4,15);
			cout<<"�����ظ�\a\n";
			SetColor(0,15);
			Sleep(1000);
			zhu_cai_dan();
		}
		cout<<"         ������������";pd_chinese(p);	//����  
		cout<<"         �������Ա�";pd_sex(p);		//�Ա�
		cout<<"         ���������䣺";pd_age(p);		//����
		cout<<"         �������";
		double mon;
		mon=pd_double();
		p->money=mon;										//�ɷ�
		cout<<"         ������绰��";pd_telephone(p);	//�绰

		p->cost=0.0;			//��ʼ������Ϊ0
		p->addmoney=p->money;	//��ʼ���ɷ�Ϊp->money
		strcpy(p->gs,"��");		//��ʼ��δ��ʧ
		strcpy(p->vip,"��ͨ");	//��ʼ����ͨ��Ա

		p->next=NULL;//����next����ֵ
		if(head==NULL)  //���������Ϊ�գ���ͷָ��Ϊ��
			head=rear=p;//��ʹͷָ���βָ�붼ָ��p
		else
		{				  //���������Ϊ��
			rear->next=p; //��p����βָ��ĺ���
			rear=p;       //����βָ��---ָ���µ�β���
		}
		SetColor(0,14);
		cout<<"������ɣ�\n";
		SetColor(0,15);
		cout<<"�������¼���밴y|Y:";
		fun=pd_fun();
	}
	system("pause");
	zhu_cai_dan();
}

//�޸�
void xiou_gai()
{
	system("cls");
	cout<<"                      ***********��Ϣ�޸�*************\n"; 
	char* card=new char;//����ռ�
	cout<<"������Ҫ�޸���Ϣ�Ŀ��ţ�";
	cin>>card;
	M p=find(card);//pΪ���ҵ���Ϣ�Ľ��
	if(!p)
	{
		SetColor(4,15);
		cout<<"���޴���Ϣ��\a\n";	
		SetColor(0,15);
		system("pause");
		zhu_cai_dan();
	}
	else
	{
		pd_gs(p);
		cout<<"         1.  ���ţ� "<<p->card<<endl;
		cout<<"         2.  ������ "<<p->name<<endl;
		cout<<"         3.  �Ա� "<<p->sex<<endl;	
		cout<<"         4.  ���䣺 "<<p->age<<endl;				
		cout<<"         5.  �绰�� "<<p->telephone<<endl;
		printLine();
		cout<<"��ѡ���޸ĵ���Ŀ��1-5����";
		int choose;
		choose=pd_int();
		switch(choose)
		{
		case 1:	cout<<"    �����뿨�ţ�";pd_card(p);		break;
		case 2:	cout<<"    ������������";pd_chinese(p);		break;     
		case 3: cout<<"    �������Ա�";pd_sex(p);			break;
		case 4: cout<<"    ���������䣺";pd_age(p);			break;
		case 5: cout<<"    ������绰��";pd_telephone(p);	break;
		default:
			SetColor(4,15);
			cout<<"�������\a\n";	
			SetColor(0,15);
			system("pause");
			xiou_gai();
		}
		printLine();
		once(p);
		SetColor(0,14);
		cout<<"�޸���ɣ�\n";
		SetColor(0,15);
		cout<<"�����޸��밴y|Y��";
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

//����
void xu_fei()
{
	system("cls");
	printLine();
	char* card=new char;
	cout<<"     ������Ҫ���ѵĿ��ţ�";
	cin>>card;
	M p=find(card);//pΪ���ҵ���Ϣ�Ľ��
	if(!p)
	{
		SetColor(4,15);
		cout<<"���޴���Ϣ��\a\n";
		SetColor(0,15);
		system("pause");
		zhu_cai_dan();
	}
	pd_gs(p);
	once(p);//��ʾ�û�Ա����Ϣ
	printLine();
	cout<<"     ���������ѵĽ�";
	double addmoney;
	addmoney=pd_double();
	cout<<"     ȷ�������밴y|Y��";
	char fun;
	fun=pd_fun();
	if(fun=='y'||fun=='Y')
	{

		p->money += addmoney;//���=���+����
		p->addmoney += addmoney;//���˽ɷ��ܶ�=���˽ɷ��ܶ�+����
		once(p);//��ʾ�û�Ա��Ϣ
		SetColor(0,14);
		cout<<"���ѳɹ���\n";
		SetColor(0,15);
		cout<<"     ���������밴y|Y��";
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

//����
void jie_suan()
{
	system("cls");
	printLine();
	char* card=new char;
	cout<<"     ������Ҫ����Ŀ��ţ�";
	cin>>card;
	M p=find(card);//pΪ���ҵ���Ϣ�Ľ��
	if(!p)
	{
		SetColor(4,15);
		cout<<"���޴���Ϣ��\a\n";
		SetColor(0,15);
		system("pause");
		zhu_cai_dan();
	}
	pd_gs(p);
	once(p);//��ʾ�û�Ա����Ϣ
	printLine();	
	cout<<"     �����뻨�ѵĽ�";
	double cost;
	cost=pd_double();
	cout<<"     ȷ�������밴y|Y��";
	char fun;
	fun=pd_fun();
	if(fun=='y'||fun=='Y')
	{
		if(p->cost+cost >= 1000)
			strcpy(p->vip,"vip");//�Զ�����Ϊvip
		if(strcmp(p->vip,"vip")==0)
		{
			system("cls");
			cout<<"                                             ��ӭ���ĵ�����\n";
			cout<<"                                            ��������vip����\n";
			cout<<"�������Ѿ����Żݡ�\n";
			cost *= 0.9;
			cout<<"����ʵ������Ϊ"<< cost <<endl;
			if(p->money - cost <0)//�ж�����Ƿ����
			{
				cout<<"���㣬�뼰ʱ��ֵ��\n";
				system("pause");
				zhu_cai_dan();
			}
			else
			{
				p->money -= cost;//���=���-����
				p->cost += cost;//���������ܶ�
				once(p);//��ʾ�û�Ա��Ϣ
			}
		}
		else
		{
			if(p->money - cost <0)//�ж�����Ƿ����
			{
				cout<<"���㣬�뼰ʱ��ֵ��\n";
				system("pause");
				zhu_cai_dan();
			}
			else
			{
				p->money -= cost;//���=���-����
				p->cost += cost;//���������ܶ�
				once(p);//��ʾ�û�Ա��Ϣ
			}
		}
		cout<<"����ɹ���\n";
		cout<<"     ���������밴y|Y��";
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

//�˿�
void tui_ka()
{
	printLine();
	char* card=new char;
	cout<<"     ������Ҫɾ����Ϣ�Ŀ��ţ�";
	cin>>card;
	M p=find(card);//pΪ���ҵ���Ϣ�Ľ��
	if(!p)
	{
		SetColor(4,15);
		cout<<"���޴���Ϣ��\a\n";
		SetColor(0,15);
		system("pause");
		zhu_cai_dan();
	}
	pd_gs(p);//�ж��Ƿ��ʧ����ʧ��ص����˵�
	once(p);//��ʾ�û�Ա����Ϣ
	cout<<"     ȷ��ɾ���밴y|Y��";
	char fun;
	cin>>fun;
	fun=pd_fun();
	if(fun=='y'||fun=='Y')
	{
		M f=head;
		if(f==p)//���pΪͷָ�룬��ͷָ���Ϊp����һ���
			head=p->next;
		else
		{
			while(f->next!=p)//��f����һ��㲻��p����Ҫ������Ϣ�Ľ��ʱ��fָ����һ���
				f=f->next;
			f->next=p->next;//����f����pָ��p����һ���
		}
		if(p==rear)//��pΪβָ�룬��ʹfָ��βָ��
			f=rear;
		cout<<"ɾ���ɹ���\n";
		system("pause");
		zhu_cai_dan();
	}
	else zhu_cai_dan();
}

//ͳ��
void tong_ji()
{
	system("cls");
	M p=head;//����ָ��pָ�������ͷ���
	cout<<"                                              **********ͳ�ƹ���**********\n";
	cout<<"                                              1���������ۼƽɷ��ܶ�����\n";
	cout<<"                                              2���������ۼ������ܶ�����\n";
	cout<<"                                    ------------------------------------------------\n";
	SetColor(13,15);
	cout<<"                                    ����ѡ�1-2����";
	SetColor(0,15);
	int choose;
	choose=pd_int();
	switch(choose)
	{
	    case 1:px_addmoney();   break;//���ɷ��ܶ�����
	    case 2:px_cost();		break;//���ۼ������ܶ�����
	    default:
			SetColor(4,15);
			cout<<"�������\a\n";
			SetColor(0,15);
			system("pause");
			zhu_cai_dan();
			break;
	}
}

//����
void bao_cun()
{
	inFile.open("��Ա��Ϣnew.txt",ios::out|ios::in|ios::binary|ios::app);
	if(!inFile)
	{
		cout<<"��Ա��Ϣnew.txt���ļ�ʧ�ܣ������ļ�ʧ�ܡ�\n";
		system("pause");
		zhu_cai_dan();
	}
	M p=head;//����ָ��pָ�������ͷ���
	tableHead();//��ͷ
	while(p)//p��Ϊ��ʱ
	{
		inFile.write((char*)p,sizeof(Member));
		//�����ǰ�����Ϣ����pָ��Ľ����Ϣ
		cout<<setiosflags(ios::left)
			<<setw(15)<< p->card
			<<setw(15)<< p->name 
			<<setw(10)<< p->sex 
			<<setw(10)<< p->age
			<<setw(15)<< p->money	//���
			<<setw(15)<< p->cost	//�����ۼ����� 
			<<setw(15)<< p->telephone
			<<setw(15)<< p->gs 
			<<p->vip<<endl;
		printLine();
		p=p->next;//pָ���������һ�����
	}
	inFile.close();//�ر�
	remove("��Ա��Ϣ.txt");//ɾ���ļ�
	rename("��Ա��Ϣnew.txt","��Ա��Ϣ.txt");//�ļ�����
	SetColor(0,14);
	cout<<"������ɣ�\n";
	SetColor(0,15);
	system("pause");
	zhu_cai_dan();
}

//�˳�
void tui_chu()
{
	cout<<"ȷ���������޸İ�y|Y����������������޸ģ�";
	char fun;
	fun=pd_fun();
	if(fun=='y'||fun=='Y')
	{
		inFile.open("��Ա��Ϣnew.txt",ios::out|ios::in|ios::binary|ios::app);
		if(!inFile)
		{
			cout<<"��Ա��Ϣnew.txt���ļ�ʧ�ܣ������ļ�ʧ�ܡ�\n";
			system("pause");
			zhu_cai_dan();
		}
		M p=head;//����ָ��pָ�������ͷ���
		tableHead();//��ͷ
		while(p)//p��Ϊ��ʱ
		{
			inFile.write((char*)p,sizeof(Member));
			//�����ǰ�����Ϣ����pָ��Ľ����Ϣ
			cout<<setiosflags(ios::left)
			<<setw(15)<< p->card
			<<setw(15)<< p->name 
			<<setw(10)<< p->sex 
			<<setw(10)<< p->age
			<<setw(15)<< p->money	//���
			<<setw(15)<< p->cost	//�����ۼ����� 
			<<setw(15)<< p->telephone
			<<setw(15)<< p->gs 
			<<p->vip<<endl;
			printLine();
			p=p->next;//pָ���������һ�����
		}
		inFile.close();//�ر�
		remove("��Ա��Ϣ.txt");//ɾ���ļ�
		rename("��Ա��Ϣnew.txt","��Ա��Ϣ.txt");//�ļ�����
	}
	system("cls");
	for(int i=0;i<10;i++)
	{  cout<<"\n";  }
	SetColor(12,15);
	cout<<"                                         ~~~     ��лʹ�ñ�ϵͳ!     ~~~\n";
	SetColor(0,15);
	Sleep(2000);
	exit(0);
}

//��������
void import() 
{
	outFile.open("��Ա��Ϣ.txt",ios::out|ios::in|ios::binary|ios::app);//���ļ����ڶ���д
	if(!outFile)
	{
		cout<<"��Ա��Ϣ.txt���ļ�ʧ�ܡ�\n";
		exit(0);//�˳�ϵͳ
	}
	M p;
	outFile.seekg(ios::beg);//��Ҫ����������ļ�outFile ���¶�λ�ļ�λ��ָ�� ���ҷ���Ϊios::beg�������Ŀ�ͷ��ʼ��λ��
	while(!outFile.eof())//�ж��Ƿ񳬹��ļ�βeof():end of file
	{
		p=new Member;//����һ���»�Ա��Ϣ �����ڴ�ռ�
		if(outFile.read((char *)p,sizeof(Member)))
		{
			p->next=NULL;//����next����ֵ
			if(head==NULL)  //���������Ϊ�գ���ͷָ��Ϊ��
				head=rear=p;//��ʹͷָ���βָ�붼ָ��p
			else
			{					 //���������Ϊ��
				rear->next=p;    //��p����βָ��ĺ���
				rear=p;          //����βָ��---ָ���µ�β���
			}
		}
	}
	outFile.close();//�ر��ļ�
	for(int i=0;i<10;i++)
	{  cout<<"\n";  }
	SetColor(12,15);
	cout<<"                                         ~~~     ��ӭ�����Ա�Ʒ�ϵͳ!     ~~~\n";
	SetColor(0,15);
	Sleep(1000);
}

//�������ݣ����ţ�
M find(char*card)
{
	M p=head;
	while(p&&(strcmp(p->card,card)!=0))//��pΪ�ջ������card��card����ͬʱ����ѭ��������ָ����һ���
		p=p->next;//����p��Ϊ�ջ������kh��card��ͬʱ��ָ����һ���
	return p;
}

//��ʾһλ��Ա����Ϣ
void once(M p)
{
	//�����ǰ�����Ϣ����pָ��Ľ����Ϣ
	SetColor(0,11);
	cout<<setiosflags(ios::left)<<setw(15)<<"����"<<setw(15)<<"����"<<setw(10)<<"�Ա�"<<setw(10)<<"����" <<setw(15)<<"���" <<setw(15)<<"������"<<setw(15)<<"�绰"<<setw(15)<<"�Ƿ��ʧ"<<setw(10)<<"vip"<<endl;
	SetColor(0,15);
	cout<<setiosflags(ios::left)<<setw(15)<<p->card<<setw(15)<<p->name<<setw(10)<<p->sex<<setw(10)<<p->age<<setw(15)<<p->money<<setw(15)<<p->cost<<setw(15)<<p->telephone<<setw(15)<<p->gs<<setw(10)<<p->vip<<endl;
}

//��ͷ
void tableHead()
{
	cout<<"                                              **********��Ա��Ϣ**********\n";
	printLine();
	SetColor(0,3);
	cout<<setiosflags(ios::left)<<setw(15)<<"����"<<setw(15)<<"����"<<setw(10)<<"�Ա�"<<setw(10)<<"����" <<setw(15)<<"���" <<setw(15)<<"������"<<setw(15)<<"�绰"<<setw(15)<<"�Ƿ��ʧ"<<setw(10)<<"vip"<<endl;
	SetColor(0,15);
	printLine();
}

//��ʾȫ����Ա��Ϣ
void all(M p)
{
	tableHead();
	p=head;
	while(p)//p��Ϊ��ʱ
	{
		//�����ǰ�����Ϣ����pָ��Ľ����Ϣ
		cout<<setiosflags(ios::left)
			<<setw(15)<< p->card
			<<setw(15)<< p->name 
			<<setw(10)<< p->sex 
			<<setw(10)<< p->age
			<<setw(15)<< p->money	//���
			<<setw(15)<< p->cost	//�����ۼ����� 
			<<setw(15)<< p->telephone
			<<setw(15)<< p->gs 
			<<p->vip<<endl;
		printLine();
		p=p->next;//pָ���������һ�����
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
		cout<<"���л�Ա�Ľɷ��ܶ��ѳ���1000000"<<endl;
		else if(cost>1000000)
			cout<<"���л�Ա�������ܶ��ѳ���1000000"<<endl;
		else
		{
			cout<<"���л�Ա�Ľɷ��ܶ"<<addmoney<<endl;
			cout<<"���л�Ա�������ܶ"<<cost<<endl;
		}
}

//��ÿ����Ա�Ľɷ��ܶ��������
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
	cout<<"                                            **********�ɷ��ܶ�����**********\n\n";
	M f=head;
	all(f);//��ʾ���г�Ա��Ϣ
	tongji_back();//ѡ���Ƿ񷵻���һ����
}

//�������ۼ������ܶ��������
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
	cout<<"                                             **********�����ܶ�����**********\n\n";
	M f=head;
	all(f);//��ʾ���г�Ա��Ϣ
	tongji_back();//ѡ���Ƿ񷵻���һ����
}


//ͳ�Ʒ���
void tongji_back()
{
	printLine();
	cout<<"����ͳ�ƽ����밴1"<<endl;
	cout<<"���������ַ���������"<<endl;
	cout<<"������ѡ��";
	int choose;
	choose=pd_int();
	if(choose==1)tong_ji();
	else zhu_cai_dan();
}

//��ʧ�����ʧ������
void lost_find_make()
{
	system("cls");
	M p=head;//����ָ��pָ�������ͷ���
	cout<<"                                         **********��ʧ�����ʧ������**********\n";
	cout<<"                                            1����ʧ\n";
	cout<<"                                            2�����ʧ\n";
	cout<<"                                            3������\n";
	cout<<"                                    ------------------------------------------------\n";
	SetColor(13,15);
	cout<<"                                    ����ѡ�1-3����";
	SetColor(0,15);
	int choose;
	choose=pd_int();
	switch(choose)
	{
		case 1:lostcard();	break;//��ʧ
	    case 2:findcard();	break;//���ʧ
		case 3:makecard();	break;//����
	    default:
			SetColor(4,15);
			cout<<"�������\a\n";
			SetColor(0,15);
			system("pause");
			zhu_cai_dan();
			break;
	}
}

void lostcard()//��ʧ
{
	printLine();
	char* card=new char;
	cout<<"     ������Ҫ��ʧ�Ŀ��ţ�";
	cin>>card;
	M p=find(card);//pΪ���ҵ���Ϣ�Ľ��
	if(!p)
	{
		SetColor(4,15);
		cout<<"���޴���Ϣ��\a\n";
		SetColor(0,15);
		system("pause");
		zhu_cai_dan();
	}
	pd_gs(p);//�ж��Ƿ��ʧ����ʧ��ص����˵�
	once(p);//��ʾ�û�Ա����Ϣ
	printLine();
	cout<<"     ȷ����ʧ�밴y|Y��";
	char fun;
	fun=pd_fun();
	if(fun=='y'||fun=='Y')
	{
		strcpy(p->gs,"��");
		once(p);//��ʾ�û�Ա��Ϣ
		SetColor(0,14);
		cout<<"��ʧ�ɹ���\n";
		SetColor(0,15);
		system("pause");
		zhu_cai_dan();
	}
	else zhu_cai_dan();
}
void findcard()//���ʧ
{
	printLine();
	char* card=new char;
	cout<<"     ������Ҫ���ʧ�Ŀ��ţ�";
	cin>>card;
	M p=find(card);//pΪ���ҵ���Ϣ�Ľ��
	if(!p)
	{
		SetColor(4,15);
		cout<<"���޴���Ϣ��\a\n";
		SetColor(0,15);
		system("pause");
		zhu_cai_dan();
	}
	once(p);//��ʾ�û�Ա����Ϣ
	printLine();
	cout<<"     ȷ�����ʧ�밴y|Y��";
	char fun;
	fun=pd_fun();
	if(fun=='y'||fun=='Y')
	{
		strcpy(p->gs,"��");
		once(p);//��ʾ�û�Ա��Ϣ
		SetColor(0,14);
		cout<<"���ʧ�ɹ���\n";
		SetColor(0,15);
		system("pause");
		zhu_cai_dan();
	}
	else zhu_cai_dan();
}
void  makecard()//����
{
	printLine();
	char* card=new char;
	cout<<"     ������Ҫ����Ŀ��ţ�";
	cin>>card;
	M p=find(card);//pΪ���ҵ���Ϣ�Ľ��
	if(!p)
	{
		SetColor(4,15);
		cout<<"���޴���Ϣ��\a\n";
		SetColor(0,15);
		system("pause");
		zhu_cai_dan();
	}
	if(strcmp(p->gs,"��")==0)
	{
		SetColor(4,15);
		cout<<"�˿�δ��ʧ��\a\n";	
		SetColor(0,15);
		cout<<"���ȹ�ʧ���ٲ��죡\n";
		system("pause");
		zhu_cai_dan();
	}
	once(p);//��ʾ�û�Ա����Ϣ
	printLine();
	cout<<"     ȷ�������밴y|Y��";
	char fun;
	fun=pd_fun();
	if(fun=='y'||fun=='Y')
	{
		cout<<"     �������¿��ţ�";
		char cr[10];
		int ju=1,end;
		while(ju)
		{
			ju=0;
			cin>>cr;
			string st=cr;//������ת��Ϊstring��
			end=getchar();//�ж�����������Ƿ�Ϊ�س�
			if(end!=10)
			{
				ju=1;
				SetColor(4,15);
				cout<<" ������������ĸ�����֣����Ÿ���\a\n";
				SetColor(0,15);
				cout<<"�������뿨�ţ�";
				cin.clear();//�ָ�����״̬
				cin.sync();//��ջ�����
			}
			if(st.size()!=9)//��������Ÿ�
			{
				ju=1;
				SetColor(4,15);
				cout<<"������������ĸ�����֣����Ÿ���\a\n";
				SetColor(0,15);
				cout<<" �������뿨�� ��";
				cin.clear();//�ָ�����״̬
				cin.sync();//��ջ�����
				continue;
			}
			for(int i=0;i<10;i++)//��ÿһ�ַ�����С�����ж�
			{
				if((int)st[i]==46)//46ΪС����
				{
					ju=1;
					SetColor(4,15);
					cout<<"���󣨲�����С���㣩\a\n";
					SetColor(0,15);
					cout<<" �������뿨�� ��";
					cin.clear();//�ָ�����״̬
					cin.sync();//��ջ�����
					break;
				}
			}
		}
		M f = head;
		while(f)
		{
		  if(strcmp(cr,f->card)==0)
		  {
              cout<<"�����ظ�!"<<endl;
			  system("pause");
			  makecard();
		  }
		  f = f->next;
		}
	    strcpy(p->card,cr);

		strcpy(p->gs,"��");
		once(p);//��ʾ�û�Ա��Ϣ
		SetColor(0,14);
		cout<<"����ɹ���\n";
		SetColor(0,15);
		system("pause");
		zhu_cai_dan();
	}
	else zhu_cai_dan();
}
void  pd_gs(M p)
{
	if(strcmp(p->gs,"��")==0)
	{
		SetColor(4,15);
		cout<<"�˿��ѹ�ʧ��\a\n";	
		SetColor(0,15);
		system("pause");
		zhu_cai_dan();
	}
}


	

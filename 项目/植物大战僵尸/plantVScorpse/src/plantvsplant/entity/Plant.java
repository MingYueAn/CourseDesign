package plantvsplant.entity;

public class Plant
{
	private int id;
	private int power;// 战斗力
	private int hp;// 生命值
	private int price;// 价格
	private int trainTime;// 培养时间
	private boolean trainFinish = true;
	private boolean canPlant = false;

	public Plant(int id)
	{
		this.id = id;
		init(id);
	}

	private void init(int id)
	{
		switch (id)
		{
		case 1:
			power = 0;
			hp = 4;
			price = 50;
			trainTime = 5;
			trainFinish = true;
			canPlant = false;
			break;// 向日葵
		case 2:
			power = 1000;
			hp = 100;
			price = 150;
			trainTime = 27;
			trainFinish = true;
			canPlant = false;
			break;// 炸弹
		case 3:
			power = 25;
			hp = 6;
			price = 100;
			trainTime = 17;
			trainFinish = true;
			canPlant = false;
			break;// 豌豆
		case 4:
			power = 0;
			hp = 20;
			price = 50;
			trainTime = 13;
			trainFinish = true;
			canPlant = false;
			break;// 土豆
		case 5:
			power = 25;
			hp = 6;
			price = 175;
			trainTime = 21;
			trainFinish = true;
			canPlant = false;
			break;// 雪花豌豆
		}

	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getPower()
	{
		return power;
	}

	public void setPower(int power)
	{
		this.power = power;
	}

	public int getHp()
	{
		return hp;
	}

	public void setHp(int hp)
	{
		this.hp = hp;
	}

	public int getPrice()
	{
		return price;
	}

	public void setPrice(int price)
	{
		this.price = price;
	}

	public int getTrainTime()
	{
		return trainTime;
	}

	public void setTrainTime(int trainTime)
	{
		this.trainTime = trainTime;
	}

	public boolean isTrainFinish()
	{
		return trainFinish;
	}

	public void setTrainFinish(boolean trainFinish)
	{
		this.trainFinish = trainFinish;
	}

	public boolean isCanPlant()
	{
		return canPlant;
	}

	public void setCanPlant(boolean canPlant)
	{
		this.canPlant = canPlant;
	}

}

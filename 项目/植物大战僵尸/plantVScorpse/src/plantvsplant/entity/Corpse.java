package plantvsplant.entity;

import java.util.ArrayList;
import java.util.List;

public class Corpse
{
	private int id;
	private int hp;
	private int isHit = 0;
	public static final List<String> CORPSES = new ArrayList<String>();

	public Corpse(int id)
	{
		this.id = id;
		init(id);
	}

	private void init(int id)
	{
		CORPSES.add("11111");
		CORPSES.add("12111");
		CORPSES.add("13111");
		CORPSES.add("14111");
		switch (id)
		{
		case 11:
			hp = 100;
			break;
		case 12:
			hp = 150;
			break;
		case 13:
			hp = 200;
			break;
		case 14:
			hp = 250;
			break;
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

	public int getHp()
	{
		return hp;
	}

	public void setHp(int hp)
	{
		this.hp = hp;
	}

	public int getIsHit()
	{
		return isHit;
	}

	public void setIsHit(int isHit)
	{
		this.isHit = isHit;
	}

	@Override
	public int hashCode()
	{
		return id;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof Corpse)
			return this.id == ((Corpse) obj).id;
		return false;
	}
}

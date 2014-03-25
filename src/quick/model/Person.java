package quick.model;

public class Person
{   
    private String name;
    private String birthDate;
    private String deathDate;
    private boolean isMarried;
    private boolean hasChildren;
    private int age;
    
    public Person()
    {
	name = "";
	birthDate = "unknown";
	deathDate = "unknown";
	isMarried = false;
	hasChildren = false;
	age = -500;
    }
    
    public Person(String name, String deathDate)
    {
	this.name = name;
	this.deathDate = deathDate;
    }
    
    public String getName()
    {
        return name;
    }

    public String getBirthDate()
    {
        return birthDate;
    }

    public String getDeathDate()
    {
        return deathDate;
    }

    public boolean isMarried()
    {
        return isMarried;
    }

    public boolean isHasChildren()
    {
        return hasChildren;
    }

    public int getAge()
    {
        return age;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setBirthDate(String birthDate)
    {
        this.birthDate = birthDate;
    }

    public void setDeathDate(String deathDate)
    {
        this.deathDate = deathDate;
    }

    public void setMarried(boolean isMarried)
    {
        this.isMarried = isMarried;
    }

    public void setHasChildren(boolean hasChildren)
    {
        this.hasChildren = hasChildren;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public String toString()
    {
	String personInfo = "";
	
	personInfo += "This is: " + name;
	personInfo += "; died around: " + deathDate;
	personInfo += " at age: " + age;
	
	return personInfo;
    }
    

}

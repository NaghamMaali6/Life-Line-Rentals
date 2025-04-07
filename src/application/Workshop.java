/*This class represents the Workshop table with its attributes*/


package application ;

public class Workshop 
{
    private int Workshop_Number ;   //Primary key
    private String Workshop_Name ;
    private String Address ;
    private String Phone_Number ;

    //Constructor of the Workshop object:
    public Workshop(int Workshop_Number , String Workshop_Name , String Address , String Phone_Number)
    {
        this.Workshop_Number = Workshop_Number ;
        this.Workshop_Name = Workshop_Name ;
        this.Address = Address ;
        this.Phone_Number = Phone_Number ;
    }

    //Getters and setters:
    public int getWorkshop_Number() 
    {
        return Workshop_Number ;
    }

    public void setWorkshop_Number(int Workshop_Number) 
    {
        this.Workshop_Number = Workshop_Number;
    }

    public String getWorkshop_Name() 
    {
        return Workshop_Name ;
    }

    public void setWorkshop_Name(String Workshop_Name) 
    {
        this.Workshop_Name = Workshop_Name ;
    }

    public String getAddress() 
    {
        return Address ;
    }

    public void setAddress(String Address) 
    {
        this.Address = Address ;
    }

    public String getPhone_Number() 
    {
        return Phone_Number ;
    }

    public void setPhone_Number(String Phone_Number) 
    {
        this.Phone_Number = Phone_Number ; 
    }
}

/*This class represents the Location table with its attributes*/

package application ;

public class Location 
{
    private int Location_Number ;  //primary key
    private String Address ;
    private String Phone_Number;

    //Constructor of the Location object:
    public Location(int Location_Number , String Address , String Phone_Number) 
    {
        this.Location_Number = Location_Number ;
        this.Address = Address ;
        this.Phone_Number = Phone_Number ;
    }

    //Getters and setters:
    public int getLocationNumber() 
    {
        return Location_Number ;
    }

    public void setLocationNumber(int Location_Number) 
    {
        this.Location_Number = Location_Number ;
    }

    public String getAddress() 
    {
        return Address ;
    }

    public void setAddress(String Address) 
    {
        this.Address = Address ;
    }

    public String getPhoneNumber() 
    {
        return Phone_Number ;
    }

    public void setPhoneNumber(String Phone_Number) 
    {
        this.Phone_Number = Phone_Number ;
    }
}

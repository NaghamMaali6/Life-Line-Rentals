/*This class represents the Supplier table with its attributes*/

package application ;

public class Supplier 
{
    private int Supplier_Number ; //primary key
    private String S_Name ;
    private String phoneNumber ;

    //Constructor of the Supplier object:
    public Supplier(int Supplier_Number , String S_Name , String phoneNumber) 
    {
        this.Supplier_Number = Supplier_Number ;
        this.S_Name = S_Name ;
        this.phoneNumber = phoneNumber ;
    }

    //Getters and setters:
    public int getSupplierNumber() 
    {
        return Supplier_Number ;
    }

    public void setSupplierNumber(int Supplier_Number) 
    {
        this.Supplier_Number = Supplier_Number ;
    }

    public String getName() 
    {
        return S_Name ;
    }

    public void setName(String S_Name) 
    {
        this.S_Name = S_Name ;
    }

    public String getPhoneNumber() 
    {
        return phoneNumber ;
    }

    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber = phoneNumber ;
    }
}

/*this class represents the customer table with it's attributes*/

package application ;

public class Customer
{
    private int customerNumber ;  //primary key 
    private String nationalId ;
    private String fullName ;
    private String address ;
    private String phoneNumber ;
    private String haveInsurance ;

    //Constructor of the Customer object:
    public Customer(int customerNumber , String nationalId , String fullName , String address , String phoneNumber , String haveInsurance)
    {
        this.customerNumber = customerNumber ;
        this.nationalId = nationalId ;
        this.fullName = fullName ;
        this.address = address ;
        this.phoneNumber = phoneNumber ;
        this.haveInsurance = haveInsurance ;
    }

    //Getters and setters:
    public int getCustomerNumber() 
    {
        return customerNumber ;
    }

    public void setCustomerNumber(int customerNumber) 
    {
        this.customerNumber = customerNumber ;
    }

    public String getNationalId() 
    {
        return nationalId ;
    }

    public void setNationalId(String nationalId)
    {
        this.nationalId = nationalId ;
    }

    public String getFullName()
    {
        return fullName ;
    }

    public void setFullName(String fullName) 
    {
        this.fullName = fullName ;
    }

    public String getAddress() 
    {
        return address ;
    }

    public void setAddress(String address)
    {
        this.address = address ;
    }

    public String getPhoneNumber() 
    {
        return phoneNumber ;
    }

    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber = phoneNumber ;
    }

    public String getHaveInsurance() 
    {
        return haveInsurance ;
    }

    public void setHaveInsurance(String haveInsurance) 
    {
        this.haveInsurance = haveInsurance ;
    }
}

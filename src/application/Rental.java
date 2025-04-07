/*This class represents the Rental table with its attributes*/

package application ;

import java.util.Date ;

public class Rental 
{
    private int Rental_Number; // Primary key
    private int Customer_Number; // Foreign key referencing Customer(Customer_Number)
    private int Employee_Number; // Foreign key referencing Employee(Employee_Number)
    private int Equipment_Number; // Foreign key referencing Equipment(Equipment_Number)
    private int Invoice_Number; // Foreign key referencing Invoice(Invoice_Number)
    private Date Start_Date;
    private Date End_Date;
    private String Contract_Image; 

    //Constructor of the Rental object:
    public Rental(int Rental_Number , int Customer_Number , int Employee_Number , int Equipment_Number , int Invoice_Number , Date Start_Date , Date End_Date , String Contract_Image) 
    {
        this.Rental_Number = Rental_Number ;
        this.Customer_Number = Customer_Number ;
        this.Employee_Number = Employee_Number ;
        this.Equipment_Number = Equipment_Number ;
        this.Invoice_Number = Invoice_Number ;
        this.Start_Date = Start_Date ;
        this.End_Date = End_Date ; 
        this.Contract_Image = Contract_Image ;
    }

    //Getters and setters:
    public int getRental_Number() 
    {
        return Rental_Number ;
    }

    public void setRental_Number(int Rental_Number) 
    {
        this.Rental_Number = Rental_Number ;
    }

    public int getCustomer_Number() 
    {
        return Customer_Number ;
    }

    public void setCustomer_Number(int Customer_Number) 
    {
        this.Customer_Number = Customer_Number ;
    }

    public int getEmployee_Number() 
    {
        return Employee_Number ;
    }

    public void setEmployee_Number(int Employee_Number) 
    {
        this.Employee_Number = Employee_Number ;
    }

    public int getEquipment_Number() 
    {
        return Equipment_Number ;
    }

    public void setEquipment_Number(int Equipment_Number) 
    {
        this.Equipment_Number = Equipment_Number ;
    }

    public int getInvoice_Number() 
    {
        return Invoice_Number ;
    }

    public void setInvoice_Number(int Invoice_Number)
    {
        this.Invoice_Number = Invoice_Number ;
    }

    public Date getStart_Date() 
    {
        return Start_Date ;
    }

    public void setStart_Date(Date Start_Date) 
    {
        this.Start_Date = Start_Date ;
    }

    public Date getEnd_Date() 
    {
        return End_Date ;
    }

    public void setEnd_Date(Date End_Date) 
    {
        this.End_Date = End_Date ;
    }

    public String getContract_Image() 
    {
        return Contract_Image ;
    }

    public void setContract_Image(String Contract_Image) 
    {
        this.Contract_Image = Contract_Image ;
    }
}

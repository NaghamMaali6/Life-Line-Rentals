/*This class represents the Invoice table with its attributes*/

package application ;

import java.util.Date ;

public class Invoice  
{
    private int Invoice_Number ;  //Primary key
    private double Total_Amount ;
    private double Discount ;
    private String Payment_Status ;
    private Date Invoice_Date ;
    private Date Due_Date ;
    private String Invoice_Type ;

    //Constructor of the Invoice object:
    public Invoice(int Invoice_Number , double Total_Amount , double Discount , String Payment_Status , Date Invoice_Date , Date Due_Date , String Invoice_Type) 
    {
        this.Invoice_Number = Invoice_Number ;
        this.Total_Amount = Total_Amount ;
        this.Discount = Discount ;
        this.Payment_Status = Payment_Status ;
        this.Invoice_Date = Invoice_Date ;
        this.Due_Date = Due_Date ;
        this.Invoice_Type = Invoice_Type ;
    }

    //Getters and setters:
    public int getInvoiceNumber() 
    {
        return Invoice_Number ;
    }

    public void setInvoiceNumber(int Invoice_Number) 
    {
        this.Invoice_Number = Invoice_Number ;
    }

    public double getTotalAmount() 
    {
        return Total_Amount ;
    }

    public void setTotalAmount(double Total_Amount)
    {
        this.Total_Amount = Total_Amount ;
    }

    public double getDiscount() 
    {
        return Discount ;
    }

    public void setDiscount(double Discount) 
    {
        this.Discount = Discount ;
    }

    public String getPaymentStatus() 
    {
        return Payment_Status ;
    }

    public void setPaymentStatus(String Payment_Status) 
    {
        this.Payment_Status = Payment_Status ;
    }

    public Date getInvoiceDate() 
    {
        return Invoice_Date ;
    }

    public void setInvoiceDate(Date Invoice_Date) 
    {
        this.Invoice_Date = Invoice_Date ;
    }

    public Date getDueDate() 
    {
        return Due_Date ;
    }

    public void setDueDate(Date Due_Date) 
    {
        this.Due_Date = Due_Date ;
    }

    public String getInvoiceType() 
    {
        return Invoice_Type ;
    }

    public void setInvoiceType(String Invoice_Type) 
    {
        this.Invoice_Type = Invoice_Type ;
    }
}

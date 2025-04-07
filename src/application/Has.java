/*This class represents the Has relation table with its attributes*/

package application ;

public class Has 
{
    private double Total_Amount ; 
    private int Rental_Number ;  
    private int Payment_Number ;  

    //Constructor of the Has object
    public Has(double Total_Amount , int Rental_Number , int Payment_Number) 
    {
        this.Total_Amount = Total_Amount ;
        this.Rental_Number = Rental_Number ;
        this.Payment_Number = Payment_Number ;
    }

    //Getters and setters
    public double getTotal_Amount() 
    {
        return Total_Amount ;
    }

    public void setTotal_Amount(double Total_Amount) 
    {
        this.Total_Amount = Total_Amount ;
    }

    public int getRental_Number() 
    {
        return Rental_Number ;
    }

    public void setRental_Number(int Rental_Number) 
    {
        this.Rental_Number = Rental_Number ;
    }

    public int getPayment_Number() 
    {
        return Payment_Number ;
    }

    public void setPayment_Number(int Payment_Number) 
    {
        this.Payment_Number = Payment_Number ;
    }
}

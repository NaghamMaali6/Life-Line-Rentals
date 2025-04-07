/*This class represents the Payment table with its attributes*/

package application ;

public class Payment 
{
    private int Payment_Number ;  //Primary key
    private int Rental_Number ;   //Foreign key  referencing Rental table
    private int Number_of_Rental_Days ;
    private double Price_per_Day ;
    private String Payment_Method ;

    //Constructor of the Payment object:
    public Payment(int Payment_Number , int Rental_Number , int Number_of_Rental_Days , double Price_per_Day , String Payment_Method) 
    {
        this.Payment_Number = Payment_Number ;
        this.Rental_Number = Rental_Number ;
        this.Number_of_Rental_Days = Number_of_Rental_Days ;
        this.Price_per_Day = Price_per_Day ;
        this.Payment_Method = Payment_Method ;
    }

    //Getters and setters:
    public int getPayment_Number() 
    {
        return Payment_Number ;
    }

    public void setPayment_Number(int Payment_Number) 
    {
        this.Payment_Number = Payment_Number ;
    }

    public int getRental_Number() 
    {
        return Rental_Number ;
    }

    public void setRental_Number(int Rental_Number) 
    {
        this.Rental_Number = Rental_Number ;
    }

    public int getNumber_of_Rental_Days() 
    {
        return Number_of_Rental_Days ;
    }

    public void setNumber_of_Rental_Days(int Number_of_Rental_Days) 
    {
        this.Number_of_Rental_Days = Number_of_Rental_Days ;
    }

    public double getPrice_per_Day() 
    {
        return Price_per_Day ;
    }

    public void setPrice_per_Day(double Price_per_Day)
    {
        this.Price_per_Day = Price_per_Day ;
    }

    public String getPayment_Method()
    {
        return Payment_Method ;
    }

    public void setPayment_Method(String Payment_Method) 
    {
        this.Payment_Method = Payment_Method ;
    }
}

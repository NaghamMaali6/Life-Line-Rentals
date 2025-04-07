/*This class represents the Involve relation table with its attributes*/

package application ;

public class Involve
{
    private String R_Status ;  
    private int Equipment_Number ;  
    private int Rental_Number ;  

    //Constructor of the Involve object:
    public Involve(String R_Status , int Equipment_Number , int Rental_Number)
    {
        this.R_Status = R_Status ;
        this.Equipment_Number = Equipment_Number ;
        this.Rental_Number = Rental_Number ;
    }

    //Getters and setters:
    public String getR_Status() 
    {
        return R_Status ;
    }

    public void setR_Status(String R_Status) 
    {
        this.R_Status = R_Status ;
    }

    public int getEquipment_Number() 
    {
        return Equipment_Number ;
    }

    public void setEquipment_Number(int Equipment_Number) 
    {
        this.Equipment_Number = Equipment_Number ;
    }

    public int getRental_Number() 
    {
        return Rental_Number ;
    }

    public void setRental_Number(int Rental_Number) 
    {
        this.Rental_Number = Rental_Number ;
    }
}

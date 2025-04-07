/*This class represents the Maintenance_Request table with its attributes*/

package application ;

public class Maintenance_Request 
{
    private int Request_Number ;      //Primary key
    private int Equipment_Number ;    //Foreign key referencing Equipment table
    private String Issue_Description ;
    private String Request_Date ;
    private String M_Status ;
    private int Workshop_Number ;     //Foreign key referencing Workshop table
    private int Invoice_Number ;      //Foreign key referencing Invoice table

    //Constructor of the Maintenance_Request object:
    public Maintenance_Request(int Request_Number , int Equipment_Number , String Issue_Description , String Request_Date , String M_Status , int Workshop_Number , int Invoice_Number) 
    {
        this.Request_Number = Request_Number ;
        this.Equipment_Number = Equipment_Number ;
        this.Issue_Description = Issue_Description;
        this.Request_Date = Request_Date;
        this.M_Status = M_Status;
        this.Workshop_Number = Workshop_Number;
        this.Invoice_Number = Invoice_Number;
    }

    //Getters and setters:
    public int getRequest_Number() 
    {
        return Request_Number ;
    }

    public void setRequest_Number(int Request_Number) 
    {
        this.Request_Number = Request_Number ;
    }

    public int getEquipment_Number() 
    {
        return Equipment_Number ;
    }

    public void setEquipment_Number(int Equipment_Number)
    {
        this.Equipment_Number = Equipment_Number ;
    }

    public String getIssue_Description() 
    {
        return Issue_Description ;
    }

    public void setIssue_Description(String Issue_Description) 
    {
        this.Issue_Description = Issue_Description ;
    }

    public String getRequest_Date() 
    {
        return Request_Date ;
    }

    public void setRequest_Date(String Request_Date) 
    {
        this.Request_Date = Request_Date ;
    }

    public String getM_Status() 
    {
        return M_Status ;
    }

    public void setM_Status(String M_Status) 
    {
        this.M_Status = M_Status ;
    }

    public int getWorkshop_Number() 
    {
        return Workshop_Number ;
    }

    public void setWorkshop_Number(int Workshop_Number) 
    {
        this.Workshop_Number = Workshop_Number ;
    }

    public int getInvoice_Number() 
    {
        return Invoice_Number ;
    }

    public void setInvoice_Number(int Invoice_Number) 
    {
        this.Invoice_Number = Invoice_Number ;
    }
}

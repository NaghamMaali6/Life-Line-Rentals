/*this class represents the Employee table with it's attributes*/

package application ;

import java.util.Date ;  

public class Employee 
{
    private int employeeNumber ;  //primary key 
    private String name ;
    private Date workingStartDate ;
    private Date workingEndDate ;   
    private String password ;

    //constructor of the Employee object:
    public Employee(int employeeNumber , String name , Date workingStartDate , Date workingEndDate , String password) 
    {
        this.employeeNumber = employeeNumber ;
        this.name = name ;
        this.workingStartDate = workingStartDate ;
        this.workingEndDate = workingEndDate ;
        this.password = password ;
    }

    //Getters and Setters:
    public int getEmployeeNumber()
    {
        return employeeNumber ;
    }

    public void setEmployeeNumber(int employeeNumber) 
    {
        this.employeeNumber = employeeNumber ;
    }

    public String getName() 
    {
        return name ;
    }

    public void setName(String name) 
    {
        this.name = name ;
    } 

    public Date getWorkingStartDate() 
    {
        return workingStartDate ;
    }

    public void setWorkingStartDate(Date workingStartDate) 
    {
        this.workingStartDate = workingStartDate ;
    }

    public Date getWorkingEndDate() 
    {
        return workingEndDate ;
    }

    public void setWorkingEndDate(Date workingEndDate) 
    {
        this.workingEndDate = workingEndDate ;
    }

    public String getPassword() 
    {
        return password ;
    }

    public void setPassword(String password) 
    {
        this.password = password ;
    }
    
    /*
    method to test signing-up process
    public void printDetails()  //Method to print Employee table 
    {
        System.out.println("Employee Number: " + employeeNumber) ;
        System.out.println("Name: " + name) ;
        System.out.println("Working Start Date: " + workingStartDate) ;
        System.out.println("Working End Date: " + (workingEndDate != null ? workingEndDate : "Still Working")) ;
        System.out.println("Password: " + password) ;
        System.out.println("-------------------------") ;
    }
    */

}

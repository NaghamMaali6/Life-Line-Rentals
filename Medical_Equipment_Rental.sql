DROP DATABASE Medical_Equipment_Rental ;

CREATE DATABASE Medical_Equipment_Rental ;

USE Medical_Equipment_Rental ;

CREATE TABLE Customer (
    Customer_Number INT ,
    National_ID_Number VARCHAR(20) ,
    Full_Name VARCHAR(200) ,
    Address VARCHAR(300) ,
    Phone_Number VARCHAR(20) ,
    Have_an_Insurance VARCHAR(6) ,
    PRIMARY KEY (Customer_Number) 
);


CREATE TABLE Location (
    Location_Number INT ,
    Address VARCHAR(300) ,
    Phone_Number CHAR(20) ,
    PRIMARY KEY (Location_Number)
);


CREATE TABLE Supplier (
    Supplier_Number INT ,
    S_Name VARCHAR(200) ,
    Phone_Number CHAR(20) ,
    PRIMARY KEY (Supplier_Number)
);


CREATE TABLE Equipment (
    Equipment_Number INT ,
    Equipment_Name VARCHAR(100) ,
    Availability CHAR(20) ,
    Location_Number INT ,
    Supplier_Number INT ,
    Equipment_Use VARCHAR(500) ,
    PRIMARY KEY (Equipment_Number) ,
    FOREIGN KEY (Location_Number) REFERENCES Location(Location_Number) ,
    FOREIGN KEY (Supplier_Number) REFERENCES Supplier(Supplier_Number)
);


CREATE TABLE Employee (
    Employee_Number INT ,
    E_Name VARCHAR(100) ,
    WorkingStart_Date DATE ,
    WorkingEnd_Date DATE ,
    E_Password VARCHAR(20) ,
    PRIMARY KEY (Employee_Number) 
);


CREATE TABLE Invoice (
    Invoice_Number INT ,
    Total_Amount DOUBLE,
    Discount DOUBLE ,
    Payment_Status VARCHAR(100) ,
    Invoice_Date DATE ,
    Due_Date DATE ,
    Invoice_Type CHAR(50) ,
    PRIMARY KEY (Invoice_Number)
);


CREATE TABLE Rental (
    Rental_Number INT ,
    Customer_Number INT ,
    Employee_Number INT ,
    Equipment_Number INT ,
    Invoice_Number INT ,
    Start_Date DATE ,
    End_Date DATE ,
    Contract_Image VARCHAR(200) ,
    PRIMARY KEY (Rental_Number) ,
    FOREIGN KEY (Customer_Number) REFERENCES Customer(Customer_Number) ,
    FOREIGN KEY (Employee_Number) REFERENCES Employee(Employee_Number) ,
    FOREIGN KEY (Equipment_Number) REFERENCES Equipment(Equipment_Number) ,
    FOREIGN KEY (Invoice_Number) REFERENCES Invoice(Invoice_Number)
);


CREATE TABLE Payment (
    Payment_Number INT ,
    Rental_Number INT ,
    Number_of_Rental_Days INT ,
    Price_per_Day DOUBLE ,
    Payment_Method VARCHAR(100) ,
    PRIMARY KEY (Payment_Number) ,
    FOREIGN KEY (Rental_Number) REFERENCES Rental(Rental_Number)
);


CREATE TABLE Workshop (
    Workshop_Number INT ,
    Workshop_Name VARCHAR(200) ,
    Address VARCHAR(300) ,
    Phone_Number CHAR(20) ,
    PRIMARY KEY (Workshop_Number)
);


CREATE TABLE Maintenance_Request (
    Request_Number INT ,
    Equipment_Number INT ,
    Issue_Description VARCHAR(500) ,
    Request_Date DATE ,
    M_Status VARCHAR(50) ,
    Workshop_Number INT ,
    Invoice_Number INT ,
    PRIMARY KEY (Request_Number) ,
    FOREIGN KEY (Equipment_Number) REFERENCES Equipment(Equipment_Number) ,
    FOREIGN KEY (Workshop_Number) REFERENCES Workshop(Workshop_Number) ,
    FOREIGN KEY (Invoice_Number) REFERENCES Invoice(Invoice_Number)
);


CREATE TABLE Involve (
    R_Status VARCHAR(50) ,
    Equipment_Number INT ,
    Rental_Number INT ,
    PRIMARY KEY (Rental_Number , Equipment_Number) ,
    FOREIGN KEY (Rental_Number) REFERENCES Rental(Rental_Number) ,
    FOREIGN KEY (Equipment_Number) REFERENCES Equipment(Equipment_Number) 
);


CREATE TABLE Has (
    Total_Amount DOUBLE ,
    Rental_Number INT ,
    Payment_Number INT ,
    PRIMARY KEY (Rental_Number , Payment_Number) ,
    FOREIGN KEY (Rental_Number) REFERENCES Rental(Rental_Number) ,
    FOREIGN KEY (Payment_Number) REFERENCES Payment(Payment_Number)
);


INSERT INTO Customer (Customer_Number , National_ID_Number , Full_Name , Address , Phone_Number , Have_an_Insurance)
VALUES 
(1 , '123456789' , 'Ahmad Al-Masri' , 'Al-Quds Street - Ramallah - West Bank' , '0591234567', 'yes') ,
(2 , '234567890' , 'Fatima Abu-Zeid' , 'Al-Bireh Road - Al-Bireh - West Bank' , '0591122334' , 'no') ,
(3 , '345678901' , 'Nidal Hasan' , 'Jenin Road - Nablus - West Bank' , '0599988776' , 'yes') ,
(4 , '456789012' , 'Yara Kassem' , 'Tulkarem Street - Tulkarem - West Bank' , '0594321098' , 'no') ,
(5 , '567890123' , 'Omar Nasser' , 'Salfit Road - Salfit - West Bank' , '0598765432' , 'yes') ,
(6 , '678901234' , 'Layla Qasem' , 'Hebron Road - Hebron - West Bank' , '0592233445' , 'no') ;


INSERT INTO Location (Location_Number , Address , Phone_Number)
VALUES 
(1 , 'Palestine Medical Complex - Ramallah - West Bank' , '022982222') ,
(2 , 'Warehouse - Bethlehem - West Bank' , '022355478') ,
(3 , 'Hospital - Nablus - West Bank' , '022220134') ,
(4 , 'Warehouse - Hebron - West Bank' , '022222111') ,
(5 , 'Palestine Medical Complex - Jericho - West Bank' , '022982222') ,
(6 , 'Central Warehouse - Ramallah - West Bank' , '022445566') ;


INSERT INTO Supplier (Supplier_Number , S_Name , Phone_Number)
VALUES 
(1 , 'International Relief Agency' , '0592233445') ,
(2 , 'Red Crescent Society' , '0595566778') ,
(3 , 'Al-Quds Medical Supply' , '0591122334') ,
(4 , 'Bethlehem Medical Supplies' , '0594455667') ,
(5 , 'Jericho Healthcare Suppliers' , '0599988777') ,
(6 , 'Palestinian Aid Organization' , '0597788990') ;


INSERT INTO Equipment (Equipment_Number , Equipment_Name , Availability , Location_Number , Supplier_Number , Equipment_Use)
VALUES 
(1 , 'Wheelchair' , 'available' , 1 , 1 , 'Used for patients with limited mobility') ,
(2 , 'Oxygen Tank' , 'available' , 2 , 2 , 'Used for patients with respiratory issues') ,
(3 , 'Patient Monitor' , 'rented' , 1 , 3 , 'Monitors the vital signs of patients') ,
(4 , 'X-Ray Machine' , 'under maintenance' , 1 , 1 , 'Used for medical imaging of the bones and internal organs') ,
(5 , 'ECG Machine' , 'available' , 3 , 4 , 'Used for measuring the electrical activity of the heart') ,
(6 , 'Infusion Pump' , 'rented' , 2 , 5 , 'Used for administering fluids, medication, or nutrients to patients') ;


INSERT INTO Employee (Employee_Number , E_Name , WorkingStart_Date , WorkingEnd_Date , E_Password)
VALUES 
(1 , 'Khaled Abdel Rahman' , '2020-07-07' , '2021-03-03' , 'R@7uQx2!sB1kVf9') ,
(2 , 'Maya Jamil' , '2021-03-10' , '2022-03-10' , 'Tz#hA8Yp$9qVw8Rz') ,
(3 , 'Sami Khalil' , '2023-03-23' , '2024-05-15' , 'Wg!7iO3eVf^1X5pB') ,
(4 , 'Rania Mahmoud' , '2024-06-27' , '2024-12-01' , 'Qz$9uLm1rE@4Kv2') ,
(5 , 'Faisal Nasser' , '2024-12-02' , NULL , '7jZz^nN4L!wP5z3H') ;


INSERT INTO Invoice (Invoice_Number , Total_Amount , Discount , Payment_Status , Invoice_Date , Due_Date , Invoice_Type)
VALUES 
(1 , 4050.00 , 50.00 , 'paid' , '2021-01-15' , '2021-02-10' , 'rental') , 
(2 , 150.00 , 00.00 , 'pending' , '2024-01-10' , '2024-02-15' , 'maintenance') ,
(3 , 42200.00 , 100.00 , 'pending' , '2021-04-28' , '2022-05-01' , 'rental') ,  
(4 , 36885.00 , 75.00 , 'paid' , '2023-03-27' , '2024-01-01' , 'rental') ,  
(5 , 70.00 , 00.00 , 'paid' , '2024-01-15', '2024-01-26' , 'maintenance') ,
(6 , 1100.00 , 00.00 , 'unpaid' , '2024-02-05' , '2024-02-20' , 'rental') , 
(7 , 34000.00 , 100.00 , 'paid' , '2024-06-29', '2024-12-06' , 'rental') , 
(8 , 12000.00 , 610.00 , 'unpaid' , '2024-12-03' , '2025-03-15' , 'rental') ,  
(9 , 90.00 , 00.00 , 'paid' , '2024-02-12' , '2024-02-15' , 'maintenance') ,
(10 , 200.00 , 00.00 , 'paid' , '2024-02-15' , '2024-03-12' , 'maintenance') ,
(11 , 120.00 , 00.00 , 'paid' , '2024-03-01' , '2024-03-17' , 'maintenance') ,
(12 , 500.00 , 00.00 , 'unpaid' , '2024-03-05' , '2024-06-11' , 'maintenance') ;


INSERT INTO Rental (Rental_Number , Customer_Number , Employee_Number , Equipment_Number , Invoice_Number , Start_Date , End_Date , Contract_Image)
VALUES 
(1 , 1 , 1 , 1 , 1 , '2021-01-05' , '2021-02-15' , 'C:\\Users\\User\\Desktop\\DataBase\\contracts\\Contract1.jpeg') ,
(2 , 2 , 2 , 3 , 3 , '2021-04-18' , '2022-01-25' , 'C:\\Users\\User\\Desktop\\DataBase\\contracts\\Contract2.jpeg') ,
(3 , 3 , 3 , 4 , 4 , '2023-03-27' , '2024-01-29' , 'C:\\Users\\User\\Desktop\\DataBase\\contracts\\Contract3.jpeg') ,
(4 , 4 , 3 , 5 , 6 , '2024-02-05' , '2024-02-15' , 'C:\\Users\\User\\Desktop\\DataBase\\contracts\\Contract4.jpeg') ,
(5 , 5 , 4 , 6 , 7 , '2024-06-29' , '2024-12-01' , 'C:\\Users\\User\\Desktop\\DataBase\\contracts\\Contract5.jpeg') ,
(6 , 6 , 5 , 2 , 8 , '2024-12-03' , '2025-03-10' , 'C:\\Users\\User\\Desktop\\DataBase\\contracts\\Contract6.jpeg') ;


INSERT INTO Payment (Payment_Number , Rental_Number , Number_of_Rental_Days , Price_per_Day , Payment_Method)
VALUES 
(1 , 1 , 41 , 100.00 , 'credit card') ,
(2 , 2 , 282 , 150.00 , 'cash') ,
(3 , 3 , 308 , 120.00 , 'visa') ,
(4 , 4 , 10 , 110.00 , 'credit card') ,
(5 , 5 , 155 , 220.00 , 'charity') ,
(6 , 6 , 97 , 130.00 , 'bank transfer') ;


INSERT INTO Workshop (Workshop_Number , Workshop_Name , Address , Phone_Number)
VALUES 
(1 , 'Al-Bireh Workshop'  , 'Al-Bireh - West Bank' , '022589547') ,
(2 , 'Bethlehem Workshop' , 'Bethlehem - West Bank' , '022358477') ,
(3 , 'Nablus Workshop' , 'Nablus - West Bank' , '022221000') ,
(4 , 'Jericho Workshop' , 'Jericho - West Bank' , '022311000') ,
(5 , 'Ramallah Workshop' , 'Ramallah - West Bank' , '022333555') ,
(6 , 'Hebron Workshop' , 'Hebron - West Bank' , '022445666') ;


INSERT INTO Maintenance_Request (Request_Number , Equipment_Number , Issue_Description , Request_Date , M_Status , Workshop_Number , Invoice_Number)
VALUES 
(1 , 4 , 'X-ray machine not powering on' , '2024-01-10' , 'pending' , 1 , 2) ,
(2 , 3 , 'Patient monitor screen is flickering' , '2024-01-15' , 'complete' , 2 , 5) ,
(3 , 2 , 'Oxygen tank leaking', '2024-02-12' , 'complete' , 3 , 9) , 
(4 , 1 , 'Wheelchair broken', '2024-02-15' , 'complete' , 4 , 10) ,
(5 , 6 , 'Infusion pump malfunctioning' , '2024-03-01' , 'complete' , 5 , 11) ,
(6 , 5 , 'ECG machine calibration error' , '2024-03-05' , 'complete' , 6 , 12) ;


INSERT INTO Involve (R_Status , Equipment_Number , Rental_Number)
VALUES 
('complete' , 1 , 1) ,
('late' , 3 , 2) ,
('complete' , 4 , 3) ,
('active' , 5 , 4) ,
('late' , 2 , 5) ,
('active' , 6 , 6) ;


INSERT INTO Has (Total_Amount , Rental_Number , Payment_Number)
VALUES 
(4050.00 , 1 , 1) ,
(42200.00 , 2 , 2) ,
(36885.00 , 3 , 3) ,
(1100.00 , 4 , 4) ,
(34000.00 , 5 , 5) ,
(12000.00 , 6 , 6) ;
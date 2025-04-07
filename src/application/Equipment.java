package application;

public class Equipment {
    private int EquipmentNumber;
    private String EquipmentName;
    private String Availability;
    private int LocationNumber;
    private int SupplierNumber;
    private String EquipmentUse;

    // Constructor
    public Equipment(int EquipmentNumber,String EquipmentName, String Availability,int LocationNumber,int SupplierNumber,String EquipmentUse) {
        this.EquipmentNumber = EquipmentNumber;
        this.EquipmentName = EquipmentName;
        this.Availability = Availability;
        this.LocationNumber = LocationNumber;
        this.SupplierNumber = SupplierNumber;
        this.EquipmentUse = EquipmentUse;
    }

    // Getter methods
    public int getid() {
        return EquipmentNumber;
    }

    public String get_Equipment_Name() {
        return EquipmentName;
    }

    public String get_Availability() {
        return Availability;
    }

    public int getLocationNumber() {
        return LocationNumber;
    }

    public int getSupplierNumber() {
        return SupplierNumber;
    }

    public String getEquipmentUse() {
        return EquipmentUse;
    }

    // Setter methods
    public void setEquipmentNumber(int EquipmentNumber) {
        this.EquipmentNumber = EquipmentNumber;
    }

    public void set_Equipment_Name(String EquipmentName) {
        this.EquipmentName = EquipmentName;
    }

    public void set_Availability(String Availability) {
        this.Availability = Availability;
    }

    public void setLocationNumber(int LocationNumber) {
        this.LocationNumber = LocationNumber;
    }

    public void setSupplierNumber(int SupplierNumber) {
        this.SupplierNumber = SupplierNumber;
    }

    public void setEquipmentUse(String EquipmentUse) {
        this.EquipmentUse = EquipmentUse;
    }
}
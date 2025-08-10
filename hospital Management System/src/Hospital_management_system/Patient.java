package Hospital_management_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Patient {

    private Connection connection;
    private Scanner scanner ;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient(){
        System.out.println("Enter Patient name: ");
        String name = scanner.next();

        System.out.println("Enter Patient's Age: ");
        int age = scanner.nextInt();

        System.out.println("EnterPatient Age: ");
        String gender = scanner.next();

        try{
            String query = "INSERT INTO patients (name,age,gender) VALUES(?,?,?)";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1,name);
            pst.setInt(2,age);
            pst.setString(3,gender);

            int affectedRows = pst.executeUpdate();
            if(affectedRows >0){
                System.out.println("Patient data entered successfully!");

            }else{
                System.out.println("Failed to add patient!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewPatients(){
        String query = "SELECT * FROM patients";
        try{
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+--------------+-------------------------+----------+----------------+");
            System.out.println("| Patient id   | Name                    | Age      | Gender          ");
            System.out.println("+--------------+-------------------------+----------+----------------+");
            while(rst.next()){
                int id = rst.getInt("id");
                String name = rst.getString("name");
                int age = rst.getInt("age");
                String gender = rst.getString("gender");
                System.out.printf("|%-14s|%-25s|%-10s|%-17s|\n",id,name,age,gender);
                System.out.println("+--------------+-------------------------+----------+----------------+");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean getPatientById(int id){
        String query = "SELECT * FROM patients WHERE id = ?";
        try{
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1,id);
            ResultSet rst = pst.executeQuery();

            if(rst.next()){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

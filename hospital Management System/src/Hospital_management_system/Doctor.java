package Hospital_management_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Doctor {

    private Connection connection;


    public Doctor(Connection connection) {
        this.connection = connection;

    }



    public void viewDoctors(){
        try{
            String query = "SELECT * FROM doctors";
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("+--------------+-------------------------+---------------------------+");
            System.out.println("| Doctor id    | Name                    | Specialization            |");
            System.out.println("+--------------+-------------------------+---------------------------+");
            while(rst.next()){
                int id = rst.getInt("id");
                String name = rst.getString("name");
                String specialization = rst.getString("specialization");
                System.out.printf("| %-12s | %-26s | %-28s|\n",id,name,specialization);
                System.out.println("+--------------+-------------------------+-------------------------+");

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean getDoctorById(int id){
        String query = "SELECT * FROM doctors WHERE id = ?";
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

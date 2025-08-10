package Hospital_management_system;

import javax.print.Doc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url="jdbc:mysql://localhost:3306/hospital";
    private static final String username="root";
    private static final String password = "1234";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try{
            Connection con = DriverManager.getConnection(url,username,password);
            Patient patient = new Patient(con,scanner);
            Doctor doctor = new Doctor(con);

            while(true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM ");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. View Doctors");
                System.out.println("5. Exit!");
                System.out.println("Enter your choice: ");

                int choice = scanner.nextInt();

                switch(choice){
                    case 1: patient.addPatient();
                        System.out.println();
                        break;
                    case 2:patient.viewPatients();
                        System.out.println();
                        break;
                    case 3:doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:bookAppointment(patient,doctor,con,scanner);
                        System.out.println();
                        break;
                    case 5:
                        System.out.println("THANKYOU SO MUCH for using HOSPITAL MANAGEMENT SYSTEM \n" +
                                "Please visit again!!!");
                        return;
                    default:
                        System.out.println("Enter valid choice: ");
                        break;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void bookAppointment(Patient patient,Doctor doctor,Connection connection,Scanner scanner){
        System.out.println("Enter patient ID: ");
        int patientID = scanner.nextInt();
        System.out.println("Enter Doctor ID: ");
        int doctorId = scanner.nextInt();
        System.out.println("Enter appointment date (YYYY-MM-DD");
        String appointmentdate = scanner.next();

        if(patient.getPatientById(patientID) && (doctor.getDoctorById(doctorId))){
            if(checkDoctorAvailability(doctorId,appointmentdate,connection)){
                String appointmentQuery = "INSERT INTO appointments (patient_id,doctor_id, appointment_date) VALUE (?,?,?)";
                try{
                    PreparedStatement pst = connection.prepareStatement(appointmentQuery);
                    pst.setInt(1,patientID);
                    pst.setInt(2,doctorId);
                    pst.setString(3,appointmentdate);

                    int affectedRows = pst.executeUpdate();
                    if(affectedRows >0){
                        System.out.println("Appointment Booked!!!");

                    }else{
                        System.out.println("Failed to book appointment!!!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("Doctor not available on this date!");
            }
        }else{
            System.out.println("Either patient or doctor id does'nt exist!!!");

        }

    }

    public  static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection){
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try{
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1,doctorId);
            pst.setString(2,appointmentDate);
            ResultSet rst = pst.executeQuery();
            if(rst.next()){
                int count = rst.getInt(1);
                if(count==0){
                    return true;
                }else{
                    return false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

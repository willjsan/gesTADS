package src.ui.screen;

import src.ui.GesTADSUI;
import src.util.tools.BroadcastReceiver;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

import java.util.List;
import java.util.MissingFormatArgumentException;

public class RegisterEmployeeScreen extends GesTADSUI {


    private final String TAG = "RegisterEmployeeScreen";

    public RegisterEmployeeScreen(Intent intent) {
        super(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void createView() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "createView");

        System.out.println(formattedTitle("REGISTER"));
        System.out.println(formattedTitle("Bem vindo ao GesTADS"));
        System.out.println(formattedTitle("Vocês está registrando um usuário"));
        System.out.println();

        Intent intent = new Intent(Intent.ACTION_VALIDATE_NEW_USER);

        System.out.print("Please insert your name: ");
        intent.putString(Intent.KEY_EMPLOYEE_NAME, screenGetTextFromUser());

        System.out.print("Please insert your CPF: ");
        intent.putString(Intent.KEY_EMPLOYEE_CPF, screenGetTextFromUser());

        System.out.print("Please insert username: ");
        intent.putString(Intent.KEY_EMPLOYEE_USERNAME, screenGetTextFromUser());

        System.out.print("Please insert password: ");
        intent.putString(Intent.KEY_EMPLOYEE_PASSWORD, getPassword());

        System.out.println("Please insert the position: ");
        System.out.println();
        intent.putString(Intent.KEY_EMPLOYEE_CARGO, getCargo());

        BroadcastReceiver.sendBroadcast(intent);

        onDestroy();
    }

    private String getPassword() {
        //[LAS]

        boolean isPswrdSetd = false;
        String pass = "";
        while (!isPswrdSetd) {
            String pswrd = screenGetTextFromUser();
            System.out.print("Please re-insert password: ");
            String repswrd = screenGetTextFromUser();

            if (!pswrd.equals(repswrd)) {
                System.out.println("password mismatch");
                System.out.print("Please insert password: ");
            } else {
                pass = pswrd;
                isPswrdSetd = true;

            }
        }
        return pass;
    }

    private String getCargo() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "getCargo");

        boolean isPositionSet = false;
        String position = "";
        while (!isPositionSet) {

            try {
                List<String> positions = (List<String>) mContextIntent.getList(Intent.KEY_DATA_CARGOS);

                int listSize = positions.size();
                for (int i = 0; i < positions.size(); i++) {

                    System.out.println(formattedLineMenu(positions.get(i), "[" + i + "] "));
                }

                System.out.print("position: ");
                int input = Integer.parseInt(screenGetTextFromUser());

                if (input >= 0 && input < listSize) {

                    position = positions.get(input);
                    isPositionSet = true;
                } else {
                    throw new MissingFormatArgumentException("array out of boundary");
                }

            } catch (NullPointerException e) {
                if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE) {
                    GesLogger.e(TAG, e.getMessage());
                }
                System.out.println(formattedTitle("erro"));

            } catch (MissingFormatArgumentException e) {
                if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE) {
                    GesLogger.e(TAG, e.getMessage());
                }
                System.out.println(formattedTitle("dado inválido"));
            } catch (NumberFormatException e){
                if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE) {
                    GesLogger.e(TAG, e.getMessage());
                }
                System.out.println(formattedTitle("você não digitou um número"));
            } catch (Exception e){
                if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE) {
                    GesLogger.e(TAG, e.getMessage());
                }
                System.out.println(formattedTitle("Algo deu muito errado"));
            }

        }
        return position;
    }

    @Override
    protected void onDestroy() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "onDestroy");
        super.onDestroy();
    }

}
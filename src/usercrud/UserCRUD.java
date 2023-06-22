package usercrud;
import javax.swing.UIManager;
import usercrud.view.LoginView;

public class UserCRUD {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        LoginView loginView = new LoginView();
        loginView.showView();
    }
}

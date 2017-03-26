import PhoneApp.Phone;
import PhoneApp.PhoneHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import java.util.Scanner;

/**
 * Created by Lenovo Z on 26.03.2017.
 */
public class PhoneClient {

    static Phone phoneImpl;

    public static void main(String[] args) {
        boolean notExit = true;
        Scanner input = new Scanner(System.in);

        try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            String name = "Phone";
            phoneImpl = PhoneHelper.narrow(ncRef.resolve_str(name));
            while (notExit) {
                System.out.println("1 - Add");
                System.out.println("2 - View");
                System.out.println("3 - Delete");
                System.out.println("4 - Edit");
                System.out.println("0 - Exit");
                int ur = Integer.parseInt(input.next());
                switch (ur) {
                    case 1:
                        phoneImpl.addInfo();
                        break;
                    case 2:
                        String []strs =phoneImpl.viewInfo().split(";");
                        for(int i=0;i<strs.length;i++)
                        System.out.println(strs[i]);
                        break;
                    case 3:
                        phoneImpl.deleteInfo();
                        break;
                    case 4:
                        phoneImpl.editInfo();
                        break;
                    case 0:
                        phoneImpl.shutdown();
                        notExit = false;
                        break;
                } // switch
            }
        } catch (Exception e) {
            System.out.println("ERROR : " + e);
            e.printStackTrace(System.out);
        }
    }
}

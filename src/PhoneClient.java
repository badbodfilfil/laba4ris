import PhoneApp.Phone;
import PhoneApp.PhoneHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PhoneClient {

    static Phone phoneImpl;

    public static void main(String[] args) {
        boolean notExit = true;
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int index;
        String note;
        String[] strs;
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
                int ur = Integer.parseInt(input.readLine());
                switch (ur) {
                    case 1:
                        System.out.println("Enter note:");
                        note = input.readLine();
                        phoneImpl.addInfo(note);
                        break;
                    case 2:
                        System.out.println("--------------------------------");
                        System.out.println("Notes:");
                        strs = phoneImpl.viewInfo().split(";");
                        for (int i = 0; i < strs.length; i++)
                            System.out.println(i + 1 + " - " + strs[i]);
                        System.out.println("--------------------------------");
                        break;
                    case 3:
                        System.out.println("--------------------------------");
                        System.out.println("Notes:");
                        strs = phoneImpl.viewInfo().split(";");
                        for (int i = 0; i < strs.length; i++)
                            System.out.println(i + 1 + " - " + strs[i]);
                        System.out.println("--------------------------------");
                        System.out.println("Enter note number:");
                        index = Integer.parseInt(input.readLine());
                        phoneImpl.deleteInfo(index);
                        break;
                    case 4:
                        System.out.println("--------------------------------");
                        System.out.println("Notes:");
                        strs = phoneImpl.viewInfo().split(";");
                        for (int i = 0; i < strs.length; i++)
                            System.out.println(i + 1 + " - " + strs[i]);
                        System.out.println("--------------------------------");
                        System.out.println("Enter note number:");
                        index = Integer.parseInt(input.readLine());
                        System.out.println("Enter note:");
                        note = input.readLine();
                        phoneImpl.editInfo(index, note);
                        break;
                    case 0:
                        phoneImpl.shutdown();
                        notExit = false;
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR : " + e);
            e.printStackTrace(System.out);
        }
    }
}

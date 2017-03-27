import PhoneApp.Phone;
import PhoneApp.PhoneHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import java.util.Scanner;

public class PhoneClient {

    static Phone phoneImpl;

    public static void main(String[] args) {
        boolean notExit = true;
        Scanner input = new Scanner(System.in);
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
                int ur = Integer.parseInt(input.next());
                switch (ur) {
                    case 1:
                        System.out.println("Enter note:");
                        note = input.nextLine();
                        phoneImpl.addInfo(note);
                        break;
                    case 2:
                        System.out.println("Notes:");
                        strs = phoneImpl.viewInfo().split(";");
                        for (int i = 0; i < strs.length; i++)
                            System.out.println(strs[i]);
                        break;
                    case 3:
                        System.out.println("Notes:");
                        strs = phoneImpl.viewInfo().split(";");
                        for (int i = 0; i < strs.length; i++)
                            System.out.println(strs[i]);
                        System.out.println("Enter note number:");
                        index = Integer.parseInt(input.next());
                        phoneImpl.deleteInfo(index);
                        break;
                    case 4:
                        System.out.println("Notes:");
                        strs = phoneImpl.viewInfo().split(";");
                        for (int i = 0; i < strs.length; i++)
                            System.out.println(strs[i]);
                        System.out.println("Enter note number:");
                        index = Integer.parseInt(input.next());
                        System.out.println("Enter note:");
                        note = input.nextLine();
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
        } finally {
            input.close();
        }
    }
}

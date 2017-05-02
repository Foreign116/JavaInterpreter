import java.util.*;
import java.io.*;

public class interpreter {
    public static boolean isint(String s) {
        boolean in = false;
        try {
            Integer.parseInt(s);
            in = true;
        } catch (NumberFormatException t) {
        }
        return in;
    }

    public static boolean isstring(String i) {
        int ii = 0;
        boolean torf = false;
        char[] test = i.toCharArray();
        for (char in : test) {
            if (in == '"') {
                ii++;
            }
        }
        if (ii == 2) {
            torf = true;
        }
        return torf;
    }

    public static void interpreter(String input, String output)
    throws FileNotFoundException, IOException {
        String tempfunname = "";
        int iii=0;
        int funcstodef = 0;
        int cs = 0;
        int cm = 0;
        int funscope = 0;
        int ttff = 0;
        int istherenest = 0;
        Stack<Boolean> inornot = new Stack<Boolean>();
        Stack<String> currentarg = new Stack<String>();
        Stack<String> paramarg = new Stack<String>();
        Stack<String> tempstack = new Stack<String>();
        Stack<String> tempstack2 = new Stack<String>();
        Stack<Stack> queue = new Stack<Stack>();
        Map<String,Stack> funcbindings = new HashMap();
        Stack<String> st = new Stack<String>();
        ArrayList<Stack> sa = new ArrayList<Stack>();
        ArrayList<Map> ma = new ArrayList<Map>();
        ArrayList<Map> fma = new ArrayList<Map>();
        Stack<String> funcionnames = new Stack<String>();
        Map<String, String> storage = new HashMap();
        Stack<Map> funcmap = new Stack<Map>();
        fma.add(funcbindings);
        sa.add(st);
        ma.add(storage);
        File in = new File(input);
        PrintWriter writer = new PrintWriter(output);
        try (BufferedReader br = new BufferedReader(new FileReader(in))) {
            String linetemp;
            while ((linetemp = br.readLine()) != null) {
                tempstack.push(linetemp);
            }
        }
        while(!tempstack.empty()){
            tempstack2.push(tempstack.pop());
        }
        queue.add(tempstack2);
        while(!queue.empty()){
            while (!queue.peek().empty()) {
                String line = queue.peek().pop().toString();
           // System.out.println(line);
                if (line.equals("add")) {
                    if (funcstodef > 0) {
                       Stack<String> temptemp = (Stack)fma.get(funscope).get(tempfunname);
                       temptemp.push(line);
                       fma.get(funscope).put(tempfunname,temptemp);
                   } else if (sa.get(cs).empty())
                   sa.get(cs).push("&&&:error:");
                   else if (sa.get(cs).size() >= 2) {
                    String a = sa.get(cs).pop().toString();
                    String b = sa.get(cs).pop().toString();
                    if (a.contains("@#$") || b.contains("@#$")) {
                        if (a.contains("@#$") && b.contains("@#$")) {
                            boolean aft = false;
                            boolean bft = false;
                            int iofa = 0;
                            int iofb = 0;
                            for (int i = cm; i >= 0; i--) {
                                if (ma.get(i).containsKey(a)) {
                                    aft = true;
                                    iofa = i;
                                    break;

                                }
                            }
                            for (int i = cm; i >= 0; i--) {
                                if (ma.get(i).containsKey(b)) {
                                    bft = true;
                                    iofb = i;
                                    break;

                                }
                            }
                            if (aft && bft) {
                                String aa = ma.get(iofa).get(a).toString();
                                String bb = ma.get(iofb).get(b).toString();
                                if (isint(aa) && isint(bb)) {
                                    int r = Integer.parseInt(bb)
                                    + Integer.parseInt(aa);
                                    sa.get(cs).push(r + "");

                                } else {
                                    sa.get(cs).push(b);
                                    sa.get(cs).push(a);
                                    sa.get(cs).push("&&&:error:");

                                }
                            }

                            else {
                                sa.get(cs).push(b);
                                sa.get(cs).push(a);
                                sa.get(cs).push("&&&:error:");
                            }

                        } else {
                            if (isint(a) || isint(b)) {
                                boolean aft = false;
                                boolean bft = false;
                                int iofa = 0;
                                int iofb = 0;
                                for (int i = cm; i >= 0; i--) {
                                    if (ma.get(i).containsKey(a)) {
                                        aft = true;
                                        iofa = i;
                                        break;

                                    }
                                }
                                for (int i = cm; i >= 0; i--) {
                                    if (ma.get(i).containsKey(b)) {
                                        bft = true;
                                        iofb = i;
                                        break;

                                    }
                                }
                                if (isint(a) && bft) {
                                    String bb = ma.get(iofb).get(b).toString();
                                    if (isint(bb)) {
                                        int r = Integer.parseInt(bb)
                                        + Integer.parseInt(a);
                                        sa.get(cs).push(r + "");
                                    } else {
                                        sa.get(cs).push(b);
                                        sa.get(cs).push(a);
                                        sa.get(cs).push("&&&:error:");
                                    }
                                }

                                else if (isint(b) && aft) {
                                    String aa = ma.get(iofa).get(a).toString();
                                    if (isint(aa)) {
                                        int r = Integer.parseInt(b)
                                        + Integer.parseInt(aa);
                                        sa.get(cs).push(r + "");
                                    } else {
                                        sa.get(cs).push(b);
                                        sa.get(cs).push(a);
                                        sa.get(cs).push("&&&:error:");
                                    }
                                } else {
                                    sa.get(cs).push(b);
                                    sa.get(cs).push(a);
                                    sa.get(cs).push("&&&:error:");
                                }
                            }

                            else {
                                sa.get(cs).push(b);
                                sa.get(cs).push(a);
                                sa.get(cs).push("&&&:error:");
                            }

                        }
                    } else if (!isint(a) || !isint(b)) {
                        sa.get(cs).push(b);
                        sa.get(cs).push(a);
                        sa.get(cs).push("&&&:error:");
                    } else {

                        int r = Integer.parseInt(b) + Integer.parseInt(a);
                        sa.get(cs).push(r + "");
                    }
                } else {
                    sa.get(cs).push("&&&:error:");
                }
            }

            else if (line.contains("fun") && (!line.contains("End")&&!line.contains("inOutFun")) && line.split("\\s+").length==3) {
                if(funcstodef==0){
                    funcstodef++;
                    String split[] = line.split("\\s+");
                    tempfunname ="@#$" + split[1];
                    funcionnames.add(tempfunname);
                    Stack<String> temp2 = new Stack<String>();
                    temp2.push("@#$"+split[2]);
                    fma.get(funscope).put(tempfunname,temp2);
                }
                else{
                    funcstodef++;
                    funscope++;
                    istherenest++;
                    String split[] = line.split("\\s+");
                    tempfunname ="@#$" + split[1];
                    funcionnames.add(tempfunname);
                    Map<String,Stack> temp2 = new HashMap();
                    Stack<String> temp3 = new Stack<String>();
                    fma.add(temp2);
                    temp3.push("@#$"+split[2]);
                    fma.get(funscope).put(tempfunname,temp3);
                }
            }
            else if(line.contains("inOutFun")){
                if(funcstodef==0){
                    funcstodef++;
                    String split[] = line.split("\\s+");
                    tempfunname ="***" + split[1];
                    funcionnames.add(tempfunname);
                    Stack<String> temp2 = new Stack<String>();
                    temp2.push("@#$"+split[2]);
                    paramarg.push("@#$"+split[2]);
                    fma.get(funscope).put(tempfunname,temp2);
                }
                else{
                    funcstodef++;
                    funscope++;
                    istherenest++;
                    String split[] = line.split("\\s+");
                    tempfunname ="***" + split[1];
                    funcionnames.add(tempfunname);
                    Map<String,Stack> temp2 = new HashMap();
                    Stack<String> temp3 = new Stack<String>();
                    fma.add(temp2);
                    temp3.push("@#$"+split[2]);
                    paramarg.push("@#$"+split[2]);
                    fma.get(funscope).put(tempfunname,temp3);
                }


            }

            else if (line.equals("funEnd")) {
                funcstodef--;
                if(funcstodef==0){
                    sa.get(cs).push(":unit:");
                    funscope=0;
                    tempfunname="";

                }
                else{
                    funcionnames.pop();
                    tempfunname = funcionnames.peek();
                    Stack<String> temptemp = (Stack)fma.get(funscope-1).get(tempfunname);
                    temptemp.push(":unit:");
                    fma.get(funscope).put(tempfunname,temptemp);
                }
                //System.out.println(ma.get(cm));
                funcmap.push(new HashMap(ma.get(cm)));
               // System.out.println(funcnamebindings);
            } else if (line.equals("call")) {
                //System.out.println(fma);
                if (funcstodef > 0) {
                  Stack<String> temptemp = (Stack)fma.get(funscope).get(tempfunname);
                  temptemp.push(line);
                  fma.get(funscope).put(tempfunname,temptemp);
              }
              else{
                if(sa.get(cs).size()>=2 && fma.size()!=0){
                    String a = sa.get(cs).pop().toString();// func name 
                    String b = sa.get(cs).pop().toString();// value, name , or fun name
                    String ina = "***" + a.substring(3);
                    System.out.println(ina);
                    String funcnamea = "";
                    String funcnameb = "";
                    boolean aft = false;
                    boolean aaftt = false;
                    boolean bft = false;
                    boolean afuncft = false;
                    boolean bfuncft = false;
                    boolean ainft = false;
                    int inofa = 0;
                    int iofa = 0;
                    int iofaa = 0;
                    int iofb = 0;
                    int ifuncb = 0;
                    int ifunca = 0;
                    if(istherenest==0){
                        for (int i = funscope; i >= 0; i--) {
                            if (fma.get(i).containsKey(ina)) {
                                ainft = true;
                                inofa = i;
                                break;

                            }
                        }
                        for (int i = funscope; i >= 0; i--) {
                            if (fma.get(i).containsKey(a)) {
                                aft = true;
                                iofa = i;
                                break;

                            }
                        }
                        for (int i = cm; i >= 0; i--) {
                            if (ma.get(i).containsKey(b)) {
                                bft = true;
                                iofb = i;
                                break;

                            }
                        }
                        for (int i = cm; i >= 0; i--) {
                            if (ma.get(i).containsKey(a)) {
                                aaftt = true;
                                iofaa = i;
                                break;

                            }
                        }
                        
                        for (int i = funscope; i >= 0; i--) {
                            if (fma.get(i).containsKey(b)) {
                                bfuncft = true;
                                ifuncb = i;
                                break;

                            }
                        }
                    }
                    else{
                        for (int i = funscope+1; i >= 0; i--) {
                            if (fma.get(i).containsKey(ina)) {
                                ainft = true;
                                inofa = i;
                                break;

                            }
                        }
                        for (int i = funscope+1; i >= 0; i--) {
                            if (fma.get(i).containsKey(a)) {
                                aft = true;
                                iofa = i;
                                break;

                            }
                        }
                        for (int i = cm; i >= 0; i--) {
                            if (ma.get(i).containsKey(b)) {
                                bft = true;
                                iofb = i;
                                break;

                            }
                        }
                        for (int i = cm; i >= 0; i--) {
                            if (ma.get(i).containsKey(a)) {
                                aaftt = true;
                                iofaa = i;
                                break;

                            }
                        }
                        
                        for (int i = funscope+1; i >= 0; i--) {
                            if (fma.get(i).containsKey(b)) {
                                bfuncft = true;
                                ifuncb = i;
                                break;

                            }
                        }


                    }

                    if(aaftt){
                        for (int i = funscope; i >= 0; i--) {
                            if (fma.get(i).containsKey(ma.get(iofaa).get(a))) {
                                afuncft = true;
                                ifunca = i;
                                funcnamea = fma.get(ifunca).get(ma.get(iofaa).get(a)).toString();
                                break;

                            }
                        }
                    }

                    if(ainft&&!b.equals("&&&:error:")){
                        System.out.println("hello");
                        if(b.contains("@#$")){
                            if(bft&&!bfuncft){
                                System.out.println("hello");
                                inornot.push(true);
                                String bb = ma.get(iofb).get(b).toString();
                                currentarg.push(b);
                                Stack<String> temptemp =   (Stack)((Stack)fma.get(inofa).get(ina)).clone();
                                Stack<String> temptemp2 = new Stack<String>();
                                while(!temptemp.empty()){
                                    temptemp2.push(temptemp.pop());
                                }
                                String arg = temptemp2.pop();
                                funcmap.peek().put(arg,bb);
                                sa.add(new Stack<String>());

                                ma.add(funcmap.peek());
                                queue.push(temptemp2);
                                cs++;
                                cm++;
                            }
                            else if(bfuncft){
                                Stack<String> temptemp = (Stack)((Stack)fma.get(inofa).get(ina)).clone();
                                Stack<String> temptemp2 = new Stack<String>();
                                while(!temptemp.empty()){
                                    temptemp2.push(temptemp.pop());
                                }
                                String arg = temptemp2.pop();
                                funcmap.peek().put(arg,b);
                                sa.add(new Stack<String>());
                                ma.add(funcmap.peek());
                                queue.push(temptemp2);
                                cs++;
                                cm++;

                            }
                            else{
                                sa.get(cs).push(b);
                                sa.get(cs).push(a);
                                sa.get(cs).push(":error:");   
                            }

                        }
                        else{
                            inornot.push(true);
                            currentarg.push(b);
                            Stack<String> temptemp = (Stack)((Stack)fma.get(iofa).get(a)).clone();
                            Stack<String> temptemp2 = new Stack<String>();
                            while(!temptemp.empty()){
                                temptemp2.push(temptemp.pop());
                            }
                            String arg = temptemp2.pop();
                            funcmap.peek().put(arg,b);
                            sa.add(new Stack<String>());
                            System.out.println(ma);
                            ma.add( new HashMap(funcmap.peek()));
                            queue.push(temptemp2);
                            cs++;
                            cm++;

                        }


                    }
                    else if(aft&&!b.equals("&&&:error:")){
                        if(b.contains("@#$")){
                            if(bft&&!bfuncft){
                                inornot.push(false);
                                String bb = ma.get(iofb).get(b).toString();
                                Stack<String> temptemp =   (Stack)((Stack)fma.get(iofa).get(a)).clone();
                                Stack<String> temptemp2 = new Stack<String>();
                                while(!temptemp.empty()){
                                    temptemp2.push(temptemp.pop());
                                }
                                String arg = temptemp2.pop();
                                funcmap.peek().put(arg,bb);
                                sa.add(new Stack<String>());

                                ma.add(funcmap.peek());
                                queue.push(temptemp2);
                                cs++;
                                cm++;
                            }
                            else if(bfuncft){
                                inornot.push(false);
                                Stack<String> temptemp = (Stack)((Stack)fma.get(iofa).get(a)).clone();
                                Stack<String> temptemp2 = new Stack<String>();
                                while(!temptemp.empty()){
                                    temptemp2.push(temptemp.pop());
                                }
                                String arg = temptemp2.pop();
                                funcmap.peek().put(arg,b);
                                sa.add(new Stack<String>());
                                ma.add(funcmap.peek());
                                queue.push(temptemp2);
                                cs++;
                                cm++;

                            }
                            else{
                                sa.get(cs).push(b);
                                sa.get(cs).push(a);
                                sa.get(cs).push(":error:");   
                            }

                        }
                        else{
                            inornot.push(false);
                            Stack<String> temptemp = (Stack)((Stack)fma.get(iofa).get(a)).clone();
                            Stack<String> temptemp2 = new Stack<String>();
                            while(!temptemp.empty()){
                                temptemp2.push(temptemp.pop());
                            }
                            String arg = temptemp2.pop();
                            funcmap.peek().put(arg,b);
                            sa.add(new Stack<String>());
                            System.out.println(ma);
                            ma.add( new HashMap(funcmap.peek()));
                            queue.push(temptemp2);
                            cs++;
                            cm++;

                        }


                    }
                    else if(afuncft&&!b.equals("&&&:error:")){
                        if(b.contains("@#$")){
                            if(bft&&!bfuncft){
                                inornot.push(false);
                                String bb = ma.get(iofb).get(b).toString();
                                Stack<String> temptemp =   (Stack)((Stack)fma.get(iofa).get(ma.get(iofaa).get(a))).clone();
                                Stack<String> temptemp2 = new Stack<String>();
                                while(!temptemp.empty()){
                                    temptemp2.push(temptemp.pop());
                                }
                                String arg = temptemp2.pop();
                                funcmap.peek().put(arg,bb);
                                sa.add(new Stack<String>());

                                ma.add(funcmap.peek());
                                queue.push(temptemp2);
                                cs++;
                                cm++;
                            }
                            else if(bfuncft){
                                inornot.push(false);
                                Stack<String> temptemp = (Stack)((Stack)fma.get(iofa).get(ma.get(iofaa).get(a))).clone();
                                Stack<String> temptemp2 = new Stack<String>();
                                while(!temptemp.empty()){
                                    temptemp2.push(temptemp.pop());
                                }
                                String arg = temptemp2.pop();
                                funcmap.peek().put(arg,b);
                                sa.add(new Stack<String>());
                                ma.add(funcmap.peek());
                                queue.push(temptemp2);
                                cs++;
                                cm++;

                            }
                            else{
                                sa.get(cs).push(b);
                                sa.get(cs).push(a);
                                sa.get(cs).push(":error:");   
                            }

                        }
                        else{
                            inornot.push(false);
                            Stack<String> temptemp = (Stack)((Stack)fma.get(iofa).get(ma.get(iofaa).get(a))).clone();
                            Stack<String> temptemp2 = new Stack<String>();
                            while(!temptemp.empty()){
                                temptemp2.push(temptemp.pop());
                            }
                            String arg = temptemp2.pop();
                            funcmap.peek().put(arg,b);
                            sa.add(new Stack<String>());
                            System.out.println(ma);
                            ma.add( new HashMap(funcmap.peek()));
                            queue.push(temptemp2);
                            cs++;
                            cm++;

                        }

                    }
                    else{
                        sa.get(cs).push(b);
                        sa.get(cs).push(a);
                        sa.get(cs).push(":error:");
                    }

                }
                else{
                    sa.get(cs).push("&&&:error:");
                }
            }
        }
        else if (line.equals("return")) {
            if(funcstodef>0){
               Stack<String> temptemp = (Stack)fma.get(funscope).get(tempfunname);
               temptemp.push(line);
               fma.get(funscope).put(tempfunname,temptemp);
           }
           
           else{
            String ss = sa.get(cs).pop().toString();
            if(ss.contains("@#$")){
                if(ma.get(cm).containsKey(ss)){
                    String kk = ma.get(cm).get(ss).toString();
                    sa.get(cs-1).push(kk);
                }
                else{

                    sa.get(cs-1).push(ss);
                }
            }
            else{
             sa.get(cs-1).push(ss);
         }
         
     }

 }
 else if (line.equals("mul")) {
    if (funcstodef > 0) {
      Stack<String> temptemp = (Stack)fma.get(funscope).get(tempfunname);
      temptemp.push(line);
      fma.get(funscope).put(tempfunname,temptemp);
  } else if (sa.get(cs).empty())
  sa.get(cs).push("&&&:error:");
  else if (sa.get(cs).size() >= 2) {
    String a = sa.get(cs).pop().toString();
    String b = sa.get(cs).pop().toString();
    if (a.contains("@#$") || b.contains("@#$")) {
        if (a.contains("@#$") && b.contains("@#$")) {
            boolean aft = false;
            boolean bft = false;
            int iofa = 0;
            int iofb = 0;
            for (int i = cm; i >= 0; i--) {
                if (ma.get(i).containsKey(a)) {
                    aft = true;
                    iofa = i;
                    break;

                }
            }
            for (int i = cm; i >= 0; i--) {
                if (ma.get(i).containsKey(b)) {
                    bft = true;
                    iofb = i;
                    break;

                }
            }
            if (aft && bft) {
                String aa = ma.get(iofa).get(a).toString();
                String bb = ma.get(iofb).get(b).toString();
                if (isint(aa) && isint(bb)) {
                    int r = Integer.parseInt(bb)
                                            * Integer.parseInt(aa);
                    sa.get(cs).push(r + "");

                } else {
                    sa.get(cs).push(b);
                    sa.get(cs).push(a);
                    sa.get(cs).push("&&&:error:");

                }
            }

            else {
                sa.get(cs).push(b);
                sa.get(cs).push(a);
                sa.get(cs).push("&&&:error:");
            }

        } else {
            if (isint(a) || isint(b)) {
                boolean aft = false;
                boolean bft = false;
                int iofa = 0;
                int iofb = 0;
                for (int i = cm; i >= 0; i--) {
                    if (ma.get(i).containsKey(a)) {
                        aft = true;
                        iofa = i;
                        break;

                    }
                }
                for (int i = cm; i >= 0; i--) {
                    if (ma.get(i).containsKey(b)) {
                        bft = true;
                        iofb = i;
                        break;

                    }
                }
                if (isint(a) && bft) {
                    String bb = ma.get(iofb).get(b).toString();
                    if (isint(bb)) {
                        int r = Integer.parseInt(bb)
                                                * Integer.parseInt(a);
                        sa.get(cs).push(r + "");
                    } else {
                        sa.get(cs).push(b);
                        sa.get(cs).push(a);
                        sa.get(cs).push("&&&:error:");
                    }
                }

                else if (isint(b) && aft) {
                    String aa = ma.get(iofa).get(a).toString();
                    if (isint(aa)) {
                        int r = Integer.parseInt(b)
                                                * Integer.parseInt(aa);
                        sa.get(cs).push(r + "");
                    } else {
                        sa.get(cs).push(b);
                        sa.get(cs).push(a);
                        sa.get(cs).push("&&&:error:");
                    }
                } else {
                    sa.get(cs).push(b);
                    sa.get(cs).push(a);
                    sa.get(cs).push("&&&:error:");
                }
            }

            else {
                sa.get(cs).push(b);
                sa.get(cs).push(a);
                sa.get(cs).push("&&&:error:");
            }

        }
    } else if (!isint(a) || !isint(b)) {
        sa.get(cs).push(b);
        sa.get(cs).push(a);
        sa.get(cs).push("&&&:error:");
    } else {

        int r = Integer.parseInt(b) * Integer.parseInt(a);
        sa.get(cs).push(r + "");
    }
} else {
    sa.get(cs).push("&&&:error:");
}
}

else if (line.equals("div")) {
    if (funcstodef > 0) {
        Stack<String> temptemp = (Stack)fma.get(funscope).get(tempfunname);
        temptemp.push(line);
        fma.get(funscope).put(tempfunname,temptemp);
    } else if (sa.get(cs).empty())
    sa.get(cs).push("&&&:error:");
    else if (sa.get(cs).size() >= 2) {
        String a = sa.get(cs).pop().toString();
        String b = sa.get(cs).pop().toString();
        if (a.equals("0")) {
            sa.get(cs).push(b);
            sa.get(cs).push(a);
            sa.get(cs).push("&&&:error:");
        } else if (a.contains("@#$") || b.contains("@#$")) {
            if (a.contains("@#$") && b.contains("@#$")) {
                boolean aft = false;
                boolean bft = false;
                int iofa = 0;
                int iofb = 0;
                for (int i = cm; i >= 0; i--) {
                    if (ma.get(i).containsKey(a)) {
                        aft = true;
                        iofa = i;
                        break;

                    }
                }
                for (int i = cm; i >= 0; i--) {
                    if (ma.get(i).containsKey(b)) {
                        bft = true;
                        iofb = i;
                        break;

                    }
                }
                if (aft && bft) {
                    String aa = ma.get(iofa).get(a).toString();
                    String bb = ma.get(iofb).get(b).toString();
                    if (aa.equals("0")) {
                        sa.get(cs).push(b);
                        sa.get(cs).push(a);
                        sa.get(cs).push("&&&:error:");
                    } else if (isint(aa) && isint(bb)) {
                        int r = Integer.parseInt(bb)
                        / Integer.parseInt(aa);
                        sa.get(cs).push(r + "");
                    } else {
                        sa.get(cs).push(b);
                        sa.get(cs).push(a);
                        sa.get(cs).push("&&&:error:");
                    }
                } else {
                    sa.get(cs).push(b);
                    sa.get(cs).push(a);
                    sa.get(cs).push("&&&:error:");
                }
            } else {
                if (isint(a) || isint(b)) {
                    boolean aft = false;
                    boolean bft = false;
                    int iofa = 0;
                    int iofb = 0;
                    for (int i = cm; i >= 0; i--) {
                        if (ma.get(i).containsKey(a)) {
                            aft = true;
                            iofa = i;
                            break;

                        }
                    }
                    for (int i = cm; i >= 0; i--) {
                        if (ma.get(i).containsKey(b)) {
                            bft = true;
                            iofb = i;
                            break;

                        }
                    }
                    if (isint(a) && bft) {
                        String bb = ma.get(iofb).get(b).toString();
                        if (isint(bb) && !a.equals("0")) {
                            int r = Integer.parseInt(bb)
                            / Integer.parseInt(a);
                            sa.get(cs).push(r + "");
                        } else {
                            sa.get(cs).push(b);
                            sa.get(cs).push(a);
                            sa.get(cs).push("&&&:error:");
                        }
                    } else if (isint(b) && aft) {
                        String aa = ma.get(iofa).get(a).toString();
                        if (isint(aa) && !aa.equals("0")) {
                            int r = Integer.parseInt(b)
                            / Integer.parseInt(aa);
                            sa.get(cs).push(r + "");
                        } else {
                            sa.get(cs).push(b);
                            sa.get(cs).push(a);
                            sa.get(cs).push("&&&:error:");
                        }
                    } else {
                        sa.get(cs).push(b);
                        sa.get(cs).push(a);
                        sa.get(cs).push("&&&:error:");
                    }
                } else {
                    sa.get(cs).push(b);
                    sa.get(cs).push(a);
                    sa.get(cs).push("&&&:error:");
                }

            }
        } else if (!isint(a) || !isint(b)) {
            sa.get(cs).push(b);
            sa.get(cs).push(a);
            sa.get(cs).push("&&&:error:");
        } else {

            int r = Integer.parseInt(b) / Integer.parseInt(a);
            sa.get(cs).push(r + "");
        }
    } else {
        sa.get(cs).push("&&&:error:");
    }
} else if (line.equals("and")) {
    if (funcstodef > 0) {
        Stack<String> temptemp = (Stack)fma.get(funscope).get(tempfunname);
        temptemp.push(line);
        fma.get(funscope).put(tempfunname,temptemp);
    } else if (sa.get(cs).empty())
    sa.get(cs).push("&&&:error:");
    else if (sa.get(cs).size() >= 2) {
        String a = sa.get(cs).pop().toString();
        String b = sa.get(cs).pop().toString();
        if ((a.equals("$#@:false:") || a.equals("$#@:true:"))
            && (b.equals("$#@:false:") || b.equals("$#@:true:"))) {
            if (a.equals("$#@:true:") && b.equals("$#@:true:")) {
                sa.get(cs).push("$#@:true:");
            } else {
                sa.get(cs).push("$#@:false:");
            }
        } else if (a.contains("@#$") || b.contains("@#$")) {
            if (a.contains("@#$") && b.contains("@#$")) {
                boolean aft = false;
                boolean bft = false;
                int iofa = 0;
                int iofb = 0;
                for (int i = cm; i >= 0; i--) {
                    if (ma.get(i).containsKey(a)) {
                        aft = true;
                        iofa = i;
                        break;

                    }
                }
                for (int i = cm; i >= 0; i--) {
                    if (ma.get(i).containsKey(b)) {
                        bft = true;
                        iofb = i;
                        break;

                    }
                }
                if (aft && bft) {
                    if ((ma.get(iofa).get(a).toString()
                        .equals("$#@:true:") || ma.get(iofa)
                        .get(a).toString().equals("$#@:false:"))
                        && (ma.get(iofb).get(b).toString()
                            .equals("$#@:true:") || ma
                            .get(iofb).get(b).toString()
                            .equals("$#@:false:"))) {
                        if (ma.get(iofa).get(a).toString()
                            .equals("$#@:true:")
                            && ma.get(iofb).get(b).toString()
                            .equals("$#@:true:")) {
                            sa.get(cs).push("$#@:true:");
                    } else {
                        sa.get(cs).push("$#@:false:");
                    }
                } else {
                    sa.get(cs).push(b);
                    sa.get(cs).push(a);
                    sa.get(cs).push("&&&:error:");
                }
            } else {
                sa.get(cs).push(b);
                sa.get(cs).push(a);
                sa.get(cs).push("&&&:error:");
            }
        } else if (a.contains("@#$")) {
            boolean aft = false;
            int iofa = 0;
            for (int i = cm; i >= 0; i--) {
                if (ma.get(i).containsKey(a)) {
                    aft = true;
                    iofa = i;
                    break;

                }
            }
            if (aft) {
                if ((ma.get(iofa).get(a).toString()
                    .equals("$#@:false:") || ma.get(iofa)
                    .get(a).toString().equals("$#@:true:"))
                    && (b.equals("$#@:true:") || b
                        .equals("$#@:false:"))) {
                    if (ma.get(iofa).get(a).toString()
                        .equals("$#@:true:")
                        && b.equals("$#@:true:")) {
                        sa.get(cs).push("$#@:true:");
                } else {
                    sa.get(cs).push("$#@:false:");
                }
            } else {
                sa.get(cs).push(b);
                sa.get(cs).push(a);
                sa.get(cs).push("&&&:error:");
            }

        } else {
            sa.get(cs).push(b);
            sa.get(cs).push(a);
            sa.get(cs).push("&&&:error:");
        }
    } else {
        boolean bft = false;
        int iofb = 0;
        for (int i = cm; i >= 0; i--) {
            if (ma.get(i).containsKey(b)) {
                bft = true;
                iofb = i;
                break;

            }
        }
        if (bft) {
            if ((ma.get(iofb).get(b).toString()
                .equals("$#@:false:") || ma.get(iofb)
                .get(b).toString().equals("$#@:true:"))
                && (a.equals("$#@:true:") || a
                    .equals("$#@:false:"))) {
                if (ma.get(iofb).get(b).toString()
                    .equals("$#@:true:")
                    && a.equals("$#@:true:")) {
                    sa.get(cs).push("$#@:true:");
            } else {
                sa.get(cs).push("$#@:false:");
            }
        } else {
            sa.get(cs).push(b);
            sa.get(cs).push(a);
            sa.get(cs).push("&&&:error:");
        }

    } else {
        sa.get(cs).push(b);
        sa.get(cs).push(a);
        sa.get(cs).push("&&&:error:");
    }

}
} else {
    sa.get(cs).push(b);
    sa.get(cs).push(a);
    sa.get(cs).push("&&&:error:");
}
} else {
    sa.get(cs).push("&&&:error:");
}
} else if (line.equals("or")) {
    if (funcstodef > 0) {
       Stack<String> temptemp = (Stack)fma.get(funscope).get(tempfunname);
       temptemp.push(line);
       fma.get(funscope).put(tempfunname,temptemp);
   } else if (sa.get(cs).empty())
   sa.get(cs).push("&&&:error:");
   else if (sa.get(cs).size() >= 2) {
    String a = sa.get(cs).pop().toString();
    String b = sa.get(cs).pop().toString();
    if ((a.equals("$#@:false:") || a.equals("$#@:true:"))
        && (b.equals("$#@:false:") || b.equals("$#@:true:"))) {
        if (a.equals("$#@:false:") && b.equals("$#@:false:")) {
            sa.get(cs).push("$#@:false:");
        } else {
            sa.get(cs).push("$#@:true:");
        }
    } else if (a.contains("@#$") || b.contains("@#$")) {
        if (a.contains("@#$") && b.contains("@#$")) {
            boolean aft = false;
            boolean bft = false;
            int iofa = 0;
            int iofb = 0;
            for (int i = cm; i >= 0; i--) {
                if (ma.get(i).containsKey(a)) {
                    aft = true;
                    iofa = i;
                    break;

                }
            }
            for (int i = cm; i >= 0; i--) {
                if (ma.get(i).containsKey(b)) {
                    bft = true;
                    iofb = i;
                    break;

                }
            }
            if (aft && bft) {
                if ((ma.get(iofa).get(a).toString()
                    .equals("$#@:true:") || ma.get(iofa)
                    .get(a).toString().equals("$#@:false:"))
                    && (ma.get(iofb).get(b).toString()
                        .equals("$#@:true:") || ma
                        .get(iofb).get(b).toString()
                        .equals("$#@:false:"))) {
                    if (ma.get(iofa).get(a).toString()
                        .equals("$#@:false:")
                        && ma.get(iofb).get(b).toString()
                        .equals("$#@:false:")) {
                        sa.get(cs).push("$#@:false:");
                } else {
                    sa.get(cs).push("$#@:true:");
                }
            } else {
                sa.get(cs).push(b);
                sa.get(cs).push(a);
                sa.get(cs).push("&&&:error:");
            }
        } else {
            sa.get(cs).push(b);
            sa.get(cs).push(a);
            sa.get(cs).push("&&&:error:");
        }
    } else if (a.contains("@#$")) {
        boolean aft = false;
        int iofa = 0;
        for (int i = cm; i >= 0; i--) {
            if (ma.get(i).containsKey(a)) {
                aft = true;
                iofa = i;
                break;

            }
        }
        if (aft) {
            if ((ma.get(iofa).get(a).toString()
                .equals("$#@:false:") || ma.get(iofa)
                .get(a).toString().equals("$#@:true:"))
                && (b.equals("$#@:true:") || b
                    .equals("$#@:false:"))) {
                if (ma.get(iofa).get(a).toString()
                    .equals("$#@:false:")
                    && b.equals("$#@:false:")) {
                    sa.get(cs).push("$#@:false:");
            } else {
                sa.get(cs).push("$#@:true:");
            }
        } else {
            sa.get(cs).push(b);
            sa.get(cs).push(a);
            sa.get(cs).push("&&&:error:");
        }

    } else {
        sa.get(cs).push(b);
        sa.get(cs).push(a);
        sa.get(cs).push("&&&:error:");
    }
} else {
    boolean bft = false;
    int iofb = 0;
    for (int i = cm; i >= 0; i--) {
        if (ma.get(i).containsKey(b)) {
            bft = true;
            iofb = i;
            break;

        }
    }
    if (bft) {
        if ((ma.get(iofb).get(b).toString()
            .equals("$#@:false:") || ma.get(iofb)
            .get(b).toString().equals("$#@:true:"))
            && ((a.equals("$#@:true:") || a
                .equals("$#@:false:")))) {
            if (ma.get(iofb).get(b).toString()
                .equals("$#@:false:")
                && a.equals("$#@:false:")) {
                sa.get(cs).push("$#@:false:");
        } else {
            sa.get(cs).push("$#@:true:");
        }
    } else {
        sa.get(cs).push(b);
        sa.get(cs).push(a);
        sa.get(cs).push("&&&:error:");
    }

} else {
    sa.get(cs).push(b);
    sa.get(cs).push(a);
    sa.get(cs).push("&&&:error:");
}

}
} else {
    sa.get(cs).push(b);
    sa.get(cs).push(a);
    sa.get(cs).push("&&&:error:");
}
} else {
    sa.get(cs).push("&&&:error:");
}
} else if (line.equals("not")) {
    if (funcstodef > 0) {
       Stack<String> temptemp = (Stack)fma.get(funscope).get(tempfunname);
       temptemp.push(line);
       fma.get(funscope).put(tempfunname,temptemp);               
   } else if (sa.get(cs).empty())
   sa.get(cs).push("&&&:error:");
   else if (sa.get(cs).size() >= 1) {
    String a = sa.get(cs).pop().toString();
    if (a.equals("$#@:true:") || a.equals("$#@:false:")) {
        if (a.equals("$#@:true:")) {
            sa.get(cs).push("$#@:false:");
        } else {
            sa.get(cs).push("$#@:true:");
        }
    } else if (a.contains("@#$")) {
        boolean aft = false;
        int iofa = 0;
        for (int i = cm; i >= 0; i--) {
            if (ma.get(i).containsKey(a)) {
                aft = true;
                iofa = i;
                break;

            }
        }
        if (aft) {
            if (ma.get(iofa).get(a).toString()
                .equals("$#@:true:")
                || ma.get(iofa).get(a).toString()
                .equals("$#@:false:")) {
                if (ma.get(iofa).get(a).toString()
                    .equals("$#@:true:")) {
                    sa.get(cs).push("$#@:false:");
            } else {
                sa.get(cs).push("$#@:true:");
            }

        } else {
            sa.get(cs).push(a);
            sa.get(cs).push("&&&:error:");
        }

    } else {
        sa.get(cs).push(a);
        sa.get(cs).push("&&&:error:");
    }
} else {
    sa.get(cs).push(a);
    sa.get(cs).push("&&&:error:");
}
} else {
    sa.get(cs).push("&&&:error:");
}
}

else if (line.equals("rem")) {
    if (funcstodef > 0) {
        Stack<String> temptemp = (Stack)fma.get(funscope).get(tempfunname);
        temptemp.push(line);
        fma.get(funscope).put(tempfunname,temptemp);
    } else if (sa.get(cs).empty())
    sa.get(cs).push("&&&:error:");
    else if (sa.get(cs).size() >= 2) {
        String a = sa.get(cs).pop().toString();
        String b = sa.get(cs).pop().toString();
        if (a.equals("0")) {
            sa.get(cs).push(b);
            sa.get(cs).push(a);
            sa.get(cs).push("&&&:error:");
        } else if (a.contains("@#$") || b.contains("@#$")) {
            if (a.contains("@#$") && b.contains("@#$")) {
                boolean aft = false;
                boolean bft = false;
                int iofa = 0;
                int iofb = 0;
                for (int i = cm; i >= 0; i--) {
                    if (ma.get(i).containsKey(a)) {
                        aft = true;
                        iofa = i;
                        break;

                    }
                }
                for (int i = cm; i >= 0; i--) {
                    if (ma.get(i).containsKey(b)) {
                        bft = true;
                        iofb = i;
                        break;

                    }
                }
                if (aft && bft) {
                    String aa = ma.get(iofa).get(a).toString();
                    String bb = ma.get(iofb).get(b).toString();
                    if (aa.equals("0")) {
                        sa.get(cs).push(b);
                        sa.get(cs).push(a);
                        sa.get(cs).push("&&&:error:");
                    } else if (isint(aa) && isint(bb)) {
                        int r = Integer.parseInt(bb)
                        % Integer.parseInt(aa);
                        sa.get(cs).push(r + "");
                    } else {
                        sa.get(cs).push(b);
                        sa.get(cs).push(a);
                        sa.get(cs).push("&&&:error:");
                    }
                } else {
                    sa.get(cs).push(b);
                    sa.get(cs).push(a);
                    sa.get(cs).push("&&&:error:");
                }
            } else {
                if (isint(a) || isint(b)) {
                    boolean aft = false;
                    boolean bft = false;
                    int iofa = 0;
                    int iofb = 0;
                    for (int i = cm; i >= 0; i--) {
                        if (ma.get(i).containsKey(a)) {
                            aft = true;
                            iofa = i;
                            break;

                        }
                    }
                    for (int i = cm; i >= 0; i--) {
                        if (ma.get(i).containsKey(b)) {
                            bft = true;
                            iofb = i;
                            break;

                        }
                    }
                    if (isint(a) && bft) {
                        String bb = ma.get(iofb).get(b).toString();
                        if (isint(bb) && !a.equals("0")) {
                            int r = Integer.parseInt(bb)
                            % Integer.parseInt(a);
                            sa.get(cs).push(r + "");
                        } else {
                            sa.get(cs).push(b);
                            sa.get(cs).push(a);
                            sa.get(cs).push("&&&:error:");
                        }
                    } else if (isint(b) && aft) {
                        String aa = ma.get(iofa).get(a).toString();
                        if (isint(aa) && !aa.equals("0")) {
                            int r = Integer.parseInt(b)
                            % Integer.parseInt(aa);
                            sa.get(cs).push(r + "");
                        } else {
                            sa.get(cs).push(b);
                            sa.get(cs).push(a);
                            sa.get(cs).push("&&&:error:");
                        }
                    } else {
                        sa.get(cs).push(b);
                        sa.get(cs).push(a);
                        sa.get(cs).push("&&&:error:");
                    }
                } else {
                    sa.get(cs).push(b);
                    sa.get(cs).push(a);
                    sa.get(cs).push("&&&:error:");
                }

            }
        } else if (!isint(a) || !isint(b)) {
            sa.get(cs).push(b);
            sa.get(cs).push(a);
            sa.get(cs).push("&&&:error:");
        }

        else {

            int r = Integer.parseInt(b) % Integer.parseInt(a);
            sa.get(cs).push(r + "");
        }
    } else {
        sa.get(cs).push("&&&:error:");
    }
}

else if (line.indexOf("push") == 0) {
    if (funcstodef > 0) {
     Stack<String> temptemp = (Stack)fma.get(funscope).get(tempfunname);
     temptemp.push(line);
     fma.get(funscope).put(tempfunname,temptemp);
 } else {
    String s = line.replace("push", "");
    boolean torf = isstring(s);
    String t = s.replace("\"", "");
    String tt = t.substring(1);
    if (!isint(tt) && torf == false
        && (!tt.equals(":true:") || !tt.equals(":false:"))) {
        tt = "@#$" + tt;
}

int i = 0;
for (int j = 0; j < tt.length(); j++) {
    if (tt.charAt(j) == '.') {
        i++;
    }

}
if (t.equals("-0")) {
    sa.get(cs).push("0");
} else if (tt.contains("@#$")
    && !Character.isLetter(tt.charAt(3))) {
    sa.get(cs).push("&&&:error:");
} else if (i > 0) {
    sa.get(cs).push("&&&:error:");
} else {
    sa.get(cs).push(tt);
}
}
} else if (line.equals("bind")) {
    if (funcstodef > 0) {
     Stack<String> temptemp = (Stack)fma.get(funscope).get(tempfunname);
     temptemp.push(line);
     fma.get(funscope).put(tempfunname,temptemp);
 } else if (sa.get(cs).empty())
 sa.get(cs).push("&&&:error:");
 else if (sa.get(cs).size() >= 2) {
    String a = sa.get(cs).pop().toString();
    String b = sa.get(cs).pop().toString();
    if (!a.equals("&&&:error:") && b.contains("@#$")) {
        if (a.contains("@#$")) {
            boolean aft = false;
            int iofa = 0;
            for (int i = cm; i >= 0; i--) {
                if (ma.get(i).containsKey(a)) {
                    aft = true;
                    iofa = i;
                    break;

                }
            }

            if (aft) {
                String value = ma.get(iofa).get(a).toString();
                ma.get(cm).put(b, value);
                sa.get(cs).push(":unit:");
            } else {
                sa.get(cs).push(b);
                sa.get(cs).push(a);
                sa.get(cs).push("&&&:error:");
            }
        } else {

            ma.get(cm).put(b, a);
            sa.get(cs).push(":unit:");

        }
    } else {
        sa.get(cs).push(b);
        sa.get(cs).push(a);
        sa.get(cs).push("&&&:error:");
    }

} else {
    sa.get(cs).push("&&&:error:");
}
}

else if (line.equals("sub")) {
    if (funcstodef > 0) {
        Stack<String> temptemp = (Stack)fma.get(funscope).get(tempfunname);
        temptemp.push(line);
        fma.get(funscope).put(tempfunname,temptemp);
    } else if (sa.get(cs).empty())
    sa.get(cs).push("&&&:error:");
    else if (sa.get(cs).size() >= 2) {
        String a = sa.get(cs).pop().toString();
        String b = sa.get(cs).pop().toString();
        if (a.contains("@#$") || b.contains("@#$")) {
            if (a.contains("@#$") && b.contains("@#$")) {
                boolean aft = false;
                boolean bft = false;
                int iofa = 0;
                int iofb = 0;
                for (int i = cm; i >= 0; i--) {
                    if (ma.get(i).containsKey(a)) {
                        aft = true;
                        iofa = i;
                        break;

                    }
                }
                for (int i = cm; i >= 0; i--) {
                    if (ma.get(i).containsKey(b)) {
                        bft = true;
                        iofb = i;
                        break;

                    }
                }
                if (aft && bft) {
                    String aa = ma.get(iofa).get(a).toString();
                    String bb = ma.get(iofb).get(b).toString();
                    if (isint(aa) && isint(bb)) {
                        int r = Integer.parseInt(bb)
                        - Integer.parseInt(aa);
                        sa.get(cs).push(r + "");

                    } else {
                        sa.get(cs).push(b);
                        sa.get(cs).push(a);
                        sa.get(cs).push("&&&:error:");

                    }
                }

                else {
                    sa.get(cs).push(b);
                    sa.get(cs).push(a);
                    sa.get(cs).push("&&&:error:");
                }

            } else {
                if (isint(a) || isint(b)) {
                    boolean aft = false;
                    boolean bft = false;
                    int iofa = 0;
                    int iofb = 0;
                    for (int i = cm; i >= 0; i--) {
                        if (ma.get(i).containsKey(a)) {
                            aft = true;
                            iofa = i;
                            break;

                        }
                    }
                    for (int i = cm; i >= 0; i--) {
                        if (ma.get(i).containsKey(b)) {
                            bft = true;
                            iofb = i;
                            break;

                        }
                    }
                    if (isint(a) && bft) {
                        String bb = ma.get(iofb).get(b).toString();
                        if (isint(bb)) {
                            int r = Integer.parseInt(bb)
                            - Integer.parseInt(a);
                            sa.get(cs).push(r + "");
                        } else {
                            sa.get(cs).push(b);
                            sa.get(cs).push(a);
                            sa.get(cs).push("&&&:error:");
                        }
                    }

                    else if (isint(b) && aft) {
                        String aa = ma.get(iofa).get(a).toString();
                        if (isint(aa)) {
                            int r = Integer.parseInt(b)
                            - Integer.parseInt(aa);
                            sa.get(cs).push(r + "");
                        } else {
                            sa.get(cs).push(b);
                            sa.get(cs).push(a);
                            sa.get(cs).push("&&&:error:");
                        }
                    } else {
                        sa.get(cs).push(b);
                        sa.get(cs).push(a);
                        sa.get(cs).push("&&&:error:");
                    }
                }

                else {
                    sa.get(cs).push(b);
                    sa.get(cs).push(a);
                    sa.get(cs).push("&&&:error:");
                }

            }
        } else if (!isint(a) || !isint(b)) {
            sa.get(cs).push(b);
            sa.get(cs).push(a);
            sa.get(cs).push("&&&:error:");
        } else {

            int r = Integer.parseInt(b) - Integer.parseInt(a);
            sa.get(cs).push(r + "");
        }
    } else {
        sa.get(cs).push("&&&:error:");
    }
} else if (line.equals("let")) {
    if (funcstodef > 0) {
        Stack<String> temptemp = (Stack)fma.get(funscope).get(tempfunname);
        temptemp.push(line);
        fma.get(funscope).put(tempfunname,temptemp);
    } else {
        cs++;
        cm++;
        sa.add(new Stack<String>());
        Map<String, String> temp = new HashMap();
        ma.add(temp);
    }
} else if (line.equals("end")) {
    if (funcstodef > 0) {
        Stack<String> temptemp = (Stack)fma.get(funscope).get(tempfunname);
        temptemp.push(line);
        fma.get(funscope).put(tempfunname,temptemp);
    } else {
        cs--;
        cm--;
        sa.get(cs).push(sa.get(cs + 1).pop().toString());
        fma.clear();
    }
} else if (line.equals("if")) {
    if (funcstodef > 0) {
     Stack<String> temptemp = (Stack)fma.get(funscope).get(tempfunname);
     temptemp.push(line);
     fma.get(funscope).put(tempfunname,temptemp);                
 } else if (sa.get(cs).empty())
 sa.get(cs).push("&&&:error:");
 else if (sa.get(cs).size() >= 3) {
    String x = sa.get(cs).pop().toString();
    String y = sa.get(cs).pop().toString();
    String z = sa.get(cs).pop().toString();
    if (z.equals("$#@:true:") || z.equals("$#@:false:")) {
        if (z.equals("$#@:true:")) {
            sa.get(cs).push(x);
        } else {
            sa.get(cs).push(y);
        }
    } else if (z.contains("@#$")) {
        boolean zft = false;
        int iofz = 0;
        for (int i = cm; i >= 0; i--) {
            if (ma.get(i).containsKey(z)) {
                zft = true;
                iofz = i;
                break;

            }
        }
        if (zft) {
            if (ma.get(iofz).get(z).equals("$#@:true:")
                || ma.get(iofz).get(z).equals("$#@:false:")) {
                if (ma.get(iofz).get(z).equals("$#@:true:")) {
                    sa.get(cs).push(x);
                } else {
                    sa.get(cs).push(y);
                }
            } else {
                sa.get(cs).push(z);
                sa.get(cs).push(y);
                sa.get(cs).push(x);
                sa.get(cs).push("&&&:error:");
            }
        } else {
            sa.get(cs).push(z);
            sa.get(cs).push(y);
            sa.get(cs).push(x);
            sa.get(cs).push("&&&:error:");
        }
    } else {
        sa.get(cs).push(z);
        sa.get(cs).push(y);
        sa.get(cs).push(x);
        sa.get(cs).push("&&&:error:");
    }

} else {
    sa.get(cs).push("&&&:error:");
}
}

else if (line.equals("pop")) {
    if (funcstodef > 0) {
     Stack<String> temptemp = (Stack)fma.get(funscope).get(tempfunname);
     temptemp.push(line);
     fma.get(funscope).put(tempfunname,temptemp);
 } else if (sa.get(cs).empty())
 sa.get(cs).push("&&&:error:");
 else {
    sa.get(cs).pop().toString();
}
}

else if (line.equals(":false:") || line.equals(":true:")) {
    if (funcstodef > 0) {
        Stack<String> temptemp = (Stack)fma.get(funscope).get(tempfunname);
        temptemp.push(line);
        fma.get(funscope).put(tempfunname,temptemp);
    } else {
        sa.get(cs).push("$#@" + line);
    }
}
else if(line.equals(":unit:")){
    sa.get(cs).push(":unit:");
}

else if (line.equals(":error:")) {
    if (funcstodef > 0) {
        Stack<String> temptemp = (Stack)fma.get(funscope).get(tempfunname);
        temptemp.push(line);
        fma.get(funscope).put(tempfunname,temptemp);
    } else {
        sa.get(cs).push("&&&:error:");
    }
}

else if (line.equals("neg")) {
    if (funcstodef > 0) {
       Stack<String> temptemp = (Stack)fma.get(funscope).get(tempfunname);
       temptemp.push(line);
       fma.get(funscope).put(tempfunname,temptemp);
   } else if (sa.get(cs).empty()) {
    sa.get(cs).push("&&&:error:");
} else {
    String a = sa.get(cs).pop().toString();
    if (a.contains("@#$")) {
        boolean aft = false;
        int iofa = 0;
        for (int i = cm; i >= 0; i--) {
            if (ma.get(i).containsKey(a)) {
                aft = true;
                iofa = i;
                break;

            }
        }
        if (aft) {
            if (isint(ma.get(iofa).get(a).toString())) {
                if (ma.get(iofa).get(a).toString().equals("0")) {
                                    // ma.get(iofa).put(a,"0");
                    sa.get(cs).push("0");
                } else if (ma.get(iofa).get(a).toString()
                    .charAt(0) == '-') {
                    String t = ma.get(iofa).get(a).toString()
                    .substring(1);
                                    // ma.get(iofa).put(a,t);
                    sa.get(cs).push(t);
                } else {
                    String t = "-"
                    + ma.get(iofa).get(a).toString();
                                    // ma.get(iofa).put(a,t);
                    sa.get(cs).push(t);
                }

            } else {
                sa.get(cs).push(a);
                sa.get(cs).push("&&&:error:");
            }

        } else {
            sa.get(cs).push(a);
            sa.get(cs).push("&&&:error:");
        }
    } else if (!isint(a)) {
        sa.get(cs).push(a);
        sa.get(cs).push("&&&:error:");
    } else if (a.equals("0")) {
        sa.get(cs).push("0");
    } else if (a.charAt(0) == '-') {
        String t = a.substring(1);
        sa.get(cs).push(t);
    } else {
        String t = "-" + a;
        sa.get(cs).push(t);

    }
}

} else if (line.equals("equal")) {
    if (funcstodef > 0) {
     Stack<String> temptemp = (Stack)fma.get(funscope).get(tempfunname);
     temptemp.push(line);
     fma.get(funscope).put(tempfunname,temptemp);
 } else if (sa.get(cs).empty())
 sa.get(cs).push("&&&:error:");
 else if (sa.get(cs).size() >= 2) {
    String a = sa.get(cs).pop().toString();
    String b = sa.get(cs).pop().toString();
    if (a.contains("@#$") || b.contains("@#$")) {
        if (a.contains("@#$") && b.contains("@#$")) {
            boolean aft = false;
            boolean bft = false;
            int iofa = 0;
            int iofb = 0;
            for (int i = cm; i >= 0; i--) {
                if (ma.get(i).containsKey(a)) {
                    aft = true;
                    iofa = i;
                    break;

                }
            }
            for (int i = cm; i >= 0; i--) {
                if (ma.get(i).containsKey(b)) {
                    bft = true;
                    iofb = i;
                    break;

                }
            }
            if (aft && bft) {
                if (isint(ma.get(iofa).get(a).toString())
                    && isint(ma.get(iofb).get(b).toString())) {
                    int aa = Integer.parseInt(ma.get(iofa)
                        .get(a).toString());
                int bb = Integer.parseInt(ma.get(iofb)
                    .get(b).toString());
                if (aa == bb) {
                    sa.get(cs).push("$#@:true:");
                } else {
                    sa.get(cs).push("$#@:false:");
                }
            } else {
                sa.get(cs).push(b);
                sa.get(cs).push(a);
                sa.get(cs).push("&&&:error:");
            }
        } else {
            sa.get(cs).push(b);
            sa.get(cs).push(a);
            sa.get(cs).push("&&&:error:");
        }
    } else {
        if (a.contains("@#$")) {
            boolean aft = false;
            int iofa = 0;
            for (int i = cm; i >= 0; i--) {
                if (ma.get(i).containsKey(a)) {
                    aft = true;
                    iofa = i;
                    break;

                }
            }
            if (aft && isint(b)) {
                if (isint(ma.get(iofa).get(a).toString())) {
                    int aa = Integer.parseInt(ma.get(iofa)
                        .get(a).toString());
                    int bb = Integer.parseInt(b);
                    if (aa == bb) {
                        sa.get(cs).push("$#@:true:");
                    } else {
                        sa.get(cs).push("$#@:false:");
                    }

                } else {
                    sa.get(cs).push(b);
                    sa.get(cs).push(a);
                    sa.get(cs).push("&&&:error:");
                }
            } else {
                sa.get(cs).push(b);
                sa.get(cs).push(a);
                sa.get(cs).push("&&&:error:");
            }
        } else if (b.contains("@#$")) {
            boolean bft = false;
            int iofb = 0;
            for (int i = cm; i >= 0; i--) {
                if (ma.get(i).containsKey(b)) {
                    bft = true;
                    iofb = i;
                    break;

                }
            }
            if (bft && isint(a)) {
                if (isint(ma.get(iofb).get(b).toString())) {
                    int aa = Integer.parseInt(a);
                    int bb = Integer.parseInt(ma.get(iofb)
                        .get(b).toString());
                    if (aa == bb) {
                        sa.get(cs).push("$#@:true:");
                    } else {
                        sa.get(cs).push("$#@:false:");
                    }

                } else {
                    sa.get(cs).push(b);
                    sa.get(cs).push(a);
                    sa.get(cs).push("&&&:error:");
                }
            } else {
                sa.get(cs).push(b);
                sa.get(cs).push(a);
                sa.get(cs).push("&&&:error:");
            }

        } else {
            sa.get(cs).push(b);
            sa.get(cs).push(a);
            sa.get(cs).push("&&&:error:");

        }
    }
} else if (!isint(a) || !isint(b)) {
    sa.get(cs).push(b);
    sa.get(cs).push(a);
    sa.get(cs).push("&&&:error:");
} else {
    int aa = Integer.parseInt(a);
    int bb = Integer.parseInt(b);
    if (aa == bb) {
        sa.get(cs).push("$#@:true:");
    } else {
        sa.get(cs).push("$#@:false:");
    }
}
}
} else if (line.equals("lessThan")) {
    if (funcstodef > 0) {
     Stack<String> temptemp = (Stack)fma.get(funscope).get(tempfunname);
     temptemp.push(line);
     fma.get(funscope).put(tempfunname,temptemp);
 } else if (sa.get(cs).empty())
 sa.get(cs).push("&&&:error:");
 else if (sa.get(cs).size() >= 2) {
    String a = sa.get(cs).pop().toString();
    String b = sa.get(cs).pop().toString();
    if (a.contains("@#$") || b.contains("@#$")) {
        if (a.contains("@#$") && b.contains("@#$")) {
            boolean aft = false;
            boolean bft = false;
            int iofa = 0;
            int iofb = 0;
            for (int i = cm; i >= 0; i--) {
                if (ma.get(i).containsKey(a)) {
                    aft = true;
                    iofa = i;
                    break;

                }
            }
            for (int i = cm; i >= 0; i--) {
                if (ma.get(i).containsKey(b)) {
                    bft = true;
                    iofb = i;
                    break;

                }
            }
            if (aft && bft) {
                if (isint(ma.get(iofa).get(a).toString())
                    && isint(ma.get(iofb).get(b).toString())) {
                    int aa = Integer.parseInt(ma.get(iofa)
                        .get(a).toString());
                int bb = Integer.parseInt(ma.get(iofb)
                    .get(b).toString());
                if (aa > bb) {
                    sa.get(cs).push("$#@:true:");
                } else {
                    sa.get(cs).push("$#@:false:");
                }
            } else {
                sa.get(cs).push(b);
                sa.get(cs).push(a);
                sa.get(cs).push("&&&:error:");
            }
        } else {
            sa.get(cs).push(b);
            sa.get(cs).push(a);
            sa.get(cs).push("&&&:error:");
        }
    } else {
        if (a.contains("@#$")) {
            boolean aft = false;
            int iofa = 0;
            for (int i = cm; i >= 0; i--) {
                if (ma.get(i).containsKey(a)) {
                    aft = true;
                    iofa = i;
                    break;

                }
            }
            if (aft && isint(b)) {
                if (isint(ma.get(iofa).get(a).toString())) {
                    int aa = Integer.parseInt(ma.get(iofa)
                        .get(a).toString());
                    int bb = Integer.parseInt(b);
                    if (aa > bb) {
                        sa.get(cs).push("$#@:true:");
                    } else {
                        sa.get(cs).push("$#@:false:");
                    }

                } else {
                    sa.get(cs).push(b);
                    sa.get(cs).push(a);
                    sa.get(cs).push("&&&:error:");
                }
            } else {
                sa.get(cs).push(b);
                sa.get(cs).push(a);
                sa.get(cs).push("&&&:error:");
            }
        } else if (b.contains("@#$")) {
            boolean bft = false;
            int iofb = 0;
            for (int i = cm; i >= 0; i--) {
                if (ma.get(i).containsKey(b)) {
                    bft = true;
                    iofb = i;
                    break;

                }
            }
            if (bft && isint(a)) {
                if (isint(ma.get(iofb).get(b).toString())) {
                    int aa = Integer.parseInt(a);
                    int bb = Integer.parseInt(ma.get(iofb)
                        .get(b).toString());
                    if (aa > bb) {
                        sa.get(cs).push("$#@:true:");
                    } else {
                        sa.get(cs).push("$#@:false:");
                    }

                } else {
                    sa.get(cs).push(b);
                    sa.get(cs).push(a);
                    sa.get(cs).push("&&&:error:");
                }
            } else {
                sa.get(cs).push(b);
                sa.get(cs).push(a);
                sa.get(cs).push("&&&:error:");
            }

        } else {
            sa.get(cs).push(b);
            sa.get(cs).push(a);
            sa.get(cs).push("&&&:error:");
        }
    }
} else if (!isint(a) || !isint(b)) {
    sa.get(cs).push(b);
    sa.get(cs).push(a);
    sa.get(cs).push("&&&:error:");
} else {
    int aa = Integer.parseInt(a);
    int bb = Integer.parseInt(b);
    if (aa > bb) {
        sa.get(cs).push("$#@:true:");
    } else {
        sa.get(cs).push("$#@:false:");
    }
}
}
} else if (line.equals("swap")) {
    if (funcstodef > 0) {
     Stack<String> temptemp = (Stack)fma.get(funscope).get(tempfunname);
     temptemp.push(line);
     fma.get(funscope).put(tempfunname,temptemp);
 } else if (sa.get(cs).empty()) {
    sa.get(cs).push("&&&:error:");
} else {
    if (sa.get(cs).size() == 1) {
        sa.get(cs).push("&&&:error:");
    } else {
        String a = sa.get(cs).pop().toString();
        String b = sa.get(cs).pop().toString();
        sa.get(cs).push(a);
        sa.get(cs).push(b);
    }

}
}

else if (line.equals("quit")) {
                // System.out.println(ma.get(cm));
    while (!sa.get(cs).empty()) {
        String a = sa.get(cs).pop().toString();
        if (a.contains("@#$")) {
            String b = a.replace("@#$", "");
            writer.println(b);
        } else if (a.contains("$#@")) {
            String c = a.replace("$#@", "");
            writer.println(c);
        } else if (a.contains("&&&")) {
            String d = a.replace("&&&", "");
            writer.println(d);
        }

        else {
            writer.println(a);
        }
    }
}

}
if(queue.peek().empty()){
    if(queue.size()>1){
        System.out.println(inornot);
        if(inornot.pop()==true){
            String altarg = paramarg.pop();
            String realarg = currentarg.pop();
            String valuearg = ma.get(cm).get(altarg).toString();
            ma.get(cm-1).put(realarg,valuearg);
        }
    }
    System.out.println(queue.size());
    System.out.println(cm + " " + cs);
    queue.pop();
    ma.remove(ma.size()-1);
    sa.remove(sa.size()-1);
    cm--;
    cs--;   
}
}
writer.close();
}
//public static void main(String[] args) throws FileNotFoundException,
//IOException {
  //  interpreter("/home/foreign/txtfiles/input.txt","/home/foreign/txtfiles/output.txt");
//}
}

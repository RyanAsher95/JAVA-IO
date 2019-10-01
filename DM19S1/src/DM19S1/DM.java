package DM19S1;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Project DM19S1
 * @Package DM19S1
 * @Author Ryan<ywan3120 @ uni.sydney.edu.au>
 * @Date 26/04/2019
 */

public class DM {

    public static void main(String[] args) throws IOException, ParseException {
        if(args.length == 4){
            String record = IO.readFile(args[0]);
            ArrayList<HashMap<String,String>> list = IO.getMembers(record);
            String instruction = IO.readFile(args[1]);
            ArrayList<Donator> donators = new ArrayList<Donator>();
            for(HashMap<String,String> h:list){
                if(IO.isValid(h)){
                    donators.add(new Donator(h));
                }
            }
            String[] instructions = IO.getInstruction(instruction);
            Process ps = new Process(donators);
            ps.executeInstructions(instructions);
            String results = IO.getResults(donators);
            IO.writeFile(args[2],results);
            String report = ps.getReport();
            IO.writeFile(args[3],report);

        }

    }
}

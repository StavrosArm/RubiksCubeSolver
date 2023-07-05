import java.util.ArrayList;
import java.util.Collections;

public class Main
{
    public static void main(String[] args)
    {
        State initialState = new State(5, false);
        initialState.printCube();
        // our initial 3x3 state.
        SpaceSearcher searcher = new SpaceSearcher();
        long start = System.currentTimeMillis();
        State terminalState = searcher.Astar(initialState);  // we pick an algorithm to find our solution.
        long end = System.currentTimeMillis();
       
			// print the path from beggining to start.
            State temp = terminalState; // begin from the end.
            ArrayList<State> path = new ArrayList<>();
			path.add(terminalState);
            while(temp.getFather() != null) // if father is null, then we are at the root.
            {
                path.add(temp.getFather());
                temp = temp.getFather();
            }
			// reverse the path and print.
			System.out.println(path.size()-1+" κινήσεις");
			System.out.println("");
            Collections.reverse(path); 
            for(State item: path)
            {
                item.printCube();
            }
            System.out.println();
            System.out.println("Search time:" + (double)(end - start) / 1000 + " sec.");  // total time of searching in seconds.
        
    }
}
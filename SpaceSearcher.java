import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;


public class SpaceSearcher
{
    private ArrayList<State> frontier;
    private ArrayList<State> closedSet;
    

    SpaceSearcher()
    {
        this.frontier = new ArrayList<State>();
        this.closedSet = new ArrayList<State>();
    }
    
    State Astar(State initialState)
    {
    	 if(initialState.isFinal()) return initialState;
         // step 1: put initial state in the frontier.
         this.frontier.add(initialState);
         // step 2: check for empty frontier.
         while(this.frontier.size() > 0)
         {
             // step 3: get the first node out of the frontier.
             State currentState = this.frontier.remove(0);
             // step 4: if final state, return.
             if (currentState.isFinal())
             {
                 return currentState;
             }
             // step 5: if the node is not in the closed set, put the children at the START of the frontier (stack).
             // else go to step 2.
             if(!this.closedSet.contains(currentState))
             {
                 this.closedSet.add(currentState);
                 this.frontier.addAll(0, currentState.getChildren()); // first argument denotes position of adding.
                 Collections.sort(this.frontier);
             }

         }

         return null;
    }

}

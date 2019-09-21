package validators;

import java.util.*;

public class AcyclicGraphValidator implements AcyclicGraphValidatorIfc<Integer[]> {
    private Map<Integer, Set<Integer>> generateMap(Integer[] graph){
        Map<Integer, Set<Integer>> map = new HashMap<>();

        for(int i=0; i<graph.length+1; ++i) {
            map.put(i, new HashSet<>());
        }
        for(int i=0; i<graph.length; ++i){
            map.get(i+1).add(graph[i]);
        }
        return map;
    }
    @Override
    public Boolean isGraphAcyclic(Integer[] graph) {
        Map<Integer, Set<Integer>> map = generateMap(graph);
        int maxDegree = 0;
        int maxNode = 0;
        for (Map.Entry<Integer, Set<Integer>> entry : map.entrySet()) {
            if (entry.getValue().size() > maxDegree){
                maxDegree = entry.getValue().size();
                maxNode = entry.getKey();
            }
        }

        Set<Integer> visited = new HashSet<>();
        Stack<Integer> toBeVisited = new Stack<>();
        toBeVisited.push(maxNode);

        while(!toBeVisited.isEmpty()){
            Integer node = toBeVisited.pop();
            boolean isCyclic = map.get(node).stream().anyMatch(n->visited.contains(n));
            if (isCyclic) {
                return false;
            }
            map.get(node).forEach(n-> toBeVisited.push(n));
            visited.add(node);
        }

        return true;
    }
}

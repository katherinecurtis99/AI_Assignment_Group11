package agent;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Scanner;
import agent.TreeNode;
public class Tree<T> {
    private T coordinate;
    private List<Tree<T>> children;

    private Tree(T coordinate) {
        this.coordinate = coordinate;
        this.children = new ArrayList<>();
    }

    public static <T> Tree<T> of(T coordinate) {
        return new Tree<>(coordinate);
    }

    public Tree<T> addChild(T value) {
        Tree<T> newChild = new Tree<>(value);
        children.add(newChild);
        return newChild;
    }
    
    
    
  //search method
  	public static <T> Optional<Tree<T>> search(T value, Tree<T> root) {
  	  
  		//add root node to queue
  		Queue<Tree<T>> queue = new ArrayDeque<>();
  		queue.add(root);
  	
  		//loop while queue is not empty, each time popping out node from queue:
  		while(!queue.isEmpty()) {
  			Tree<T> currentNode = queue.remove();
  			if (currentNode.getCoordinate().equals(value)) {
  			    return Optional.of(currentNode);
  			} else {
  			    queue.addAll(currentNode.getChildren());
  			}

  	}
  		return Optional.empty();
  }
}


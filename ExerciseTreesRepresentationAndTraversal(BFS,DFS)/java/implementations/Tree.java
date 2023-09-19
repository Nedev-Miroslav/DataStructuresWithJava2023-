package implementations;

import interfaces.AbstractTree;

import java.util.*;
import java.util.stream.Collectors;

public class Tree<E> implements AbstractTree<E> {

    private E key;
    private Tree<E> parent;
    private List<Tree<E>> children;

    public Tree(E key, Tree<E>... children) {

        this.key = key;
        this.children = new ArrayList<>();
        this.children.addAll(Arrays.asList(children));

        for (int i = 0; i < children.length; i++) {
            children[i].setParent(this);

        }


    }


    @Override
    public void setParent(Tree<E> parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(Tree<E> child) {
        this.children.add(child);
    }

    @Override
    public Tree<E> getParent() {
        return this.parent;
    }

    @Override
    public E getKey() {
        return this.key;
    }

    @Override
    public String getAsString() {
        StringBuilder sb = new StringBuilder();

        traverseTreeWithRecursion(sb, 0, this);


        return sb.toString().trim();
    }

    private void traverseTreeWithRecursion(StringBuilder sb, int ident, Tree<E> tree) {
        sb.append(this.getPadding(ident))
                .append(tree.getKey())
                .append(System.lineSeparator());

        for (Tree<E> child : tree.children) {
            traverseTreeWithRecursion(sb, ident + 2, child);
        }

    }


    private String getPadding(int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }


    @Override
    public List<E> getLeafKeys() {
        return traverseWithBFS()
                .stream()
                .filter(tree -> tree.children.size() == 0)
                .map(Tree::getKey)
                .collect(Collectors.toList());

    }

    public List<Tree<E>> traverseWithBFS() {
        Deque<Tree<E>> queue = new ArrayDeque<>();

        queue.offer(this);

        List<Tree<E>> allNodes = new ArrayList<>();

        while (!queue.isEmpty()) {
            Tree<E> tree = queue.poll();

            allNodes.add(tree);

            for (Tree<E> child : tree.children) {
                queue.offer(child);
            }
        }


        return allNodes;
    }


    @Override
    public List<E> getMiddleKeys() {
        List<Tree<E>> allNodes = new ArrayList<>();

        this.traverseTreeWithRecursion(allNodes, this);

        return allNodes.stream()
                .filter(tree -> tree.parent != null && tree.children.size() > 0)
                .map(Tree::getKey)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private void traverseTreeWithRecursion(List<Tree<E>> collection, Tree<E> tree) {

        collection.add(tree);

        for (Tree<E> child : tree.children) {
            traverseTreeWithRecursion(collection, child);
        }

    }


    @Override
    public Tree<E> getDeepestLeftmostNode() {

        List<Tree<E>> trees = this.traverseWithBFS();

        int maxPath = 0;
        Tree<E> leafToReturn = null;

        for (Tree<E> tree : trees) {
            if (tree.isLeaf()) {

                int currentPath = getStepsFromLeafToRoot(tree);

                if (currentPath > maxPath) {
                    maxPath = currentPath;
                    leafToReturn = tree;
                }

            }
        }

        return leafToReturn;


    }


    private int getStepsFromLeafToRoot(Tree<E> tree) {

        int counter = 0;

        Tree<E> current = tree;

        while (current.parent != null) {
            counter++;

            current = current.parent;

        }

        return counter;

    }

    private boolean isLeaf() {
        return this.parent != null && this.children.size() == 0;

    }

    @Override
    public List<E> getLongestPath() {
        List<Tree<E>> trees = this.traverseWithBFS();
        int maxPath = 0;
        List<E> toReturn = new ArrayList<>();

        for (Tree<E> tree : trees) {
            if (tree.isLeaf()) {

                int currentPath = getStepsFromLeafToRoot(tree);

                if (currentPath > maxPath) {
                    maxPath = currentPath;
                    toReturn.clear();
                    toReturn = getAllElementFromRootToLeaf(tree);
                }

            }
        }


        Collections.reverse(toReturn);
        return toReturn;

    }

    private List<E> getAllElementFromRootToLeaf(Tree<E> tree) {

        int counter = 0;
        List<E> toReturnList = new ArrayList<>();

        Tree<E> current = tree;

        while (current != null) {
            counter++;
            toReturnList.add(current.key);

            current = current.parent;

        }
        return toReturnList;
    }


    @Override
    public List<List<E>> pathsWithGivenSum(int sum) {
        List<Tree<E>> trees = this.traverseWithBFS();


        List<List<E>> toReturn = new ArrayList<>();

        for (Tree<E> tree : trees) {
            if (tree.isLeaf()) {

                List<E> currentPath = getAllPathWithSum27(tree);

                int sumForEachPath = 0;

                for (E element : currentPath) {
                    sumForEachPath += (int) element;
                }

                if (sumForEachPath == sum) {

                    Collections.reverse(currentPath);
                    toReturn.add(currentPath);
                }

            }
        }
        return toReturn;
    }


    private List<E> getAllPathWithSum27(Tree<E> tree) {


        List<E> toReturnList = new ArrayList<>();

        Tree<E> current = tree;

        while (current != null) {

            toReturnList.add(current.key);

            current = current.parent;

        }

        return toReturnList;


    }


    @Override
    public List<Tree<E>> subTreesWithGivenSum(int sum) {
        List<Tree<E>> trees = new ArrayList<>();

        this.traverseTreeWithRecursion(trees, this);

        List<Tree<E>> toReturn = new ArrayList<>();

        for (Tree<E> tree : trees) {

            int currentSum = 0;

            currentSum += (int) tree.getKey();
            for (Tree<E> child : tree.children) {
                currentSum += (int) child.getKey();

                List<Tree<E>> childToChild = child.children;
                for (Tree<E> eTree : childToChild) {
                    currentSum += (int) eTree.key;
                }
            }


            if (currentSum == sum) {
                toReturn.add(tree);
            }

        }
        return toReturn;
    }


}



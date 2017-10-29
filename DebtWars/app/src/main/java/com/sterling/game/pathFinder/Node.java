package com.sterling.game.pathFinder;

import java.util.Set;


public interface Node<T> {
        /**
         * The heuristic cost to move from the current node to the goal. In most
         * cases this is the Manhattan distance or Chebyshev distance.
         *
         * @param goal
         * @return
         */
        double getHeuristic(T goal);

        /**
         * The cost of moving from the current node to the neighbor. In most cases
         * this is just the distance between the nodes, but additional terrain cost
         * can be added as well (for example climbing a mountain is more expensive
         * than walking on a road).
         *
         * @param neighbor
         *            Neighbour of current node
         * @return Traversal cost
         */
        double getTraversalCost(T neighbor);

        /**
         * Gets the set of neighboring nodes.
         *
         * @return Neighboring nodes
         */
        Set<T> getNeighbors();
}

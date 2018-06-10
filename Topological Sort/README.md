# Topological Sort

This is my code for a topological sort created in java. It uses a node and linked list to keep track of information about the problem and it prints up to the first 25 topological orderings it can find, and then it yields the total number of permutations as a check to prove the programs correctness for problems too large to check manually.

# Description of Problem

## Explanation/Example from https://www.geeksforgeeks.org/topological-sorting/

Topological sorting for Directed Acyclic Graph (DAG) is a linear ordering of vertices such that for every directed edge uv, vertex u comes before v in the ordering. Topological Sorting for a graph is not possible if the graph is not a DAG. 

![Image](example.png)

For example, a topological sorting of the following graph is “5 4 2 3 1 0”. There can be more than one topological sorting for a graph. For example, another topological sorting of the following graph is “4 5 2 3 1 0”. The first vertex in topological sorting is always a vertex with in-degree as 0 (a vertex with no in-coming edges).

# Description of Solution

My solution to the topological sorting problem utilizes 2 separate linked lists to keep track of the current sorted order, and the nodes still yet to be added to the order. It also utilizes an array of linked lists (one for each node) to keep track of which nodes have successors that come after them, and an array of how many predecessors each node currently has.

My solution iterates from the lowest possible digit it could select by seeing which nodes are in the potential nodes list (have 0 predecessors), then it selects that node, removes it from the potential list and adds it to the sorted order list. When we add a node from the potential list to the sorted list, we access the linked list of successors of the node we just added and decrease the predecessor count for any nodes that came after it. If the predecessor count was reduced to 0 as a result, then the node is added into the list of potential nodes. 

This is continued recursively until there are no nodes remaining, and this sorted order is printed out. Once all the nodes are in the list, we need to backtrack and explore different permutations. This is done by taking the node we just added into the potential array, and moving it back, increasing the predecessor counts of any nodes that come after it, and potentially removing anything from the potential nodes list if their predecessor count increases to be greater than 0.

My algorithm reads in a specifically formatted string to initialize the problem, for the example image above, the string would be: "6\n 52\n 50\n 40\n 41\n 23\n 31". The first number represents the number of nodes the problem should contain (where the first node is node 0, node 1, node 2, ..., node n up to n = 10), every number after the first represents an edge from the first digit to the second digit. The number 50 represents that node 5 has an edge to node 0 (since nodes are 0 indexed).

# Results

The results for the example problem are:

![Image](results.png)

These results make sense. In the given example, the only 2 nodes that could come first are 4 and 5 since they both have no predecessors/no incoming edges/an in degree of 0. When we select 4 as the incoming node, nodes 0 and 1 both have their predecessor count reduced by 1. This reduces node 1's to a predecessor count from 2 to 1 and node 0's from 2 to 1. This did not add any new nodes to the potential list, so after selecting 4, the only option available is to select node 5. When 5 is selected after 4, both 2 and 0 are added to the potential list. When 3 is selected 1 is added to the potential list. In this way we can see in the sample space where 4 is selected first, that the only node directly after 4 is 5, and then after 5, 0 can come anywhere, and 2 comes before 3 which comes before 1.

The analysis for when 5 is selected first is similar but much longer. Overall I am confident in the correctness of the solutions my program offers. This is because when we enter a problem with a number of nodes, n, and no edges between any of these nodes the potential topological sorts end up being the same as n! And this holds in my program where n = 4 yields 24 and n = 5 yiels 120. I have not proved this conclusion but it should be easy to verify.

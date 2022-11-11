import numpy as np

kek = 7


class Node:
    def __init__(self, height_of_tower, parent_node):
        self.height_of_tower = height_of_tower
        self.parent_node = parent_node

        self.state = np.array([np.arange(1, height_of_tower+1), np.zeros(height_of_tower, dtype = int), np.zeros(height_of_tower, dtype = int)])

    def is_goal_state(self):
        goal = np.array([np.zeros(kek, dtype = int), np.zeros(kek, dtype = int), np.arange(1, kek+1)])
        return np.array_equal(self.state, goal)




    def get_child_nodes(self):
        orig = Node(height_of_tower = self.height_of_tower, parent_node = self)
        list_of_children = []
        store_of_val = 0
        orig.state = self.state.copy()
        lst = get_valid_actions(self)
        for i in range(0, len(lst)):
            orig.state = self.state.copy()
            store_of_val = orig.state[lst[i][0][0]][lst[i][0][1]]
            orig.state[lst[i][0][0]][lst[i][0][1]] = orig.state[lst[i][1][0]][lst[i][1][1]]
            orig.state[lst[i][1][0]][lst[i][1][1]] = store_of_val
            child = Node(height_of_tower = orig.height_of_tower, parent_node = self)
            child.state = orig.state.copy()
            list_of_children.append(child)


        return list_of_children

    def __eq__(self, other):
        return np.array_equal(self.state, other.state)


    def __hash__(self):
        return hash(tuple(self.state))




    def __str__(self):
        empt_str = ""
        for i in range(0, self.height_of_tower):
            for j in range(0, 3):
                if self.state[j][i] != 0:
                    empt_str += str(self.state[j][i])
                    empt_str += " "
                else:
                    empt_str += "|"
                    empt_str += " "
            empt_str += '\n'
        return empt_str




def get_valid_actions(Node):
    actions = []
    valid_actions = []
    appendable_list = []
    for i in range(0, 3):
        for j in range(0, Node.height_of_tower):
            if Node.state[i][j] != 0:
                for k in range (0, 3):
                    for l in range (0, Node.height_of_tower):
                        if Node.state[k][l] == 0 and l == Node.height_of_tower - 1:
                            appendable_list.append([i, j])
                            appendable_list.append([k, l])
                            valid_actions.append(appendable_list)
                            appendable_list = []
                        elif Node.state[k][l] == 0 and Node.state[k][l+1]>Node.state[i][j]:
                            appendable_list.append([i, j])
                            appendable_list.append([k, l])
                            valid_actions.append(appendable_list)
                            appendable_list = []
                        appendable_list = []
                break
    
    return valid_actions



def find_path(Node):

    if isinstance(Node.parent_node, str) and Node.parent_node == "initial":
        print(Node)
        return Node.parent_node
    else:
        find_path(Node.parent_node)
        print(Node)

class Game:
    def __init__(self, height):
        self.initial_node = Node(height_of_tower = height, parent_node = "initial")


    def breadth_first_search(self):
        if self.initial_node.is_goal_state() == True:
            return self.initial_node

        frontier = []
        frontier.append(self.initial_node)
        explored = []

        while True:
            if len(frontier) == 0:
                return "Failure"
            node = frontier.pop()
            explored.append(node)
            print("Exploring node:\n")
            print(node)
            lst_of_children = node.get_child_nodes()
            for child in lst_of_children:
                if child.is_goal_state() == True:
                        return child
                if child not in explored and child not in frontier:
                    
                    frontier.append(child)


g = Game(height = kek)
goal_state = g.breadth_first_search()
print("Found goal state:\n")
print(goal_state)


print("PATH:\n")


print(find_path(goal_state))


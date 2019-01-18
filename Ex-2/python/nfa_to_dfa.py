from nfa_utilities import NFA, DFA

epsilon = 'epsilon'
all_states = []
all_names = []
class State:
	def __init__(self, name):
		self.nodes = []
		self.name = name
	
	def add_node(self, node):
		self.nodes.extend([node])
		self.nodes = list(set(self.nodes))

	def add_nodes(self, nodes):
		self.nodes.extend(nodes)
		self.nodes = list(set(self.nodes))


	def extend(self, state):
		self.nodes.extend(state.nodes)
		self.nodes = list(set(self.nodes))

	def __str__(self):
		#return 'Nodes: {}'.format(self.nodes)
		return self.name

	def __eq__(self, state):
		for n in state.nodes:
			if n not in self.nodes:
				return False
		if(len(state.nodes)==len(self.nodes)):
			return True
		else:
			return False

	def __len__(self):
		return len(self.nodes)

	def __lt__(self, other):
		return int(self.name[1:]) < int(other.name[1:])

def epsilon_closure(nfa, state):
	if(len(state)==0):
		return state
	global epsilon
	name = int(state.name[1:])+1
	while(name in all_names):
		name += 1
	all_names.extend([name])
	new_state = State('s{}'.format(name))
	for node in state.nodes:
		edges = nfa.find_edges_from(node)
		for e in edges:
			if(e.label == epsilon):
				new_state.add_node(e.t)
	new_state.extend(epsilon_closure(nfa, new_state))
	new_state.extend(state)
	return new_state

def closure(nfa, state, inp):
	i = 1
	name = int(state.name[1:])+i
	while(name in all_names):
		i+=1
		name = int(state.name[1:])+i
	all_names.extend([name])
	new_state = State('s{}'.format(name))
	for i in state.nodes:
		edges = nfa.find_edges_from(i)
		for j in edges:
			if j.label == inp:
				new_state.add_node(j.t)
	return new_state

		
def nfa_to_dfa(nfa):
	global all_states
	dfa = DFA()
	processed = []
	s0 = State('s0')
	s0.add_node(0)
	all_states = []
	all_states.extend([s0])
	all_names.extend(['s0'])
	k = 0
	while(True):
		for i in all_states:
			if i in processed:
				continue
			temp1 = epsilon_closure(nfa, i)
			for j in nfa.get_alphabet():
				temp2 = closure(nfa, temp1, j)
				temp2 = epsilon_closure(nfa, temp2)
				if temp2 not in all_states:
					all_states.extend([temp2])
					dfa.add_edge(i, temp2, j)
					#dfa = dfa + i.name + ' ' + j + ' ' + temp2.name + '\n'
				else:
					ind = all_states.index(temp2)
					dfa.add_edge(i, all_states[ind], j)
					#dfa = dfa + i.name + ' ' + j + ' ' + all_states[ind].name + '\n'
			processed.extend([i])
		
			
			"""print([[k.name, str(k)] for k in all_states])
			print([[k.name, str(k)] for k in processed])"""
		if(len(all_states) == len(processed)):
			break
	return dfa	
			
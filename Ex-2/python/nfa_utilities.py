class Edge:
	def __init__(self, from_node, to_node, label):
		self.f = from_node
		self.t = to_node
		self.label = label

	def __str__(self):
		return '{} {} {}'.format(self.f, self.label, self.t)

	def increase_nodes_by(self, diff):
		self.f+=diff
		self.t+=diff

	def __lt__(self, other):
		return self.f < other.f

class FA:
	def __init__(self):
		self.edges = []
		self.nodes = []
		self.alphabets = []

	def add_edge(self, f, t, label):
		self.edges.extend([Edge(f, t, label)])
		self.nodes.extend([f, t])
		#self.nodes = list(set(self.nodes))
		if(label != 'epsilon'):
			self.alphabets.extend([label])
		#self.alphabets = list(set(self.alphabets))


	def get_last_node(self):
		return max(self.nodes) if len(self.nodes)!=0 else 0

	def __str__(self):
		self.edges.sort()
		return str('\n'.join([str(i) for i in self.edges]))

	def change_start_node(self, val):
		diff = (val - min(self.nodes)) if len(self.nodes) != 0 else 0
		for edge in self.edges:
			edge.increase_nodes_by(diff)
		for i in range(len(self.nodes)):
			self.nodes[i]+=diff
		
	def extend(self, nfa):
		last_node = self.get_last_node()
		nfa.change_start_node(last_node)
		self.edges.extend(nfa.edges)
		self.nodes.extend(nfa.nodes)
		self.alphabets.extend(list(set(nfa.alphabets)))

	def find_edges_from(self, node):
		edges = []
		for edge in self.edges:
			if edge.f == node:
				edges.extend([edge])
		return edges
	
	def get_alphabet(self):
		return self.alphabets

def add_star(nfa, prev_char, node):
	nfa.add_edge(node, node+1, 'epsilon')
	node+=1
	nfa.add_edge(node, node+1, prev_char)
	node+=1
	nfa.add_edge(node, node-1, 'epsilon')
	nfa.add_edge(node, node+1, 'epsilon')
	node+=1
	nfa.add_edge(node-3, node, 'epsilon')
	return node

def add_star_to_nfa(nfa):
	nfa.change_start_node(1)
	last_node = nfa.get_last_node()
	nfa.add_edge(0, 1, 'epsilon')
	nfa.add_edge(last_node, last_node+1, 'epsilon')
	nfa.add_edge(last_node, 1, 'epsilon')
	nfa.add_edge(0, last_node+1, 'epsilon')
	return nfa


class NFA(FA):
	pass

class DFA(FA):
	def arrange_state_names(self):
		cnt = 0
		conv_dict = {0:0}
		for s in self.edges:
			n = int(s.f.name[1:])
			if n in conv_dict.keys():
				s.f.name = s.f.name[0]+str(conv_dict[n])
			else:
				cnt+=1
				s.t.name = s.t.name[0]+str(cnt)
				conv_dict[n] = cnt
			n = int(s.t.name[1:])
			if n in conv_dict.keys():
				s.t.name = s.t.name[0]+str(conv_dict[n])
			else:
				cnt+=1
				s.t.name = s.t.name[0]+str(cnt)
				conv_dict[n] = cnt

def build_nfa(exp):
	nfa = NFA()
	node = 0
	or_flag = False
	for index, i in enumerate(exp):
		if(index != len(exp)-1):
			if(exp[index+1] == '*'):
				node = add_star(nfa, i, node)
				continue
		if(i == '*'):
			continue
		if(i != '|' and not or_flag):
			nfa.add_edge(node, node+1, i)
			node += 1
		elif(i != '|' and or_flag):
			nfa.add_edge(node-1, node, i)
			or_flag = False
		elif(i == '|'):
			or_flag = True
	return nfa

def generate_nfa(exp):
	sub_exp = ''
	cur_nfa = build_nfa('')
	for index, c in enumerate(exp):
		if(c=='(' or c==')'):
			temp_nfa = build_nfa(sub_exp)
			if(index!=len(exp)-1 and c==')'):
				if(exp[index+1]=='*'):
					temp_nfa = add_star_to_nfa(temp_nfa)
			if(sub_exp!=''):
				cur_nfa.extend(temp_nfa)
				sub_exp = ''
			continue
		sub_exp += c
	return cur_nfa

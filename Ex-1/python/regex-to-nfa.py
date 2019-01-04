from nfa_utilities import generate_nfa

exp = input('Enter regex (Enclose entire expression within brackets): ')
exp = ''.join(exp.split(' '))
nfa = generate_nfa(exp)
print(nfa)

from nfa_utilities import generate_nfa
from nfa_to_dfa import nfa_to_dfa

nfa = generate_nfa(input('Enter Regex (Enclose Reges in brackets): '))
print('--\nNFA\n--')
print(nfa)
print('--\nDFA')
print(nfa_to_dfa(nfa))
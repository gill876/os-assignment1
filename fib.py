"""Fibonacci python examples from New Assignment sheet"""
def fib1():
    a = 0
    b = 1
    n = 10
    counter = 0
    while (counter < n):
        print(a)
        a = a + b
        b = a - b
        counter = counter + 1

def fib2():
    a = 0
    b = 1
    while (a >= 0):
        print(a)
        a = a + b
        b = a - b

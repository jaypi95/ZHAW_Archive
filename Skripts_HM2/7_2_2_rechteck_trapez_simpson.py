# -*- coding: utf-8 -*-
"""
Created on Tue Jun  7 11:26:15 2022

@author: vonwareb blyat
"""

import numpy as np


'''
Summierte Rechteck, Trapez und Simpsonformel
Achtung: sobald bei einer summierten Formel mit n gerechnet wird, handelt es sich um äquidistante Unterteilungen.
n = anzahl iteration
h = schrittweite
'''
def sum_rechteck(f, a, b, n=None, h=None, print_steps=True):
    if n is None and h is None: raise Exception("Either n or h needs to be defined")
    if h is None:
        h = (b - a) / n
    else:
        n = (b - a) / h
    x = np.linspace(a, b, round(n + 1))
    print('x:', x)

    if print_steps:
        # do the calculations without using numpy.sum
        rf = 0
        for i in range(len(x) - 1):
            rf += f(x[i])
            rf += f(x[i+1])
        rf *= h/2
        return rf
    rf = h * np.sum(f(x[:-1] + h/2))
    return rf


"""
Funktioniert nur für tabellierte äquidistanze Intervalle, da abhängig von n
"""
def sum_trapez(f, a, b, n=None, h=None):
    if n is None and h is None: raise Exception("Either n or h needs to be defined")
    if h is None:
        h = (b - a) / n
    else:
        n = (b - a) / h
    x = np.linspace(a, b, round(n + 1)) #weil n Anzahl Intervalle entspricht, brauchts n+1
    tf = h * ((f(a) + f(b))/2 + np.sum(f(x[1:-1])))
    return tf


def sum_simpson(f, a, b, n=None, h=None):
    if n is None and h is None: raise Exception("Either n or h needs to be defined")
    if h is None:
        h = (b - a) / n
    else:
        n = (b - a) / h
    x = np.linspace(a, b, round(n+1))
    #Simpsonregel: h/3 * (1/2 * f(a) + Summe von f(x1) bis f(xn-1) + 2 * Summe von f(x_mittelpunkt) + 1/2 * f(b))
    sf =  h/3 * (0.5 * (f(a) + f(b)) + np.sum(f(x[1:-1])) + 2 * np.sum(f((x[1:] + x[:-1]) * 0.5)))
    return sf

#Funktion
#f = lambda t: 2000 * np.log( 10000 / (10000 - 100 * t) ) - 9.8 * t
f = lambda t: np.cos(t)


a = 0 # inclusive
b = np.pi # inclusive
n = 19

integral_rf = sum_rechteck(f, a, b, n=n)
print(f'Integral über Rechtecksregel: {integral_rf}')

integral_tf = sum_trapez(f, a, b, h=10)
print(f'Integral über Trapezregel: {integral_tf}')

integral_sf = sum_simpson(f, a, b, n)
print(f'Integral über Simpsonregel: {integral_sf}')

#manuell integriert siehe pdf: t = 20 * (-np.sqrt(20) + np.sqrt(5))
integral_exact = 20 * (-1/np.sqrt(20) + 1/np.sqrt(5))
print(f'Integral manuell exakt: {np.log(2)}')



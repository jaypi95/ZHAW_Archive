import os
import urllib.parse
from rich.console import Console
from rich.table import Table


def encodeURL(stringToParse):
    """
    Encodes the passed string to URL characters
    :param stringToParse:
    :return: encoded string
    """
    return urllib.parse.quote(stringToParse)


def getCompatibleConsole():
    """
    Returns a compatible console object. It ensures that colors work on host OS.
    :return:
    """
    console = ""
    if os.name == 'nt':
        console = Console(color_system="windows")
    else:
        console = Console(color_system="auto")
    return console


def compareArraySizes(array1, array2):
    """
    Compare the sizes of two arrays. Throw an error if they are not the same.
    """
    if array1.shape != array2.shape:
        raise ValueError("Arrays must have the same shape.")

    return True

def convertToSub(numbers):
    SUB = str.maketrans("0123456789", "₀₁₂₃₄₅₆₇₈₉")
    return str(numbers).translate(SUB)

def convertToSup(numbers):
    SUP = str.maketrans("0123456789", "⁰¹²³⁴⁵⁶⁷⁸⁹")
    return str(numbers).translate(SUP)